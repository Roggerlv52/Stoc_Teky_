package com.rogger.myapplication.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public  class ProductEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public int quantity;
    public String expirationDate;
    public String barcode;

    public ProductEntity(String name, int quantity, String expirationDate, String barcode) {
        this.name = name;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
        this.barcode = barcode;
    }
}
