package ru.dansh1nv.quizhub

import android.annotation.SuppressLint
import android.location.Location
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.launch
import ru.dansh1nv.core.location.Coordinates
import ru.dansh1nv.core.location.LocationListener
import ru.dansh1nv.core.presentation.viewModel.BaseViewModel
import ru.dansh1nv.core.presentation.viewModel.Router

internal interface MainActivityRouter : Router {
    fun openDeeplink(link: Uri)
}

internal class MainActivityViewModel(
    private val locationClient: FusedLocationProviderClient,
    private val locationListener: LocationListener,
) : BaseViewModel<MainActivityRouter>() {

    @SuppressLint("MissingPermission")
    fun getLocation() {
        locationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    viewModelScope.launch {
                        locationListener.updateLocation(
                            Coordinates(
                                latitude = latitude,
                                longitude = longitude
                            )
                        )
                    }
                }
            }
    }
}