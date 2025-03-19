package com.rogger.myapplication.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProductEntity product);

    @Query("DELETE FROM products WHERE id = :productId")
    void deleteById(int productId);

    @Query("SELECT * FROM products")
    LiveData<List<ProductEntity>> getAllProducts();

}
