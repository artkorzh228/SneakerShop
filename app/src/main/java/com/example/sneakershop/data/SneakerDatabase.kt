package com.example.sneakershop.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sneakershop.model.Sneaker

@Database(entities = [Sneaker::class], version = 2, exportSchema = false)
abstract class SneakerDatabase : RoomDatabase() {

    abstract fun sneakerDao(): SneakerDao

    companion object {
        @Volatile
        private var INSTANCE: SneakerDatabase? = null

        fun getDatabase(context: Context): SneakerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SneakerDatabase::class.java,
                    "sneaker_database"
                )
                    // TODO: добавить объекты Migration перед релизом в production
                    // WARNING: fallbackToDestructiveMigration() стирает все данные при изменении схемы
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
