package com.example.usergroup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder>{
    List<User>users=new ArrayList<>();
    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        return new UserHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        User currentUser=users.get(position);
        holder.nameUser.setText(currentUser.getNameUser());
        holder.surnameUser.setText(currentUser.getSurnameUser());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
    public User getUserat(int position) {
        return users.get(position);
    }

    public void setUsers(List<User>users){
        this.users=users;
        notifyDataSetChanged();
    }

    class UserHolder extends RecyclerView.ViewHolder{
        private TextView nameUser;
        private TextView surnameUser;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            nameUser=itemView.findViewById(R.id.nameUser);
            surnameUser=itemView.findViewById(R.id.surnameUser);
        }
    }
}
