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
@Table(name = "unique_code")
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UniqueCode extends BaseEntity{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "ref_code")
	private int refCode;

}
