package com.example.usergroup;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Repository {
    private GroupDao groupDao;
    private UserDao userDao;
    private LiveData<List<Group>> allGroups;
    private LiveData<List<User>> allUsers;

    public Repository(Application application) {
        MyDatabase database = MyDatabase.getInstance(application);
        groupDao = database.getGroupDao();
        allGroups = groupDao.getallGroups();
        userDao = database.getUserDao();
        allUsers = userDao.getallUsers();
    }

    public void insert(Group group) {
        new InsertGroupAsyncTask(groupDao).execute(group);
    }

    public void update(Group group) {
        new UpdateGroupAsyncTask(groupDao).execute(group);

    }

    public void delete(Group group) {
        new DeleteGroupAsyncTask(groupDao).execute(group);
    }

    public LiveData<List<Group>> getAllGroups() {
        return allGroups;
    }

    public void insert(User user) {
        new InsertUserAsyncTask(userDao).execute(user);

    }

    public void update(User user) {
        new UpdateUserAsyncTask(userDao).execute(user);
    }

    public void delete(User user) {
        new DeleteUserAsyncTask(userDao).execute(user);
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    private static class InsertGroupAsyncTask extends AsyncTask<Group, Void, Void> {
        private GroupDao groupDao;

        public InsertGroupAsyncTask(GroupDao groupDao) {
            this.groupDao = groupDao;
        }

        @Override
        protected Void doInBackground(Group... groups) {
            groupDao.insert(groups[0]);
            return null;
        }
    }

    private static class UpdateGroupAsyncTask extends AsyncTask<Group, Void, Void> {
        private GroupDao groupDao;

        public UpdateGroupAsyncTask(GroupDao groupDao) {
            this.groupDao = groupDao;
        }

        @Override
        protected Void doInBackground(Group... groups) {
            groupDao.update(groups[0]);
            return null;
        }
    }

    private static class DeleteGroupAsyncTask extends AsyncTask<Group, Void, Void> {
        private GroupDao groupDao;

        public DeleteGroupAsyncTask(GroupDao groupDao) {
            this.groupDao = groupDao;
        }

        @Override
        protected Void doInBackground(Group... groups) {
            groupDao.delete(groups[0]);
            return null;
        }
    }

    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        public InsertUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }

    private static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        public UpdateUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.update(users[0]);
            return null;
        }
    }

    private static class DeleteUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        public DeleteUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.delete(users[0]);
            return null;
        }
    }

}
