package com.rogger.myapplication.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.rogger.myapplication.database.ProductEntity;
import com.rogger.myapplication.repositiry.ProductRepositoryImpl;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private final ProductRepositoryImpl repository;
    private LiveData<List<ProductEntity>> allProducts;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        repository = new ProductRepositoryImpl(application);
        allProducts = repository.getAllProducts();
    }
    public LiveData<List<ProductEntity>> getAllProducts() {
        return allProducts;
    }

    public void insert(String name, int quantity, String expirationDate, String barcode) {
        repository.insert(name, quantity, expirationDate, barcode);
    }
    public void deleteById(int id) {
        repository.deleteById(id);
    }
}