package com.nyller.projeto2ad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nyller.projeto2ad.adapters.TaskAdapter
import com.nyller.projeto2ad.databinding.ActivityMainBinding
import com.nyller.projeto2ad.models.Task

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapter()
        setupLayout()

    }

    private fun setupLayout() {
        binding.fabAddTask.setOnClickListener {

            val rand = (1..20).random()

            adapter.addTask(
                Task(
                "Titulo $rand",
                "Descrição"
            ))

        }
    }

    private fun setupAdapter() {
        adapter = TaskAdapter { task ->
            adapter.deleteTask(task)
        }

        binding.rvTasks.adapter = adapter
        binding.rvTasks.setHasFixedSize(true)

    }

}