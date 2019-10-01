package com.ams.checkin.exception;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CheckInError
{
	private final String errorMessage;
	private List<String> errors = new ArrayList<String>();

	public void addCheckInValidationError(String error)
	{
		this.errors.add(error);
	}

}
