package com.levioza.velox.components.pickers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

/**
 * Holds the selection and the displayed month of a single-date [VeloxCalendar].
 *
 * @param initialDate Initially selected date in ISO YYYY-MM-DD format, or null for no selection.
 * @param todayEpochDay Epoch day marked as today, or null when today should not be marked.
 */
@Stable
internal class DateCalendarState(
    initialDate: String?,
    todayEpochDay: Long?,
) : CalendarState(initialDate?.isoDateToEpochDay(), todayEpochDay) {
    var selectedEpochDay by mutableStateOf(initialDate?.isoDateToEpochDay())
        private set

    /** Selected date in ISO YYYY-MM-DD format, or null when nothing is selected. */
    val selectedIsoDate: String?
        get() = selectedEpochDay?.epochDayToIsoDate()

    /** Selection read back by the calendar header. */
    val headline: String
        get() = selectedEpochDay?.toWeekDayAndDate() ?: "No date selected"

    /** Selects [epochDay], replacing any earlier selection. */
    fun selectDay(epochDay: Long) {
        selectedEpochDay = epochDay
    }
}

/**
 * Creates a [DateCalendarState] that survives recomposition.
 *
 * @param date Initially selected date in ISO YYYY-MM-DD format.
 * @param todayEpochDay Epoch day marked as today, or null when today should not be marked.
 */
@Composable
internal fun rememberDateCalendarState(date: String?, todayEpochDay: Long?): DateCalendarState =
    remember(date, todayEpochDay) { DateCalendarState(date, todayEpochDay) }

/**
 * Renders a single-date [VeloxCalendar] driven by a [DateCalendarState].
 *
 * @param state Selection and displayed month held by the calendar.
 * @param modifier Modifier applied to the component.
 * @param onDaySelected Callback invoked with the epoch day of a tapped day.
 */
@Composable
internal fun DateCalendar(
    state: DateCalendarState,
    modifier: Modifier = Modifier,
    onDaySelected: (Long) -> Unit = {},
) {
    VeloxCalendar(
        title = "Select date",
        headline = state.headline,
        displayedYear = state.displayedYear,
        displayedMonth = state.displayedMonth,
        onShiftMonth = state::shiftDisplayedMonth,
        onMonthSelected = state::showMonth,
        onYearSelected = state::showYear,
        selectionStart = state.selectedEpochDay,
        selectionEnd = state.selectedEpochDay,
        todayEpochDay = state.todayEpochDay,
        onDayClick = { epochDay ->
            state.selectDay(epochDay)
            onDaySelected(epochDay)
        },
        modifier = modifier,
    )
}

/**
 * Renders the Velox date picker component.
 *
 * @param selectedDateMillis Selected date as UTC milliseconds, or null when no date is selected.
 * @param onDateSelected Callback invoked when a date is selected.
 * @param modifier Modifier applied to the component.
 */
@Composable
fun DatePicker(
    selectedDateMillis: Long?,
    onDateSelected: (Long?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberDateCalendarState(selectedDateMillis?.toIsoDate(), rememberTodayEpochDay())
    DateCalendar(
        state = state,
        modifier = modifier,
        onDaySelected = { epochDay -> onDateSelected(epochDay * MillisPerDay) },
    )
}
