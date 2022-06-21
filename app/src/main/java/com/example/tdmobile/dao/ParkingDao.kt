package com.example.tdmobile.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.tdmobile.entity.ParkingEntity


@Dao
    interface ParkingDao:BaseDao<ParkingEntity> {

        @Query("SELECT * FROM parkings")
        fun getAllParkings():List<ParkingEntity>


    }
