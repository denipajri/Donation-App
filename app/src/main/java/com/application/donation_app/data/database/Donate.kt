package com.application.donation_app.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "donation")
data class Donate(
    @ColumnInfo(name = "name")
    var donaturName: String,

    @ColumnInfo(name = "tglDonasi")
    var tglDonasi: String,

    @ColumnInfo(name = "category")
    var category: String,

    @ColumnInfo(name = "amount")
    var amount: String,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)