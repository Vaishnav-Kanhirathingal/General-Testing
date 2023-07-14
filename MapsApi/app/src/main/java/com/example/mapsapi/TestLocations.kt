package com.example.mapsapi

import com.google.android.gms.maps.model.LatLng

object TestLocations {
    val data = listOf(
        Stop(name = "vasai", dest = LatLng(19.385924598906364, 72.83088294932057)),
        Stop(name = "naigaon", dest = LatLng(19.351764971346253, 72.84636202720255)),
        Stop(name = "bhayandar", dest = LatLng(19.311534035872448, 72.85259789653145)),
        Stop(name = "miraRoad", dest = LatLng(19.280212832020876, 72.85617711091665)),
        Stop(name = "dahisar", dest = LatLng(19.25028665668593, 72.85922629071318)),
        Stop(name = "borivali", dest = LatLng(19.229312060637938, 72.85741224394799)),
        Stop(name = "kandivali", dest = LatLng(19.204239100222324, 72.85175593746052)),
        Stop(name = "malad", dest = LatLng(19.187122219774075, 72.84890137650258)),
        Stop(name = "goregaon", dest = LatLng(19.16469712510094, 72.84935096685422)),
        Stop(name = "ramMandir", dest = LatLng(19.151470020173996, 72.85072519357833)),
        Stop(name = "jogeshwari", dest = LatLng(19.13624173443887, 72.84894421962396)),
        Stop(name = "andheri", dest = LatLng(19.120762368573583, 72.84560013885628)),
    )
}

data class Stop(
    val name: String,
    val dest: LatLng
)