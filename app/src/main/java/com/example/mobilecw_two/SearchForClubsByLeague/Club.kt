package com.example.mobilecw_two.SearchForClubsByLeague

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Club(
    //mark this field as the primary key of the entity.
    @PrimaryKey(autoGenerate = true)//automatically generate a unique ID for each row
    val idTeam: Int,
    val Name: String,
    val strTeamShort: String,
    val strAlternate: String,
    val intFormedYear: String,
    val strLeague: String,
    val idLeague: String,
    val strStadium: String,
    val strKeywords: String,
    val strStadiumThumb: String,
    val strStadiumLocation: String,
    val intStadiumCapacity: String,
    val strWebsite: String,
    val strTeamJersey: String,
    val strTeamLogo: String
)
