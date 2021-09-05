package com.example.project1;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Insert
    void registerUser(UserEntity userEntity);

    //Adding login Dao methods
    @Query("SELECT * FROM users where userId=(:userId) AND password= (:password)")
    UserEntity login(String userId, String password);


}
