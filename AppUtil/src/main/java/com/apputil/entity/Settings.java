package com.apputil.entity;

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
@Table(name = "settings")
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Settings extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "platform_fee")
	private double platformFee;
	
	@Column(name = "package_charge")
	private double packageCharge;
	
	@Column(name = "utility")
	private double utility;

}
