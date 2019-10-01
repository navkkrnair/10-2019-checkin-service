package com.ams.checkin.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ams.checkin.entity.CheckInRecord;
import com.ams.checkin.repository.CheckinRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CheckinService
{
	private static final Logger logger = LoggerFactory.getLogger(CheckinService.class);

	private final CheckinRepository checkinRepository;
	private final Sender            sender;

	public CheckInRecord checkIn(CheckInRecord checkIn)
	{
		checkIn.setCheckInTime(LocalDateTime.now());
		logger.info(">> Saving checkin");
		//save
		CheckInRecord cr = checkinRepository.save(checkIn);
		logger.info(">> Successfully saved checkin ");
		//send a message back to booking to update status
		logger.info(">> Sending booking id " + cr.getBookingId());
		sender.send(cr.getBookingId());
		return cr;

	}

	public Optional<CheckInRecord> getCheckInRecord(String id)
	{
		return checkinRepository.findById(id);
	}

}
