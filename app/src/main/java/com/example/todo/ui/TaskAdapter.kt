package com.example.todo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.data.entity.TodoTask
import com.example.todo.databinding.ItemLayoutBinding

class TaskAdapter(
    private val onTaskCheckChanged: (TodoTask) -> Unit,
    private val onTaskDeleted: (TodoTask) -> Unit
) : ListAdapter<TodoTask, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.TaskViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: TodoTask) {
            binding.apply {
                txtTitle.text = task.title
                txtDes.text = task.des
                taskCheckBox.isChecked = task.isCompleted

                taskCheckBox.setOnClickListener {
                    onTaskCheckChanged(task)
                }

                delBtn.setOnClickListener { onTaskDeleted(task) }
            }
        }
    }

    class TaskDiffCallback : DiffUtil.ItemCallback<TodoTask>() {
        override fun areItemsTheSame(oldItem: TodoTask, newItem: TodoTask) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TodoTask, newItem: TodoTask) =
            oldItem == newItem

    }

}


