package com.example.exceptionHandling;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
public class ErrorMessage {

	private int statusCode;
	private Date timestamp;
	private String message;
	private String description;
	
}
