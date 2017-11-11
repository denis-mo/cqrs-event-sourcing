package com.cqrs.command;

import com.cqrs.cqrs.Command;
import com.cqrs.cqrs.CommandType;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReturnOrderCommand extends Command {

	public final UUID orderId;
	public final LocalDateTime returnTime;

	public ReturnOrderCommand(UUID orderId) {
		this.orderId = orderId;
		this.returnTime = LocalDateTime.now();
	}

	public ReturnOrderCommand(UUID orderId, LocalDateTime returnTime) {
		this.orderId = orderId;
		this.returnTime = returnTime;
	}

	@Override
	public CommandType getType() {
		return CommandType.RETURN_ORDER;
	}
}
