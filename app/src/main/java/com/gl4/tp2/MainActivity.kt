package com.gl4.tp2
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener  {
    var courses = arrayOf("Application mobile", "Data Mining","IA","TLA")
    val spinner : Spinner by lazy { findViewById(R.id.seancesType) }
    var students = ArrayList<Student>()

    var etud = ArrayList<Student>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        spinner!!.setOnItemSelectedListener(this)
        val courseAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, courses)
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.setAdapter(courseAdapter)

        this.createStudents()

        //getting the recycle view by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // This will pass the ArrayList to our Adapter
        var adapter = CustomAdapter(students)
        recyclerview.adapter = adapter
        val textInput : TextInputEditText = findViewById(R.id.textInputEditText)
        textInput.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                adapter = CustomAdapter(filter(s.toString()))
                recyclerview.adapter = adapter
            }
        })
        val check : CheckBox = findViewById(R.id.checkBox)
        check.setOnCheckedChangeListener { buttonView, isChecked ->
            adapter = CustomAdapter(filter(isChecked))
            recyclerview.adapter = adapter
        }


    }
    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        print("le type de cours selectionné est : "  + courses[position])
    }
    override fun onNothingSelected(arg0: AdapterView<*>) {
    }

    fun createStudents() {
        var genre :String = "male"
        for(i in 1..20){
            if(i%2 == 0){
                genre = "male"
            }else{
                genre = "female"
            }
            this.students.add(Student("nom$i","prenom$i",genre , i%3 == 0))
        }
    }
    fun filter (name : String) : ArrayList<Student>{
        var filteredList = ArrayList<Student>()
        for (student in students) {
            if (student.nom.lowercase(Locale.ROOT)
                    .contains(name.lowercase(Locale.ROOT))
            ) {
                filteredList.add(student)
            }
        }
        return filteredList
    }

    fun filter (present : Boolean ) : ArrayList<Student>{
        var filteredList = ArrayList<Student>()
        for (student in students) {
            if (student.present == present) {
                filteredList.add(student)
            }
        }
        return filteredList
    }

}