package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


import java.util.ArrayList;

/**
 * Created by HD on 4/22/2018.
 */

public class RecycleviewAdapter extends RecyclerView.Adapter<RecycleviewAdapter.MyViewHolder> {

    private static int viewHolderCount;
    final private GridItemClickListener mOnClickListener;
    private int viewItemsCount;
    private ArrayList<Movie> movieArrayList;
    private Context context;

    public RecycleviewAdapter(int numberOfItems, GridItemClickListener listener, ArrayList moviesList, Context appContext) {
        viewItemsCount = numberOfItems;
        mOnClickListener = listener;
        viewHolderCount = 0;
        movieArrayList = moviesList;
        context = appContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = layoutInflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        MyViewHolder viewHolder = new MyViewHolder(view);
        viewHolderCount++;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(context, movieArrayList.get(position), mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return viewItemsCount;
    }

    public interface GridItemClickListener {
        void onGridItemClick(Movie clickedItemIndex);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        ImageView movieImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            movieImageView = (ImageView) itemView.findViewById(R.id.iv_movie);
        }

       public  void bind(Context context, final Movie movie, final GridItemClickListener listener) {
            int index = viewHolderCount - 1;
            StringBuilder url = new StringBuilder("http://image.tmdb.org/t/p/w185/");
            Movie movieItem = movieArrayList.get(index);
            String poster_path = movieItem.getPosterPath();
            url.append(poster_path);
            Picasso.with(context).load(url.toString()).into(movieImageView);
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override public void onClick(View v) {
                   listener.onGridItemClick(movie);
               }
           });

        }

       }
}
