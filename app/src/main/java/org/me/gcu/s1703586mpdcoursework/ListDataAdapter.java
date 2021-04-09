//Student Name: Sofi Bambrick
//Student ID: S1703586

package org.me.gcu.s1703586mpdcoursework;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class ListDataAdapter extends RecyclerView.Adapter<ListDataAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    public ArrayList<ItemClass> itemList;

    @Override
    public void onClick(View v) {
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View view) {
            super(view);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public ListDataAdapter(Context context,ArrayList<ItemClass> events) {
        this.context = context;
        this.itemList =events;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View viewTWO = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        MainViewImage rowTWO = new MainViewImage(viewTWO);
        return rowTWO;
    }


    public class MainViewImage extends MyViewHolder {

        LinearLayout rootView;
        TextView title;
        TextView description;

        public MainViewImage(View v) {
            super(v);

            rootView = (LinearLayout) v.findViewById(R.id.rootView);
            title = (TextView) v.findViewById(R.id.title);
            description = (TextView) v.findViewById(R.id.description);
        }
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final ItemClass event = itemList.get(position);
        final MainViewImage mainHolder = (MainViewImage) holder;

        Collections.sort(itemList);
        mainHolder.description.setText(event.getLocation() + "\n" + " Strength: " + event.getMagnitude());

        mainHolder.description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent intent = new Intent(context.getApplicationContext(), More.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Earthquake", event);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }
}