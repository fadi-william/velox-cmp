package com.levioza.velox.components.pickers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

/**
 * Holds the selection and the displayed month of a range [VeloxCalendar].
 *
 * @param initialStart Initially selected start date, or null when there is no selection.
 * @param initialEnd Initially selected end date, or null when there is no end selection.
 * @param todayEpochDay Epoch day marked as today, or null when today should not be marked.
 */
@Stable
internal class DateRangeCalendarState(
    initialStart: String?,
    initialEnd: String?,
    todayEpochDay: Long?,
) : CalendarState(initialStart?.isoDateToEpochDay(), todayEpochDay) {
    var startEpochDay by mutableStateOf(initialStart?.isoDateToEpochDay())
        private set

    var endEpochDay by mutableStateOf(initialEnd?.isoDateToEpochDay())
        private set

    /** Whether the current selection is a complete range. */
    val hasCompleteRange: Boolean
        get() = startEpochDay != null && endEpochDay != null

    /** Selected start date in ISO YYYY-MM-DD format, or null when nothing is selected. */
    val startIsoDate: String?
        get() = startEpochDay?.epochDayToIsoDate()

    /** Selected end date in ISO YYYY-MM-DD format, or null when no end date is selected. */
    val endIsoDate: String?
        get() = endEpochDay?.epochDayToIsoDate()

    /** Selection read back by the calendar header. */
    val headline: String
        get() {
            val start = startEpochDay ?: return "Select start date"
            val end = endEpochDay ?: return "${start.toDayAndMonth()} - ..."
            return "${start.toDayAndMonth()} - ${end.toDayAndMonth()}"
        }

    private var selectingEnd by mutableStateOf(initialStart != null && initialEnd == null)

    /**
     * Applies a day tap. The first tap starts a new range and the next one closes it, swapping the
     * two days when the second tap lands before the start.
     */
    fun selectDay(epochDay: Long) {
        val start = startEpochDay
        if (start == null || endEpochDay != null || !selectingEnd) {
            startEpochDay = epochDay
            endEpochDay = null
            selectingEnd = true
            return
        }
        if (epochDay < start) {
            startEpochDay = epochDay
            endEpochDay = start
        } else {
            endEpochDay = epochDay
        }
        selectingEnd = false
    }
}

/**
 * Creates a [DateRangeCalendarState] that survives recomposition.
 *
 * @param start Initially selected start date in ISO YYYY-MM-DD format.
 * @param end Initially selected end date in ISO YYYY-MM-DD format.
 * @param todayEpochDay Epoch day marked as today, or null when today should not be marked.
 */
@Composable
internal fun rememberDateRangeCalendarState(
    start: String?,
    end: String?,
    todayEpochDay: Long?,
): DateRangeCalendarState =
    remember(start, end, todayEpochDay) { DateRangeCalendarState(start, end, todayEpochDay) }

/** Renders a range [VeloxCalendar] driven by a [DateRangeCalendarState]. */
@Composable
internal fun DateRangeCalendar(state: DateRangeCalendarState, modifier: Modifier = Modifier) {
    VeloxCalendar(
        title = "Select range",
        headline = state.headline,
        displayedYear = state.displayedYear,
        displayedMonth = state.displayedMonth,
        onShiftMonth = state::shiftDisplayedMonth,
        onMonthSelected = state::showMonth,
        onYearSelected = state::showYear,
        selectionStart = state.startEpochDay,
        selectionEnd = state.endEpochDay,
        todayEpochDay = state.todayEpochDay,
        onDayClick = state::selectDay,
        modifier = modifier,
    )
}
