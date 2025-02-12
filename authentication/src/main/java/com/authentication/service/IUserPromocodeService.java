package com.authentication.service;

import java.util.List;

import com.authentication.dto.UserPromocodeDto;
import com.authentication.entity.UserPromocode;

public interface IUserPromocodeService {
	
	void createUserPromocode(UserPromocodeDto userPromocodeDto);
	UserPromocode fetchUserPromocode(int id);
	boolean updateUserPromocode(UserPromocodeDto userPromocodeDto);
	boolean deleteUserPromocode(int id);
	List<UserPromocode> fetchAllUserPromocodes();

}
