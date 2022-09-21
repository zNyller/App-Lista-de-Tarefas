package com.nyller.projeto2ad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
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

            val rand = (1..90).random()

            adapter.addTask(
                Task(
                "Titulo $rand",
                "Descrição"
            ))

        }
    }

    private fun setupAdapter() {
        adapter = TaskAdapter(
            onClick = { task ->

                showTaskDetails(task) {taskSelected ->
                    adapter.updateTask(taskSelected)
                }


            },
            onDeleteClick = { task ->

                showDeleteConfirmation(task) { taskDeleted ->
                    adapter.deleteTask(taskDeleted)
                }

            }
        )

        binding.rvTasks.adapter = adapter
        binding.rvTasks.setHasFixedSize(true)

    }

    private fun showTaskDetails(task: Task, onTaskStatusChanged: (Task) -> Unit) {

        val dialog = AlertDialog.Builder(this)
        dialog.apply {
            setTitle("Detalhes da tarefa:")
            setMessage(
                """
                    Título: ${task.title}
                    Descrição: ${task.description}
                    Status: ${ if (task.done) "Sim" else "Não" }
                """.trimIndent()
            )
                setPositiveButton(if (task.done) "Não concluída" else "Concluída") {_, _ ->
                    task.done = !task.done
                    onTaskStatusChanged(task)
                }
            setNegativeButton("Fechar") {dialog, _ ->
                dialog.dismiss()
            }
        }

        dialog.show()

    }

    private fun showDeleteConfirmation(task: Task, onConfirm: (Task) -> Unit) {

        val dialog = AlertDialog.Builder(this)

            dialog.apply {
                setTitle("Confirmação")
                setMessage("Deseja realmente excluir a tarefa \"${task.title}?\"")
                setPositiveButton("Sim") {_, _ ->
                    onConfirm(task)
                }
                setNegativeButton("Não") {dialog, _ ->
                    dialog.dismiss()
                }
            }

        dialog.show()

    }

}