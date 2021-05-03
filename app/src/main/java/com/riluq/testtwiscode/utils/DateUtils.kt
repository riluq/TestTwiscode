package com.riluq.testtwiscode.utils

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.datePicker
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import java.util.*

class DateUtils {
    companion object {
        fun convertStringToDateTime(formatDateString: String, dateString: String): DateTime =
            DateTimeFormat.forPattern(formatDateString).parseDateTime(dateString)

        fun convertDateTimeToString(formatOutput: String, dateTime: DateTime): String =
            dateTime.toString(formatOutput)

        fun dialogDate(currentCalendar: Calendar? = null,
                       formatOutput: String,
                       formatOutput2: String? = null,
                       context: Context,
                       dateResult: (dateResult: String, dateResult2: String?) -> Unit){
            val dateNow = LocalDateTime.now().toDateTime(DateTimeZone.forID("Asia/Jakarta"))
            MaterialDialog(context).show {
                datePicker(maxDate = dateNow.toGregorianCalendar(), currentDate = currentCalendar) { dialog, date ->
                    val dateTime = DateTime(date)
                    dateResult(dateTime.toString(formatOutput), if (formatOutput2 != null) dateTime.toString(formatOutput2) else null)
                }
            }
        }

        fun dateNow(ouputFormat: String): String {
            val dateNow = LocalDateTime.now().toDateTime(DateTimeZone.forID("Asia/Jakarta"))
            return dateNow.toString(ouputFormat)
        }

        // Minggu = 0, Sabtu = 6
        fun getDayNowInt(): Int {
            val dateNow = LocalDateTime.now().toDateTime(DateTimeZone.forID("Asia/Jakarta"))
            var dayOfWeek = dateNow.dayOfWeek().get()
            if (dayOfWeek == 7) {
                dayOfWeek = 0
            }
            return dayOfWeek
        }

    }
}