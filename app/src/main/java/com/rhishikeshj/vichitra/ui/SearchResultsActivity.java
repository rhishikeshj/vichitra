package com.rhishikeshj.vichitra.ui;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rhishikeshj.vichitra.R;
import com.rhishikeshj.vichitra.managers.ImageSearchManager;
import com.rhishikeshj.vichitra.models.FlickrImage;
import com.rhishikeshj.vichitra.viewmodels.ImageSearchViewModel;

import java.util.List;

/**
 * Created by mjolnir on 23/03/18.
 */

public class SearchResultsActivity extends AppCompatActivity {
    private boolean isLoading;
    private String query;
    private FlickrImageAdapter adapter;
    private ImageSearchViewModel imageSearchViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String currentTheme = preferences.getString("USE_THEME", "Default");
        switch (currentTheme) {
            case "Light":
                setTheme(R.style.AppThemeLight);
                break;
            case "Dark":
                setTheme(R.style.AppThemeDark);
                break;
            default:
                setTheme(R.style.AppTheme);
                break;
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_results_activity);
        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        final GridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 400);
        recyclerView.setLayoutManager(layoutManager);
        // specify an adapter (see also next example)
        adapter = new FlickrImageAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                FlickrImageAdapter.ViewHolder photoViewHolder = (FlickrImageAdapter.ViewHolder) holder;
                Glide.with(SearchResultsActivity.this).clear(photoViewHolder.imageView);
            }
        });

        final AppCompatActivity appCompatActivity = this;
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= 20) {
                        isLoading = true;
                        imageSearchViewModel.getMoreImagesForQuery(query).observe(appCompatActivity,
                                new Observer<List<FlickrImage>>() {
                                    @Override
                                    public void onChanged(@Nullable List<FlickrImage> flickrImages) {
                                        adapter.setImageList(flickrImages);
                                        isLoading = false;
                                    }
                                });
                    }
                }
            }
        });
        ImageSearchManager searchManager = new ImageSearchManager(getApplicationContext());
        imageSearchViewModel = ViewModelProviders.of(this).get(ImageSearchViewModel.class);
        imageSearchViewModel.setImageSearchManager(searchManager);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent searchIntent) {
        if (Intent.ACTION_SEARCH.equals(searchIntent.getAction())) {
            query = searchIntent.getStringExtra(SearchManager.QUERY);
            imageSearchViewModel.getImagesForQuery(query).observe(this, new Observer<List<FlickrImage>>() {
                @Override
                public void onChanged(@Nullable List<FlickrImage> flickrImages) {
                    adapter.setImageList(flickrImages);
                }
            });
        }
    }
}
