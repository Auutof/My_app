package com.example.my_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.my_app.model.Priority
import com.example.my_app.model.Task

class AddTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_task)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etTitle = findViewById<EditText>(R.id.etTitle)
        val rgPriority = findViewById<RadioGroup>(R.id.rgPriority)
        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            val title = etTitle.text.toString().trim()

            if (title.isEmpty()) {
                Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val priority = when (rgPriority.checkedRadioButtonId) {
                R.id.rbLow -> Priority.LOW
                R.id.rbHigh -> Priority.HIGH
                else -> Priority.MEDIUM
            }

            val task = Task(
                id = System.currentTimeMillis(),
                title = title,
                priority = priority
            )

            val resultIntent = Intent()
            resultIntent.putExtra("title", task.title)
            resultIntent.putExtra("priority", task.priority.name)
            setResult(RESULT_OK, resultIntent)

            finish()
        }
    }
}
