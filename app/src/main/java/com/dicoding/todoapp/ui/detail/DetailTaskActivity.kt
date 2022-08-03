package com.dicoding.todoapp.ui.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.todoapp.R
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.ui.list.TaskActivity
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID

class DetailTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        //TODO 11 : Show detail task and implement delete action
        val idTask = intent.getIntExtra(TASK_ID, 1)
        val factory = ViewModelFactory.getInstance(this)
        val model = ViewModelProvider(this, factory).get(DetailTaskViewModel::class.java)
        val detailEdTitle: EditText = findViewById(R.id.detail_ed_title)
        val detailEdDesc: EditText = findViewById(R.id.detail_ed_description)
        val detailEdDate: EditText = findViewById(R.id.detail_ed_due_date)
        val deleteButton: Button = findViewById(R.id.btn_delete_task)
        model.setTaskId(idTask)
        model.task.observe(this) {
            detailEdTitle.setText(it.title)
            detailEdDesc.setText(it.description)
            detailEdDate.setText(DateConverter.convertMillisToString(it.dueDateMillis))

            deleteButton.setOnClickListener {
                model.task.removeObservers(this)
                model.deleteTask()

                val toDetail = Intent(this, TaskActivity::class.java)
                this.startActivity(toDetail)
            }
        }

    }
}