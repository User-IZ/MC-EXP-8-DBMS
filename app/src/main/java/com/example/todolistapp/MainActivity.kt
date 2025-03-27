package com.example.todolistapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskList: MutableList<String>
    private lateinit var recyclerView: RecyclerView
    private lateinit var editTextTask: EditText
    private lateinit var buttonAdd: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = DatabaseHelper(this)
        recyclerView = findViewById(R.id.recyclerViewTasks)
        editTextTask = findViewById(R.id.editTextTask)
        buttonAdd = findViewById(R.id.buttonAdd)

        // Initialize task list and RecyclerView
        taskList = databaseHelper.getAllTasks().toMutableList()
        taskAdapter = TaskAdapter(taskList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = taskAdapter

        // Add button click listener
        buttonAdd.setOnClickListener {
            val task = editTextTask.text.toString().trim()
            if (task.isNotEmpty()) {
                databaseHelper.addTask(task)  // Add task to database
                taskList.add(task)  // Update the list
                taskAdapter.notifyItemInserted(taskList.size - 1)
                editTextTask.text.clear()  // Clear input field
            }
        }
    }
}
