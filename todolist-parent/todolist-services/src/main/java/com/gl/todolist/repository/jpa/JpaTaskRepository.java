package com.gl.todolist.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gl.todolist.domain.Task;
import com.gl.todolist.domain.User;
import com.gl.todolist.repository.TaskRepository;

@Repository
@Transactional
public class JpaTaskRepository implements TaskRepository{
	
	@PersistenceContext
	private EntityManager em;
	
	public Task saveUpdateTask(Task task){
		return em.merge(task);
	}

	public void remove(Long id) throws EntityNotFoundException{
		try {
			em.createQuery("DELETE tasks where id = :taskId")
				.setParameter("taskId", id)
				.executeUpdate();
		} catch (Exception e) {
			throw new EntityNotFoundException(e.getMessage());
		}	
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
