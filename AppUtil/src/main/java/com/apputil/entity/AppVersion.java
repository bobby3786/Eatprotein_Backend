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
@Table(name = "app_version")
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AppVersion extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "android_version")
	private String androidVersion;
	
	@Column(name = "android_employee_version")
	private String androidEmployeeVersion;
	
	@Column(name = "ios_version")
	private String iosVersion;
	
	@Column(name = "ios_employee_version")
	private String iosEmployeeVersion;

	

}
