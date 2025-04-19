package com.cyber.taskmanager.presentation.ui.screen.details

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cyber.taskmanager.data.local.Task
import com.cyber.taskmanager.presentation.ui.theme.LocalAppColors
import compose.icons.FeatherIcons
import compose.icons.feathericons.ChevronDown
import es.dmoral.toasty.Toasty

@Composable
fun DetailsContent(
    paddingValues: PaddingValues,
    task: Task,
    onBackClick: () -> Unit,
    updateTaskStatus: (String) -> Unit
) {
    val colors = LocalAppColors.current
    var status by remember(task.status) { mutableStateOf(task.status) }
    var expanded by remember { mutableStateOf(false) }
    val statusOptions = listOf("Pending", "In Progress", "Completed")
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues) ,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Back Arrow
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.padding(top = 4.dp) // subtle top spacing
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = colors.White
            )
        }

        // 2. Details content with extra padding
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 5.dp, start = 20.dp, end = 20.dp), // extra top padding from arrow
            verticalArrangement = Arrangement.spacedBy(20.dp) // more spacing between fields
        ) {
            TitleWithIcon(icon = Icons.Default.Title, title = task.title)
            InfoWithIcon(icon = Icons.Default.Description, label = "Description", value = task.description)
            InfoWithIcon(icon = Icons.Default.CalendarToday, label = "Due Date", value = task.dueDate.toString())
            InfoWithIcon(icon = Icons.Default.Flag, label = "Priority", value = task.priority)
            InfoWithIcon(icon = Icons.Default.Info, label = "Status", value = status)

            Text(
                text = "Update Status:",
                fontSize = 16.sp,
                color = colors.White,
                fontWeight = FontWeight.SemiBold
            )

            // 3. Status Dropdown
            StatusDropdown(
                currentStatus = status,
                options = statusOptions,
                onStatusSelected = { selected ->
                    status = selected
                    updateTaskStatus(selected)
                    Toasty.success(
                        context,
                        "Task status updated!",
                        Toast.LENGTH_SHORT,
                        true
                    ).show()
                }
            )
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusDropdown(
    currentStatus: String,
    options: List<String>,
    onStatusSelected: (String) -> Unit
) {
    val colors = LocalAppColors.current
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
        modifier = Modifier
    ) {
        BasicTextField(
            modifier = Modifier
                .background(colors.Grey, RoundedCornerShape(12.dp))
                .padding(4.dp)
                .fillMaxWidth()
                .height(50.dp)
                .menuAnchor(),
            value = currentStatus,
            onValueChange = {},
            readOnly = true,
            textStyle = LocalTextStyle.current.copy(
                color =colors.White,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                textAlign = TextAlign.Start
            ),
            decorationBox = { innerTextField ->
                Row(
                    Modifier
                        .fillMaxSize()
                        .padding(vertical = 14.5.dp),
                    verticalAlignment = Alignment.Top,
                ) {
                    Box(
                        Modifier
                            .weight(1f)
                            .padding(horizontal = 13.dp)
                    ) {
                        innerTextField()
                        Icon(
                            imageVector = FeatherIcons.ChevronDown,
                            contentDescription = null,
                            tint = colors.White,
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.CenterEnd)
                        )
                    }
                }
            }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(color = colors.GreyLight, shape = RoundedCornerShape(12.dp))
                .padding(vertical = 4.dp)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option,
                            color = if (currentStatus == option) colors.Blue else colors.White,
                            fontWeight = if (currentStatus == option) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    onClick = {
                        onStatusSelected(option)
                        expanded = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (currentStatus == option)
                                colors.White.copy(alpha = 0.1f)
                            else
                                colors.GreyLight
                        )
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Composable
fun TitleWithIcon(icon: ImageVector, title: String) {
    val colors = LocalAppColors.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Title Icon",
            tint = colors.White,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colors.White
        )
    }
}

@Composable
fun InfoWithIcon(icon: ImageVector, label: String, value: String) {
    val colors = LocalAppColors.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "$label Icon",
            tint = colors.White.copy(alpha = 0.7f),
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = label,
                fontSize = 12.sp,
                color = colors.White.copy(alpha = 0.5f)
            )
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = colors.White
            )
        }
    }
}