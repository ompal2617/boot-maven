package com.test.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.entity.Users;
import com.test.service.IGenericService;

@RestController 
@RequestMapping("/user")
public class UsersController {

	@Autowired
	private IGenericService<Users> iGenericServicUsers;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping()  
	public ResponseEntity<?> getUsersDetails(Users users) {
		List<Users> listUsers = iGenericServicUsers.fetchAll(users," order by id DESC");
		if (listUsers.isEmpty()) {
			return new ResponseEntity<String>("NO user details found!", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Users>>(listUsers, HttpStatus.OK);
	}
	
	
	@GetMapping("/{id}") 
	public ResponseEntity<?> getUserById(Users user,@PathVariable String id) { 
		try {
			user = iGenericServicUsers.find(new Users()," where id = "+Integer.parseInt(id)+"");
		} catch (NumberFormatException e) {
			return new ResponseEntity<String>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (user == null) {
			return new ResponseEntity<String>("Invalid user id "+id+"!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Users>(user, HttpStatus.OK);
	}

	@PostMapping() 
	public ResponseEntity<?> saveUser(@RequestBody @Valid Users user,Errors error) {
		if(error.hasErrors()) { 
			return new ResponseEntity<String>(error.getFieldError().getDefaultMessage().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		try { 
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			iGenericServicUsers.create(user);
			return new ResponseEntity<Users>(user, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@PutMapping() 
	public ResponseEntity<?> updateUser(@RequestBody @Valid Users user,Errors error) {
		if(error.hasErrors()) { 
			return new ResponseEntity<String>(error.getFieldError().getDefaultMessage().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		try { 
			iGenericServicUsers.update(user);
			return new ResponseEntity<Users>(user, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@DeleteMapping("/{id}") 
	public ResponseEntity<?> deleteUser(Users user,@PathVariable String id) { 
		try {
			user = iGenericServicUsers.find(new Users(),  " where id = "+Integer.parseInt(id)+"");
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(user == null) {
			return new ResponseEntity<String>("Invalid user id "+id+" !", HttpStatus.BAD_REQUEST);
		}
		iGenericServicUsers.delete(user);
		return new ResponseEntity<Users>(user, HttpStatus.GONE);
	}
  
}
