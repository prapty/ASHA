package com.thenewboston.project;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Prapty on 07-Jun-16.
 */
public class CardItemTest {
    private CardItem testCard;
    @Before
    public void setUp() throws Exception {
        testCard=new CardItem("test","/drawable",1);
    }

    @Test
    public void testToString() throws Exception {
        String str=testCard.toString();
        assertEquals(testCard.path,str);
    }

    @Test
    public void testGetCardName() throws Exception {
        String str=testCard.getCardName();
        assertEquals(testCard.cardName,str);
    }

    @Test
    public void testGetPath() throws Exception {
        String str=testCard.getPath();
        assertEquals(testCard.path,str);
    }
}