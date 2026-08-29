package com.example.silancarapps.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.silancarapps.data.local.dao.*

@Database(
    entities = [
        PengajuanAktaKelahiran::class,
        PengajuanAktaKematian::class,
        PengajuanKIA::class,
        PengajuanKK::class,
        PengajuanKTP::class,
        User::class
    ],
    version = 10,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pengajuanAktaKelahiranDao(): PengajuanAktaKelahiranDao
    abstract fun pengajuanAktaKematianDao(): PengajuanAktaKematianDao
    abstract fun pengajuanKIADao(): PengajuanKIADao
    abstract fun pengajuanKKDao(): PengajuanKKDao
    abstract fun pengajuanKTPDao(): PengajuanKTPDao
    abstract fun userDao(): UserDao

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
