package com.example.maissaude.model

interface OnListClickListener {

    fun onClick(id: Int, type: String)
    fun onLongClick(position: Int, calc: Calc)
}