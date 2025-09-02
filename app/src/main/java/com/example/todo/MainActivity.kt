package com.example.todo

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import  android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.databinding.ActivityMainBinding
import com.example.todo.ui.TaskAdapter
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.todo.data.entity.TodoTask
import com.example.todo.data.repository.Repository
import com.example.todo.ui.ViewModel.TaskViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter

    private var isAdded = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repo = Repository(MainApplication.todoDatabase.getTodoDao())
        viewModel = TaskViewModel(repo)

        setupRecyclerView()
        setupObserver()

        setupClickListner()
    }

    private fun setupClickListner() {
        binding.addTask.setOnClickListener { showDialogBox() }
    }

    private fun showDialogBox() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.add_item)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val titleInput = dialog.findViewById<TextInputEditText>(R.id.edtTitle)
        val desInput = dialog.findViewById<TextInputEditText>(R.id.edtDes)
        val saveBtn = dialog.findViewById<Button>(R.id.btnSave)

        saveBtn.setOnClickListener {
            val title = titleInput.text.toString()
            val des = desInput.text.toString()
            if (title.isNotEmpty() && des.isNotEmpty()) {
                val task = TodoTask(
                    title = title,
                    des = des,
                    createdAt = System.currentTimeMillis().toString()
                )
                viewModel.addTask(task)
                isAdded = true
                dialog.dismiss()
            } else {
                if (title.isEmpty()) {
                    Snackbar.make(
                        findViewById(android.R.id.content), // root view of your activity
                        "Please enter Title",          // message
                        Snackbar.LENGTH_SHORT                 // duration
                    ).show()
                } else
                    if (des.isEmpty()) {
                        Snackbar.make(
                            findViewById(android.R.id.content), // root view of your activity
                            "Please enter Description",          // message
                            Snackbar.LENGTH_SHORT                 // duration
                        ).show()
                    }
            }
        }
        // Force dialog width to match parent
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialog.show()
    }

    private fun setupObserver() {

        viewModel.tasks.observe(this) { tasks ->
            taskAdapter.submitList(tasks) {
                // ✅ Scroll only if a new item was added
                if (tasks.isNotEmpty() && isAdded) {
                    binding.lytEmpty.visibility = View.GONE
                    binding.tasksRecyclerView.visibility = View.VISIBLE
                    binding.tasksRecyclerView.smoothScrollToPosition(tasks.size - 1)
                    isAdded = false
                }

                //here

                if(tasks.isEmpty()){
                    binding.lytEmpty.visibility = View.VISIBLE
                    binding.tasksRecyclerView.visibility = View.GONE
                }
            }
        }
    }


    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(onTaskCheckChanged = { task ->
            viewModel.updateTask(task)
        }, onTaskDeleted = { task -> viewModel.deleteTask(task) })
        binding.tasksRecyclerView.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}