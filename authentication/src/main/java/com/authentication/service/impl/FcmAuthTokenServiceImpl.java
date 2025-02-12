package com.authentication.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authentication.dto.FcmAuthTokenDto;
import com.authentication.entity.FcmAuthToken;
import com.authentication.repository.FcmAuthTokenRepository;
import com.authentication.service.IFcmAuthTokenService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class FcmAuthTokenServiceImpl implements IFcmAuthTokenService{
	
	@Autowired
	private FcmAuthTokenRepository fcmAuthTokenRepository;

	@Override
	public void createFcmAuthToken(FcmAuthTokenDto fcmAuthTokenDto) {

		FcmAuthToken fcmAuthToken=new FcmAuthToken();
		ObjectEntityCheckutil.copyNonNullProperties(fcmAuthTokenDto, fcmAuthToken);
		fcmAuthTokenRepository.save(fcmAuthToken);
		
	}

	@Override
	public FcmAuthToken fetchFcmAuthToken(int id) {
	
		FcmAuthToken fcmAuthToken = fcmAuthTokenRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("fcmAuthToken", "id", id)
				);
		
		return fcmAuthToken;
		
	}

	@Override
	public boolean updateFcmAuthToken(FcmAuthTokenDto fcmAuthTokenDto) {
	
		FcmAuthToken fcmAuthToken = fcmAuthTokenRepository.findById(fcmAuthTokenDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("fcmAuthToken", "id", fcmAuthTokenDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(fcmAuthTokenDto, fcmAuthToken);
		fcmAuthTokenRepository.save(fcmAuthToken);
		
		return true;
	}

	@Override
	public boolean deleteFcmAuthToken(int id) {

		fcmAuthTokenRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("FcmAuthToken", "id", id)
				);
		fcmAuthTokenRepository.deleteById(id);
		return true;
	}

	@Override
	public List<FcmAuthToken> fetchAllFcmAuthTokens() {
		
		return fcmAuthTokenRepository.findAll();
	}



}
