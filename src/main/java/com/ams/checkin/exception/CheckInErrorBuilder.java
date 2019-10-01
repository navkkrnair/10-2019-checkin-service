package com.ams.checkin.exception;

import org.springframework.validation.Errors;

public class CheckInErrorBuilder
{
	private static final CheckInErrorBuilder builder = new CheckInErrorBuilder();
	private String                           errorMessage;

	private CheckInErrorBuilder()
	{
	}

	public static CheckInErrorBuilder create()
	{
		return builder;
	}

	public CheckInErrorBuilder withError(String errorMessage)
	{
		this.errorMessage = errorMessage;
		return this;
	}

	public CheckInError build()
	{
		return new CheckInError(this.errorMessage);
	}

	public static CheckInError fromBindingErrors(Errors errors)
	{
		CheckInError error = new CheckInError("Validation failed. " + errors.getErrorCount()
				+ " errors(s)");
		errors.getAllErrors()
			.forEach(oe -> error.addCheckInValidationError(oe.getDefaultMessage()));
		return error;
	}
}
