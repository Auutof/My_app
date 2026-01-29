package com.example.my_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.my_app.model.Task

// Adapter used to connect task data with RecyclerView
class TaskAdapter(
    private val tasks: MutableList<Task>
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    // ViewHolder that holds views for one task item
    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // Checkbox to mark task as done
        val cbDone: CheckBox = itemView.findViewById(R.id.cbDone)

        // TextView to show task title
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)

        // TextView to show task priority
        val tvPriority: TextView = itemView.findViewById(R.id.tvPriority)
    }

    // Creates a new ViewHolder when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {

        // Inflate layout for a single task item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)

        return TaskViewHolder(view)
    }

    // Binds task data to the ViewHolder
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        // Get task at current position
        val task = tasks[position]

        // Set task title and priority text
        holder.tvTitle.text = task.title
        holder.tvPriority.text = task.priority.name

        // Remove old listener to avoid incorrect state
        holder.cbDone.setOnCheckedChangeListener(null)

        // Set checkbox state based on task status
        holder.cbDone.isChecked = task.isDone

        // Update task status when checkbox is changed
        holder.cbDone.setOnCheckedChangeListener { _, checked ->
            task.isDone = checked
        }
    }

    // Returns total number of tasks
    override fun getItemCount(): Int = tasks.size
}
