package com.authentication.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authentication.dto.DeletedUsersDto;
import com.authentication.entity.DeletedUsers;
import com.authentication.repository.DeletedUsersRepository;
import com.authentication.service.IDeletedUsersService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;


@Service
public class DeletedUsersServiceImpl implements IDeletedUsersService{
	
	@Autowired
	private DeletedUsersRepository deletedUsersRepository;

	@Override
	public void createDeletedUsers(DeletedUsersDto deletedUsersDto) {

		DeletedUsers DeletedUsers=new DeletedUsers();
		ObjectEntityCheckutil.copyNonNullProperties(deletedUsersDto, DeletedUsers);
		deletedUsersRepository.save(DeletedUsers);
		
	}

	@Override
	public DeletedUsers fetchDeletedUsers(int id) {
	
		DeletedUsers deletedUsers = deletedUsersRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("deletedUsers", "id", id)
				);
		
		return deletedUsers;
		
	}

	@Override
	public boolean updateDeletedUsers(DeletedUsersDto deletedUsersDto) {
	
		DeletedUsers deletedUsers = deletedUsersRepository.findById(deletedUsersDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("deletedUsers", "id", deletedUsersDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(deletedUsersDto, deletedUsers);
		deletedUsersRepository.save(deletedUsers);
		
		return true;
	}

	@Override
	public boolean deleteDeletedUsers(int id) {

		deletedUsersRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("deletedUsers", "id", id)
				);
		deletedUsersRepository.deleteById(id);
		return true;
	}

	@Override
	public List<DeletedUsers> fetchAllDeletedUsers() {
		
		return deletedUsersRepository.findAll();
	}



}
