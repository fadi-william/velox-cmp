package com.levioza.velox.components.pickers

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

/** Pane the calendar is showing: days of a month, the months, or a window of years. */
internal enum class CalendarPane {
    Days,
    Months,
    Years,
}

/**
 * Holds the month a calendar is showing. Subclasses add the selection they track.
 *
 * @param anchorEpochDay Day whose month opens first, or null to open on [todayEpochDay].
 * @param todayEpochDay Epoch day marked as today, or null when today should not be marked.
 */
@Stable
internal abstract class CalendarState(
    anchorEpochDay: Long?,
    val todayEpochDay: Long?,
) {
    /** Year of the month currently on screen. */
    var displayedYear by mutableStateOf(0)
        private set

    /** Month, 1-12, currently on screen. */
    var displayedMonth by mutableStateOf(1)
        private set

    init {
        val (year, month, _) = (anchorEpochDay ?: todayEpochDay ?: 0L).toCalendarDate()
        displayedYear = year.coerceIn(CalendarYearRange.first, CalendarYearRange.last)
        displayedMonth = month
    }

    /** Shows the month [offset] months away from the displayed one. */
    fun shiftDisplayedMonth(offset: Int) {
        val monthIndex = displayedYear * 12 + (displayedMonth - 1) + offset
        val year = monthIndex.floorDivInt(12)
        if (year !in CalendarYearRange) return
        displayedYear = year
        displayedMonth = monthIndex - year * 12 + 1
    }

    /** Shows [month], 1-12, of the displayed year. */
    fun showMonth(month: Int) {
        displayedMonth = month
    }

    /** Shows the displayed month of [year]. */
    fun showYear(year: Int) {
        displayedYear = year.coerceIn(CalendarYearRange.first, CalendarYearRange.last)
    }
}

/**
 * Renders the Velox calendar: a header that reads back the selection, month and year buttons that
 * swap the calendar for a month or year picker, and one month of days that highlights a single day
 * or a range.
 *
 * @param title Small label above the headline, such as `Select date`.
 * @param headline Selection read back below the title.
 * @param displayedYear Year of the month on screen.
 * @param displayedMonth Month, 1-12, on screen.
 * @param onShiftMonth Callback invoked with a month offset when the arrows are used.
 * @param onMonthSelected Callback invoked with a month, 1-12, chosen from the month pane.
 * @param onYearSelected Callback invoked with a year chosen from the year pane.
 * @param selectionStart First selected day as an epoch day, or null when nothing is selected.
 * @param selectionEnd Last selected day as an epoch day, or null when no end day is selected.
 * @param todayEpochDay Epoch day marked as today, or null when today should not be marked.
 * @param onDayClick Callback invoked with the epoch day of a tapped day.
 * @param modifier Modifier applied to the component.
 */
@Composable
internal fun VeloxCalendar(
    title: String,
    headline: String,
    displayedYear: Int,
    displayedMonth: Int,
    onShiftMonth: (Int) -> Unit,
    onMonthSelected: (Int) -> Unit,
    onYearSelected: (Int) -> Unit,
    selectionStart: Long?,
    selectionEnd: Long?,
    todayEpochDay: Long?,
    onDayClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    var pane by remember { mutableStateOf(CalendarPane.Days) }
    var yearWindowStart by remember { mutableStateOf(displayedYear - YearWindowOffset) }

    Column(modifier.fillMaxWidth()) {
        CalendarHeader(title = title, headline = headline)
        HorizontalDivider()
        CalendarNavigation(
            displayedYear = displayedYear,
            displayedMonth = displayedMonth,
            pane = pane,
            onPaneChange = { requested ->
                pane = if (pane == requested) CalendarPane.Days else requested
                if (pane == CalendarPane.Years) {
                    yearWindowStart = displayedYear - YearWindowOffset
                }
            },
            onShiftMonth = onShiftMonth,
            onShiftYearWindow = { offset -> yearWindowStart += offset },
        )
        Box(Modifier.fillMaxWidth().padding(PaneHorizontalPadding).heightIn(min = PaneMinHeight)) {
            when (pane) {
                CalendarPane.Days ->
                    DaysPane(
                        displayedYear = displayedYear,
                        displayedMonth = displayedMonth,
                        selectionStart = selectionStart,
                        selectionEnd = selectionEnd,
                        todayEpochDay = todayEpochDay,
                        onDayClick = onDayClick,
                    )
                CalendarPane.Months ->
                    MonthsPane(
                        displayedMonth = displayedMonth,
                        onMonthSelected = { month ->
                            onMonthSelected(month)
                            pane = CalendarPane.Days
                        },
                    )
                CalendarPane.Years ->
                    YearsPane(
                        displayedYear = displayedYear,
                        windowStart = yearWindowStart,
                        onYearSelected = { year ->
                            onYearSelected(year)
                            pane = CalendarPane.Days
                        },
                    )
            }
        }
    }
}

