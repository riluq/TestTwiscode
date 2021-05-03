package com.riluq.testtwiscode.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.riluq.testtwiscode.R


@BindingAdapter("app:imageUrl")
fun ImageView.bindImage(imgUrl: String?) {
    imgUrl?.let {
        val imgUri = "https://ranting.twisdev.com/uploads/$it".toUri().buildUpon().scheme("https").build()
        Glide.with(context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken)
            )
            .into(this)

    }
}

@BindingAdapter("app:textString")
fun TextView.bindTextString(text: String?) {
    text.let {
        var text = it
        if (text == "")
            text = null
        this.text = text ?: "-"
    }
}

@BindingAdapter("app:textInt")
fun TextView.bindTextInt(text: Int?) {
    text.let {
        this.text = "${it ?: "-"}"
    }
}

@BindingAdapter("app:textMoney")
fun TextView.bindTextMoney(text: String? /* umumnya integer */) {
    text.let {
        this.text = "Rp. ${if (it != null) CommonUtils.currencyFormat(it) else "0"}"
    }
}