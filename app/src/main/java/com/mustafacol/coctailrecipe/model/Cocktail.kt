package com.mustafacol.coctailrecipe.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_cocktails")
data class Cocktail(
    val cocktailId:String,
    val name: String,
    val category: String,
    val imageUrl :String,
){
    @PrimaryKey(autoGenerate = true)
    var id :Long=0
}