package com.catalog.service;

import java.util.List;

import com.catalog.dto.UserRecentViewDto;
import com.catalog.entity.UserRecentView;

public interface IUserRecentViewService {
	
	void createUserRecentView(UserRecentViewDto userRecentViewDto);
	UserRecentView fetchUserRecentView(int id);
	boolean updateUserRecentView(UserRecentViewDto userRecentViewDto);
	boolean deleteUserRecentView(int id);
	List<UserRecentView> fetchAllUserRecentViews();

}
