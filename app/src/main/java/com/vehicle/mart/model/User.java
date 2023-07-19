package com.vehicle.mart.model;

public class User {
    private String userId, userType, fullName, email, mobileNumber, password, CNIC;

    public User() {
    }

    public User(String userId, String userType, String fullName, String email, String mobileNumber, String password, String CNIC) {
        this.userId = userId;
        this.userType = userType;
        this.fullName = fullName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.CNIC = CNIC;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCNIC() {
        return CNIC;
    }

    public void setCNIC(String CNIC) {
        this.CNIC = CNIC;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userType='" + userType + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", password='" + password + '\'' +
                ", CNIC='" + CNIC + '\'' +
                '}';
    }
}
