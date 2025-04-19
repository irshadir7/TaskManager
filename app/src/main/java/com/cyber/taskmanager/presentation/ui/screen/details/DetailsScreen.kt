package com.cyber.taskmanager.presentation.ui.screen.details

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.platform.LocalView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.cyber.taskmanager.presentation.ui.theme.LocalAppColors
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun DetailsScreen(
    navController: NavController,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val taskDetails by viewModel.taskDetails.collectAsStateWithLifecycle()
    val systemUiController = rememberSystemUiController()
    val colors = LocalAppColors.current

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Transparent,
            darkIcons = true
        )
    }

    val view = LocalView.current
    val screenWidth = view.width
    val screenHeight = view.height
    val animatedRadius = remember { Animatable(0f) }
    var animationPlayed by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val finalRadius = kotlin.math.hypot(screenWidth.toFloat(), screenHeight.toFloat())
        animatedRadius.animateTo(
            targetValue = finalRadius,
            animationSpec = tween(durationMillis = 600)
        )
        animationPlayed = true
    }

    val center = Offset(screenWidth / 2f, screenHeight / 2f)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        containerColor = colors.Black,
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .circularRevealClip(center, animatedRadius.value)
        ) {
            DetailsContent(
                paddingValues = paddingValues,
                task = taskDetails,
                onBackClick = { navController.popBackStack() },
                updateTaskStatus = { newStatus ->
                    viewModel.updateTaskStatus(newStatus)
                }
            )
        }
    }
}

fun Modifier.circularRevealClip(center: Offset, radius: Float): Modifier = drawWithContent {
    val path = Path().apply {
        addOval(androidx.compose.ui.geometry.Rect(center - Offset(radius, radius), Size(radius * 2, radius * 2)))
    }

    clipPath(path) {
        this@drawWithContent.drawContent()
    }
}
