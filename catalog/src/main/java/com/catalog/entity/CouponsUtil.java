package com.catalog.entity;

import com.sharedLibrary.entity.BaseEntity;

import jakarta.persistence.Column;
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
@Table(name = "coupons_util")
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CouponsUtil extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "referral_percentage")
	private double referralPercentage;
	
	@Column(name = "referred_percentage")
	private double referredPercentage;
	
	@Column(name = "max_limit")
	private int maxLimit;
	
	@Column(name = "expire_days")
	private int expireDays;

}
