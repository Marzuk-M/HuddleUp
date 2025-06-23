package com.example.huddleup.sharedcomponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HUTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    label: String
) {
    TextButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = label)
    }
}