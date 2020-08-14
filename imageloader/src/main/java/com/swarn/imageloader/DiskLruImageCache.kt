package com.swarn.imageloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.os.Environment
import com.jakewharton.disklrucache.DiskLruCache
import java.io.*


const val KEY_REGEX = "[^a-z0-9_]"
const val UNDERSCORE = "_"

class DiskLruImageCache(
    context: Context,
    uniqueName: String,
    diskCacheSize: Int,
    compressFormat: CompressFormat,
    quality: Int
) {
    private lateinit var mDiskCache: DiskLruCache
    private var mCompressFormat = CompressFormat.JPEG
    private var mCompressQuality = 100
    private val APP_VERSION = 1
    private val VALUE_COUNT = 1

    init {
        try {
            val diskCacheDir: File = getDiskCacheDir(context, uniqueName)
            mDiskCache = DiskLruCache.open(
                diskCacheDir,
                APP_VERSION,
                VALUE_COUNT,
                diskCacheSize.toLong()
            )
            mCompressFormat = compressFormat
            mCompressQuality = quality
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class, FileNotFoundException::class)
    private fun writeBitmapToFile(
        bitmap: Bitmap,
        editor: DiskLruCache.Editor
    ): Boolean {
        var out: OutputStream? = null
        return try {
            out = BufferedOutputStream(editor.newOutputStream(0), Util.IO_BUFFER_SIZE)
            bitmap.compress(mCompressFormat, mCompressQuality, out)
        } finally {
            out?.close()
        }
    }

    private fun getDiskCacheDir(
        context: Context,
        uniqueName: String
    ): File {

        // Check if media is mounted or storage is built-in, if so, try and use external cache dir
        // otherwise use internal cache dir
        val cachePath =
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
            ) Util.getExternalCacheDir(context)?.path else context.cacheDir.path
        return File(cachePath + File.separator.toString() + uniqueName)
    }

    fun put(key: String, data: Bitmap) {
        var editor: DiskLruCache.Editor? = null
        try {
            editor = mDiskCache.edit(getDiskCacheKey(key))
            if (editor == null) {
                return
            }
            if (writeBitmapToFile(data, editor)) {
                mDiskCache.flush()
                editor.commit()
            } else {
                editor.abort()
            }
        } catch (e: IOException) {
            try {
                editor?.abort()
            } catch (ignored: IOException) {
            }
        }
    }

    fun getBitmap(key: String): Bitmap? {
        var bitmap: Bitmap? = null
        var snapshot: DiskLruCache.Snapshot? = null
        try {
            snapshot = mDiskCache[getDiskCacheKey(key)]
            if (snapshot == null) {
                return null
            }
            val inputStream = snapshot.getInputStream(0)
            if (inputStream != null) {
                val buffIn = BufferedInputStream(inputStream, Util.IO_BUFFER_SIZE)
                bitmap = BitmapFactory.decodeStream(buffIn)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            snapshot?.close()
        }
        return bitmap
    }

    fun containsKey(key: String): Boolean {
        var contained = false
        var snapshot: DiskLruCache.Snapshot? = null
        try {
            snapshot = mDiskCache[getDiskCacheKey(key)]
            contained = snapshot != null
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            snapshot?.close()
        }
        return contained
    }

    fun clearCache() {
        try {
            mDiskCache.delete()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getCacheFolder(): File? {
        return mDiskCache.directory
    }

    private fun getDiskCacheKey(key: String): String {
        return key.substring(0, 64).replace(Regex(KEY_REGEX), UNDERSCORE).toLowerCase()
    }
}