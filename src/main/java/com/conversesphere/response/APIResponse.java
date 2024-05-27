package com.conversesphere.response;

import lombok.Data;

@Data
public class APIResponse {
	private String message;
	private Boolean status;

	public APIResponse(String string, boolean b) {
		this.message = string;
		this.status = b;
	}
}
