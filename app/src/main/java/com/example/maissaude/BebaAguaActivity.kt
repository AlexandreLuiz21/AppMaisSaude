package com.example.maissaude

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.maissaude.model.CalcularIngestaoDiaria
import java.text.NumberFormat
import java.util.*

class BebaAguaActivity : AppCompatActivity() {

    private lateinit var edit_peso : EditText
    private lateinit var edit_idade : EditText
    private lateinit var btn_calcular : Button
    private lateinit var txt_resultado_ml : TextView

    private lateinit var calcularIngestaoDiaria : CalcularIngestaoDiaria
    private var resultadoMl = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beba_agua)

        //iniciando todos os componentes
        iniciarComponentes()

        calcularIngestaoDiaria = CalcularIngestaoDiaria()

        btn_calcular.setOnClickListener {
            if (!validade()){
                //Toast.makeText(this, R.string.fields_messages, Toast.LENGTH_SHORT).show()
                AlertDialog.Builder(this)
                    .setMessage(getString(R.string.fields_messages))
                    .setPositiveButton(android.R.string.ok) { dialog, which ->
                        //aqui vai rodar depois do click
                    }
                    .create()
                    .show()
                return@setOnClickListener
            } else {
                val peso = edit_peso.text.toString().toDouble()
                val idade = edit_idade.text.toString().toInt()

                calcularIngestaoDiaria.calcularTotalMl(peso, idade)

                resultadoMl = calcularIngestaoDiaria.resultadoMl()
                val formatar = NumberFormat.getNumberInstance(Locale("pt", "BR"))
                formatar.isGroupingUsed = false
                txt_resultado_ml.text = formatar.format(resultadoMl) + " " + "ml"

            }

            //código pra esconder o teclado
            val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    private fun iniciarComponentes(){
        edit_peso = findViewById(R.id.edit_agua_weight)
        edit_idade = findViewById(R.id.edit_agua_age)
        btn_calcular = findViewById(R.id.btn_calculate)
        txt_resultado_ml = findViewById(R.id.txt_result_ml)
    }


    private fun validade(): Boolean {

        if (
            edit_peso.text.toString().isNotEmpty()
            && edit_idade.text.toString().isNotEmpty()
            && !edit_peso.text.toString().startsWith("0")
            && !edit_idade.text.toString().startsWith("0")
        ) {
            return true
        }
        return false
    }
}