package com.mathieu.cauchy.tp_final_android.city

import android.os.Bundle
import android.view.*
import android.widget.Adapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mathieu.cauchy.tp_final_android.App
import com.mathieu.cauchy.tp_final_android.Database
import com.mathieu.cauchy.tp_final_android.R
import com.mathieu.cauchy.tp_final_android.utils.toast

class CityFragment : Fragment(), CityAdapter.CityItemListener {
    interface CityFragmentListener {
        fun onCitySelected(city : City)
    }
    var listener : CityFragmentListener? = null
    private lateinit var database: Database
    private lateinit var cities : MutableList<City>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CityAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = App.database
        cities = mutableListOf()
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_city, container, false)
        recyclerView = view.findViewById(R.id.cities_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cities = database.getAllCities()
        adapter = CityAdapter(cities, this)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_city, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_create_city -> {
                showCreateCityDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCitySelected(city: City) {
        listener?.onCitySelected(city)
    }

    override fun onCityDeleted(city: City) {
        showDeleteCityDialog(city)
    }

    private fun showDeleteCityDialog(city: City) {
        val deleteCityFragment = DeleteCityDialogFragment.newInstance(city.name)
        deleteCityFragment.listener = object : DeleteCityDialogFragment.DeleteCityDialogListener{
            override fun onDialogPositiveClick() {
                deleteCity(city)
            }

            override fun onDialogNegativeClick() {
            }

        }
        fragmentManager?.let { deleteCityFragment.show(it, "DeleteCityFragment") }
    }

    private fun deleteCity(city: City) {
        if(database.deleteCity(city)){
            cities.remove(city)
            adapter.notifyDataSetChanged()
            context?.toast(getString(R.string.city_message_info_city_deleted, city.name))
        }else {
            context?.toast(getString(R.string.city_message_error_could_not_delete, city.name))

        }
    }

    private fun showCreateCityDialog() {
        val createCityFragment = CreateCityDialogFragement()
        createCityFragment.listener = object : CreateCityDialogFragement.CreateCityDialogListener {
            override fun onDialogPositiveClick(cityName: String) {
                saveCity(City(cityName))
            }

            override fun onDialogNegativeClick() {}
        }
        fragmentManager?.let { createCityFragment.show(it, "CreateCityDialogFragment") }
    }

    private fun saveCity(city: City) {
        if (database.createCity(city)){
            cities.add(city)
            adapter.notifyDataSetChanged()
        } else{
            context?.toast(getString(R.string.city_message_error_could_not_create))
        }
    }

}