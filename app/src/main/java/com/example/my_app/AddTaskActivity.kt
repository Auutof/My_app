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

// Activity used to add a new task
class AddTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enables full screen layout
        enableEdgeToEdge()

        // Sets the layout for this activity
        setContentView(R.layout.activity_add_task)

        // Handles padding for system bars (status bar, navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Input field for task title
        val etTitle = findViewById<EditText>(R.id.etTitle)

        // Radio group used to select task priority
        val rgPriority = findViewById<RadioGroup>(R.id.rgPriority)

        // Button to save the task
        val btnSave = findViewById<Button>(R.id.btnSave)


        // Called when the Save button is clicked
        btnSave.setOnClickListener {

            // Get and clean the title text
            val title = etTitle.text.toString().trim()

            // Check if title is empty
            if (title.isEmpty()) {
                Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Determine selected priority
            val priority = when (rgPriority.checkedRadioButtonId) {
                R.id.rbLow -> Priority.LOW
                R.id.rbHigh -> Priority.HIGH
                else -> Priority.MEDIUM
            }


            // Create a Task object
            val task = Task(
                id = System.currentTimeMillis(),
                title = title,
                priority = priority
            )

            // Send task data back to previous activity
            val resultIntent = Intent()
            resultIntent.putExtra("title", task.title)
            resultIntent.putExtra("priority", task.priority.name)
            setResult(RESULT_OK, resultIntent)

            // Close this activity
            finish()
        }
    }
}
