package com.example.studyadmin.Fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TimePicker
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.studyadmin.Model.ClassTest
import com.example.studyadmin.R
import com.example.studyadmin.databinding.FragmentCTAddBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Calendar
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController


class CTAddFragment : Fragment() {
    lateinit var binding : FragmentCTAddBinding
    lateinit var courseCodeList : ArrayList<String>
    lateinit var courseNameList : ArrayList<String>
     var selectedDate : String?=null
     var selectedTime : String?=null
    lateinit var selectedCourseCode : String
    lateinit var selectedCourseName : String
    lateinit var topic : String
    lateinit var database: DatabaseReference



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCTAddBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance().getReference("Class_Test")

        initVariable()
        showCourseList()

        binding.date.setOnClickListener{
            showDateDialog()
        }

        binding.time.setOnClickListener{
            showTimeDialog()
        }

        binding.add.setOnClickListener{
            saveData()
        }


        return binding.root
    }

    private fun saveData() {
        if(selectedDate==null){
            Toast.makeText(context,"Select Date",Toast.LENGTH_LONG).show()
        }
        else if(selectedTime==null){
            Toast.makeText(context,"Select Time",Toast.LENGTH_LONG).show()
        }
        else{
            var id = database.push()!!.key!!
            topic = binding.topic.text.toString()
            var classTest = ClassTest(id,selectedCourseCode,selectedCourseName,selectedDate,selectedTime,topic)

            database.child(id).setValue(classTest).addOnSuccessListener {
                Toast.makeText(context,"Class Test added successfully",Toast.LENGTH_SHORT).show()
               // it.findNavController().navigate(R.id.action_CTAddFragment_to_CTHomeFragment)
                findNavController().navigateUp()
            }.addOnFailureListener{
                Toast.makeText(context,"Failed to add Class Test",Toast.LENGTH_SHORT).show()
            }

        }


    }


    private fun showCourseList(){
        val dataAdapter = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            courseCodeList
        )
        binding.courseCode.adapter = dataAdapter

        binding.courseCode.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedCourseCode = courseCodeList[p2]
                selectedCourseName = courseNameList[p2]
                binding.coursename.text = selectedCourseName
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    private fun initVariable() {
        //init courseNameList
        courseNameList = arrayListOf()
        courseNameList.add("Software Engineering")
        courseNameList.add("Microprocessors, Microcontrollers and Embedded Systems ")
        courseNameList.add("Computer Architecture ")
        courseNameList.add("Data Communication ")
        courseNameList.add("Compiler")
        courseNameList.add("Basic Mechanical Engineering ")




        //init courseName
        courseCodeList = arrayListOf()
        courseCodeList.add("CSE 3101")
        courseCodeList.add("CSE 3103")
        courseCodeList.add("CSE 3105")
        courseCodeList.add("CSE 3107")
        courseCodeList.add("CSE 3109")
        courseCodeList.add("ME 3181")


    }

    private fun showTimeDialog() {
        // Get current time
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        // Create a TimePickerDialog
        val timePickerDialog = TimePickerDialog(requireContext(), { _: TimePicker, selectedHour: Int, selectedMinute: Int ->
            // Format and display the selected time
            selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
            binding.time.text = selectedTime
        }, hour, minute, true)

        timePickerDialog.show()
    }

    private fun showDateDialog() {
        // Get the current date
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Create a DatePickerDialog
        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            // Format the date (Month starts from 0, so add 1)
            selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
             binding.date.text = selectedDate
        }, year, month, day)

        datePickerDialog.show()
    }


}