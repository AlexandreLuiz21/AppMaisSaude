package com.example.maissaude

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class AguaActivity : AppCompatActivity() {

    private lateinit var editWeight: EditText
    private lateinit var editAge: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agua)

        editWeight = findViewById(R.id.edit_agua_weight)
        editAge = findViewById(R.id.edit_agua_age)


        val btnSend: Button = findViewById(R.id.btn_agua_send)

        btnSend.setOnClickListener {
            if (!validate()){
                //Toast.makeText(this, R.string.fields_messages, Toast.LENGTH_SHORT).show()
                AlertDialog.Builder(this)
                    .setMessage(getString(R.string.fields_messages))
                    .setPositiveButton(android.R.string.ok) { dialog, which ->
                        //aqui vai rodar depois do click
                    }
                    .create()
                    .show()



                return@setOnClickListener
            }

            val weight = editWeight.text.toString().toInt()
            val age = editAge.text.toString().toInt()

            val result = calcularAgua(weight, age)
            //val response = calcularQtdCoposAgua(result)

            AlertDialog.Builder(this)
                .setMessage(getString(R.string.agua_response, result))
                .setPositiveButton(android.R.string.ok) { dialog, which ->
                    //aqui vai rodar depois do click
                }
                .create()
                .show()

        }
    }


    //Validação do furmulário
    private fun validate(): Boolean {

        return (editWeight.text.toString().isNotEmpty()
                &&editAge.text.toString().isNotEmpty()
                && !editWeight.text.toString().startsWith("0")
                && !editAge.text.toString().startsWith("0"))

    }

    private fun calcularAgua(weight: Int, age: Int): Double{

        if (age <= 17){
           return weight * (40.0 / 1000)
        } else if (age in 18..55){
            return weight * (35.0 / 1000)
        } else if (age in 55..65){
            return weight * (30.0 / 1000)
        } else if (age > 65){
            return weight * (25.0 / 1000)
        } else {
            return 0.0
        }
    }

    private fun calcularQtdCoposAgua(agua: Double): Double{
        return (agua * 1000) / 310
    }
}