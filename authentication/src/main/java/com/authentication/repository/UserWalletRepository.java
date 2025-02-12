package com.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authentication.entity.UserWallet;

@Repository
public interface UserWalletRepository extends JpaRepository<UserWallet, Integer>{

}
