package com.example.usergroup.LoginAndRegistration;

public interface LoginView {
    void openHomePage();
    void showToastLoggedIn();
    void showToastException(Exception e);
}