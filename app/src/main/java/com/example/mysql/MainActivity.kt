package com.example.mysql

import android.annotation.SuppressLint
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.mysql.DatabaseHelper.Companion.COL_1

class MainActivity : AppCompatActivity() {
    lateinit var id:EditText
    lateinit var name:EditText
    lateinit var age:EditText
    lateinit var email:EditText
    lateinit var phone:EditText
    lateinit var insert:Button
    lateinit var update:Button
    lateinit var delete:Button
    lateinit var viewall:Button

    internal var dbHelper = DatabaseHelper(this)

    fun showDialog(title : String,Message : String){
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(Message)
        builder.show()
    }
    fun clearEditTexts(){
        id.setText("")
        name.setText("")
        age.setText("")
        email.setText("")
        phone.setText("")
    }
    fun showToast(text: String){
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_LONG).show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        id=findViewById(R.id.idedit)
        name=findViewById(R.id.nameedit)
        age=findViewById(R.id.ageedit)
        email=findViewById(R.id.emailedit)
        phone=findViewById(R.id.phoneedit)

        insert=findViewById(R.id.insertbutton)
        update=findViewById(R.id.updatebutton)
        delete=findViewById(R.id.deletebutton)
        viewall=findViewById(R.id.viewbutton)

        handleInserts()
        handleUpdates()
        handleDeletes()
        handleViewing()
    }

     @SuppressLint("Range")
     fun handleViewing() {
         viewall.setOnClickListener(
             View.OnClickListener {
                 val res = dbHelper.allData
                 if (res.count == 0) {
                     showDialog("Error", "No Data Found")
                     return@OnClickListener
                 }

                 val buffer = StringBuffer()
                 while (res.moveToNext()) {
                     //  buffer.append("ID :" + res.getString(0) + "\n")
                     buffer.append("ID :" +res.getString(res.getColumnIndex(COL_1))+"\n")
                     buffer.append("NAME :" + res.getString(1) + "\n")
                     buffer.append("AGE :" + res.getString(2) + "\n")
                     buffer.append("EMAIL :" + res.getString(3) + "\n")
                             buffer.append("PHONE :" + res.getString(4) + "\n\n")
                 }
                 showDialog("Data Listing", buffer.toString())
             }
         )
     }


     fun handleDeletes() {
         delete.setOnClickListener {
             try {
                 dbHelper.deleteData(id.text.toString())
                 clearEditTexts()
             }catch (e: Exception){
                 e.printStackTrace()
                 showToast(e.message.toString())
             }
         }
    }

    fun handleUpdates() {
        update.setOnClickListener {
            try {
                val isUpdate = dbHelper.updateData(id.text.toString(),
                    name.text.toString(),
                    age.text.toString(), email.text.toString(),phone.text.toString())
                if (isUpdate !=true) {
                    Log.e("ssssss", "kkkkk")
                    Toast.makeText(this, "Data not updated", Toast.LENGTH_LONG).show()
                }
                // showToast("Data Updated Successfully")
                if(isUpdate ==true) {
                    Log.e("zdjhjkd", "djfkds")
                    Toast.makeText(this, "Data Updated Successfully", Toast.LENGTH_LONG).show()
                }
                //   showToast("Data Not Updated")
            }catch (e: Exception){
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }

    fun handleInserts() {
        insert.setOnClickListener {
            try {
                if(name.text.toString().isEmpty() && email.text.toString().isEmpty()){
                    name.setError("Please enter name")
                    email.setError("Please enter email")
                }else
                    dbHelper.insertData(name.text.toString(),age.text.toString(),
                        email.text.toString(),phone.text.toString())
                clearEditTexts()
            }catch (e: Exception){
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }
}