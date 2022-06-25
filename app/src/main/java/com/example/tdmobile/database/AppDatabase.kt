package com.example.tdmobile.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tdmobile.dao.ReservationDao
import com.example.tdmobile.entity.ParkingEntity
import com.example.tdmobile.entity.ReservationEntity

@Database(entities = [ReservationEntity::class],version=2)
abstract class AppDatabase:RoomDatabase() {

    abstract fun getReservationDao(): ReservationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun buildDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
               synchronized(this) {
                    INSTANCE = Room.databaseBuilder(context,AppDatabase::class.java, "reservation_db").allowMainThreadQueries().fallbackToDestructiveMigration().build()
               }
            }
            return INSTANCE
        }
    }

}