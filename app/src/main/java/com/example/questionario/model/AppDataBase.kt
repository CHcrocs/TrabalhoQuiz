package com.example.questionario.model

import com.example.questionario.model.entity.Pergunta
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `categoria` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nome` TEXT NOT NULL)")
                database.execSQL("ALTER TABLE `pergunta` ADD COLUMN `categoriaId` INTEGER NOT NULL DEFAULT 0 REFERENCES `categoria`(`id`) ON DELETE CASCADE")
                database.execSQL("CREATE TABLE IF NOT EXISTS `resposta` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `respostaCorreta` TEXT NOT NULL, `respostaIncorreta1` TEXT NOT NULL, `respostaIncorreta2` TEXT NOT NULL, `respostaIncorreta3` TEXT NOT NULL)")
                database.execSQL("ALTER TABLE `pergunta` ADD COLUMN `respostaId` INTEGER NOT NULL DEFAULT 0 REFERENCES `resposta`(`id`) ON DELETE CASCADE")
            }
        }
    }
}
