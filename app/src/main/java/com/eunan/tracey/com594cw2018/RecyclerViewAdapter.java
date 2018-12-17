package com.eunan.tracey.com594cw2018;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private Context context;
    ArrayList<StackModel> stackModels;

    public RecyclerViewAdapter(Context context, ArrayList<StackModel> stackModel) {
        Log.d(TAG, "RecyclerViewAdapter: called");
        this.context = context;
        this.stackModels = stackModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.d(TAG, "onCreateViewHolder: starts");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stack_layoutitems, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        Log.d(TAG, "onBindViewHolder: called");
        final StackModel stackModel = stackModels.get(i);
        holder.title.setText(stackModel.title);
        try {

            Picasso.with(context).load(stackModel.getImage()).into(holder.image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: starts");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(stackModel.getLink()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                Log.d(TAG, "onClick: ends");
            }
        });
        Log.d(TAG, "onBindViewHolder: ends");
    }

    @Override
    public int getItemCount() {
        return stackModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        ConstraintLayout link;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.image_name);
            constraintLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}

