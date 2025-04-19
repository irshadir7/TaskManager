package com.cyber.taskmanager.presentation.ui.screen.home


import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.cyber.taskmanager.R
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.cyber.taskmanager.data.local.Task
import com.cyber.taskmanager.data.local.ThemePreference
import com.cyber.taskmanager.presentation.ui.screen.navigation.Destinations
import com.cyber.taskmanager.presentation.ui.theme.LocalAppColors
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.DismissValue.DismissedToEnd
import androidx.compose.material.DismissValue.DismissedToStart
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DeleteForever
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import kotlinx.coroutines.launch
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable


@Composable
fun TopBarComponent() {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 27.dp)
            .fillMaxWidth()
            .height(30.dp)

    ) {
    }
}

@Composable
fun InlineTitleIconComponent() {
    val myId = "inlineContent"
    val text = buildAnnotatedString {
        append("Manage\nyour tasks")
    }
    val colors = LocalAppColors.current

    val inlineContent = mapOf(
        Pair(
            myId,
            InlineTextContent(
                Placeholder(
                    width = 60.sp,
                    height = 60.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pencil),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 8.dp, top = 7.dp)
                        .size(50.dp)
                )
            }
        )
    )

    // Calculate the progress

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Title with inline icon
        Text(
            text = text,
            color = colors.White,
            fontWeight = FontWeight.Normal,
            fontSize = 35.sp,
            lineHeight = 48.sp,
            modifier = Modifier.weight(1f), // Allow title to take up remaining space
            inlineContent = inlineContent
        )

        // Progress indicator at the end of the title
        val viewModel: TaskViewModel = hiltViewModel()
        val allTasks by viewModel.allTasks.collectAsStateWithLifecycle()
        TaskCompletionProgress(allTasks)
        Spacer(modifier = Modifier.width(24.dp))

    }
}

@Composable
fun TaskCompletionProgress(allTasks: List<Task>) {
    // Calculate the percentage of completed tasks
    val completedTasks = allTasks.count { it.status == "Completed" }
    val totalTasks = allTasks.size
    val completionPercentage = if (totalTasks > 0) (completedTasks / totalTasks.toFloat()) * 100 else 0f

    // Animate the progress value
    val progress by animateFloatAsState(
        targetValue = completionPercentage,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
    )

    // Define the size and colors for the progress indicator
    val size = 100.dp
    val strokeWidth = 10.dp
    val progressColor = Color.Green
    val colors = LocalAppColors.current

    // Conditionally show progress only if it's not 0
    if (progress > 0) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(size)
                .padding(16.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                // Draw the progress circle
                drawArc(
                    color = progressColor,
                    startAngle = -90f,
                    sweepAngle = 360f * (progress / 100f),
                    useCenter = false,
                    style = Stroke(width = strokeWidth.toPx())
                )
            }

            // Display the percentage inside the circle
            Text(
                text = "${progress.toInt()}%",
                style = TextStyle(fontSize = 20.sp, color = colors.White, fontWeight = FontWeight.Bold)
            )
        }
    }
}


