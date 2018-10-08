package com.test.repo.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.test.repo.IGenericDao;
 
@Repository 
@Transactional
public class GenericDaoImpl<T> implements IGenericDao<T> {

	@PersistenceContext
	EntityManager entityManager;
 
	@SuppressWarnings("unchecked")
	public T find(final T entity, String condition) {
		return (T) entityManager.createQuery("from " + entity.getClass().getName() + "  " + condition)
					.getSingleResult();
	}
 
	@SuppressWarnings("unchecked")
	public List<T> fetchAll(final T entity, String condition) {
		if (condition.isEmpty() || condition == null) {
			return (List<T>) entityManager.createQuery("from " + entity.getClass().getName()).getResultList();
		} else {
			return (List<T>) entityManager.createQuery("from " + entity.getClass().getName() + " " + condition)
					.getResultList();
		}

	}
 
	public void create(T entity) {
		entityManager.persist(entity);
	}
 
	public void update(T entity) {
		entityManager.merge(entity);
	}
 
	public void delete(T entity) {
		entityManager.remove(entity);
	}

}
