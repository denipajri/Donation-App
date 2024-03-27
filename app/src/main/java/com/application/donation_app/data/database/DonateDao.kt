package com.application.donation_app.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DonateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDonate(donate: Donate)

    @Query("SELECT * FROM donation WHERE id = :id")
    fun getDonate(id: Int): Donate

    @Query("SELECT * FROM donation")
    fun getAllDonates(): List<Donate>

    @Delete
    fun deleteDonate(donate: Donate)

    @Query("UPDATE donation SET name = :donaturName, tglDonasi = :tglDonasi, category = :category, amount = :amount WHERE id = :id")
    fun updateDonate(id: Int, donaturName: String, tglDonasi: String, category: String, amount: String)
}