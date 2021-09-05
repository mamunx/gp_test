package com.example.gp_test_md_hasanuzzaman

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etYears = findViewById<EditText>(R.id.et_year)
        val btnFind = findViewById<Button>(R.id.btn_find)
        val tvTotalEvents = findViewById<TextView>(R.id.tv_total_event)
        val tvRelaxStarts = findViewById<TextView>(R.id.tv_relax_starts)

        btnFind.setOnClickListener {
            val inputText = etYears.text.toString()
            if (inputText.isEmpty())
                Toast.makeText(this, getString(R.string.year_input_hint), Toast.LENGTH_SHORT).show()
            else {
                try {
                    val yearsArray = inputText.split(",")
                    val startYear = yearsArray[0].trim().toInt()
                    val endYear = yearsArray[1].trim().toInt()
                    if (endYear <= startYear)
                        Toast.makeText(
                            this,
                            getString(R.string.year_input_hint),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    else {
                        val numberOfEvents = calculateNumberOfEvents(startYear, endYear)
                        tvTotalEvents.text = numberOfEvents.toString()
                        tvRelaxStarts.text =
                            calculateRelaxStarts(startYear, endYear)
                    }

                } catch (e: Exception) {
                    Toast.makeText(this, getString(R.string.year_input_hint), Toast.LENGTH_SHORT)
                        .show()
                    e.printStackTrace()
                }
            }
        }
    }

    private fun calculateRelaxStarts(startYear: Int, endYear: Int): String {
        val relaxList = listOf("Thursday", "Friday", "Saturday")
        var count = 0
        for (i in startYear..endYear) {
            if (i % 4 == 0) {
                val dayName = getDay("23-07-$i")
                if (dayName in relaxList)
                    count++
            }
        }
        return count.toString()
    }

    private fun calculateNumberOfEvents(startYear: Int, endYear: Int): Int {
        var count = (endYear - startYear - 1) / 4
        if (endYear % 4 == 0)
            count++
        if (startYear % 4 == 0)
            count++
        return count
    }

    private fun getDay(input: String): String {
        val inFormat = SimpleDateFormat("dd-MM-yyyy")
        val date: Date = inFormat.parse(input)
        val outFormat = SimpleDateFormat("EEEE")
        return outFormat.format(date)
    }
}