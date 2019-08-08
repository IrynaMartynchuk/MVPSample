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

class ItemListAdaptor(var context: Context?, val onClickListener: OnHeartIconClickListener) : RecyclerView.Adapter<ItemListAdaptor.ViewHolder>() {

    private var data: ArrayList<Item> = ArrayList()

    fun setData(items: List<Item>){
        this.data.clear()
        this.data.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.grid_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.description.text = data.get(position).description
        holder.location.text = data.get(position).location
        if (data.get(position).price?.value != null) {
            holder.price.text =  String.format("%s,-", data.get(position).price?.value.toString())
        } else {
            holder.price.text = "0,-"
        }

        holder.favoriteHeart.setImageResource(R.drawable.heart_icon)
        holder.favoriteHeart.isActivated = data.get(position).isFavorite
        data.get(position).image!!.url.let {
            Glide.with(holder.finnImage.context)
                .load(BASE_IMG_URL + data.get(position).image?.url)
                .transform(CenterCrop(), RoundedCorners(25))
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.broken_image))
                .into(holder.finnImage)
        }

        //to animate heart icon on add and remove to/from favorites
        val scaleAnimation = ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f,
            Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f)
        scaleAnimation.duration = 500
        val bounceInterpolator = BounceInterpolator()
        scaleAnimation.setInterpolator(bounceInterpolator)

        holder.favoriteHeart.setOnClickListener{
            onClickListener.onClick(data.get(position), it)
            it.startAnimation(scaleAnimation)
            it.favorite_heart.isActivated = !it.favorite_heart.isActivated
        }

        holder.finnImage.setOnClickListener {
            onClickListener.onClick(data.get(position), it)
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val finnImage: ImageView
        val favoriteHeart: ImageView
        val price: TextView
        val location: TextView
        val description: TextView

        init {
            finnImage = itemView.finn_image
            favoriteHeart = itemView.favorite_heart
            price = itemView.price_text
            location = itemView.location
            description = itemView.description
        }
    }

    class OnHeartIconClickListener(val clickListener: (item: Item, view: View) -> Unit) {
        fun onClick(item: Item, view: View) = clickListener(item, view)
    }

}