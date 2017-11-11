package com.cqrs.domain;

import java.util.UUID;

public class OrderItem {
	public final UUID filmId;
	public final int days;

	public OrderItem(UUID filmId, int days) {
		this.filmId = filmId;
		this.days = days;
	}
}
