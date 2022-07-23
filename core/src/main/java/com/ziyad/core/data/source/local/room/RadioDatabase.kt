package com.ziyad.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ziyad.core.data.source.local.entity.RadioEntity

@Database(entities = [RadioEntity::class], version = 1)
abstract class RadioDatabase : RoomDatabase() {
    abstract fun radioDAO(): RadioDAO
}