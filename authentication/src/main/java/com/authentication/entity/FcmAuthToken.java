package com.authentication.entity;

import jakarta.persistence.Column;
import com.sharedLibrary.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "fcm_auth_token")
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FcmAuthToken extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "token")
	private String token;
	
	@Column(name = "expires_in")
	private double expiresIn;

}
