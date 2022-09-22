package com.nyller.projeto2ad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.nyller.projeto2ad.adapters.TaskAdapter
import com.nyller.projeto2ad.constants.Constants.EXTRA_NEW_TASK
import com.nyller.projeto2ad.databinding.ActivityMainBinding
import com.nyller.projeto2ad.databinding.ActivityNewTaskBinding
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

    private val getResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode != RESULT_OK) {
            return@registerForActivityResult
        }

        val task = result.data?.extras?.getSerializable(EXTRA_NEW_TASK) as Task
        adapter.addTask(task)
        onDataUpdate()

    }

    fun onDataUpdate() = if (adapter.isEmpty()){
        binding.rvTasks.visibility = View.GONE
        binding.tvNoData.visibility = View.VISIBLE
    }else {
        binding.rvTasks.visibility = View.VISIBLE
        binding.tvNoData.visibility = View.GONE
    }

    private fun setupLayout() {
        binding.fabAddTask.setOnClickListener {

            getResult.launch(Intent(this, NewTaskActivity::class.java))

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
                    onDataUpdate()
                }

            }
        )
        binding.rvTasks.adapter = adapter
        binding.rvTasks.setHasFixedSize(true)
        onDataUpdate()

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