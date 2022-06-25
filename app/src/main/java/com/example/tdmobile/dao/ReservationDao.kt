package com.example.tdmobile.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.tdmobile.entity.ReservationEntity
import java.sql.Date


@Dao
interface ReservationDao:BaseDao<ReservationEntity> {

    @Query("SELECT * FROM RESERVATIONS")
    fun getAllReservations():List<ReservationEntity>

//    @Query("SELECT * FROM RESERVATIONS WHERE  :targetDate LIKE start_time")
//    fun getTodayReservation(targetDate: String): List<ReservationEntity>

    @Query("DELETE  FROM RESERVATIONS")
    fun truncateReservations()

}