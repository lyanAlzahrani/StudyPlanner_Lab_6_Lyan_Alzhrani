package com.example.studyplanner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

// ───────────────────────── The Route: owns the state ─────────────────────────
@Composable
fun StudyPlannerRoute(viewModel: StudyPlannerViewModel = viewModel()) {

    var draft by rememberSaveable { mutableStateOf("") }

    StudyPlannerScreen(
        uiState = viewModel.uiState,
        draft = draft,
        onDraftChange = {
            draft = it
        },
        onAddTask = {
            viewModel.addTask(draft)
            draft = ""
        }
    )
}

// ───────────────────────── The Screen: stateless ─────────────────────────────
@Composable
fun StudyPlannerScreen(
    uiState: StudyPlannerUiState,
    draft: String,
    onDraftChange: (String) -> Unit,
    onAddTask: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        TaskCount(count = uiState.tasks.size)

        AddTaskRow(
            text = draft,
            onTextChange = onDraftChange,
            onAdd = onAddTask
        )

        uiState.tasks.forEach { task ->
            Text("• $task")
        }
    }
}

// ───────────────────────── Preview: no app, no ViewModel needed ──────────────
@Preview(showBackground = true)
@Composable
fun StudyPlannerScreenPreview() {
    StudyPlannerScreen(
        uiState = StudyPlannerUiState(tasks = listOf("Read Ch. 2", "Finish Lab 6")),
        draft = "",
        onDraftChange = {},
        onAddTask = {}
    )
}