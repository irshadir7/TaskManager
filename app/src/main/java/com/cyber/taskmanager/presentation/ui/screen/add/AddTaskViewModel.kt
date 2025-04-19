package com.cyber.taskmanager.presentation.ui.screen.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyber.taskmanager.domain.model.TaskValidationState
import com.cyber.taskmanager.domain.usecase.ValidateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(private val validateTaskUseCase: ValidateTaskUseCase): ViewModel() {

    private var _taskFormState = MutableStateFlow(TaskFormState())
    val taskFormState = _taskFormState.asStateFlow()

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: TaskFormEvent) {
        when (event) {
            is TaskFormEvent.TitleChanged -> {
                _taskFormState.update { it.copy(title = event.title) }
            }
            is TaskFormEvent.DescriptionChanged -> {
                _taskFormState.update { it.copy(description = event.description) }
            }
            is TaskFormEvent.DueDateChanged -> {
                _taskFormState.update { it.copy(dueDate = event.dueDate) }
            }
            is TaskFormEvent.EstimateTaskChanged -> {
                _taskFormState.update { it.copy(estimateTask = event.estimateTime) }
            }
            is TaskFormEvent.PriorityChanged -> {
                _taskFormState.update { it.copy(selectedPriority = event.priority) }
            }
            is TaskFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val validationState = validateTaskUseCase.execute(
            _taskFormState.value.title,
            _taskFormState.value.description,
            _taskFormState.value.dueDate
        )

        viewModelScope.launch {
            if (validationState.isSuccessful) {
                validationEventChannel.send(ValidationEvent.Success)
            } else {
                val errorMessage = getErrorMessage(validationState)
                validationEventChannel.send(ValidationEvent.Error(errorMessage))
            }
        }
    }

    private fun getErrorMessage(validationState: TaskValidationState): String {
        return when {
            !validationState.isValidTitle && !validationState.isValidDescription ->
                "Enter a valid title and description."
            !validationState.isValidTitle -> "Enter a valid title."
            !validationState.isValidDescription -> "Enter a valid description."
            !validationState.isValidDueDate -> "Choose a due date for today or a future date."
            else -> "Fill in the task information correctly."
        }
    }


    fun resetTaskState() {
        _taskFormState.value = TaskFormState()
    }

    sealed class ValidationEvent {
        data object Success: ValidationEvent()
        data class Error(val errorMessage: String): ValidationEvent()
    }
}