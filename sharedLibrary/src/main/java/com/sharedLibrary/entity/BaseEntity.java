package com.sharedLibrary.entity;


import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Data
public class BaseEntity {
	
	@Column(updatable = false,name = "created_at")
	@CreatedDate
	private LocalDateTime createAt;
	
	@Column(insertable = false,name = "updated_at")
	@LastModifiedDate
	private LocalDateTime updatedAt;
	
	@Column(name = "deleted_at")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime deletedAt;

	
	
	

}

