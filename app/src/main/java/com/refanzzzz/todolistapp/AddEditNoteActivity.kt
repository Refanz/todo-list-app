package com.refanzzzz.todolistapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.Date

class AddEditNoteActivity : AppCompatActivity() {

    private lateinit var viewModel: NoteViewModel
    private var noteID = -1
    private lateinit var editNoteTitle:EditText
    private lateinit var editNoteDescription:EditText
    private lateinit var btnSave:Button
    private var noteType:String? = null

    private val LOG = "AddEditNoteActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        init()

        initViewModel()

        initListener()

        noteType = intent.getStringExtra("noteType")

        if (noteType.equals("Edit")) {
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDescription = intent.getStringExtra("noteDescription")
            noteID = intent.getIntExtra("noteID", -1)

            btnSave.text = "Update Note"
            editNoteTitle.setText(noteTitle)
            editNoteDescription.setText(noteDescription)
        } else {
            btnSave.text = "Save Note"
        }
    }

    private fun init() {
        editNoteTitle = findViewById(R.id.etNoteName)
        editNoteDescription = findViewById(R.id.etNoteDesc)
        btnSave = findViewById(R.id.btnSave)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
    }

    private fun initListener() {
        btnSave.setOnClickListener {
            val noteTitle = editNoteTitle.text.toString()
            val noteDescription = editNoteDescription.text.toString()

            if (noteType.equals("Edit")) {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MM, yyyy - HH:mm")
                    val currentDateTime:String = sdf.format(Date())
                    val updatedNote = Note(noteTitle, noteDescription, currentDateTime)

                    updatedNote.id = noteID
                    viewModel.updateNote(updatedNote)

                    Toast.makeText(this, "Note Updated..", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MM, yyyy - HH:mm")
                    val currentDateTime:String = sdf.format(Date())

                    viewModel.addNote(Note(noteTitle, noteDescription, currentDateTime))

                    Log.d(LOG, "Curent Time: $currentDateTime")

                    Toast.makeText(this, "$noteTitle Added..", Toast.LENGTH_SHORT).show()
                }
            }

            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        }
    }
}