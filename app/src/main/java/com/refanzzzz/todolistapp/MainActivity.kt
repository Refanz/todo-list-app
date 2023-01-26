package com.refanzzzz.todolistapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.refanzzzz.todolistapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NoteAdapter.NoteClickInterface, NoteAdapter.NoteClickDeleteInterface {

    private lateinit var binding:ActivityMainBinding
    private lateinit var viewModel:NoteViewModel
    private lateinit var rvNoteAdapter:NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        initViewModel()

        initListener()
    }

    private fun initRecyclerView() {
        binding.rvNotes.layoutManager = LinearLayoutManager(this)

        rvNoteAdapter = NoteAdapter(this, this, this)
        binding.rvNotes.adapter = rvNoteAdapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModel.allNotes.observe(this, Observer { list ->
            list?.let {
                rvNoteAdapter.updateList(it)
            }
        })
    }

    private fun initListener() {
        binding.btnFloat.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", note.noteTitle)
        intent.putExtra("noteDescription", note.noteDescription)
        intent.putExtra("noteID", note.id)
        startActivity(intent)
    }

    override fun onDeleteIconClick(note: Note) {
        viewModel.deleteNote(note)

        Toast.makeText(this, "${note.noteTitle} Deleted", Toast.LENGTH_SHORT).show()
    }
}