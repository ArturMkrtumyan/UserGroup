package com.example.usergroup;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int idUser;
    private String nameUser;
    private String surnameUser;

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public String getSurnameUser() {
        return surnameUser;
    }

    public User(String nameUser, String surnameUser) {
        this.nameUser = nameUser;
        this.surnameUser = surnameUser;
    }
}
