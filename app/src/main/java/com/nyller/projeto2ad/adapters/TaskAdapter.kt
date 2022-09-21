package com.nyller.projeto2ad.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nyller.projeto2ad.databinding.ResItemTaskBinding
import com.nyller.projeto2ad.models.Task

class TaskAdapter(
    private val onClick : (Task) -> Unit,
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
            onDeleteClick: (Task) -> Unit,
            onClick: (Task) -> Unit
        ) {
            tvTaskTitle.text = task.title
            imgBtnDelete.setOnClickListener { onDeleteClick(task) }
            clTask.setOnClickListener { onClick(task) }

            if (task.done) {
                tvTaskTitle.setTextColor(ContextCompat.getColor(itemView.context, com.nyller.projeto2ad.R.color.white))
                imgBtnDelete.setImageResource(com.nyller.projeto2ad.R.drawable.delete_white)
                clTask.setBackgroundColor(ContextCompat.getColor(itemView.context, com.nyller.projeto2ad.R.color.medium_sea_green))
            }

            else {
                tvTaskTitle.setTextColor(ContextCompat.getColor(itemView.context, com.nyller.projeto2ad.R.color.black))
                imgBtnDelete.setImageResource(com.nyller.projeto2ad.R.drawable.delete_black)
                clTask.setBackgroundColor(ContextCompat.getColor(itemView.context, com.nyller.projeto2ad.R.color.bright_gray))
            }

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
            onDeleteClick,
            onClick
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

    fun updateTask(task: Task) {

        val updatedPosition = tasks.indexOf(task)
        tasks[updatedPosition] = task
        notifyItemChanged(updatedPosition)

    }

}