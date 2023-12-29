package com.example.roomdemo

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    var etName:EditText? = null
    var etEmailId:EditText? = null
    var btnAdd:Button? = null
    var ivEdit:ImageView? = null
    var rvItemsList:RecyclerView? = null
    var tvNoRecordsAvailable:TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val employeeDao = (application as EmployeeApp).db.employeeDao()
        etName = findViewById(R.id.etName)
        etEmailId = findViewById(R.id.etEmailId)
        btnAdd = findViewById(R.id.btnAdd)
        rvItemsList = findViewById(R.id.rvItemsList)
        tvNoRecordsAvailable = findViewById(R.id.tvNoRecordsAvailable)
        btnAdd?.setOnClickListener{
            // TODO call addRecord with employeeDao
            addRecord(employeeDao)
        }

        lifecycleScope.launch {
            employeeDao.getAll().collect{
                val list = ArrayList(it)
                setUpListOfDataIntoRecyclerView(employeeDao,list)
            }
        }
    }
    fun addRecord(employeeDao: employeeDao){
        val name = etName?.text.toString()
        val emailId = etEmailId?.text.toString()
        if(name.isNotEmpty() && emailId.isNotEmpty()){
            lifecycleScope.launch {
                employeeDao.insert(EmployeeEntity(name = name, email = emailId))
                Toast.makeText(applicationContext,"Record Saved",Toast.LENGTH_SHORT).show()
                etName?.text?.clear()
                etEmailId?.text?.clear()
            }
        }
        else{
            Toast.makeText(this,"Name or Email cannot be blank",Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpListOfDataIntoRecyclerView(employeeDao: employeeDao,employeeList:ArrayList<EmployeeEntity>){
        if(employeeList.isNotEmpty()){
            val itemAdapter = itemAdapter(employeeList,
                {
                updateId -> updateRecordDialog(updateId, employeeDao)
                },
                {
                    deleteId->deleteRecordAlertDialog(deleteId,employeeDao)
                })
            rvItemsList?.layoutManager = LinearLayoutManager(this)
            rvItemsList?.adapter = itemAdapter
            rvItemsList?.visibility = View.VISIBLE
            tvNoRecordsAvailable?.visibility = View.GONE
        }
        else{
            rvItemsList?.visibility = View.GONE
            tvNoRecordsAvailable?.visibility = View.VISIBLE
        }
    }

    private fun updateRecordDialog(id:Int,employeeDao: employeeDao){
    }

    private fun deleteRecordAlertDialog(id: Int,employeeDao: employeeDao){

    }
}