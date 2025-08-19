package ru.quizHub.core.presentation.calendar

import com.kizitonwose.calendar.core.atDay
import com.kizitonwose.calendar.core.atEndOfMonth
import com.kizitonwose.calendar.core.minusMonths
import com.kizitonwose.calendar.core.plusMonths
import com.kizitonwose.calendar.core.yearMonth
import kotlinx.datetime.LocalDate
import kotlinx.datetime.daysUntil
import kotlin.LazyThreadSafetyMode.NONE

data class DateSelection(val startDate: LocalDate? = null, val endDate: LocalDate? = null) {
    val daysBetween by lazy(NONE) {
        if (startDate == null || endDate == null) {
            null
        } else {
            startDate.daysUntil(endDate)
        }
    }
}

object ContinuousSelectionHelper {
    fun getSelection(
        clickedDate: LocalDate,
        dateSelection: DateSelection,
    ): DateSelection {
        val (selectionStartDate, selectionEndDate) = dateSelection
        return if (selectionStartDate != null) {
            if (clickedDate < selectionStartDate || selectionEndDate != null) {
                DateSelection(startDate = clickedDate, endDate = null)
            } else if (clickedDate != selectionStartDate) {
                DateSelection(startDate = selectionStartDate, endDate = clickedDate)
            } else {
                DateSelection(startDate = clickedDate, endDate = null)
            }
        } else {
            DateSelection(startDate = clickedDate, endDate = null)
        }
    }

    fun isInDateBetweenSelection(
        inDate: LocalDate,
        startDate: LocalDate,
        endDate: LocalDate,
    ): Boolean {
        if (startDate.yearMonth == endDate.yearMonth) return false
        if (inDate.yearMonth == startDate.yearMonth) return true
        val firstDateInThisMonth = inDate.yearMonth.plusMonths(1).atDay(1)
        return firstDateInThisMonth in startDate..endDate && startDate != firstDateInThisMonth
    }

    fun isOutDateBetweenSelection(
        outDate: LocalDate,
        startDate: LocalDate,
        endDate: LocalDate,
    ): Boolean {
        if (startDate.yearMonth == endDate.yearMonth) return false
        if (outDate.yearMonth == endDate.yearMonth) return true
        val lastDateInThisMonth = outDate.yearMonth.minusMonths(1).atEndOfMonth()
        return lastDateInThisMonth in startDate..endDate && endDate != lastDateInThisMonth
    }
}

