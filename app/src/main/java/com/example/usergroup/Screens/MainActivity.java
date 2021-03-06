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
import com.example.usergroup.GroupAdapter;
import com.example.usergroup.MyViewModelFactory;
import com.example.usergroup.R;
import com.example.usergroup.ViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_GROUP_REQUEST = 1;
    public static final int EDIT_GROUP_REQUEST = 2;

    private ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton floatingActionButton = findViewById(R.id.button_add_group);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditGroupActivity.class);
                startActivityForResult(intent, ADD_GROUP_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final GroupAdapter groupAdapter = new GroupAdapter();
        recyclerView.setAdapter(groupAdapter);
        viewModel = new ViewModelProvider(this, new MyViewModelFactory(this.getApplication())).get(ViewModel.class);
        viewModel.getAllGroup().observe(MainActivity.this, new Observer<List<Group>>() {
            @Override
            public void onChanged(List<Group> groups) {
                groupAdapter.setGroups(groups);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.delete(groupAdapter.getGroupat(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Group deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
        groupAdapter.setOnItemClickLongListener(new GroupAdapter.OnItemClickLongListener() {
            @Override
            public void onItemLongClick(Group group) {
                Intent intent = new Intent(MainActivity.this, AddEditGroupActivity.class);
                intent.putExtra(AddEditGroupActivity.GROUP_NAME, group.getName());
                intent.putExtra(AddEditGroupActivity.GROUP_ID, group.getGroupId());
                startActivityForResult(intent, EDIT_GROUP_REQUEST);
            }
        });
        groupAdapter.setOnItemClickListener(new GroupAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Group group) {
                Intent intent=new Intent(MainActivity.this,UserActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_GROUP_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddEditGroupActivity.GROUP_NAME);
            Group group = new Group(name);
            viewModel.insert(group);
            Toast.makeText(this, "Group Saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_GROUP_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditGroupActivity.GROUP_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Group can't be apdated", Toast.LENGTH_SHORT).show();
                return;
            }
            String name = data.getStringExtra(AddEditGroupActivity.GROUP_NAME);
            Group group = new Group(name);
            group.setGroupId(id);
            viewModel.update(group);
            Toast.makeText(this, "Group updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Group note Saved", Toast.LENGTH_SHORT).show();
        }
    }
}
