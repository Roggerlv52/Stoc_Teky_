package com.rogger.myapplication.database;

import androidx.lifecycle.LiveData;
import java.util.List;


public interface ProductRepository {

    public void insert(String name, int quantity, String expirationDate, String barcode);

    void deleteById(int id);

    public LiveData<List<ProductEntity>> getAllProducts();
}
