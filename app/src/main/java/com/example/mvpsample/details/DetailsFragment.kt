package com.example.mvpsample.details

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.mvpsample.main.BaseFragment
import com.example.mvpsample.R
import com.example.mvpsample.main.MainActivity
import com.example.mvpsample.model.networking.Item

private const val BASE_IMG_URL = "https://images.finncdn.no/dynamic/480x360c/"

class DetailsFragment: BaseFragment(),
    DetailsFragmentContract.View {

    private lateinit var presenter: DetailsFragmentContract.Presenter
    private lateinit var image: ImageView
    private lateinit var location: TextView
    private lateinit var description: TextView
    private lateinit var price: TextView
    private var item: Item? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = DetailsFragmentPresenter(this)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        item = this.arguments?.getParcelable("item")
        image = rootView.findViewById(R.id.main_photo_image)
        location = rootView.findViewById(R.id.item_location)
        description = rootView.findViewById(R.id.item_description)
        price = rootView.findViewById(R.id.item_price)

        item?.image?.url.let {
            Glide.with(image.context)
                .load(BASE_IMG_URL + item?.image?.url)
                .transform(CenterCrop(), RoundedCorners(25))
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.broken_image))
                .into(image)
        }
        location.text = item?.location
        description.text = item?.description
        price.text = String.format("%s,-", item?.price?.value.toString())

        //to show back button in a toolbar menu
        showBackArrow()
        setHasOptionsMenu(true)
    }

    fun showBackArrow() {
        val activity = activity as MainActivity
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.setDisplayShowTitleEnabled(false)
        activity.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp)
    }

    override fun getLayout(): Int {
        return R.layout.fragment_details
    }

    override fun getContext(): Context {
        return super.getContext()!!
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.favorite_heart, menu)
        setIcon(presenter.isFavorite(item), menu.getItem(0))

        super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onOptionsItemSelected(fav_icon: MenuItem): Boolean {
        val activity = activity as? MainActivity
        return when (fav_icon.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }
            R.id.favorite_heart -> {
                presenter.clickOnHeart(item)
                setIcon(presenter.isFavorite(item), fav_icon)
                true
            }
            else -> super.onOptionsItemSelected(fav_icon)
        }
    }

    private fun setIcon(isFavorite: Boolean, menuItem: MenuItem) {
        if (isFavorite) {
            menuItem.setIcon(R.drawable.ic_favorite_black_24dp)
        } else menuItem.setIcon(R.drawable.ic_favorite_border_black_24dp)
    }

}