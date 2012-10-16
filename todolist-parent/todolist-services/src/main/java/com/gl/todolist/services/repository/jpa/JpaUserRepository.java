package com.gl.todolist.services.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.gl.todolist.domain.User;
import com.gl.todolist.services.repository.IUserRepository;

@Repository
public class JpaUserRepository implements IUserRepository{
	
	@PersistenceContext
	private EntityManager em;
	
	public User saveUpdateUser(User user){
		return em.merge(user);
	}

	public void remove(User user) throws EntityNotFoundException{
		em.remove(find(user.getId()));
	}

	public User find(Long id) throws EntityNotFoundException{
		User user = em.find(User.class, id);
		if( user == null )
			throw new EntityNotFoundException();
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> findUsers(){
		List<User> resultList = em.createQuery("FROM User").getResultList();
		return resultList;
	}
	
	public User validateNameUser(User user){
		try{
			Query q = em.createQuery("SELECT user FROM User user WHERE user.name = :nameUser");
	        q.setParameter("nameUser", user.getName());
	        return (User) q.getSingleResult();
	  	}catch(NoResultException ex){
			return null;
		}
	}

	@Override
	public User find(String email, String password) throws EntityNotFoundException{
		try{
			Query q = em.createQuery("SELECT user FROM User user WHERE user.name = :nameUser and user.password = :password");
	        q.setParameter("nameUser", email);
	        q.setParameter("password", password);
	        User user = (User)q.getSingleResult();
	        if(user == null)
	        	throw new EntityNotFoundException();
	        return user;
	  	}catch(NoResultException ex){
			return null;
		}
	}

}