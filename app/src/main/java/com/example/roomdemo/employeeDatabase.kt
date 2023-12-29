package com.example.roomdemo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EmployeeEntity::class], version = 1)
abstract class employeeDatabase:RoomDatabase() {
    abstract fun employeeDao():employeeDao

    companion object{
        @Volatile
        private var INSTANCE:employeeDatabase? = null

        fun getInstance(context: Context):employeeDatabase{
            synchronized(this){
                var instance = INSTANCE

                if(instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,employeeDatabase::class.java,"employee_database"
                    ).build()

                    INSTANCE = instance
                }
                return instance
            }
        }

    }
}