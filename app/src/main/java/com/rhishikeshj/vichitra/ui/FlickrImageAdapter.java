package com.rhishikeshj.vichitra.ui;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rhishikeshj.vichitra.R;
import com.rhishikeshj.vichitra.models.FlickrImage;

import java.util.List;

/**
 * Created by mjolnir on 20/03/18.
 */

public class FlickrImageAdapter extends RecyclerView.Adapter<FlickrImageAdapter.ViewHolder> {
    private List<FlickrImage> imageList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public ImageView imageView;

        FlickrImage image;

        public ViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.textView);
            imageView = v.findViewById(R.id.imageView);
        }

        public void setImageData(FlickrImage image) {
            this.image = image;
            textView.setText(image.title);
            // set image for the view with glide
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FlickrImageAdapter() {
    }

    public void setImageList(final List<FlickrImage> newImageList) {
        if (imageList == null) {
            imageList = newImageList;
            notifyItemRangeInserted(0, newImageList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return imageList.size();
                }

                @Override
                public int getNewListSize() {
                    return newImageList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return imageList.get(oldItemPosition).imageLink.equals(newImageList.get(newItemPosition).imageLink);
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    FlickrImage newImage = newImageList.get(newItemPosition);
                    FlickrImage oldImage = imageList.get(oldItemPosition);
                    return newImage.title.equals(oldImage.title)
                            && newImage.description.equals(oldImage.description)
                            && newImage.imageLink.equals(oldImage.imageLink)
                            && newImage.getThumbnailLink().equals(oldImage.getThumbnailLink());
                }
            });
            imageList = newImageList;
            result.dispatchUpdatesTo(this);
        }
    }


    // Create new views (invoked by the layout manager)
    @Override
    public FlickrImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_grid_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.setImageData(imageList.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return imageList == null ? 0 : imageList.size();
    }
}

