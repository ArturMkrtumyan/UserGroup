package com.example.usergroup.Screens;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.usergroup.Group;
import com.example.usergroup.MyViewModelFactory;
import com.example.usergroup.R;
import com.example.usergroup.User;
import com.example.usergroup.UserAdapter;
import com.example.usergroup.ViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.usergroup.Screens.MainActivity.ADD_GROUP_REQUEST;

public class UserActivity extends AppCompatActivity {
    private ViewModel viewModel;
    public static final int ADD_USER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        FloatingActionButton floatingActionButton=findViewById(R.id.button_add_user);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, AddUserActivity.class);
                startActivityForResult(intent, ADD_USER_REQUEST);
            }
        });
        RecyclerView recyclerView=findViewById(R.id.recyclerViewUser);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final UserAdapter userAdapter=new UserAdapter();
        recyclerView.setAdapter(userAdapter);
        viewModel = new ViewModelProvider(this, new MyViewModelFactory(this.getApplication())).get(ViewModel.class);
        viewModel.getAllUsers().observe(UserActivity.this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                userAdapter.setUsers(users);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.delete(userAdapter.getUserat(viewHolder.getAdapterPosition()));
                Toast.makeText(UserActivity.this, "Group deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_USER_REQUEST && resultCode == RESULT_OK) {
            String username = data.getStringExtra(AddUserActivity.USER_NAME);
            String usersurname = data.getStringExtra(AddUserActivity.USER_SURNAME);
            User user = new User(username, usersurname);
            viewModel.insert(user);
            Toast.makeText(this, "User Saved", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(this, "User not Saved", Toast.LENGTH_SHORT).show();
        }
    }
}
