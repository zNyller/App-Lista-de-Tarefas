package com.nyller.projeto2ad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nyller.projeto2ad.constants.Constants.EXTRA_NEW_TASK
import com.nyller.projeto2ad.databinding.ActivityNewTaskBinding
import com.nyller.projeto2ad.models.Task

class NewTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener {
            onSubmit()
        }

    }

    private fun onSubmit() {

        if (binding.edtTaskTitle.text.isEmpty()) {

            binding.edtTaskTitle.error = "Por favor, preencha o título da tarefa!"
            binding.edtTaskTitle.requestFocus()
            return

        }

        if (binding.edtTaskDescription.text.isEmpty()) {
            binding.edtTaskDescription.error = "Por favor, preencha a descrição da tarefa!"
            binding.edtTaskDescription.requestFocus()
            return
        }

        val newTask = Task(
            binding.edtTaskTitle.text.toString(),
            binding.edtTaskDescription.text.toString()
        )

        val intentResult = Intent()
        intentResult.putExtra(EXTRA_NEW_TASK, newTask)
        setResult(RESULT_OK, intentResult)
        finish()

    }
}