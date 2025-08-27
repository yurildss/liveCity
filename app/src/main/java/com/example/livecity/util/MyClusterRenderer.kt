package com.example.livecity.util

import android.content.Context
import com.example.livecity.screens.feed.MyClusterItem
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class MyClusterRenderer(
    context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<MyClusterItem>
) : DefaultClusterRenderer<MyClusterItem>(context, map, clusterManager) {

    override fun onBeforeClusterItemRendered(item: MyClusterItem, markerOptions: MarkerOptions) {
        markerOptions.icon(
            BitmapDescriptorFactory.fromResource(item.iconResId)
        )
        super.onBeforeClusterItemRendered(item, markerOptions)
    }


}