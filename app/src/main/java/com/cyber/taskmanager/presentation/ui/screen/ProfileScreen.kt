package com.cyber.taskmanager.presentation.ui.screen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cyber.taskmanager.R
import com.cyber.taskmanager.presentation.ui.theme.LocalAppColors
import com.cyber.taskmanager.presentation.ui.theme.vm.ThemeViewModel

@Composable
fun ProfileScreen(
    themeViewModel: ThemeViewModel = viewModel()
) {
    val colors = LocalAppColors.current
    val isDarkTheme by themeViewModel.isDark.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colors.Black
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Header
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colors.Black.copy(alpha = 0.6f)
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Profile Image with border
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                            .background(colors.Blue.copy(alpha = 0.2f))
                            .padding(5.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.member2),
                            contentDescription = "User Avatar",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Irshad Madappat",
                        color = colors.White,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "mpirshu06@gmail.com",
                        color = colors.Grey,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Settings Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = colors.Black.copy(alpha = 0.6f)
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                )
            ) {
                ListItem(
                    headlineContent = { Text("Dark Mode", color = colors.White) },
                    leadingContent = {
                        Icon(
                            Icons.Default.DarkMode,
                            contentDescription = "Dark Mode",
                            tint = colors.Blue
                        )
                    },
                    trailingContent = {
                        Switch(
                            checked = isDarkTheme,
                            onCheckedChange = { themeViewModel.toggleTheme() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = colors.Blue,
                                checkedTrackColor = colors.Blue.copy(alpha = 0.5f),
                                uncheckedThumbColor = colors.Grey,
                                uncheckedTrackColor = colors.Grey.copy(alpha = 0.5f)
                            )
                        )
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = colors.Black.copy(alpha = 0.0f)
                    )
                )
            }
        }
    }
}