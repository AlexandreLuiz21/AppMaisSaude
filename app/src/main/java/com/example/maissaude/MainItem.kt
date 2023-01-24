package com.example.maissaude

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

//Menu da tela principal
data class MainItem(

    val id: Int,
    @DrawableRes val drawableId: Int,
    @StringRes val textStringId: Int,
    //val color: Int
)
