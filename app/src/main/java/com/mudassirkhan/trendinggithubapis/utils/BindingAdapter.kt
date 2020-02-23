package com.mudassirkhan.githubtrendingapis.utils

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


@BindingAdapter("android:visibility")
fun setVisibility(view: View, value: Boolean?) {
    view.visibility = if (value!!) View.VISIBLE else View.GONE
}


@BindingAdapter("bind:imageBitmap")
fun setBitmap(view: ImageView, bitmap: Bitmap?) {
    view.setImageBitmap(bitmap)
}

@BindingAdapter("android:src")
fun setImageUrl(imageView: ImageView, resource: Int) {
    if (resource != 0) {
        imageView.setImageResource(resource)
    }
}

//Extension function for visibility view
fun View.showView(show: Boolean) {
    visibility = if (show) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("loadUrl")
fun loadUrl(imageView: ImageView, url: String?) {
    url?.let {
        Glide.with(imageView.context)
            .load(it)
            .apply(RequestOptions.noTransformation())
            .into(imageView)
    }
}

@BindingAdapter("android:text")
fun setText(view: TextView,value:String?){
    if(value.isNullOrEmpty())
       view.setText( "NA")
   else view.setText(value)
}



@BindingAdapter("android:text")
fun addValue(view: TextView, value: Int?) {
    if (value==null)
        view.setText("N/A")
    else {
        view.setText(Integer.toString(value))
    }
}

@BindingAdapter("android:text")
fun addValue(view: TextView, value: Long) {
    val s3: String = java.lang.String.valueOf(value)
    view.setText(s3)
//
}
