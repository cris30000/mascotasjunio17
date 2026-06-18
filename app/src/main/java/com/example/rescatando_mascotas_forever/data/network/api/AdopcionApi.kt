package com.example.rescatando_mascotas_forever.data.network.api

import com.example.rescatando_mascotas_forever.data.network.models.ApiResponse
import com.example.rescatando_mascotas_forever.data.network.models.Mascota
import com.example.rescatando_mascotas_forever.data.network.models.PaginatedData
import com.example.rescatando_mascotas_forever.data.network.models.SolicitudAdopcionRequest
import retrofit2.http.*

interface AdopcionApi {

    @GET("api/v1/public/adopciones")
    suspend fun getMascotasDisponibles(): ApiResponse<PaginatedData<Mascota>>

    @GET("api/v1/public/adopciones/{id}")
    suspend fun getDetalleMascota(@Path("id") id: Int): ApiResponse<Mascota>

    @GET("api/v1/public/adopciones/{id}/verificar")
    suspend fun verificarDisponibilidad(@Path("id") id: Int): ApiResponse<Map<String, Any>>

    @POST("api/v1/public/solicitudes")
    suspend fun enviarSolicitud(@Body solicitud: SolicitudAdopcionRequest): ApiResponse<Any>

    // Ruta para el Administrador
    @GET("api/v1/admin/solicitudes")
    suspend fun getTodasLasSolicitudes(): ApiResponse<List<SolicitudAdopcionRequest>>
}
