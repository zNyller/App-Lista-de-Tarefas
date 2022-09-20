package com.nyller.projeto2ad.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.nyller.projeto2ad.databinding.ResItemTaskBinding
import com.nyller.projeto2ad.models.Task

class TaskAdapter(
    private val onDeleteClick : (Task) -> Unit
) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val tasks = mutableListOf<Task>()

    inner class TaskViewHolder(
        itemView: ResItemTaskBinding
    ) :
        RecyclerView.ViewHolder(itemView.root) {

        private val tvTaskTitle = itemView.tvTaskTitle
        private val imgBtnDelete = itemView.imgBtnDelete
        private val clTask = itemView.clTask

        fun bind(
            task: Task,
            onDeleteClick: (Task) -> Unit
        ) {
            tvTaskTitle.text = task.title
            imgBtnDelete.setOnClickListener { onDeleteClick(task) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder =
        TaskViewHolder(
            ResItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(
            tasks[position],
            onDeleteClick
        )
    }

    override fun getItemCount(): Int = tasks.size

    fun addTask(task: Task){
        tasks.add(task)
        notifyItemInserted(tasks.size - 1)
    }

    fun deleteTask(task: Task){

        val deletedPosition = tasks.indexOf(task)   // Encontra a posição da task selecionada na lista
        tasks.remove(task)
        notifyItemRemoved(deletedPosition)

    }

}