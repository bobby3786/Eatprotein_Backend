package com.authentication.dto;

import lombok.Data;

@Data
public class WalletTransactionDto {
	
	private int id;
	private int walletId;
	private int userId;
	private String amount;
	private String reason;
	private String refId;
	private String note;
	private String type;

}
