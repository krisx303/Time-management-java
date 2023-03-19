package com.relit.timemaangement.util;

import junit.framework.TestCase;

import org.junit.Before;

public class DateTest extends TestCase {

    Date date1, date2, date3;


    @Before
    public void setUp(){
        date1 = new Date(2, 3, 2023);
        date2 = new Date(15, 3, 2020);
        date3 = new Date(29, 2, 2022);
    }

    public void testDiff() {
        assertEquals(Date.diff(Date.DAY, date1, date2), 13);
        assertEquals(Date.diff(Date.DAY, date2, date3), 14);
        assertEquals(Date.diff(Date.DAY, date1, date3), 27);
        assertEquals(Date.diff(Date.MONTH, date1, date2), 0);
        assertEquals(Date.diff(Date.MONTH, date2, date3), -1);
        assertEquals(Date.diff(Date.MONTH, date1, date3), -1);
        assertEquals(Date.diff(Date.YEAR, date2, date1), 3);
        assertEquals(Date.diff(Date.YEAR, date3, date1), 1);
        assertEquals(Date.diff(Date.YEAR, date1, date1), 0);

    }

    public void testTestToString() {
        assertEquals(date1.toString(), "02/03/2023");
        assertEquals(date2.toString(), "15/03/2020");
        assertEquals(date3.toString(), "29/02/2022");
    }

    public void testIncreaseMonth() {
        assertEquals(date1.getMonth(), 3);
        date1.increaseMonth();
        assertEquals(date1.getMonth(), 4);
        for (int i = 0; i < 10; i++) {
            date1.increaseMonth();
            date2.increaseMonth();
        }
        assertEquals(date1.getMonth(), 2);
        assertEquals(date1.getYear(), 2024);
        assertEquals(date2.getMonth(), 1);
    }

    public void testIsBeforeMY() {
        assertTrue(date2.isBeforeMY(date1));
        assertTrue(date3.isBeforeMY(date1));
    }

    public void testIsSameMonth() {
        assertFalse(date1.isSameYearAndMonth(date2));
        assertFalse(date1.isSameYearAndMonth(date3));
    }

    public void testParseString() {
        Date date = Date.parseString("02/03/2023");
        assertEquals(date, date1);
        assertEquals(date2, Date.parseString("15/03/2020"));
    }

    public void testIsBefore() {
        assertTrue(new Date(19, 06, 2023).isBefore(new Date(20, 06, 2023)));
    }
}