package com.swarn.redditviewer.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.swarn.redditviewer.R
import com.swarn.redditviewer.adapter.RedditAdapter
import com.swarn.redditviewer.ui.activity.MainActivity
import com.swarn.redditviewer.viewmodel.RedditViewModel
import com.swarn.redditviewer.vo.Resource

class RedditFragment : Fragment() {

    private lateinit var redditViewModel: RedditViewModel

    private lateinit var redditAdapter: RedditAdapter

    private lateinit var redditRecyclerView: RecyclerView

    private lateinit var mProgressBar: ImageView

    companion object {
        fun newInstance() = RedditFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.reddit_fragment, container, false)
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).setHome()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mProgressBar = requireActivity().findViewById(R.id.progress_bar_img_view)

        val layoutManager = LinearLayoutManager(activity)
        redditRecyclerView = requireActivity().findViewById(R.id.reddit_recycler_view)
        redditRecyclerView.layoutManager = layoutManager
        redditRecyclerView.setHasFixedSize(false)

        redditAdapter = RedditAdapter()
        redditRecyclerView.adapter = redditAdapter

        redditViewModel = ViewModelProviders.of(this).get(RedditViewModel::class.java)

        redditViewModel.getRedditImages()
            ?.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is Resource.Loading -> {
                        mProgressBar.visibility = View.VISIBLE
                        redditRecyclerView.visibility = View.GONE
                        setRotation(mProgressBar)
                    }
                    is Resource.Success -> {
                        val data = it.data?.data?.children
                        if (data != null) {
                            redditAdapter.setData(data)
                            redditAdapter.notifyDataSetChanged()
                        }

                        mProgressBar.animation = null
                        mProgressBar.visibility = View.GONE
                        redditRecyclerView.visibility = View.VISIBLE
                    }
                    is Resource.Error -> {
                        mProgressBar.animation = null
                        mProgressBar.visibility = View.GONE
                        redditRecyclerView.visibility = View.VISIBLE
                    }
                }
            })
    }

    private fun setRotation(view: ImageView) {
        val startRotateAnimation =
            AnimationUtils.loadAnimation(
                requireActivity().applicationContext,
                R.anim.rotation_animation
            )
        view.startAnimation(startRotateAnimation)
    }

}