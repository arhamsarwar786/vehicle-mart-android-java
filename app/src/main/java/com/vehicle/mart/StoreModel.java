package com.vehicle.mart;

public class StoreModel {
    private String name;
    private String address;
    private String ntn;
    private String termsAndCondition;
    private String userId;
    private boolean verified;

    // Constructors
    public StoreModel() {
        // Default constructor required for Firebase Realtime Database
    }

    public StoreModel(String name, String address, String ntn, String termsAndCondition, String userId, boolean verified) {
        this.name = name;
        this.address = address;
        this.ntn = ntn;
        this.termsAndCondition = termsAndCondition;
        this.userId = userId;
        this.verified = verified;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNtn() {
        return ntn;
    }

    public void setNtn(String ntn) {
        this.ntn = ntn;
    }

    public String getTermsAndCondition() {
        return termsAndCondition;
    }

    public void setTermsAndCondition(String termsAndCondition) {
        this.termsAndCondition = termsAndCondition;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    // Optional: Override toString() for debugging or logging purposes
    @Override
    public String toString() {
        return "StoreModel{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", ntn='" + ntn + '\'' +
                ", termsAndCondition='" + termsAndCondition + '\'' +
                ", userId='" + userId + '\'' +
                ", verified=" + verified +
                '}';
    }
}
