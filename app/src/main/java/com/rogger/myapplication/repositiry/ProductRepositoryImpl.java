package com.rogger.myapplication.repositiry;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.rogger.myapplication.database.ProductDao;
import com.rogger.myapplication.database.ProductEntity;
import com.rogger.myapplication.database.ProductRepository;
import com.rogger.myapplication.database.StockDatabaseProvider;

import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {
    private final ProductDao productDao;
    private final LiveData<List<ProductEntity>> allProducts;

    public ProductRepositoryImpl(Application application) {
        StockDatabaseProvider db = StockDatabaseProvider.getDatabase(application);
        productDao = db.productDao();
        allProducts = productDao.getAllProducts();
    }

    @Override
    public void insert(String name, int quantity, String expirationDate, String barcode) {
        new Thread(() -> productDao.insert(new ProductEntity(name, quantity, expirationDate, barcode))).start();
    }
    @Override
    public LiveData<List<ProductEntity>> getAllProducts() {
        return productDao.getAllProducts();
    }

    @Override
    public void deleteById(int id) {
        new Thread(() -> productDao.deleteById(id)).start();
    }

}
