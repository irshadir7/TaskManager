package com.cyber.taskmanager.presentation.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cyber.taskmanager.presentation.ui.theme.LocalAppColors
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun HomeScreen(
    navController: NavController
) {
    val systemUiController = rememberSystemUiController()
    val colors = LocalAppColors.current

    SideEffect {
        systemUiController.setSystemBarsColor(
            Color.Transparent,
            darkIcons = false
        )
    }

    Scaffold(
        containerColor = colors.Black,
        topBar = {
            TopBarComponent()
        }
    ) { paddingValues ->
        Content(paddingValues, navController)
    }
}

@Composable
fun Content(
    paddingValues: PaddingValues,
    navController: NavController
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .padding(top = 5.dp)
    ) {
        InlineTitleIconComponent()
        WeekCalenderSection(navController)
    }
}

