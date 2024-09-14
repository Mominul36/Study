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
import androidx.navigation.fragment.findNavController
import com.example.studyadmin.Model.ClassTest

import com.example.studyadmin.databinding.FragmentCTUpdateBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Calendar


class CTUpdateFragment : Fragment() {
    private lateinit var binding: FragmentCTUpdateBinding
    private lateinit var database: DatabaseReference
    private var classTestId: String? = null

    lateinit var courseCodeList : ArrayList<String>
    lateinit var courseNameList : ArrayList<String>
    var selectedDate : String?=null
    var selectedTime : String?=null
     var selectedCourseCode : String?=null
     var selectedCourseName : String?=null
     var topic : String?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCTUpdateBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance().getReference("Class_Test")
        classTestId = arguments?.getString("classTestId")

        initVariable()
        binding.date.setOnClickListener{
            showDateDialog()
        }

        binding.time.setOnClickListener{
            showTimeDialog()
        }

        binding.update.setOnClickListener{
            updateData()
        }


        database.child(classTestId!!).get().addOnSuccessListener {
            if(it.exists()){
                selectedCourseCode = it.child("courseCode").value.toString()
                selectedCourseName = it.child("courseName").value.toString()
                selectedDate = it.child("date").value.toString()
                selectedTime = it.child("time").value.toString()
                topic = it.child("topic").value.toString()


                setUpForCourseListSpinner()
                setAnotherValue()

            }
        }





    return binding.root
    }

    private fun updateData() {
        topic = binding.topic.text.toString()

        val classTest = mapOf<String,String>(
            "courseCode" to selectedCourseCode.toString(),
            "courseName" to selectedCourseName.toString(),
            "date" to selectedDate.toString(),
            "time" to selectedTime.toString(),
            "topic" to topic.toString()
        )
        database.child(classTestId!!).updateChildren(classTest).addOnSuccessListener {
            Toast.makeText(context,"Class Test updated successfully",Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }.addOnFailureListener {
            Toast.makeText(context, "Failed to update Class Test", Toast.LENGTH_SHORT).show()
        }



//        var classTest = ClassTest(classTestId,selectedCourseCode,selectedCourseName,selectedDate,selectedTime,topic)
//
//        database.child(classTestId!!).setValue(classTest).addOnSuccessListener {
//            Toast.makeText(context,"Class Test updated successfully",Toast.LENGTH_SHORT).show()
//            // it.findNavController().navigate(R.id.action_CTAddFragment_to_CTHomeFragment)
//            findNavController().navigateUp()
//        }.addOnFailureListener{
//            Toast.makeText(context,"Failed to update Class Test",Toast.LENGTH_SHORT).show()
//        }
    }


    private fun setAnotherValue() {
       binding.date.text = selectedDate
        binding.time.text = selectedTime
        binding.topic.setText(topic)
    }

    private fun setUpForCourseListSpinner() {
        val dataAdapter = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            courseCodeList
        )
        binding.courseCode.adapter = dataAdapter

      if(selectedCourseCode!=null){
          val spinnerPosition = dataAdapter.getPosition(selectedCourseCode)
          binding.courseCode.setSelection(spinnerPosition)
      }


       // binding.courseCode.setSelection(2)


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


}