package com.mustafacol.coctailrecipe.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mustafacol.coctailrecipe.model.BaseDrink
import com.mustafacol.coctailrecipe.model.Cocktail

// Class that are used in database should be specified and also version number should be specified.
@Database(entities = [Cocktail::class], version = 2)
abstract class CocktailsDatabase() : RoomDatabase() {
    abstract val cocktailsDAO: CocktailsDAO


    companion object {
        @Volatile
        private var _instance: CocktailsDatabase? = null

        fun databaseInstance(context: Context): CocktailsDatabase {
            synchronized(this) {
                var instance = _instance
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        CocktailsDatabase::class.java,
                        "cocktails_database"
                    ).fallbackToDestructiveMigration().build()
                }

                return instance
            }
        }
    }
}