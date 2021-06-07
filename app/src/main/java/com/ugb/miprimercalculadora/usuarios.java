package com.ugb.miprimercalculadora;

import android.net.Uri;

public class usuarios {
    String userName, email,  token;
    Uri  urlPhotoFirestore;

    public usuarios(){}

    public usuarios(String userName, String email, String token, Uri urlPhotoFirestore) {
        this.userName = userName;
        this.email = email;
        this.token = token;
        this.urlPhotoFirestore = urlPhotoFirestore;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Uri getUrlPhotoFirestore() {
        return urlPhotoFirestore;
    }

    public void setUrlPhotoFirestore(Uri urlPhotoFirestore) {
        this.urlPhotoFirestore = urlPhotoFirestore;
    }
}
