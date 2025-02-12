package com.authentication.entity;

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
import com.sharedLibrary.entity.BaseEntity;
@Entity
@Table(name = "user")
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "referral_code")
	private String referralCode;
	
	@Column(name = "refer_by")
	private String referBy;
	
	@Column(name = "referrer_code")
	private String referrerCode;
	
	@Column(name = "super_user_id")
	private int superUserId;
	
	@Column(name = "fo_share")
	private double foShare;
	
	@Column(name = "radius")
	private int radius;
	
	@Column(name = "organization_id")
	private int organizationId;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "image_path")
	private String imagePath;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "role_id")
	private int roleId;
	
	@Column(name = "platform")
	private String platform;
	
	@Column(name = "notification_token")
	private String notificationToken;
	
	@Column(name = "os_type")
	private String osType;     //'ANDROID','IOS','WEB'
	
	@Column(name = "device_meta")
	private String deviceMeta;
	
	@Column(name = "status")
	private String status;    //'ACTIVE','BLOCKED','DISABLED'
	
	@Column(name = "db_latitude")
	private double dbLatitude;
	
	@Column(name = "db_longitude")
	private double dbLongitude;
	
	@Column(name = "db_status")
	private String dbStatus;   //'AVAILABLE','BUSY','OFF'
	
	@Column(name = "db_rating")
	private double dbRating;

}
