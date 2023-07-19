package com.vehicle.mart.model;

public class Store  {
    private String storeId, userId, name, address, ntn, termsAndCondition;
    private boolean isVerified;

    public Store() {
    }

    public Store(String userId, String name, String address, String ntn, String termsAndCondition, boolean isVerified) {
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.ntn = ntn;
        this.termsAndCondition = termsAndCondition;
        this.isVerified = isVerified;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    @Override
    public String toString() {
        return "Store{" +
                "storeId='" + storeId + '\'' +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", ntn='" + ntn + '\'' +
                ", termsAndCondition='" + termsAndCondition + '\'' +
                ", isVerified=" + isVerified +
                '}';
    }
}
