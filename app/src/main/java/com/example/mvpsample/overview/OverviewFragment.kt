package com.example.mvpsample.overview

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Switch
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mvpsample.main.BaseFragment
import com.example.mvpsample.R
import com.example.mvpsample.details.DetailsFragment
import com.example.mvpsample.main.MainActivity
import com.example.mvpsample.model.networking.Item
import kotlinx.android.synthetic.main.fragment_overview.*
import kotlinx.android.synthetic.main.fragment_overview.view.*
import kotlinx.android.synthetic.main.grid_view_item.view.*

class OverviewFragment : BaseFragment(),
    OverviewFragmentContract.View {

    private lateinit var presenter: OverviewFragmentContract.Presenter
    private lateinit var listAdapter: ItemListAdaptor
    private var isToggled: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = OverviewFragmentPresenter(this)
        retainInstance = true
        presenter.getData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //to change amount of columns in a grid according to orientation
        val columns: Int
        if (activity?.resources?.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT) {
            columns = 2
        } else columns = 3
        val layoutManager = GridLayoutManager(this.context, columns)
        photos_grid.layoutManager = layoutManager

        listAdapter = ItemListAdaptor(this.context, ItemListAdaptor.OnHeartIconClickListener { item, view ->
            if (view.id == photos_grid.finn_image.id){
                val detailsFragment = DetailsFragment()
                val args = Bundle()
                args.putParcelable("item", item)
                detailsFragment.arguments = args

                activity?.supportFragmentManager
                    ?.beginTransaction()
                    ?.replace(R.id.fragment_container, detailsFragment)
                    ?.addToBackStack(OverviewFragment().toString())
                    ?.commit()

            } else if (view.id == photos_grid.favorite_heart.id) {
                presenter.clickOnHeart(item)
            }
        })
        photos_grid.adapter = listAdapter

        //to disable back button in a toolbar
        val activity = activity as MainActivity
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toggle_switch, menu)
        val itemSwitch = menu.findItem(R.id.mySwitch)
        itemSwitch.setActionView(R.layout.switch_item)
        val mySwitch: Switch = itemSwitch.actionView.findViewById(R.id.action_switch)
        mySwitch.isChecked = isToggled
        mySwitch.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked){
                presenter.showFavorites()
                isToggled = true
            }
            else {
                presenter.hideFavorites()
                isToggled = false
            }
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun getContext(): Context {
        return super.getContext()!!
    }


    override fun onGetSuccessResult(items: List<Item>?) {
        if (items != null) {
            view?.status_image?.setImageResource(0)
            listAdapter.setData(items)
        }
        listAdapter.notifyDataSetChanged()
    }

    override fun connectionError() {
        view?.status_image?.setImageResource(R.drawable.connection_error)
    }

    override fun getLayout(): Int {
        return R.layout.fragment_overview
    }

    override fun getToggleStatus(): Boolean {
        if (isToggled) return true
        return false
    }

    override fun onResume() {
        super.onResume()
        presenter.loadData(isToggled)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.clear()
    }

}