package com.swarn.imageloader

import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.os.Process
import android.util.LruCache
import android.widget.ImageView
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * @author Swarn Singh
 */
private const val DISK_CACHE_SUB_DIR = "thumbnails"
private const val DISK_CACHE_SIZE = 1024 * 1024 * 10 // 10MB

class ImageLoader {

    private val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()

    private val threadPoolSize = Runtime.getRuntime().availableProcessors() + 1

    private val maxCacheSize = maxMemory / 8

    private var memoryCache: LruCache<String, Bitmap>

    private var diskLruImageCache: DiskLruImageCache

    private val imgMap = Collections.synchronizedMap(WeakHashMap<ImageView, String>())

    private val executorService: ExecutorService

    private val handler: Handler

    private var screenWidth: Int = 0
    private var screenHeight: Int = 0


    companion object {
        private var singleton: ImageLoader? = null
        private lateinit var context: Context
        fun get(context: Context): ImageLoader {
            this.context = context
            if (singleton == null) {
                synchronized(ImageLoader::class.java) {
                    if (singleton == null) {
                        singleton = ImageLoader()
                    }
                }
            }
            return singleton!!
        }
    }

    init {
        memoryCache = object : LruCache<String, Bitmap>(maxCacheSize) {
            override fun sizeOf(key: String, bitmap: Bitmap): Int {
                // The cache size will be measured in kilobytes rather than number of items.
                return bitmap.byteCount / 1024
            }
        }

        diskLruImageCache = DiskLruImageCache(context, DISK_CACHE_SUB_DIR, DISK_CACHE_SIZE, Bitmap.CompressFormat.JPEG, 100)

        executorService = Executors.newFixedThreadPool(threadPoolSize) { r ->
            Thread(r).apply {
                priority = Process.THREAD_PRIORITY_BACKGROUND
            }
        }

        handler = Handler(Looper.getMainLooper())
    }

    fun displayBitmap(runnable: Runnable) {
        handler.post(runnable)
    }

    fun getExecutorService(): ExecutorService {
        return executorService
    }

    fun getMemoryCache(): LruCache<String, Bitmap> {
        return memoryCache
    }

    fun getImageMap(): MutableMap<ImageView, String> {
        return imgMap
    }

    fun getScreenWidth(): Int {
        return screenWidth
    }

    fun getScreenHeight(): Int {
        return screenHeight
    }

    fun load(url: String, imageView: ImageView) {
        val metrics = context.resources.displayMetrics
        screenWidth = metrics.widthPixels
        screenHeight = metrics.heightPixels

        imageView.setImageResource(0)

        if (getImageMap()[imageView] == null) {
            getImageMap()[imageView] = url
        }

        val bitmap = checkImageInCache(url)

        if (bitmap != null) {
            loadBitmapToImageView(bitmap, imageView, url)
        } else {
            getExecutorService().submit(BitmapWorkerTask(imageView, url))
        }
    }

    @Synchronized
    private fun loadBitmapToImageView(bitmap: Bitmap?, imageView: ImageView, url: String) {
        if (bitmap != null) {
            val scaledBitmap = Util.scaleBitmapForLoad(bitmap, imageView.width, imageView.height)

            scaledBitmap?.let {
                imageView.setImageBitmap(scaledBitmap)
            }
        }
    }

    private fun isUrlReused(imageView: ImageView, url: String): Boolean {
        val tag = getImageMap()[imageView]
        return tag == null || tag != url
    }

    @Synchronized
    private fun checkImageInCache(imageUrl: String): Bitmap? =
        getMemoryCache().get(imageUrl)

    inner class BitmapWorkerTask(private var imageView: ImageView, var url: String) : Runnable {
        override fun run() {

            var bitmap = diskLruImageCache.getBitmap(url)

            if (bitmap == null) {
                bitmap = Util.downloadBitmapFromURL(
                    url,
                    getScreenWidth(),
                    getScreenHeight()
                )
                bitmap?.let { diskLruImageCache.put(url, it) }
            }

            if (bitmap != null) {
                getMemoryCache().put(url, bitmap)

                displayBitmap(Runnable {
                    loadBitmapToImageView(bitmap, imageView, url)
                })
            }
        }
    }
}