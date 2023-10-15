package com.restaurantebaratie.application.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.restaurantebaratie.application.entities.User;
import com.restaurantebaratie.application.repositories.UserRepository;
import com.restaurantebaratie.application.services.exceptions.DatabaseException;
import com.restaurantebaratie.application.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	public List<User> findAll(){
		return repository.findAll();
	}
	
	public User findById(Long id) {
		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow( () -> new ResourceNotFoundException(id));
	}

	public User insert(User obj) {
		return repository.save(obj);
	}
	
	public void delete(Long id) {
		try {
			if(!repository.existsById(id)) throw new ResourceNotFoundException(id);
			repository.deleteById(id);
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
		
		
		/* 
		 * Quando eu precisar capturar um RuntimeException, é só fazer isso:
		 * catch (RuntimeException e){
		 * 	e.printStackTrace();
		 *  }
		 * */
		
	}
	
	public User updateData(Long id, User obj) {
		
		User entity = repository.getReferenceById(id);
		updateData(entity, obj);
		return repository.save(entity);
		
	}

	private void updateData(User entity, User obj) {
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setPhone(obj.getPhone());
		
	}
	
}
