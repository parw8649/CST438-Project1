package com.example.project1.activity;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

import java.util.List;

@Dao
public interface MainDao {
    //query to insert
    @Insert(onConflict = REPLACE)
    void insert(MainData mainData);

    //query to delete
    @Delete
    void delete(MainData mainData);

    //delete all query
    @Delete
    void reset(List<MainData> mainData);

    //update query
    @Query("UPDATE table_name SET text = :sText WHERE ID= :sID")
    void update(int sID, String sText);

    //get all data from query
    @Query("SELECT * FROM table_name")
    List<MainData> getAll();
}
