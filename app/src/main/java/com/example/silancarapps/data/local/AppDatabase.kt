package com.example.silancarapps.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.silancarapps.data.local.dao.PengajuanAktaKelahiranDao
import com.example.silancarapps.data.local.dao.PengajuanAktaKematianDao
import com.example.silancarapps.data.local.dao.PengajuanKKDao
import com.example.silancarapps.data.local.dao.PengajuanKTPDao

@Database(entities = [PengajuanKK::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pengajuanKKDao(): PengajuanKKDao

    abstract fun pengajuanKTPDao(): PengajuanKTPDao

    abstract fun pengajuanAktaKelahiranDao(): PengajuanAktaKelahiranDao

    abstract fun pengajuanAktaKematianDao(): PengajuanAktaKematianDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "silancar_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
