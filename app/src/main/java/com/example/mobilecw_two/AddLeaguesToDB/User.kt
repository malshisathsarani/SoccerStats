package com.example.mobilecw_two.AddLeaguesToDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    // The PrimaryKey annotation is used to mark this field as the primary key of the entity
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val strLeague: String,
    val strLeagueAlternate:String,
    val strSport: String
)
// This is a list of User objects.
val leagues = listOf(
    User(4328, "English Premier League", "Soccer", "Premier League, EPL"),
    User(4329, "English League Championship", "Soccer", "Championship"),
    User(4330, "Scottish Premier League", "Soccer", "Scottish Premiership, SPFL"),
    User(4331, "German Bundesliga", "Soccer", "Bundesliga, Fußball-Bundesliga"),
    User(4332, "Italian Serie A", "Soccer", "Serie A"),
    User(4334, "French Ligue 1", "Soccer", "Ligue 1 Conforama"),
    User(4335, "Spanish La Liga", "Soccer", "LaLiga Santander, La Liga"),
    User(4336, "Greek Superleague Greece", "Soccer", ""),
    User(4337, "Dutch Eredivisie", "Soccer", "Eredivisie"),
    User(4338, "Belgian First Division A", "Soccer", "Jupiler Pro League"),
    User(4339, "Turkish Super Lig", "Soccer", "Super Lig"),
    User(4340, "Danish Superliga", "Soccer", ""),
    User(4344, "Portuguese Primeira Liga", "Soccer", "Liga NOS"),
    User(4346, "American Major League Soccer", "Soccer", "MLS, Major League Soccer"),
    User(4347, "Swedish Allsvenskan", "Soccer", "Fotbollsallsvenskan"),
    User(4350, "Mexican Primera League", "Soccer", "Liga MX"),
    User(4351, "Brazilian Serie A", "Soccer", ""),
    User(4354, "Ukrainian Premier League", "Soccer", ""),
    User(4355, "Russian Football Premier League", "Soccer", "Чемпионат России по футболу"),
    User(4356, "Australian A-League", "Soccer", "A-League"),
    User(4358, "Norwegian Eliteserien", "Soccer", "Eliteserien"),
    User(4359, "Chinese Super League", "Soccer", "")
)