@Composable
fun WeekCalenderSection(
    navController: NavController,
    viewModel: TaskViewModel = hiltViewModel()
) {
    val dateState by viewModel.dateUiState.collectAsStateWithLifecycle()

    val listTasks = dateState.listTasks
    val selectedDate = dateState.selectedDate
    val colors = LocalAppColors.current
    val startDate = remember { selectedDate.minusDays(30) }
    val endDate = remember { selectedDate.plusDays(30) }

    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }

    val state = rememberWeekCalendarState(
        startDate = startDate,
        endDate = endDate,
        firstVisibleWeekDate = selectedDate,
        firstDayOfWeek = firstDayOfWeek
    )

    Column {
        WeekCalendar(
            modifier = Modifier
                .background(color = colors.Black)
                .padding(horizontal = 20.dp, vertical = 5.dp),
            state = state,
            dayContent = { day ->
                Day(date = day.date, isSelected = selectedDate == day.date) { clicked ->
                    if (selectedDate != clicked) {
                        viewModel.setSelectedDate(clicked)
                    }
                }
            }
        )

        AnimatedContent(
            targetState = dateState.hasTasks,
            transitionSpec = {
                fadeIn(animationSpec = tween(200)).togetherWith(
                    fadeOut(animationSpec = tween(200)))
            },
            label = "",
        ) { targetState ->
            when (targetState) {
                true -> {
                    TasksListSection(
                        listTasks = listTasks,
                        navController = navController,
                        onTaskComplete = { task ->
                            viewModel.updateStatus(task.id, "Completed")
                        },
                        onTaskDelete = { task ->
                            viewModel.deleteTask(task)
                        },
                        onUndoComplete = { task ->
                            viewModel.updateStatus(task.id, "Pending")
                        },
                        onUndoDelete = { task ->
                            viewModel.undoDeleteTask(task)
                        }
                    )

                }
                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(340.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.nothing),
                            contentDescription = null,
                            modifier = Modifier.size(145.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Column(
                           horizontalAlignment = Alignment.CenterHorizontally,
                          verticalArrangement = Arrangement.spacedBy(9.dp)
                        ) {
                            Text(
                                text = "No Tasks",
                                fontSize = 17.sp,
                                
                                color = colors.White,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "There are no specific tasks tied to this date.",
                                fontSize = 13.sp,
                                
                                color = colors.White,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

private val dateFormatter = DateTimeFormatter.ofPattern("dd")

@Composable
fun Day(
    date: LocalDate,
    isSelected: Boolean,
    onClick: (LocalDate) -> Unit
) {
    val colors = LocalAppColors.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onClick(date) }
        ,
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .width(45.dp)
                    .height(73.dp)
                    .background(
                        color = if (isSelected) colors.White else colors.Black,
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = date.dayOfWeek.toString().substring(0, 3).lowercase()
                            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                        fontSize = 14.sp,
                        color =  if (isSelected) colors.Black else Color.Gray,
                        
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = dateFormatter.format(date),
                        fontSize = 15.sp,
                        
                        color = if (isSelected) colors.Black else colors.White,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
fun <T> MutableList<T>.move(fromIndex: Int, toIndex: Int) {
    if (fromIndex == toIndex) return
    val item = removeAt(fromIndex)
    add(toIndex, item)
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TasksListSection(
    listTasks: List<Task>,
    navController: NavController,
    onTaskComplete: (Task) -> Unit,
    onTaskDelete: (Task) -> Unit,
    onUndoComplete: (Task) -> Unit,
    onUndoDelete: (Task) -> Unit,
    onTaskReorder: (List<Task>) -> Unit = {}
) {
    val haptics = LocalHapticFeedback.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val taskList = remember { mutableStateListOf<Task>() }
    LaunchedEffect(listTasks) {
        taskList.clear()
        taskList.addAll(listTasks)
    }
    val state = rememberReorderableLazyListState(
        onMove = { from, to ->
            taskList.move(from.index, to.index)
        },
        onDragEnd = { _, _ ->
            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
            onTaskReorder(taskList)
        }
    )

    val colors = LocalAppColors.current
    val hiddenTasks = remember { mutableStateMapOf<Int, Boolean>() }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = state.listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp)
                .reorderable(state)
                .detectReorderAfterLongPress(state),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(taskList, key = { _, task -> task.id }) { _, task ->
                val isVisible = hiddenTasks[task.id] != true

                if (isVisible) {
                    ReorderableItem(state, key = task.id) {
                        val dismissState = rememberDismissState { dismissValue ->
                            when (dismissValue) {
                                DismissedToStart -> {
                                    hiddenTasks[task.id] = true
                                    onTaskDelete(task)

                                    scope.launch {
                                        val snackbarResult = snackbarHostState.showSnackbar(
                                            message = "Task deleted",
                                            actionLabel = "Undo",
                                            duration = SnackbarDuration.Short
                                        )
                                        if (snackbarResult == SnackbarResult.ActionPerformed) {
                                            hiddenTasks.remove(task.id)
                                            onUndoDelete(task)
                                        }
                                    }
                                    false
                                }

                                DismissedToEnd -> {
                                    onTaskComplete(task)

                                    scope.launch {
                                        val snackbarResult = snackbarHostState.showSnackbar(
                                            message = "Task marked as complete",
                                            actionLabel = "Undo",
                                            duration = SnackbarDuration.Short
                                        )
                                        if (snackbarResult == SnackbarResult.ActionPerformed) {
                                            onUndoComplete(task)
                                        }
                                    }
                                    false
                                }

                                DismissValue.Default -> false
                            }
                        }

                        SwipeToDismiss(
                            state = dismissState,
                            directions = setOf(
                                DismissDirection.StartToEnd,
                                DismissDirection.EndToStart
                            ),
                            background = {
                                val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                                val (bgBrush, icon, alignment) = when (direction) {
                                    DismissDirection.StartToEnd -> Triple(
                                        Brush.horizontalGradient(
                                            listOf(Color(0xFF4CAF50), Color(0xFF81C784))
                                        ),
                                        Icons.Outlined.CheckCircle,
                                        Alignment.CenterStart
                                    )
                                    DismissDirection.EndToStart -> Triple(
                                        Brush.horizontalGradient(
                                            listOf(Color(0xFFEF5350), Color(0xFFE53935))
                                        ),
                                        Icons.Outlined.DeleteForever,
                                        Alignment.CenterEnd
                                    )
                                }

                                val scale by animateFloatAsState(
                                    targetValue = if (dismissState.targetValue == DismissValue.Default) 0.9f else 1.15f,
                                    label = "icon-scale"
                                )

                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(brush = bgBrush, shape = RoundedCornerShape(12.dp))
                                        .padding(horizontal = 24.dp),
                                    contentAlignment = alignment
                                ) {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = if (direction == DismissDirection.StartToEnd) "Complete" else "Delete",
                                        modifier = Modifier
                                            .scale(scale)
                                            .size(30.dp),
                                        tint = Color.White
                                    )
                                }
                            },
                            dismissContent = {
                                PriorityTaskCard(
                                    task = task,
                                    onClick = {
                                        navController.navigate(Destinations.Details(task.id))
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .detectReorderAfterLongPress(state)
                                )
                            }
                        )
                    }
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 90.dp),
            snackbar = { data ->
                Snackbar(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    action = {
                        TextButton(onClick = { data.performAction() }) {
                            Text(text = data.visuals.actionLabel ?: "", color = colors.White)
                        }
                    },
                    containerColor = colors.Black,
                    contentColor = colors.White
                ) {
                    Text(text = data.visuals.message)
                }
            }
        )
    }
}




@Composable
fun getPriorityColors(taskPriority: String): Triple<Color, Color, String> {
    val context = LocalContext.current
    val themePreference = remember { ThemePreference(context) }
    val isDarkTheme by themePreference.isDarkMode.collectAsState(initial = false)

    return when (taskPriority) {
        "High" -> {
            if (isDarkTheme) {
                Triple(
                    Color(0xFF3B1C1C), // Dark red background
                    Color(0xFFFF4D4F), // Red indicator
                    "High"
                )
            } else {
                Triple(
                    Color(0xFFFFF1F0), // Light red background
                    Color(0xFFFF4D4F), // Red indicator
                    "High"
                )
            }
        }

        "Medium" -> {
            if (isDarkTheme) {
                Triple(
                    Color(0xFF3B360F), // Dark yellow background
                    Color(0xFFFFAA00), // Amber indicator
                    "Medium"
                )
            } else {
                Triple(
                    Color(0xFFFFFBE6), // Light yellow background
                    Color(0xFFFFAA00), // Amber indicator
                    "Medium"
                )
            }
        }

        else -> {
            if (isDarkTheme) {
                Triple(
                    Color(0xFF1E2A38), // Dark blue background
                    Color(0xFF2F54EB), // Blue indicator
                    "Low"
                )
            } else {
                Triple(
                    Color(0xFFF0F5FF), // Light blue background
                    Color(0xFF2F54EB), // Blue indicator
                    "Low"
                )
            }
        }
    }
}


@Composable
fun PriorityTaskCard(
    task: Task,
    onClick: () -> Unit,
    modifier: Modifier = Modifier // <-- Add this line

) {
    val colors = LocalAppColors.current

    // Determine colors based on priority
    val (containerColor, priorityColor, priorityText) = getPriorityColors(task.priority)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            // Title row with status badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Title text
                Text(
                    text = task.title,
                    fontSize = 16.sp,
                    color = colors.White,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                // Status badge next to title
                Surface(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(start = 8.dp),
                    shape = RoundedCornerShape(4.dp),
                    color = priorityColor.copy(alpha = 0.15f),
                    border = BorderStroke(1.dp, priorityColor.copy(alpha = 0.3f))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .background(priorityColor, CircleShape)
                        )
                        Text(
                            text = task.status,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = priorityColor
                        )
                    }
                }
            }

            // Description with subtle styling
            task.description.let { description ->
                if (description.isNotBlank()) {
                    Text(
                        text = description,
                        fontSize = 14.sp,
                        color = colors.White.copy(alpha = 0.7f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 20.sp
                    )
                }
            }

            // Divider for visual separation
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                color = colors.Grey.copy(alpha = 0.2f),
                thickness = 1.dp
            )

            // Bottom row with priority chip and date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Priority badge
                Surface(
                    modifier = Modifier
                        .wrapContentSize(),
                    shape = RoundedCornerShape(4.dp),
                    color = priorityColor.copy(alpha = 0.15f),
                    border = BorderStroke(1.dp, priorityColor.copy(alpha = 0.3f))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .background(priorityColor, CircleShape)
                        )
                        Text(
                            text = priorityText,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = priorityColor
                        )
                    }
                }

                // Date display
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.calendar_icon),
                        contentDescription = "Due date",
                        tint = colors.White.copy(alpha = 0.7f),
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = DateTimeFormatter.ofPattern("dd MMM").format(task.dueDate),
                        fontSize = 12.sp,
                        color = colors.White.copy(alpha = 0.7f),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}