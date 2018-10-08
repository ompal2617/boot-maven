package com.test.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.repo.IGenericDao;
import com.test.service.IGenericService;

@Service
public class GenericServiceImpl<T> implements IGenericService<T> {

	@Autowired
	private IGenericDao<T> iGenericDao;
 

	@Override
	public T find(final T entity,String condition) { 
		return iGenericDao.find(entity,condition);
	}
 
	@Override
	public List<T> fetchAll(final T entity,String condition) { 
		return iGenericDao.fetchAll(entity,condition);
	}

	@Override
	public void create(final T entity) {
		iGenericDao.create(entity);
	}

	@Override
	public void update(final T entity) {
		iGenericDao.update(entity);
	}

	@Override
	public void delete(final T entity) {
		iGenericDao.delete(entity);
	}

	
}
