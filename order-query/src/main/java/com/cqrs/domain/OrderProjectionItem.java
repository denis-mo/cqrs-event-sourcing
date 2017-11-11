package com.cqrs.domain;

import java.util.UUID;

public class OrderProjectionItem {

    public UUID filmId;

    public OrderProjectionItem(UUID filmId) {
        this.filmId = filmId;
    }

}
