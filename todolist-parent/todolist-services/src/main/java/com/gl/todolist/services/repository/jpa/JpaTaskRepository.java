package com.gl.todolist.services.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.gl.todolist.domain.Task;
import com.gl.todolist.domain.User;
import com.gl.todolist.services.repository.ITaskRepository;

@Repository
public class JpaTaskRepository implements ITaskRepository{
	
	@PersistenceContext
	private EntityManager em;
	
	public Task saveUpdateTask(Task task){
		return em.merge(task);
	}

	public void remove(Task task) throws EntityNotFoundException{
		em.remove(find(task.getId()));
	}

	public Task find(Long id) throws EntityNotFoundException{
		Task task = em.find(Task.class, id);
		if( task == null )
			throw new EntityNotFoundException();
		return task;
	}
	
	@SuppressWarnings("unchecked")
	public List<Task> findTasks(User user){
		Query q = em.createQuery("FROM tasks where user = :user");
        q.setParameter("user", user);
		List<Task> resultList = q.getResultList();
		return resultList;
	}

}
