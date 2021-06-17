package com.georgebodea.tipcalculator

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import java.time.Year
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var etBill : EditText
    lateinit var tvTip : TextView
    lateinit var sbTip : SeekBar
    lateinit var btnMinus : Button
    lateinit var tvPersons : TextView
    lateinit var btnPlus : Button
    lateinit var btnCalculate : Button
    lateinit var tvTotalTip : TextView
    lateinit var tvTotal : TextView
    lateinit var tvTipPerPerson : TextView
    lateinit var tvTotalPerPerson : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etBill = findViewById(R.id.etBill)

        tvTip = findViewById(R.id.tvTip)
        sbTip = findViewById(R.id.sbTip)
        sbTip.setOnSeekBarChangeListener(tipSeekBarChangeListener)
        val progress = sbTip.progress
        tvTip.text = "Tip (%): $progress"

        btnMinus = findViewById(R.id.btnMinus)
        tvPersons = findViewById(R.id.tvPersons)
        btnPlus = findViewById(R.id.btnPlus)

        btnMinus.setOnClickListener {
            if (tvPersons.text == "1") {
                Toast.makeText(this, "You can not go under 1!", Toast.LENGTH_LONG).show()
            } else {
                var minus : Int = Integer.parseInt(tvPersons.text as String)
                minus--
                tvPersons.text = minus.toString()
            }
        }

        btnPlus.setOnClickListener {
            var plus : Int = Integer.parseInt(tvPersons.text as String)
            plus++
            tvPersons.text = plus.toString()
        }

        btnCalculate = findViewById(R.id.btnCalculate)
        btnCalculate.setOnClickListener {
            if (etBill.text.isEmpty()) {
                AlertDialog.Builder(this)
                    .setTitle("Error!")
                    .setMessage("You must enter the bill! The bill field cannot be left blank!")
                    .setPositiveButton(android.R.string.yes, DialogInterface.OnClickListener { dialog, which ->  })
                    .show()
            } else {
                calculate(etBill, sbTip, tvPersons)
            }
        }

        tvTotalTip = findViewById(R.id.tvTotalTip)
        tvTotal = findViewById(R.id.tvTotal)
        tvTipPerPerson = findViewById(R.id.tvTipPerPerson)
        tvTotalPerPerson = findViewById(R.id.tvTotalPerPerson)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.help -> help()
        }
        return super.onOptionsItemSelected(item)
    }

    private var tipSeekBarChangeListener : SeekBar.OnSeekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            tvTip.text = "Tip (%): $progress"
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {

        }
    }

    private fun help() {
        val calendar = Calendar.getInstance().get(Calendar.YEAR)

        AlertDialog.Builder(this)
            .setTitle("How to use this app")
            .setMessage(Html.fromHtml("This is a <b>Tip Calculator</b> app." +
                    "All you need to do is to enter the <b>bill</b>, set the <b>tip</b> and the <b>number of people</b> who will pay (if the bill will be splitted).<br><br>" +
                    "For suggestions, criticisms, errors, update requests and on-demand features, please contact me at <b>tipcalculator@zohomail.eu</b>.<br><br>" +
                    "<b>&copy; " + calendar + " All rights reserved!</b>"))
            .setPositiveButton(android.R.string.yes, DialogInterface.OnClickListener { dialog, which ->  })
            .show()
    }

    private fun calculate(etBill : EditText, sbTip : SeekBar, tvPersons : TextView) {
        val billString = etBill.text.toString()
        val bill = billString.toDouble()

        val tipString = sbTip.progress.toString()
        val tip = tipString.toInt()

        val personsString = tvPersons.text.toString()
        val persons = personsString.toInt()

        val totalTip = (bill * tip) / 100
        tvTotalTip.text = "Total Tip: $totalTip"

        val totalBill = bill + totalTip
        tvTotal.text = "Total: $totalBill"

        val tipPerPerson = totalTip / persons
        tvTipPerPerson.text = "Tip / Person: $tipPerPerson"

        val totalPerPerson = totalBill / persons
        tvTotalPerPerson.text = "Total / Person: $totalPerPerson"
    }
}