package com.example.rescatando_mascotas_forever.data.repository

import com.example.rescatando_mascotas_forever.data.network.api.AdopcionApi
import com.example.rescatando_mascotas_forever.data.network.models.ApiResponse
import com.example.rescatando_mascotas_forever.data.network.models.Mascota
import com.example.rescatando_mascotas_forever.data.network.models.PaginatedData
import com.example.rescatando_mascotas_forever.data.network.models.SolicitudAdopcionRequest

class AdopcionRepository(private val api: AdopcionApi) {

    suspend fun getMascotasDisponibles(): ApiResponse<PaginatedData<Mascota>> {
        return api.getMascotasDisponibles()
    }

    suspend fun enviarSolicitud(solicitud: SolicitudAdopcionRequest): ApiResponse<Any> {
        return api.enviarSolicitud(solicitud)
    }

    suspend fun getTodasLasSolicitudes(): ApiResponse<List<SolicitudAdopcionRequest>> {
        return api.getTodasLasSolicitudes()
    }
}
