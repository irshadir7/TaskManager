package com.cyber.taskmanager.presentation.ui.screen.add

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cyber.taskmanager.presentation.ui.theme.LocalAppColors
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    navController: NavController,
    viewModel: AddTaskViewModel = hiltViewModel()
) {
    val colors = LocalAppColors.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )

        var isSheetOpen by remember {
            mutableStateOf(true)
        }

        val scope = rememberCoroutineScope()

        if (isSheetOpen) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    isSheetOpen = false
                    navController.popBackStack()
                },
                containerColor = colors.Black,
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                dragHandle = {
                    Box(
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .width(40.dp)
                            .height(4.dp)
                            .clip(RoundedCornerShape(50))
                            .background(colors.Grey.copy(alpha = 0.5f))
                    )
                },
                modifier = Modifier.padding(top = 7.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    // Modern header with action buttons
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        // Cancel button with icon
                        FilledTonalButton(
                            onClick = {
                                scope.launch {
                                    sheetState.hide()
                                    viewModel.resetTaskState()
                                    navController.popBackStack()
                                }
                            },
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = colors.Black.copy(alpha = 0.5f),
                                contentColor = colors.Blue
                            ),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = "Cancel",
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Cancel",
                                fontWeight = FontWeight.Medium
                            )
                        }

                        // Title
                        Text(
                            text = "New Task",
                            style = MaterialTheme.typography.titleMedium,
                            color = colors.White,
                            fontWeight = FontWeight.SemiBold
                        )

                        // Done button with icon
                        FilledTonalButton(
                            onClick = {
                                viewModel.onEvent(TaskFormEvent.Submit)
                            },
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = colors.Blue.copy(alpha = 0.2f),
                                contentColor = colors.Blue
                            ),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "Done",
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Rounded.Check,
                                contentDescription = "Done",
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    // Divider for visual separation
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        color = colors.Grey.copy(alpha = 0.2f)
                    )

                    // Animated content entry
                    AnimatedVisibility(visible = true) {
                        CreateTaskContent(onHideSheet = {
                            if (it) {
                                scope.launch {
                                    sheetState.hide()
                                    viewModel.resetTaskState()
                                    navController.popBackStack()
                                }
                            }
                        })
                    }
                }
            }
        }
    }
}