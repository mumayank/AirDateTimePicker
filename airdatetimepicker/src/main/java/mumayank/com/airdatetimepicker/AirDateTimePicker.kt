package mumayank.com.airdatetimepicker

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import mumayank.com.airdialog.AirDialog
import java.text.DecimalFormat
import java.util.*

class AirDateTimePicker {

    interface PickDateCallback {
        fun onSuccess(year: Int, month: Int, dayOfMonth: Int)
        fun onFailure()
    }

    interface PickTimeCallback {
        fun onSuccess(hour: Int, min: Int)
        fun onFailure()
    }

    interface PickDateTimeCallback {
        fun onSuccess(time: Long)
        fun onFailure()
    }

    companion object {

        fun pickDate(
            activity: Activity,
            selectedDate: Long?,
            minDate: Long?,
            maxDate: Long?,
            pickDateCallback: PickDateCallback
        ){
            try {
                var selectedYear = 0
                var selectedMonth = 0
                var selectedDay = 0

                if (selectedDate != null) {
                    val cal = Calendar.getInstance()
                    cal.time = Date(selectedDate)
                    selectedYear = cal.get(Calendar.YEAR)
                    selectedMonth = cal.get(Calendar.MONTH)
                    selectedDay = cal.get(Calendar.DAY_OF_MONTH)
                }

                val datePickerDialog = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    pickDateCallback.onSuccess(year, month, dayOfMonth)
                }, selectedYear, selectedMonth, selectedDay)

                if (minDate != null) {
                    datePickerDialog.datePicker.minDate = minDate
                }
                if (maxDate != null) {
                    datePickerDialog.datePicker.maxDate = maxDate
                }
                datePickerDialog.setOnCancelListener {
                    pickDateCallback.onFailure()
                }
                datePickerDialog.setCancelable(false)

                datePickerDialog.show()
            } catch (e: Exception) {
                pickDateCallback.onFailure()
            }
        }

        fun pickTime(
            activity: Activity,
            selectedTime: Long?,
            minTime: Long?,
            maxTime: Long?,
            isMinDateSame: Boolean = false,
            isMaxDateSame: Boolean = false,
            pickTimeCallback: PickTimeCallback
        ) {
            try {
                val timePickerDialog = TimePickerDialog(activity, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

                    var minHour: Int? = null
                    var minMinute: Int? = null
                    var maxHour: Int? = null
                    var maxMinute: Int? = null

                    if (minTime != null) {
                        val minCal = Calendar.getInstance()
                        minCal.timeInMillis = minTime
                        minHour = minCal.get(Calendar.HOUR_OF_DAY)
                        minMinute = minCal.get(Calendar.MINUTE)
                    }

                    if (maxTime != null) {
                        val maxCal = Calendar.getInstance()
                        maxCal.timeInMillis = maxTime
                        maxHour = maxCal.get(Calendar.HOUR_OF_DAY)
                        maxMinute = maxCal.get(Calendar.MINUTE)
                    }

                    var incorrectString = ""

                    if (isMinDateSame) {
                        if (minHour != null) {
                            if (hourOfDay == minHour) {
                                if (minMinute != null) {
                                    if (minute < minMinute) {
                                        incorrectString = "Time must be greater than or equal to ${DecimalFormat("00").format(minHour)}:${DecimalFormat("00").format(minMinute)}"
                                    }
                                }
                            } else if (hourOfDay < minHour) {
                                minMinute = minMinute?: 0
                                incorrectString = "Time must be greater than or equal to ${DecimalFormat("00").format(minHour)}:${DecimalFormat("00").format(minMinute)}"
                            }
                        }
                    }

                    if (isMaxDateSame) {
                        if (maxHour != null) {
                            if (hourOfDay == maxHour) {
                                if (maxMinute != null) {
                                    if (minute > maxMinute) {
                                        incorrectString = "Time must be smaller than or equal to ${DecimalFormat("00").format(maxHour)}:${DecimalFormat("00").format(maxMinute)}"
                                    }
                                }
                            } else if (hourOfDay > maxHour) {
                                maxMinute = maxMinute?: 59
                                incorrectString = "Time must be smaller than or equal to ${DecimalFormat("00").format(maxHour)}:${DecimalFormat("00").format(maxMinute)}"
                            }
                        }
                    }

                    if (incorrectString == "") {
                        pickTimeCallback.onSuccess(hourOfDay, minute)
                    } else {
                        AirDialog(
                            activity,
                            "Incorrect Time Selected",
                            incorrectString,
                            isCancelable = false,
                            airButton1 = AirDialog.Button("RETRY") {
                                pickTime(activity, selectedTime, minTime, maxTime, isMinDateSame, isMaxDateSame, pickTimeCallback)
                            },
                            airButton3 = AirDialog.Button("CANCEL") {
                                pickTimeCallback.onFailure()
                            }
                        )
                    }

                }, 0, 0, true)

                timePickerDialog.setOnCancelListener {
                    pickTimeCallback.onFailure()
                }

                timePickerDialog.setCancelable(false)
                if (selectedTime != null) {
                    val cal = Calendar.getInstance()
                    cal.timeInMillis = selectedTime
                    val selectedHour = cal.get(Calendar.HOUR_OF_DAY)
                    val selectedMinute = cal.get(Calendar.MINUTE)
                    timePickerDialog.updateTime(selectedHour, selectedMinute)
                }

                timePickerDialog.show()
            } catch (e: Exception) {
                pickTimeCallback.onFailure()
            }


        }

        fun pickDateTime(
            activity: Activity,
            selectedDate: Long?,
            minDate: Long?,
            maxDate: Long?,
            pickDateTimeCallback: PickDateTimeCallback
        ) {

            pickDate(activity, selectedDate, minDate, maxDate, object: PickDateCallback {
                override fun onSuccess(year: Int, month: Int, dayOfMonth: Int) {
                    var isMinDateSame = false
                    if (minDate != null) {
                        val cal1 = Calendar.getInstance()
                        cal1.time = Date(minDate)
                        val minYear = cal1.get(Calendar.YEAR)
                        val minMonth = cal1.get(Calendar.MONTH)
                        val minDay = cal1.get(Calendar.DAY_OF_MONTH)
                        if (minYear == year && minMonth == month && minDay == dayOfMonth) {
                            isMinDateSame = true
                        }
                    }

                    var isMaxDateSame = false
                    if (maxDate != null) {
                        val cal1 = Calendar.getInstance()
                        cal1.time = Date(maxDate)
                        val maxYear = cal1.get(Calendar.YEAR)
                        val maxMonth = cal1.get(Calendar.MONTH)
                        val maxDay = cal1.get(Calendar.DAY_OF_MONTH)
                        if (maxYear == year && maxMonth == month && maxDay == dayOfMonth) {
                            isMaxDateSame = true
                        }
                    }

                    pickTime(activity, selectedDate, minDate, maxDate, isMinDateSame, isMaxDateSame, object: PickTimeCallback {
                        override fun onSuccess(hour: Int, min: Int) {
                            val cal = Calendar.getInstance()
                            cal.set(Calendar.YEAR, year)
                            cal.set(Calendar.MONTH, month)
                            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                            cal.set(Calendar.HOUR_OF_DAY, hour)
                            cal.set(Calendar.MINUTE, min)
                            pickDateTimeCallback.onSuccess(cal.timeInMillis)
                        }

                        override fun onFailure() {
                            pickDateTimeCallback.onFailure()
                        }

                    })

                }

                override fun onFailure() {
                    pickDateTimeCallback.onFailure()
                }

            })
        }

    }
}