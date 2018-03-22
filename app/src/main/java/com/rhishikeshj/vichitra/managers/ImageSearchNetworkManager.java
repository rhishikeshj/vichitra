package com.rhishikeshj.vichitra.managers;

import android.util.Log;

import com.rhishikeshj.vichitra.models.FlickrImage;
import com.rhishikeshj.vichitra.services.ImageService;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by mjolnir on 20/03/18.
 */

public class ImageSearchNetworkManager {
    private static final String TAG = ImageSearchNetworkManager.class.getName();

    private static int NUMBER_OF_CORES =
            Runtime.getRuntime().availableProcessors();
    private static final int KEEP_ALIVE_TIME = 1;
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    private final BlockingQueue<Runnable> workerQueue;
    private final ThreadPoolExecutor workerThreadPool;
    private final ImageService imageService;
    private final ImageSearchNetworkListener listener;

    public ImageSearchNetworkManager(ImageService imageService, ImageSearchNetworkListener listener) {
        this.imageService = imageService;
        this.listener = listener;
        workerQueue = new LinkedBlockingQueue<Runnable>();
        workerThreadPool = new ThreadPoolExecutor(NUMBER_OF_CORES,
                NUMBER_OF_CORES,
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                workerQueue);
    }

    public void searchForImages(final String query) {
        workerThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                List<FlickrImage> images = imageService.getImagesForQuery(query);
                Log.d("Rhi", "DONE !");
                if (listener != null) {
                    Log.d("Rhi", "Non null listener !");
                    if (images.size() > 0) {
                        listener.imagesFetchedForQuery(images, query);
                    } else {
                        listener.imagesNotFetchedForQuery(query);
                    }
                }
            }
        });
    }
}
