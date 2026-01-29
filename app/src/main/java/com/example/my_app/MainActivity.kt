package com.example.my_app

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.my_app.model.Priority
import com.example.my_app.model.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton

// Main screen of the application that shows the task list
class MainActivity : AppCompatActivity() {

    // List that stores all created tasks
    private val tasks = mutableListOf<Task>()

    // Adapter used to display tasks in RecyclerView
    private lateinit var adapter: TaskAdapter

    // Launcher used to receive data from AddTaskActivity
    private val addTaskLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            // Check if task was added successfully
            if (result.resultCode == RESULT_OK) {
                val data = result.data ?: return@registerForActivityResult

                // Get task data from result
                val title = data.getStringExtra("title") ?: return@registerForActivityResult
                val priorityName = data.getStringExtra("priority") ?: "MEDIUM"

                // Create a new task object
                val task = Task(
                    id = System.currentTimeMillis(),
                    title = title,
                    priority = Priority.valueOf(priorityName)
                )

                // Add task to the list and update UI
                tasks.add(task)
                adapter.notifyItemInserted(tasks.size - 1)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enables full screen layout
        enableEdgeToEdge()

        // Sets the main layout of the activity
        setContentView(R.layout.activity_main)

        // Applies padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // RecyclerView that displays tasks
        val rvTasks = findViewById<RecyclerView>(R.id.rvTasks)

        // Floating button to add a new task
        val fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)

        // Initialize adapter and RecyclerView
        adapter = TaskAdapter(tasks)
        rvTasks.layoutManager = LinearLayoutManager(this)
        rvTasks.adapter = adapter

        // Open AddTaskActivity when button is clicked
        fabAdd.setOnClickListener {
            addTaskLauncher.launch(Intent(this, AddTaskActivity::class.java))
        }
    }
}
