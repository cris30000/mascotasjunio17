package com.example.rescatando_mascotas_forever.data.network.models

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    val success: Boolean,
    val data: T,
    val message: String? = null
)

// Soporte para Laravel paginate()
data class PaginatedData<T>(
    @SerializedName("current_page") val currentPage: Int,
    @SerializedName("data") val data: List<T>, // Laravel usa 'data' para los items
    @SerializedName("last_page") val lastPage: Int?,
    @SerializedName("total") val total: Int?
)

data class SolicitudAdopcionRequest(
    @SerializedName("mascota_id") val mascotaId: Int,
    @SerializedName("nombre_completo") val nombreCompleto: String,
    val edad: Int,
    val direccion: String,
    val telefono: String,
    @SerializedName("tiene_otras_mascotas") val tieneOtrasMascotas: Boolean,
    val motivo: String,
    @SerializedName("tiempo_disponible") val tiempoDisponible: String,
    val dni: String? = null,
    val ocupacion: String? = null,
    @SerializedName("tipo_vivienda") val tipoVivienda: String? = null,
    @SerializedName("tiene_patio") val tienePatio: Boolean? = null,
    @SerializedName("integrantes_familia") val integrantes: Int? = null,
    @SerializedName("experiencia_previa") val experienciaPrevia: String? = null
)
