package com.rhishikeshj.vichitra.managers;

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

/**
 * Image search network manager.
 * This class is reponsible for managing the pool of threads
 * on which the Image fetch requests are run.
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

    public ImageSearchNetworkManager(ImageService imageService) {
        this.imageService = imageService;
        workerQueue = new LinkedBlockingQueue<Runnable>();
        workerThreadPool = new ThreadPoolExecutor(NUMBER_OF_CORES,
                NUMBER_OF_CORES,
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                workerQueue);
    }

    /**
     * API to search for images on a threadpool
     *
     * @param query
     * @param listener
     */
    public void searchForImages(final String query, final ImageSearchNetworkListener listener) {
        workerThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                List<FlickrImage> images = imageService.getImagesForQuery(query);
                if (listener != null) {
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
