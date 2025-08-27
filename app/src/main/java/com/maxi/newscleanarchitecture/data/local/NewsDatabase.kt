package com.maxi.newscleanarchitecture.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.maxi.newscleanarchitecture.data.local.dao.NewsDao
import com.maxi.newscleanarchitecture.data.local.entity.ArticleEntity
import com.maxi.newscleanarchitecture.data.local.utils.Converters

@Database(
    entities = [ArticleEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(
    Converters::class
)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newDao(): NewsDao

}