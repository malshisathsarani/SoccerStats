package com.example.mobilecw_two.SearchForClubs

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.example.mobilecw_two.SearchForClubs.ui.theme.MobileCW_TwoTheme
import com.example.mobilecw_two.SearchForClubsByLeague.Club
import com.example.mobilecw_two.SearchForClubsByLeague.ClubDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class SearchForClubs : ComponentActivity() {
    //variable for the ClubDatabase instance.
    private lateinit var clubDatabase: ClubDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Building the Room database instance.
        clubDatabase= Room.databaseBuilder(this, ClubDatabase::class.java, "search-club-database").build()

        setContent {
            MobileCW_TwoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SearchForClubsUI()
                }
            }
        }
    }

    @Composable
    fun SearchForClubsUI() {
        // Mutable state variables for the list of clubs and the search query.
        var clubs by rememberSaveable { mutableStateOf(listOf<Club>()) } // Assuming club names are stored as Strings
        var club by rememberSaveable { mutableStateOf("") }
        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // A TextField Composable for the search query input.
            TextField(
                value = club,
                onValueChange = { club = it },
                placeholder = { Text("Text Here") }
            )

            //Search Button
            Button(onClick = {
                CoroutineScope(Dispatchers.IO).launch {

                    // Searching the database for clubs that match the query.
                    clubs = clubDatabase.clubDao().searchClubs(club)
                    Log.d("data", clubs.toString())
                    withContext(Dispatchers.Main) {

                    }
                }
            }) {
                Text(text = "Search")
            }

            // displays the search results using lazy column.
            LazyColumn {
                items(clubs) { club ->

                    // Displaying the club name and league.
                    Text(
                        text = "${club.Name} ",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.Red)
                    Text(
                        text = club.strLeague,
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.Blue
                    )
                    // Loading and displaying the club logo.
                    club.strTeamLogo.let { url ->
                        val imageBitmap = loadImageFromUrl(url)
                        imageBitmap?.let {
                            Image(
                                bitmap = it.asImageBitmap(),
                                contentDescription = "Club Logo",
                                modifier = Modifier.size(200.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }

    @Composable
    //loads an image from a URL and returns it as a Bitmap.
    fun loadImageFromUrl(url: String): Bitmap?{
        var bitmap by rememberSaveable { mutableStateOf<Bitmap?>(null) }
        LaunchedEffect(url) {
            bitmap = withContext(Dispatchers.IO) {
                try {
                    // Opening a connection to the URL and downloading the image.
                    val connection = URL(url).openConnection() as HttpURLConnection
                    connection.doInput = true
                    connection.connect()
                    val input = connection.inputStream
                    BitmapFactory.decodeStream(input)
                } catch (e: Exception) {
                    null
                }
            }
        }
        return bitmap
    }

}