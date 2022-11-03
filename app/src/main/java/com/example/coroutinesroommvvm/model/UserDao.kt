package com.example.coroutinesroommvvm.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertUser(users:User):Long

    @Query("Select * from user where username= :userName")
   suspend fun getUser(userName:String):User

    @Query("Delete FROM user where id= :id")
   suspend fun deleteUser(id:Long)

}