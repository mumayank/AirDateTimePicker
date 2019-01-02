package mumayank.com.airdatetimepicker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import mumayank.com.airdatetimepickerlibrary.AirDateTimePicker
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val simpleDateFormatTime = SimpleDateFormat("h:mm a")
        textViewTime.text = simpleDateFormatTime.format(System.currentTimeMillis())
        textViewTime.tag = System.currentTimeMillis()
        buttonTime.setOnClickListener {
            AirDateTimePicker.pickTime(
                this,
                textViewTime.tag.toString().toLong(),
                null,
                System.currentTimeMillis(),
                false,
                false,
                object: AirDateTimePicker.Callback {
                    override fun onSuccess(time: Long) {
                        textViewTime.tag = time
                        textViewTime.text = simpleDateFormatTime.format(time)
                    }

                    override fun onFailure() {
                        // can optionally do something
                    }
            })
        }

        val simpleDateFormatDate = SimpleDateFormat("d MMM yyyy")
        textViewDate.text = simpleDateFormatDate.format(System.currentTimeMillis())
        textViewDate.tag = System.currentTimeMillis()
        buttonDate.setOnClickListener {
            AirDateTimePicker.pickDate(this, textViewDate.tag.toString().toLong(), null, System.currentTimeMillis(), object: AirDateTimePicker.Callback {
                override fun onSuccess(time: Long) {
                    textViewDate.tag = time
                    textViewDate.text = simpleDateFormatDate.format(time)
                }

                override fun onFailure() {
                    // can optionally do something
                }

            })
        }

        val simpleDateFormatDateTime = SimpleDateFormat("d MMM yyyy h:mm a")
        textViewDateTime.text = simpleDateFormatDateTime.format(System.currentTimeMillis())
        textViewDateTime.tag = System.currentTimeMillis()
        buttonDateTime.setOnClickListener {
            AirDateTimePicker.pickDateTime(this, textViewDateTime.tag.toString().toLong(), null, System.currentTimeMillis(), object: AirDateTimePicker.Callback {
                override fun onSuccess(time: Long) {
                    textViewDateTime.tag = time
                    textViewDateTime.text = simpleDateFormatDateTime.format(time)
                }

                override fun onFailure() {
                    // can optionally do something
                }

            })
        }

    }
}
