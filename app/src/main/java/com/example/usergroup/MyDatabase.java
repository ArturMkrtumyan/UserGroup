package com.example.usergroup;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Group.class, User.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    private static MyDatabase instance;

    public abstract GroupDao getGroupDao();

    public abstract UserDao getUserDao();

    public static synchronized MyDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), MyDatabase.class, "MyDb").fallbackToDestructiveMigration().addCallback(callback).build();

        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateAsyncTask(instance).execute();
        }
    };

    private static class PopulateAsyncTask extends AsyncTask<Void, Void, Void> {
        private GroupDao groupDao;
        private UserDao userDao;

        public PopulateAsyncTask(MyDatabase db) {
            groupDao = db.getGroupDao();
            userDao = db.getUserDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            groupDao.insert(new Group("Group 1"));
            groupDao.insert(new Group("Group 2"));
            groupDao.insert(new Group("Group 3"));
            userDao.insert(new User("Artur", "Mkrtumyan"));
            userDao.insert(new User("Petros", "Karapetyan"));
            userDao.insert(new User("Taron", "Aramyan"));
            return null;
        }
    }
}
