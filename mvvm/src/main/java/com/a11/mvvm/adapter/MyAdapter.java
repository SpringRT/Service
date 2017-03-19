package com.a11.mvvm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.a11.mvvm.R;
import com.a11.mvvm.activities.MainActivity;
import com.a11.mvvm.model.User;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {

    private ArrayList<String> dataSet;
    private RecyclerView recycler;
    private MainActivity mainActivity;

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        ImageButton mDeleteButton;

        ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.tv_recycler_item);
            mDeleteButton = (ImageButton) v.findViewById(R.id.delete);
            v.setOnClickListener(MyAdapter.this);
        }
    }

    @Override
    public void onClick(final View view) {
        int itemPosition = recycler.getChildLayoutPosition(view);
        mainActivity.onItemClick(itemPosition);
    }

    public MyAdapter(ArrayList<User> dataSet, RecyclerView recycler, MainActivity mainActivity){
        this.mainActivity = mainActivity;
        this.recycler = recycler;
        this.dataSet = new ArrayList<>();
        for (int i = 0; i<dataSet.size(); i++) {
            User user = dataSet.get(i);
            this.dataSet.add(user.getName() + " " + user.getSurname());
        }
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mTextView.setText(dataSet.get(position));
        View delete = holder.mDeleteButton;
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                dataSet.remove(position);
                notifyItemRemoved(position);
                mainActivity.onClickDelete(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