@Composable
private fun CalendarHeader(title: String, headline: String) {
    Column(Modifier.fillMaxWidth().padding(HeaderPadding)) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(Modifier.height(HeaderTitleSpacing))
        Text(
            text = headline,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun CalendarNavigation(
    displayedYear: Int,
    displayedMonth: Int,
    pane: CalendarPane,
    onPaneChange: (CalendarPane) -> Unit,
    onShiftMonth: (Int) -> Unit,
    onShiftYearWindow: (Int) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(NavigationPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PaneButton(
            text = CalendarMonthNames[displayedMonth - 1],
            onClick = { onPaneChange(CalendarPane.Months) },
        )
        PaneButton(
            text = displayedYear.toString(),
            onClick = { onPaneChange(CalendarPane.Years) },
        )
        Spacer(Modifier.weight(1f))
        // The month pane fills the calendar, so there is nothing left to page through.
        if (pane != CalendarPane.Months) {
            val step = if (pane == CalendarPane.Years) YearWindowSize else 1
            val shift: (Int) -> Unit =
                if (pane == CalendarPane.Years) onShiftYearWindow else onShiftMonth
            IconButton(onClick = { shift(-step) }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = if (pane == CalendarPane.Years) {
                        "Previous years"
                    } else {
                        "Previous month"
                    },
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            IconButton(onClick = { shift(step) }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = if (pane == CalendarPane.Years) {
                        "Next years"
                    } else {
                        "Next month"
                    },
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun PaneButton(text: String, onClick: () -> Unit) {
    Row(
        modifier =
            Modifier.clip(RoundedCornerShape(PaneButtonCorner))
                .clickable(onClick = onClick)
                .padding(PaneButtonPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(Modifier.size(PaneButtonIconSpacing))
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = null,
            modifier = Modifier.size(PaneButtonIconSize),
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun DaysPane(
    displayedYear: Int,
    displayedMonth: Int,
    selectionStart: Long?,
    selectionEnd: Long?,
    todayEpochDay: Long?,
    onDayClick: (Long) -> Unit,
) {
    val firstDay = epochDayOf(displayedYear, displayedMonth, 1)
    val leadingBlanks = firstDay.dayOfWeekIndex()
    val dayCount = daysInMonth(displayedYear, displayedMonth)
    val rows = (leadingBlanks + dayCount + DaysPerWeek - 1) / DaysPerWeek

    Column(Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth().padding(bottom = WeekDayLabelSpacing)) {
            CalendarWeekDayNames.forEach { name ->
                Text(
                    text = name,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        repeat(rows) { row ->
            Row(Modifier.fillMaxWidth()) {
                repeat(DaysPerWeek) { column ->
                    val dayOfMonth = row * DaysPerWeek + column - leadingBlanks + 1
                    if (dayOfMonth in 1..dayCount) {
                        DayCell(
                            modifier = Modifier.weight(1f),
                            epochDay = firstDay + (dayOfMonth - 1),
                            dayOfMonth = dayOfMonth,
                            selectionStart = selectionStart,
                            selectionEnd = selectionEnd,
                            todayEpochDay = todayEpochDay,
                            onClick = onDayClick,
                        )
                    } else {
                        Spacer(Modifier.weight(1f).aspectRatio(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun DayCell(
    modifier: Modifier,
    epochDay: Long,
    dayOfMonth: Int,
    selectionStart: Long?,
    selectionEnd: Long?,
    todayEpochDay: Long?,
    onClick: (Long) -> Unit,
) {
    val hasRange = selectionStart != null && selectionEnd != null && selectionEnd > selectionStart
    val isStart = hasRange && epochDay == selectionStart
    val isEnd = hasRange && epochDay == selectionEnd
    val isInRange = hasRange && epochDay > selectionStart!! && epochDay < selectionEnd!!
    val isHighlighted = epochDay == selectionStart || epochDay == selectionEnd
    val isToday = epochDay == todayEpochDay
    val bandColor = MaterialTheme.colorScheme.primaryContainer

    Box(modifier.aspectRatio(1f), contentAlignment = Alignment.Center) {
        // The band is drawn as two halves so neighbouring cells join into one continuous shape:
        // the range's own ends are rounded, and the endpoints' circles sit on top of them.
        if (isInRange || isStart || isEnd) {
            Row(Modifier.fillMaxWidth().fillMaxHeight()) {
                BandHalf(
                    filled = !isStart,
                    color = bandColor,
                    shape =
                        if (isEnd) {
                            RoundedCornerShape(topEnd = BandCorner, bottomEnd = BandCorner)
                        } else {
                            null
                        },
                )
                BandHalf(
                    filled = !isEnd,
                    color = bandColor,
                    shape =
                        if (isStart) {
                            RoundedCornerShape(topStart = BandCorner, bottomStart = BandCorner)
                        } else {
                            null
                        },
                )
            }
        }

        val decoration =
            when {
                isHighlighted -> Modifier.background(MaterialTheme.colorScheme.primary, CircleShape)
                isToday ->
                    Modifier.border(TodayBorderWidth, MaterialTheme.colorScheme.primary, CircleShape)
                else -> Modifier
            }
        val contentColor =
            when {
                isHighlighted -> MaterialTheme.colorScheme.onPrimary
                isInRange -> MaterialTheme.colorScheme.onPrimaryContainer
                else -> MaterialTheme.colorScheme.onSurface
            }

        Box(
            modifier =
                Modifier.size(DayCircleSize)
                    .clip(CircleShape)
                    .then(decoration)
                    .clickable { onClick(epochDay) },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = dayOfMonth.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = contentColor,
            )
        }
    }
}

@Composable
private fun RowScope.BandHalf(
    filled: Boolean,
    color: Color,
    shape: Shape?,
) {
    Box(
        Modifier.weight(1f)
            .fillMaxHeight()
            .then(if (shape != null) Modifier.clip(shape) else Modifier)
            .background(if (filled) color else Color.Transparent),
    )
}

@Composable
private fun MonthsPane(displayedMonth: Int, onMonthSelected: (Int) -> Unit) {
    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
        repeat(MonthPaneRows) { row ->
            Row(Modifier.fillMaxWidth()) {
                repeat(MonthPaneColumns) { column ->
                    val month = row * MonthPaneColumns + column + 1
                    PaneItem(
                        modifier = Modifier.weight(1f),
                        text = CalendarMonthNames[month - 1].take(ShortMonthLength),
                        selected = month == displayedMonth,
                        verticalPadding = MonthItemPadding,
                        verticalSpacing = MonthItemSpacing,
                        onClick = { onMonthSelected(month) },
                    )
                }
            }
        }
    }
}

@Composable
private fun YearsPane(displayedYear: Int, windowStart: Int, onYearSelected: (Int) -> Unit) {
    Column(Modifier.fillMaxWidth()) {
        repeat(YearPaneRows) { row ->
            Row(Modifier.fillMaxWidth()) {
                repeat(YearPaneColumns) { column ->
                    val year = windowStart + row * YearPaneColumns + column
                    if (year in CalendarYearRange) {
                        PaneItem(
                            modifier = Modifier.weight(1f),
                            text = year.toString(),
                            selected = year == displayedYear,
                            verticalPadding = YearItemPadding,
                            verticalSpacing = YearItemSpacing,
                            onClick = { onYearSelected(year) },
                        )
                    } else {
                        Spacer(Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun PaneItem(
    modifier: Modifier,
    text: String,
    selected: Boolean,
    verticalPadding: Dp,
    verticalSpacing: Dp,
    onClick: () -> Unit,
) {
    Box(
        modifier =
            modifier.padding(vertical = verticalSpacing)
                .clip(RoundedCornerShape(PaneItemCorner))
                .clickable(onClick = onClick)
                .background(
                    if (selected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
                )
                .padding(vertical = verticalPadding),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color =
                if (selected) {
                    MaterialTheme.colorScheme.onPrimaryContainer
                } else {
                    MaterialTheme.colorScheme.onSurface
                },
        )
    }
}

/** Reads today as a UTC epoch day, matching the UTC dates the pickers exchange. */
@OptIn(ExperimentalTime::class)
@Composable
internal fun rememberTodayEpochDay(): Long =
    remember { Clock.System.now().toEpochMilliseconds().floorDivLong(MillisPerDay) }

private val HeaderPadding = PaddingValues(16.dp)
private val HeaderTitleSpacing = 4.dp
private val NavigationPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
private val PaneHorizontalPadding = PaddingValues(horizontal = 12.dp)
private val PaneMinHeight = 280.dp
private val PaneButtonPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
private val PaneButtonCorner = 20.dp
private val PaneButtonIconSize = 20.dp
private val PaneButtonIconSpacing = 4.dp
private val PaneItemCorner = 20.dp
private val MonthItemSpacing = 4.dp
private val YearItemSpacing = 2.dp
private val MonthItemPadding = 16.dp
private val YearItemPadding = 10.dp
private val WeekDayLabelSpacing = 8.dp
private val DayCircleSize = 40.dp
private val TodayBorderWidth = 1.dp
private val BandCorner = 20.dp
private const val DaysPerWeek = 7
private const val MonthPaneColumns = 3
private const val MonthPaneRows = 4
private const val YearPaneColumns = 4
private const val YearPaneRows = 6
private const val YearWindowSize = 24
private const val YearWindowOffset = 11
private const val ShortMonthLength = 3

/** Year range the calendar's year pane can reach. */
internal val CalendarYearRange = 1900..2100

/** Month names used by the calendar's header, month button, and month pane. */
internal val CalendarMonthNames =
    listOf(
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December",
    )

private val CalendarWeekDayNames = listOf("S", "M", "T", "W", "T", "F", "S")

private val CalendarShortDayNames = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")

internal const val MillisPerDay = 86_400_000L

/** Converts an ISO YYYY-MM-DD date to days since the epoch, or null when it is not a valid date. */
internal fun String.isoDateToEpochDay(): Long? {
    val parts = split('-')
    if (parts.size != 3) return null
    val year = parts[0].toIntOrNull() ?: return null
    val month = parts[1].toIntOrNull() ?: return null
    val day = parts[2].toIntOrNull() ?: return null
    if (month !in 1..12 || day !in 1..daysInMonth(year, month)) return null
    return epochDayOf(year, month, day)
}

/** Converts days since the epoch to an ISO YYYY-MM-DD date. */
internal fun Long.epochDayToIsoDate(): String {
    val (year, month, day) = toCalendarDate()
    return year.toString().padStart(4, '0') +
        "-" +
        month.toString().padStart(2, '0') +
        "-" +
        day.toString().padStart(2, '0')
}

/** Converts an ISO YYYY-MM-DD date to UTC milliseconds, or null when it is not a valid date. */
internal fun String.isoDateToEpochMillis(): Long? = isoDateToEpochDay()?.let { it * MillisPerDay }

/** Converts UTC milliseconds to an ISO YYYY-MM-DD date. */
internal fun Long.toIsoDate(): String = floorDivLong(MillisPerDay).epochDayToIsoDate()

/** Builds the days since the epoch for a year, a month in 1-12, and a day of month. */
internal fun epochDayOf(year: Int, month: Int, day: Int): Long {
    var adjustedYear = year
    if (month <= 2) adjustedYear -= 1
    val era = adjustedYear.floorDivInt(400)
    val yearOfEra = adjustedYear - era * 400
    val adjustedMonth = month + if (month > 2) -3 else 9
    val dayOfYear = (153 * adjustedMonth + 2) / 5 + day - 1
    val dayOfEra = yearOfEra * 365 + yearOfEra / 4 - yearOfEra / 100 + dayOfYear
    return era * 146_097L + dayOfEra - 719_468L
}

/** Splits days since the epoch into a year, a month in 1-12, and a day of month. */
internal fun Long.toCalendarDate(): Triple<Int, Int, Int> {
    val days = this + 719_468L
    val era = days.floorDivLong(146_097L)
    val dayOfEra = days - era * 146_097L
    val yearOfEra =
        (dayOfEra - dayOfEra / 1_460L + dayOfEra / 36_524L - dayOfEra / 146_096L) / 365L
    var year = yearOfEra + era * 400L
    val dayOfYear = dayOfEra - (365L * yearOfEra + yearOfEra / 4L - yearOfEra / 100L)
    val monthPrime = (5L * dayOfYear + 2L) / 153L
    val day = dayOfYear - (153L * monthPrime + 2L) / 5L + 1L
    val month = monthPrime + if (monthPrime < 10L) 3L else -9L
    if (month <= 2L) year += 1L
    return Triple(year.toInt(), month.toInt(), day.toInt())
}

/** Day of the week for days since the epoch, where 0 is Sunday. */
internal fun Long.dayOfWeekIndex(): Int = (this + 4L).mod(7L).toInt()

/** Number of days in a month, for a year and a month in 1-12. */
internal fun daysInMonth(year: Int, month: Int): Int =
    when (month) {
        2 -> if (isLeapYear(year)) 29 else 28
        4, 6, 9, 11 -> 30
        else -> 31
    }

/** Formats days since the epoch as a day of month and a month name, such as `8 September`. */
internal fun Long.toDayAndMonth(): String {
    val (_, month, day) = toCalendarDate()
    return "$day ${CalendarMonthNames[month - 1]}"
}

/** Formats days since the epoch with its week day, such as `Tue, 8 September`. */
internal fun Long.toWeekDayAndDate(): String =
    "${CalendarShortDayNames[dayOfWeekIndex()]}, ${toDayAndMonth()}"

private fun isLeapYear(year: Int): Boolean =
    year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)

private fun Int.floorDivInt(other: Int): Int {
    val quotient = this / other
    return if ((this xor other) < 0 && quotient * other != this) quotient - 1 else quotient
}

private fun Long.floorDivLong(other: Long): Long {
    val quotient = this / other
    return if ((this xor other) < 0L && quotient * other != this) quotient - 1L else quotient
}
