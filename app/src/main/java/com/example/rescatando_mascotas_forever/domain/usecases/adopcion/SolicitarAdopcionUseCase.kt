package com.example.rescatando_mascotas_forever.domain.usecases.adopcion

import com.example.rescatando_mascotas_forever.data.network.models.ApiResponse
import com.example.rescatando_mascotas_forever.data.network.models.SolicitudAdopcionRequest
import com.example.rescatando_mascotas_forever.data.repository.AdopcionRepository

class SolicitarAdopcionUseCase(private val repository: AdopcionRepository) {
    suspend operator fun invoke(solicitud: SolicitudAdopcionRequest): ApiResponse<Any> {
        return repository.enviarSolicitud(solicitud)
    }
}
