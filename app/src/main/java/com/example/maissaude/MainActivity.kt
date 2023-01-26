package com.example.maissaude


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    // private lateinit var btnImc: LinearLayout
    private lateinit var rvMain: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainItens = mutableListOf<MainItem>()
        mainItens.add(
            MainItem(
                id = 1,
                drawableId = R.drawable.ic_baseline_wb_sunny_24,
                textStringId = R.string.label_imc,
                //color = Color.GRAY
            )
        )
        mainItens.add(
            MainItem(
                id = 2,
                drawableId = R.drawable.ic_baseline_sports_handball_24,
                textStringId = R.string.label_tmb,
                //color = Color.GRAY
            )
        )
        mainItens.add(
            MainItem(
                id = 3,
                drawableId = R.drawable.ic_baseline_water_damage_24,
                textStringId = R.string.label_agua,
                //color = Color.GRAY
            )
        )
        mainItens.add(
            MainItem(
                id = 4,
                drawableId = R.drawable.ic_baseline_info_24,
                textStringId = R.string.label_informacoes,
                //color = Color.GRAY
            )
        )

        val adapter = MainAdapter(mainItens) { id ->
            when (id) {
                1 -> {
                    val intent = Intent(this@MainActivity, ImcActivity::class.java)
                    startActivity(intent)
                }
                2 -> {
                    //val intent = Intent(this@MainActivity, TmbActivity::class.java)
                    val intent = Intent(this@MainActivity, TmbActivity::class.java)
                    startActivity(intent)
                }

                3 -> {
                    //val intent = Intent(this@MainActivity, TmbActivity::class.java)
                    val intent = Intent(this@MainActivity, BebaAguaActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        rvMain = findViewById(R.id.rv_main)
        rvMain.adapter = adapter
        rvMain.layoutManager = GridLayoutManager(this, 2)

    }

    //Classe criada dentro da MainActivity, pq quero que tenha visibilidade só dentro dela
    private inner class MainAdapter(
        private val mainItens: List<MainItem>,
        //private val onItemClickListener: OnItemClickListener

        private val onItemClickListener: (Int) -> Unit,

        ) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {


        //Método responsável por informar a RecyclerView qual é o XML da célula específica(item)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

            val view = layoutInflater.inflate(R.layout.main_item, parent, false)
            return MainViewHolder(view)
        }

        //Método que será disparado toda vez que houver uma rolagem na tela e for necessário trocar o conteúdo da célula
        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

            val itemCurrent = mainItens[position]
            holder.bind(itemCurrent)

        }

        //Método resposável por informar quantas células essa listagens terá
        override fun getItemCount(): Int {

            return mainItens.size
        }

        //Método de click da lista
        //É a classe da célula em si
        private inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(item: MainItem) {

                val img: ImageView = itemView.findViewById(R.id.item_img_icon)
                val name: TextView = itemView.findViewById(R.id.item_txt_name)
                val container: LinearLayout = itemView.findViewById(R.id.item_container_imc)

                img.setImageResource(item.drawableId)
                name.setText(item.textStringId)
                //container.setBackgroundColor(item.color)

                container.setOnClickListener {
                    //aqui tá invocando uma referência de uma função
                    onItemClickListener.invoke(item.id)

                }
            }
        }
    }
}