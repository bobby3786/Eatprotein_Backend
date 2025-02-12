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
@Table(name = "wallet_transaction")
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WalletTransaction extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "wallet_id")
	private int walletId;
	
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "amount")
	private String amount;
	
	@Column(name = "reason")
	private String reason;
	
	@Column(name = "ref_id")
	private String refId;
	
	@Column(name = "note")
	private String note;
	
	@Column(name = "type")
	private String type;   //'CREDIT','DEBIT'x
	

}
