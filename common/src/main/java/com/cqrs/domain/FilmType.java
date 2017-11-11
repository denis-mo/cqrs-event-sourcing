package com.cqrs.domain;

public enum FilmType {

    NEW, REGULAR, OLD;

    private static final int BASIC_PRICE = 30;
    private static final int PREMIUM_PRICE = 40;

    public long calculatePrice(int days) {
        switch (this) {
            case REGULAR:
                return days <= 3 ? BASIC_PRICE : (days - 2) * BASIC_PRICE;
            case OLD:
                return days <= 5 ? BASIC_PRICE : (days - 4) * BASIC_PRICE;
            case NEW:
            default:
                return days * PREMIUM_PRICE;
        }
    }

    public int bonusPoints() {
        switch (this) {
            case NEW:
                return 2;
            case REGULAR:
            case OLD:
            default:
                return 1;
        }
    }
}
