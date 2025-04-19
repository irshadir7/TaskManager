package com.cyber.taskmanager.presentation.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.cyber.taskmanager.R
import com.cyber.taskmanager.presentation.ui.screen.home.TaskViewModel
import com.cyber.taskmanager.presentation.ui.screen.home.TasksListSection
import com.cyber.taskmanager.presentation.ui.theme.LocalAppColors

enum class SortOption { PRIORITY, DUE_DATE, ALPHABETICAL }
enum class FilterOption { ALL, COMPLETED, PENDING, IN_PROGRESS }

@Composable
fun TasksScreen(
    navController: NavController
) {
    val viewModel: TaskViewModel = hiltViewModel()
    val colors = LocalAppColors.current
    var sortOption by remember { mutableStateOf(SortOption.PRIORITY) }
    var filterOption by remember { mutableStateOf(FilterOption.ALL) }
    val allTasks by viewModel.allTasks.collectAsStateWithLifecycle()

    val filteredAndSortedTasks by remember(allTasks, sortOption, filterOption) {
        derivedStateOf {
            allTasks
                .filter {
                    when (filterOption) {
                        FilterOption.ALL -> true
                        FilterOption.COMPLETED -> it.status == "Completed"
                        FilterOption.PENDING -> it.status == "Pending"
                        FilterOption.IN_PROGRESS -> it.status == "In Progress"
                    }
                }
                .sortedWith(
                    when (sortOption) {
                        SortOption.PRIORITY -> compareBy { getPriorityValue(it.priority) }
                        SortOption.DUE_DATE -> compareBy { it.dueDate }
                        SortOption.ALPHABETICAL -> compareBy { it.title.lowercase() }
                    }
                )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        // Dropdown Row Centered
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SortDropdown(sortOption) { sortOption = it }
            FilterDropdown(filterOption) { filterOption = it }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (filteredAndSortedTasks.isEmpty()) {
            // Show "No Tasks" UI
            Spacer(modifier = Modifier.height(100.dp))

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
                        text = "There are no specific tasks tied to this filter",
                        fontSize = 13.sp,
                        color = colors.White,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            // Show task list
            TasksListSection(
                listTasks = filteredAndSortedTasks,
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
    }


}
fun getPriorityValue(priority: String): Int {
    return when (priority) {
        "High" -> 1
        "Medium" -> 2
        else -> 3
    }
}

