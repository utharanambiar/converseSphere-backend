package com.conversesphere.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Verification {

	private Boolean status;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private String plan;
}
