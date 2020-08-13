package com.swarn.redditviewer.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.swarn.imageloader.ImageLoader
import com.swarn.redditviewer.R
import com.swarn.redditviewer.ui.activity.MainActivity


/**
 * A simple [Fragment] subclass.
 * Use the [PhotoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PhotoFragment : Fragment() {

    private lateinit var photoImgView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as MainActivity).setBackIcon()
        photoImgView = requireActivity().findViewById(R.id.photo_img_view)

        val bundle = this.arguments
        if (bundle != null) {
            val url = bundle.getString("url", "")

            if (!TextUtils.isEmpty(url)) {
                ImageLoader.get(requireActivity())
                    .load(url, photoImgView)
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = PhotoFragment()
    }
}