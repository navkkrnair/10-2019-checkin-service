package com.ams.checkin.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ams.checkin.entity.CheckInRecord;
import com.ams.checkin.exception.CheckInError;
import com.ams.checkin.exception.CheckInErrorBuilder;
import com.ams.checkin.service.CheckinService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/checkin")
public class CheckInController
{
	private static final Logger logger = LoggerFactory.getLogger(CheckInController.class);

	private final CheckinService checkInService;

	@GetMapping("/{id}")
	public ResponseEntity<CheckInRecord> getCheckIn(@PathVariable String id)
	{
		logger.info(">> Finding CheckInRecord with id {}", id);
		Optional<CheckInRecord> cir = checkInService.getCheckInRecord(id);
		return cir.isPresent() ? ResponseEntity.ok(cir.get())
				: ResponseEntity.notFound()
					.build();
	}

	@PostMapping
	public ResponseEntity<?> checkIn(@Valid @RequestBody CheckInRecord checkIn, Errors errors)
	{
		if (errors.hasErrors())
		{
			return ResponseEntity.badRequest()
				.body(CheckInErrorBuilder.fromBindingErrors(errors));
		}

		logger.info(">> CheckIn in progress...");
		CheckInRecord record = checkInService.checkIn(checkIn);
		return record != null ? ResponseEntity.ok(record)
				: ResponseEntity.badRequest()
					.body(CheckInErrorBuilder.create()
						.withError("CkeckIn failed")
						.build());
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public CheckInError handleException(Exception e)
	{
		logger.info(">> Exception caught {}", e.getClass()
			.getTypeName());
		return CheckInErrorBuilder.create()
			.withError(e.getMessage())
			.build();

	}

}
