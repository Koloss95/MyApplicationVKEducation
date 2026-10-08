package com.example.myapplicationvkeducation

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplicationvkeducation.ui.theme.MyApplicationVKEducationTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationVKEducationTheme {
                var text by rememberSaveable { mutableStateOf("") }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .imePadding()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Работа с Intent",
                            style = MaterialTheme.typography.headlineSmall
                        )

                        OutlinedTextField(
                            value = text,
                            onValueChange = { text = it },
                            label = { Text("Текст или номер телефона") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = { openSecondActivity(text) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Открыть вторую Activity")
                        }

                        Button(
                            onClick = { openDialer(text) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Позвонить другу")
                        }

                        Button(
                            onClick = { shareText(text) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Поделиться текстом")
                        }
                    }
                }
            }
        }
    }

    private fun openSecondActivity(text: String) {
        if (text.isBlank()) {
            showMessage("Введите текст")
            return
        }

        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra(SecondActivity.EXTRA_TEXT, text)

        startActivity(intent)
    }

    private fun openDialer(text: String) {
        val phone = text.trim().replace(Regex("[\\s()\\-]"), "")

        if (!Regex("\\+?[0-9]{3,15}").matches(phone)) {
            showMessage("Введите номер телефона, например +79991234567")
            return
        }

        val intent = Intent(
            Intent.ACTION_DIAL,
            Uri.fromParts("tel", phone, null)
        )

        try {
            startActivity(intent)
        } catch (exception: ActivityNotFoundException) {
            showMessage("Не найдено приложение для набора номера")
        }
    }

    private fun shareText(text: String) {
        if (text.isBlank()) {
            showMessage("Введите текст для отправки")
            return
        }

        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }

        val choose = Intent.createChooser(
            sendIntent,
            "Поделиться через…"
        )

        try {
            startActivity(choose)
        } catch (exception: ActivityNotFoundException) {
            showMessage("Не удалось открыть окно отправки")
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
