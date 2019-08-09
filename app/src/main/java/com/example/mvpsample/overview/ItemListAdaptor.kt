package com.example.mvpsample.overview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.mvpsample.utils.BASE_IMG_URL
import com.example.mvpsample.R
import com.example.mvpsample.model.networking.Item
import kotlinx.android.synthetic.main.grid_view_item.view.*

typealias ClickListener = (item: Item) -> Unit

class ItemListAdaptor(
    var context: Context?,
    val onHeartClicked: ClickListener,
    val onItemClicked: ClickListener
) : RecyclerView.Adapter<ItemListAdaptor.ViewHolder>() {

    private var data: ArrayList<Item> = ArrayList()

    //to animate heart icon on add and remove to/from favorites
    val scaleAnimation = ScaleAnimation(
        0.7f, 1.0f, 0.7f, 1.0f,
        Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f
    ).apply {
        duration = 500
        interpolator = BounceInterpolator()
    }

    fun setData(items: List<Item>) {
        this.data.clear()
        this.data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.grid_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val finnImage: ImageView = itemView.finn_image
        private val favoriteHeart: ImageView = itemView.favorite_heart
        private val price: TextView = itemView.price_text
        private val location: TextView = itemView.location
        private val description: TextView = itemView.description

        fun bind(item: Item) {
            description.text = item.description
            location.text = item.location
            price.text = "${item.price?.value?.toString() ?: 0},-"

            favoriteHeart.setImageResource(R.drawable.heart_icon)
            favoriteHeart.isActivated = item.isFavorite
            item.image!!.url.let {
                Glide.with(finnImage.context)
                    .load(BASE_IMG_URL + item.image.url)
                    .transform(CenterCrop(), RoundedCorners(25))
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.loading_animation)
                            .error(R.drawable.broken_image)
                    )
                    .into(finnImage)
            }

            favoriteHeart.setOnClickListener {
                onHeartClicked(item)
                it.startAnimation(scaleAnimation)
                it.favorite_heart.isActivated = !it.favorite_heart.isActivated
            }

            finnImage.setOnClickListener {
                onItemClicked(item)
            }
        }
    }

}