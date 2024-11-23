package com.example.questionario.model

import com.example.questionario.model.entity.Pergunta
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.questionario.model.database.dao.CategoriaDao
import com.example.questionario.model.database.dao.PerguntaDao
import com.example.questionario.model.database.dao.RespostaDao

@Database(entities = [Pergunta::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun perguntaDao(): PerguntaDao
    abstract fun categoriaDao(): CategoriaDao
    abstract fun respostaDao(): RespostaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
