package com.example.maissaude


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.maissaude.model.Calc
import com.example.maissaude.model.OnListClickListener
import java.text.SimpleDateFormat
import java.util.*


class ListCalcActivity : AppCompatActivity(), OnListClickListener {

    private lateinit var adapter: ListCalcAdapter
    private lateinit var result: MutableList<Calc>

    private lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_calc)

        result = mutableListOf<Calc>()
        //val adapter = ListCalcAdapter(result)

        adapter = ListCalcAdapter(result, this)

        rv = findViewById(R.id.rv_list)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        val type =
            intent?.extras?.getString("type") ?: throw IllegalStateException("type not found")

        Thread {
            val app = application as App
            val dao = app.db.calcDao()
            val response = dao.getRegisterByType(type)

            runOnUiThread {
                //Log.i("Teste", "resposta: $response")
                result.addAll(response)
                adapter.notifyDataSetChanged()
            }
        }.start()

    }

    override fun onClick(id: Int, type: String) {

        when (type) {
            "imc" -> {
                val intent = Intent(this, ImcActivity::class.java)
                // FIXME: passando o ID do item que precisa ser atualizado, ou seja, na outra tela
                // FIXME: vamos buscar o item e suas propriedades com esse ID
                intent.putExtra("updateId", id)
                startActivity(intent)
            }
            "tmb" -> {
                val intent = Intent(this, TmbActivity::class.java)
                intent.putExtra("updateId", id)
                startActivity(intent)
            }
        }
        finish()
    }

    override fun onLongClick(position: Int, calc: Calc) {

        AlertDialog.Builder(this)
            .setMessage(getString(R.string.delete_message))
            .setNegativeButton(android.R.string.cancel) { dialog, which ->
            }
            .setPositiveButton(android.R.string.ok) { dialog, which ->
                Thread {
                    val app = application as App
                    val dao = app.db.calcDao()

                    // FIXME: exclui o item que foi clicado com long-click
                    val response = dao.delete(calc)

                    if (response > 0) {
                        runOnUiThread {
                            // FIXME: remove da lista e do adapter o item
                            result.removeAt(position)
                            adapter.notifyItemRemoved(position)
                        }
                    }
                }.start()

            }
            .create()
            .show()

    }

    private inner class ListCalcAdapter(
        private val listCalc: List<Calc>,
        private val listener: OnListClickListener
    ) : RecyclerView.Adapter<ListCalcAdapter.ListCalcViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCalcViewHolder {

            val view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false)
            return ListCalcViewHolder(view)
        }


        override fun onBindViewHolder(holder: ListCalcViewHolder, position: Int) {

            val itemCurrent = listCalc[position]
            holder.bind(itemCurrent)

        }

        override fun getItemCount(): Int {

            return listCalc.size
        }

        private inner class ListCalcViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(item: Calc) {

                val tv = itemView as TextView

                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR"))
                val nome = item.nome
                val res = item.res
                val data = sdf.format(item.createDate)

                tv.text = getString(R.string.list_response, nome, res, data)

                // FIXME: usado para delegar a quem estiver implementando a interface (Activity) o evento para
                // FIXME: excluir o item da lista.
                tv.setOnLongClickListener {
                    // FIXME: precisamos da posição corrente (adapterPosition) para saber qual item da lista
                    // FIXME: deve ser removido da recyclerview usando o notify do Adapter
                    listener.onLongClick(adapterPosition, item)
                    true
                }

                // FIXME: usado para delegar a quem estiver implementando a interface (Activity) o evento para
                // FIXME: editar um item da lista
                tv.setOnClickListener {
                    listener.onClick(item.id, item.type)
                }
            }
        }
    }
}