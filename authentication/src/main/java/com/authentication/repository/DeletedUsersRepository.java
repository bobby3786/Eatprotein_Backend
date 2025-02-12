package com.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authentication.entity.DeletedUsers;

@Repository
public interface DeletedUsersRepository extends JpaRepository<DeletedUsers, Integer>{

}
