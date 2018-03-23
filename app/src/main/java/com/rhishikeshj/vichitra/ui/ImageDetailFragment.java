package com.rhishikeshj.vichitra.ui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rhishikeshj.vichitra.GlideApp;
import com.rhishikeshj.vichitra.R;
import com.rhishikeshj.vichitra.models.FlickrImage;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

/**
 * Created by mjolnir on 22/03/18.
 */

public class ImageDetailFragment extends DialogFragment {

    public static String IMAGE_ARG = "flick_image";

    public static ImageDetailFragment newInstance(FlickrImage image) {
        ImageDetailFragment frag = new ImageDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(IMAGE_ARG, image);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.flickr_image_dialog, container, false);
        if (getArguments() != null) {
            FlickrImage flickrImage = getArguments().getParcelable(IMAGE_ARG);
            ImageView fullscreenView = v.findViewById(R.id.fullscreen_view);
            GlideApp
                    .with(this)
                    .load(flickrImage.getThumbnailLink())
                    .placeholder(R.drawable.placeholder_image)
                    .apply(fitCenterTransform())
                    .into(fullscreenView);
        }
        return v;
    }
}
