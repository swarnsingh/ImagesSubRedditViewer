ImageLoader
===========

ImageLoader is a image downloading and caching library which is use to store & retrieve images in Android. The Cache stores the data in cache and disk memory. 
ImageLoader can be used to show a list of images smooth and fast as possible.

How do I use ImageLoader?
=========================
-----
## Example

```
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
```

-----

Compatibility
=============

* **Minimum Android SDK**: ImageLoader requires a minimum API level of 19.
