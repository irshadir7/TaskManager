package com.cyber.taskmanager.presentation.ui.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cyber.taskmanager.presentation.ui.theme.LocalAppColors

@Composable
fun SortDropdown(selected: SortOption, onSelect: (SortOption) -> Unit) {
    val colors = LocalAppColors.current
    var expanded by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "rotation"
    )

    Box {
        OutlinedButton(
            onClick = { expanded = true },
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = colors.Blue.copy(alpha = 0.1f),
                contentColor = colors.Blue
            ),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
            modifier = Modifier.height(40.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Sort,
                contentDescription = "Sort",
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = selected.name.lowercase().replaceFirstChar { it.uppercase() },
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowDown,
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp)
                    .rotate(rotationState)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(
                    color = colors.Black.copy(alpha = 0.95f),
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            SortOption.entries.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            option.name
                                .replace("_", " ") // replaces underscores with spaces
                                .lowercase()
                                .replaceFirstChar { it.uppercase() },
                            fontWeight = if (selected == option) FontWeight.Bold else FontWeight.Normal,
                            color = if (selected == option) colors.Blue else colors.White
                        )
                    },
                    onClick = {
                        onSelect(option)
                        expanded = false
                    },
                    leadingIcon = {
                        if (selected == option) {
                            Icon(
                                imageVector = Icons.Filled.Sort,
                                contentDescription = null,
                                tint = colors.Blue,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = colors.White,
                        leadingIconColor = colors.Blue
                    )
                )
            }
        }
    }
}

@Composable
fun FilterDropdown(selected: FilterOption, onSelect: (FilterOption) -> Unit) {
    val colors = LocalAppColors.current
    var expanded by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "rotation"
    )

    Box {
        OutlinedButton(
            onClick = { expanded = true },
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = colors.Blue.copy(alpha = 0.1f),
                contentColor = colors.Blue
            ),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
            modifier = Modifier.height(40.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.FilterList,
                contentDescription = "Filter",
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = selected.name.lowercase().replaceFirstChar { it.uppercase() },
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowDown,
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp)
                    .rotate(rotationState)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(
                    color = colors.Black.copy(alpha = 0.95f),
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            FilterOption.entries.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            option.name
                                .replace("_", " ") // replaces underscores with spaces
                                .lowercase()
                                .replaceFirstChar { it.uppercase() },
                            fontWeight = if (selected == option) FontWeight.Bold else FontWeight.Normal,
                            color = if (selected == option) colors.Blue else colors.White
                        )
                    },
                    onClick = {
                        onSelect(option)
                        expanded = false
                    },
                    leadingIcon = {
                        if (selected == option) {
                            Icon(
                                imageVector = Icons.Filled.FilterList,
                                contentDescription = null,
                                tint = colors.Blue,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = colors.White,
                        leadingIconColor = colors.Blue
                    )
                )
            }
        }
    }
}

