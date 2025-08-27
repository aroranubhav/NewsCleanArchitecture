package com.maxi.newscleanarchitecture.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maxi.newscleanarchitecture.data.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNews(articles: List<ArticleEntity>): List<Long>

    @Query("SELECT * FROM articles ORDER BY publishedAt DESC")
    fun getNews(): Flow<List<ArticleEntity>>
}