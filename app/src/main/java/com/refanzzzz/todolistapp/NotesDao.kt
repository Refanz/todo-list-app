package com.refanzzzz.todolistapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note:Note)

    @Delete
    fun delete(note:Note)

    @Query("SELECT * FROM tb_notes ORDER BY id ASC")
    fun getAllNotes():LiveData<List<Note>>

    @Update
    fun update(note:Note)




}