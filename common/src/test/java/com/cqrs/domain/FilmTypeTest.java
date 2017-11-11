package com.cqrs.domain;

import org.junit.Assert;
import org.junit.Test;

public class FilmTypeTest {

    @Test
    public void bonusPoints(){
        Assert.assertEquals(2, FilmType.NEW.bonusPoints());
        Assert.assertEquals(1, FilmType.OLD.bonusPoints());
        Assert.assertEquals(1, FilmType.REGULAR.bonusPoints());
    }

    @Test
    public void FilmTypeNew(){
        Assert.assertEquals(40, FilmType.NEW.calculatePrice(1));
        Assert.assertEquals(120, FilmType.NEW.calculatePrice(3));
    }

    @Test
    public void FilmTypeRegular(){
        Assert.assertEquals(30, FilmType.REGULAR.calculatePrice(1));
        Assert.assertEquals(30, FilmType.REGULAR.calculatePrice(2));
        Assert.assertEquals(60, FilmType.REGULAR.calculatePrice(4));
        Assert.assertEquals(120, FilmType.REGULAR.calculatePrice(6));
    }

    @Test
    public void FilmTypeOld(){
        Assert.assertEquals(30, FilmType.OLD.calculatePrice(1));
        Assert.assertEquals(30, FilmType.OLD.calculatePrice(4));
        Assert.assertEquals(60, FilmType.OLD.calculatePrice(6));
        Assert.assertEquals(150, FilmType.OLD.calculatePrice(9));
    }
}
