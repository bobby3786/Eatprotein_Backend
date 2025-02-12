package com.authentication.service;

import java.util.List;

import com.authentication.dto.FcmAuthTokenDto;
import com.authentication.entity.FcmAuthToken;

public interface IFcmAuthTokenService {
	
	void createFcmAuthToken(FcmAuthTokenDto fcmAuthTokenDto);
	FcmAuthToken fetchFcmAuthToken(int id);
	boolean updateFcmAuthToken(FcmAuthTokenDto fcmAuthTokenDto);
	boolean deleteFcmAuthToken(int id);
	List<FcmAuthToken> fetchAllFcmAuthTokens();

}
