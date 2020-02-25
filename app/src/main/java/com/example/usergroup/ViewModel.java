package com.example.usergroup;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Group>>allGroup;
    private LiveData<List<User>>allUsers;

    public ViewModel(@NonNull Application application) {
        super(application);
        repository=new Repository(application);
        allGroup=repository.getAllGroups();
        allUsers=repository.getAllUsers();
    }
    public void insert(Group group){
        repository.insert(group);
    }
    public void update(Group group){
        repository.update(group);
    }
    public void delete(Group group){
        repository.delete(group);
    }
    public LiveData<List<Group>>getAllGroup(){
        return allGroup;
    }
    public void insert(User user){
        repository.insert(user);
    }
    public void update(User user){
        repository.update(user);
    }
    public void delete(User user){
        repository.delete(user);
    }
    public LiveData<List<User>>getAllUsers(){
        return allUsers;
    }
}
