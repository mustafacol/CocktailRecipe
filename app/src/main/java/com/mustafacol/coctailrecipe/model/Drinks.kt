package com.mustafacol.coctailrecipe.model

import com.google.gson.annotations.SerializedName

class Drinks {
    @SerializedName("drinks" ) var drinks : ArrayList<Drinks> = arrayListOf()

}