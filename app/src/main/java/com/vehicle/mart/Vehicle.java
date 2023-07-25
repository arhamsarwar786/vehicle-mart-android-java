package com.vehicle.mart;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

public class Vehicle implements Parcelable {
    private String vehicleId, storeId, vehicleType,type, brand, model, year, mileage, transmission, fuelType, engineType, engineCapacity, registerCity, status, transactionType, price, image,description;

    public Vehicle() {
    }

    public Vehicle(String storeId, String vehicleType, String type, String brand, String model, String year, String mileage, String transmission, String fuelType, String engineType, String engineCapacity, String registerCity,  String transactionType, String price, String description,String image,String status) {
        this.storeId = storeId;
        this.vehicleType = vehicleType;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.transmission = transmission;
        this.fuelType = fuelType;
        this.engineType = engineType;
        this.engineCapacity = engineCapacity;
        this.registerCity = registerCity;
        this.status = status;
        this.transactionType = transactionType;
        this.price = price;
        this.image = image;
        this.description = description;
    }

    protected Vehicle(Parcel in) {
        vehicleId = in.readString();
        storeId = in.readString();
        vehicleType = in.readString();
        type = in.readString();
        brand = in.readString();
        model = in.readString();
        year = in.readString();
        mileage = in.readString();
        transmission = in.readString();
        fuelType = in.readString();
        engineType = in.readString();
        engineCapacity = in.readString();
        registerCity = in.readString();
        status = in.readString();
        transactionType = in.readString();
        price = in.readString();
        image = in.readString();
        description = in.readString();
    }

    public static final Creator<Vehicle> CREATOR = new Creator<Vehicle>() {
        @Override
        public Vehicle createFromParcel(Parcel in) {
            return new Vehicle(in);
        }

        @Override
        public Vehicle[] newArray(int size) {
            return new Vehicle[size];
        }
    };

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleIdd) {
        Log.wtf("test id check",vehicleIdd);
        this.vehicleId = vehicleIdd;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(String engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public String getRegisterCity() {
        return registerCity;
    }

    public void setRegisterCity(String registerCity) {
        this.registerCity = registerCity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId='" + vehicleId + '\'' +
                ", storeId='" + storeId + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", type='" + type + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year='" + year + '\'' +
                ", mileage='" + mileage + '\'' +
                ", transmission='" + transmission + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", engineType='" + engineType + '\'' +
                ", engineCapacity='" + engineCapacity + '\'' +
                ", registerCity='" + registerCity + '\'' +
                ", status='" + status + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", price='" + price + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(vehicleId);
        dest.writeString(storeId);
        dest.writeString(vehicleType);
        dest.writeString(type);
        dest.writeString(brand);
        dest.writeString(model);
        dest.writeString(year);
        dest.writeString(mileage);
        dest.writeString(transmission);
        dest.writeString(fuelType);
        dest.writeString(engineType);
        dest.writeString(engineCapacity);
        dest.writeString(registerCity);
        dest.writeString(status);
        dest.writeString(transactionType);
        dest.writeString(price);
        dest.writeString(image);
        dest.writeString(description);
    }
}
