package com.test.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.entity.Role;
import com.test.exceptions.ResourceNotFoundException;
import com.test.service.IGenericService;
 
@RestController
@RequestMapping(value = "/role")
public class RoleController {

	@Autowired
	private IGenericService<Role> iGenericServicRole;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE) 
	public List<Role> getAllRole() throws ResourceNotFoundException {
		List<Role> listRoles = iGenericServicRole.fetchAll(new Role()," order by id DESC");
		if (null == listRoles || listRoles.isEmpty()) {
			throw new ResourceNotFoundException("No role content found!");
		}
		return listRoles;
	}

	@GetMapping("/{id}") 
	public Role getRoleById(Role role, @PathVariable String id) throws ResourceNotFoundException { 
		
		role = iGenericServicRole.find(new Role(), " where id = "+Integer.parseInt(id)+"");		
		if (null == role) { 
			throw new ResourceNotFoundException("Invalid Role id " + id + "!");
		} 
		return role;
	}

	@PostMapping() 
	public Role saveRole(@RequestBody @Valid Role role, Errors error) throws Exception {
		
		if (error.hasErrors()) {
			throw new Exception(error.getFieldError().getDefaultMessage().toString()); 
		} 		
		iGenericServicRole.create(role);
		return role; 
	}

	@PutMapping() 
	public Role updateRole(@RequestBody @Valid Role role, Errors error) throws Exception {
		if (error.hasErrors()) {
			throw new Exception(error.getFieldError().getDefaultMessage().toString()); 
		} 	
		
		iGenericServicRole.update(role);
		return role; 
	}

	@DeleteMapping("/{id}") 
	public Role deleteRole(Role role, @PathVariable String id) throws ResourceNotFoundException {
		
		role = iGenericServicRole.find(new Role()," where id = "+Integer.parseInt(id)+"");
		if (null == role) { 
			throw new ResourceNotFoundException("Invalid Role id " + id + "!");
		} 
		iGenericServicRole.delete(role);
		return role; 
	}

}
