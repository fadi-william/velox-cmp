package com.levioza.velox.components.pickers

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class CalendarTest {
    @Test
    fun epochDayRoundTripsThroughIsoDates() {
        listOf("1970-01-01", "2000-02-29", "2026-09-08", "2100-12-31", "1900-01-01").forEach {
            assertEquals(it, it.isoDateToEpochDay()?.epochDayToIsoDate())
        }
    }

    @Test
    fun epochDayOfMatchesKnownDates() {
        assertEquals(0L, epochDayOf(1970, 1, 1))
        assertEquals(20_704L, epochDayOf(2026, 9, 8))
        assertEquals(-1L, epochDayOf(1969, 12, 31))
    }

    @Test
    fun dayOfWeekIndexTreatsSundayAsZero() {
        // 2026-09-08 is a Tuesday, and 2026-09-06 is a Sunday.
        assertEquals(2, epochDayOf(2026, 9, 8).dayOfWeekIndex())
        assertEquals(0, epochDayOf(2026, 9, 6).dayOfWeekIndex())
        assertEquals(6, epochDayOf(2026, 9, 5).dayOfWeekIndex())
    }

    @Test
    fun daysInMonthHandlesLeapYears() {
        assertEquals(29, daysInMonth(2024, 2))
        assertEquals(28, daysInMonth(2100, 2))
        assertEquals(29, daysInMonth(2000, 2))
        assertEquals(30, daysInMonth(2026, 9))
        assertEquals(31, daysInMonth(2026, 12))
    }

    @Test
    fun invalidIsoDatesHaveNoEpochDay() {
        assertNull("2026-02-30".isoDateToEpochDay())
        assertNull("not-a-date".isoDateToEpochDay())
    }

    @Test
    fun firstTapStartsRangeAndSecondTapClosesIt() {
        val state = DateRangeCalendarState(null, null, todayEpochDay = null)
        state.selectDay(epochDayOf(2026, 9, 8))
        assertEquals("2026-09-08", state.startIsoDate)
        assertNull(state.endIsoDate)

        state.selectDay(epochDayOf(2026, 9, 15))
        assertEquals("2026-09-08", state.startIsoDate)
        assertEquals("2026-09-15", state.endIsoDate)
    }

    @Test
    fun tapBeforeStartSwapsTheRangeEnds() {
        val state = DateRangeCalendarState("2026-09-08", null, todayEpochDay = null)
        state.selectDay(epochDayOf(2026, 9, 1))
        assertEquals("2026-09-01", state.startIsoDate)
        assertEquals("2026-09-08", state.endIsoDate)
    }

    @Test
    fun aFreshRangeStartsOnTheFirstTapEvenBeforeTheOldStart() {
        val state = DateRangeCalendarState(null, null, todayEpochDay = null)
        state.selectDay(epochDayOf(2026, 9, 8))
        state.selectDay(epochDayOf(2026, 9, 15))
        state.selectDay(epochDayOf(2026, 9, 1))
        assertEquals("2026-09-01", state.startIsoDate)
        assertNull(state.endIsoDate)
    }

    @Test
    fun tapOnACompleteRangeStartsANewOne() {
        val state = DateRangeCalendarState("2026-09-08", "2026-09-15", todayEpochDay = null)
        state.selectDay(epochDayOf(2026, 9, 20))
        assertEquals("2026-09-20", state.startIsoDate)
        assertNull(state.endIsoDate)
    }

    @Test
    fun displayedMonthOpensOnTheSelectedStart() {
        val state = DateRangeCalendarState("2026-09-08", "2026-09-15", todayEpochDay = null)
        assertEquals(2026, state.displayedYear)
        assertEquals(9, state.displayedMonth)
    }

    @Test
    fun shiftingMonthsCrossesYearBoundaries() {
        val state = DateRangeCalendarState("2026-01-05", null, todayEpochDay = null)
        state.shiftDisplayedMonth(-1)
        assertEquals(2025, state.displayedYear)
        assertEquals(12, state.displayedMonth)

        state.shiftDisplayedMonth(1)
        assertEquals(2026, state.displayedYear)
        assertEquals(1, state.displayedMonth)
    }

    @Test
    fun shiftingStopsAtTheSupportedYearRange() {
        val state = DateRangeCalendarState("2100-12-01", null, todayEpochDay = null)
        state.shiftDisplayedMonth(1)
        assertEquals(2100, state.displayedYear)
        assertEquals(12, state.displayedMonth)
    }

    @Test
    fun singleDateSelectionReplacesTheEarlierDay() {
        val state = DateCalendarState("2026-09-08", todayEpochDay = null)
        assertEquals("2026-09-08", state.selectedIsoDate)
        assertEquals(9, state.displayedMonth)

        state.selectDay(epochDayOf(2026, 9, 20))
        assertEquals("2026-09-20", state.selectedIsoDate)
    }

    @Test
    fun headlinesReadBackTheSelection() {
        assertEquals("No date selected", DateCalendarState(null, null).headline)
        assertEquals("Tue, 8 September", DateCalendarState("2026-09-08", null).headline)
        assertEquals("Select start date", DateRangeCalendarState(null, null, null).headline)
        assertEquals(
            "8 September - ...",
            DateRangeCalendarState("2026-09-08", null, null).headline,
        )
        assertEquals(
            "8 September - 15 September",
            DateRangeCalendarState("2026-09-08", "2026-09-15", null).headline,
        )
    }

    @Test
    fun calendarOpensOnTodayWhenNothingIsSelected() {
        val state = DateRangeCalendarState(null, null, todayEpochDay = epochDayOf(2026, 3, 4))
        assertEquals(2026, state.displayedYear)
        assertEquals(3, state.displayedMonth)
    }

    @Test
    fun millisRoundTripThroughIsoDates() {
        val millis = epochDayOf(2026, 9, 8) * 86_400_000L
        assertEquals("2026-09-08", millis.toIsoDate())
        assertEquals(millis, "2026-09-08".isoDateToEpochMillis())
    }
}
