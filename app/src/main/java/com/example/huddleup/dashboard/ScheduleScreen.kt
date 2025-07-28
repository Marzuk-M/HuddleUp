package com.example.huddleup.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.time.LocalDate

// Game model with time
data class Game(
    val id: Int,
    val team1: String,
    val team2: String,
    val date: LocalDate,
    val time: String,
    val place: String
)
val allGames = listOf(
    Game(1, "Demo FC", "Test United", LocalDate.of(2025, 7, 12), "7:30 PM", "Paramount Fine Foods Centre"),
    Game(2, "Red Wolves", "Blue Sharks", LocalDate.of(2025, 7, 19), "6:00 PM", "Iceland Arena"),
    Game(3, "Lions", "Bears", LocalDate.of(2025, 7, 30), "8:45 PM", "Clarkson Arena")
)


@Composable
fun ScheduleScreen(navController: NavController) {
    var selectedDate by remember { mutableStateOf(LocalDate.of(2025, 7, 24)) }
    var currentMonth by remember { mutableStateOf(selectedDate.withDayOfMonth(1)) }

    val gamesForSelectedDate = allGames.filter { it.date == selectedDate }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // <-- Removed the Back to Dashboard Button here

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton(onClick = { currentMonth = currentMonth.minusMonths(1) }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Previous Month")
            }
            Text(
                text = "${currentMonth.month.name} ${currentMonth.year}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            IconButton(onClick = { currentMonth = currentMonth.plusMonths(1) }) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Next Month")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        CalendarView(
            selectedDate = selectedDate,
            monthToDisplay = currentMonth,
            gameDates = allGames.map { it.date },
            onDateSelected = { selectedDate = it }
        )

        Spacer(modifier = Modifier.height(12.dp))
        Text("Games on $selectedDate", style = MaterialTheme.typography.titleMedium)

        LazyColumn {
            items(gamesForSelectedDate) { game ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            navController.navigate("game_details/${game.id}")
                        }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("${game.team1} vs ${game.team2}")
                        Text("Date: ${game.date}")
                        Text("Time: ${game.time}")
                    }
                }
            }
        }
    }
}


@Composable
fun CalendarView(
    selectedDate: LocalDate,
    monthToDisplay: LocalDate,
    gameDates: List<LocalDate>,
    onDateSelected: (LocalDate) -> Unit
) {
    val firstDayOfMonth = monthToDisplay.withDayOfMonth(1)
    val daysInMonth = monthToDisplay.lengthOfMonth()
    val firstDayOfWeek = (firstDayOfMonth.dayOfWeek.value % 7)

    val calendarDays = mutableListOf<LocalDate?>()
    repeat(firstDayOfWeek) { calendarDays.add(null) }
    for (day in 1..daysInMonth) {
        calendarDays.add(LocalDate.of(monthToDisplay.year, monthToDisplay.monthValue, day))
    }

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach {
                Text(
                    text = it,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Box(modifier = Modifier.fillMaxWidth().aspectRatio(1f)) {
            Column(modifier = Modifier.fillMaxSize()) {
                calendarDays.chunked(7).forEach { week ->
                    Row(modifier = Modifier.weight(1f).fillMaxWidth()) {
                        week.forEach { date ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .padding(2.dp)
                                    .clickable(enabled = date != null) {
                                        if (date != null) onDateSelected(date)
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                if (date != null) {
                                    val isSelected = date == selectedDate
                                    val hasGame = gameDates.any { it == date }

                                    Surface(
                                        modifier = Modifier.fillMaxSize(),
                                        color = when {
                                            isSelected -> MaterialTheme.colorScheme.primary
                                            hasGame -> MaterialTheme.colorScheme.secondaryContainer
                                            else -> MaterialTheme.colorScheme.surface
                                        },
                                        shape = MaterialTheme.shapes.small
                                    ) {
                                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                Text(
                                                    text = "${date.dayOfMonth}",
                                                    fontSize = 14.sp,
                                                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                                                )
                                                if (hasGame) {
                                                    Icon(
                                                        imageVector = Icons.Default.SportsSoccer,
                                                        contentDescription = "Game Day",
                                                        modifier = Modifier.size(14.dp),
                                                        tint = if (isSelected) MaterialTheme.colorScheme.onPrimary else Color.Gray
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        repeat(7 - week.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

