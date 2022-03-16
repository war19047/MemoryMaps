
package com.example.memory_maps;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    // Variable for our context and array list.
    private final Context context;
    private final ArrayList<String> imagePathArrayList;

    // Constructor.
    public RecyclerViewAdapter(Context context, ArrayList<String> imagePathArrayList) {
        this.context = context;
        this.imagePathArrayList = imagePathArrayList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout in this method which we have created.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        // Get file from path from the list.
        File imgFile = new File(imagePathArrayList.get(position));

        // Check if file exists.
        if (imgFile.exists()) {

            // Display image.
            Picasso.get().load(imgFile).placeholder(R.drawable.ic_launcher_background).into(holder.imageIV);

            // Add on click listener to our item of recycler view.
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(context, ImageDetailActivity.class);

                    // Pass image path to new activity.
                    i.putExtra("imgPath", imagePathArrayList.get(position));

                    // start activity.
                    context.startActivity(i);

                }
            });
        }
    }

    @Override
    public int getItemCount() {

        // Size of recyclerview.
        return imagePathArrayList.size();
    }

    // View Holder Class to handle Recycler View.

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageIV;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing views with their ids.
            imageIV = itemView.findViewById(R.id.idIVImage);
        }
    }
}
