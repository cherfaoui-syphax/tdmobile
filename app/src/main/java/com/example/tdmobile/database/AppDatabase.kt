package com.example.tdmobile.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tdmobile.dao.ParkingDao
import com.example.tdmobile.dao.ReservationDao
import com.example.tdmobile.entity.ParkingEntity
import com.example.tdmobile.entity.ReservationEntity

@Database(entities = [ParkingEntity::class,ReservationEntity::class],version=1)
abstract class AppDatabase:RoomDatabase() {

    abstract fun getReservationDao(): ReservationDao
    abstract fun getParkingDao():ParkingDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun buildDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
               synchronized(this) {
                    INSTANCE = Room.databaseBuilder(context,AppDatabase::class.java, "reservation_db").allowMainThreadQueries().build()
               }
            }
            return INSTANCE
        }
    }

}