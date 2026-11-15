package com.example.myapplicationvkeducation.presentation.details

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InstallButton(
    installState: InstallState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val buttonText = when (installState) {
        InstallState.NotInstalled -> "Установить"

        is InstallState.Installing ->
            "Отменить (${installState.progress}%)"

        InstallState.Installed -> "Установлено"
    }

    Button(
        onClick = onClick,
        enabled = installState != InstallState.Installed,
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(vertical = 12.dp),
        modifier = modifier
    ) {
        Text(buttonText)
    }
}