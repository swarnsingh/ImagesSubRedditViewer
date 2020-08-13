package com.swarn.redditviewer.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.swarn.imageloader.ImageLoader
import com.swarn.redditviewer.R
import com.swarn.redditviewer.data.Children
import com.swarn.redditviewer.ui.activity.MainActivity
import com.swarn.redditviewer.ui.fragment.PhotoFragment
import java.util.*

/**
 * @author Swarn Singh
 */
class RedditAdapter : RecyclerView.Adapter<RedditAdapter.RedditViewHolder>() {

    private var redditData: List<Children> = ArrayList()

    fun setData(data: List<Children>) {
        redditData = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RedditViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.reddit_image_holder_layout, parent, false)

        return RedditViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return redditData.size
    }

    override fun onBindViewHolder(holder: RedditViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class RedditViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val redditImgView: ImageView = itemView.findViewById(R.id.reddit_img_view)
        fun onBind(position: Int) {
            val url = redditData[position].data.thumbnail

            ImageLoader.get(redditImgView.context)
                .load(url, redditImgView)

            redditImgView.setOnClickListener {
                if (redditImgView.context is MainActivity) {
                    val fragment = PhotoFragment()
                    val bundle = Bundle()
                    bundle.putString("url", url)
                    fragment.arguments = bundle
                    (redditImgView.context as MainActivity).navigateFragment(fragment, false)
                }
            }
        }
    }
}