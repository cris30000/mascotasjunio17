package com.example.rescatando_mascotas_forever.presentation.adopciones

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rescatando_mascotas_forever.data.network.api.RetrofitClient
import com.example.rescatando_mascotas_forever.data.network.models.SolicitudAdopcionRequest
import kotlinx.coroutines.launch

class FormularioAdopcionViewModel : ViewModel() {

    var currentPage by mutableIntStateOf(1)
    val totalPages = 3

    // ID de la mascota a adoptar
    var mascotaId by mutableIntStateOf(0)

    // Paso 1: Información Personal
    var nombreCompleto by mutableStateOf("")
    var edad by mutableStateOf("")
    var direccion by mutableStateOf("")
    var telefono by mutableStateOf("")

    // Paso 2: Entorno y Motivo
    var tieneOtrasMascotas by mutableStateOf(false)
    var motivo by mutableStateOf("")
    var tiempoDisponible by mutableStateOf("")

    // Paso 3: Compromiso
    var aceptaTerminos by mutableStateOf(false)

    var isSaving by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var saveSuccess by mutableStateOf(false)

    fun isStepValid(step: Int): Boolean {
        return when (step) {
            1 -> nombreCompleto.isNotBlank() && edad.isNotBlank() && direccion.isNotBlank() && telefono.isNotBlank()
            2 -> motivo.isNotBlank() && tiempoDisponible.isNotBlank()
            3 -> aceptaTerminos
            else -> true
        }
    }

    fun nextStep() {
        if (currentPage < totalPages && isStepValid(currentPage)) {
            currentPage++
        }
    }

    fun previousStep() {
        if (currentPage > 1) {
            currentPage--
        }
    }

    fun enviarSolicitud(onSuccess: () -> Unit) {
        if (!isStepValid(3)) return

        viewModelScope.launch {
            isSaving = true
            errorMessage = null
            try {
                val request = SolicitudAdopcionRequest(
                    mascotaId = mascotaId,
                    nombreCompleto = nombreCompleto,
                    edad = edad.toIntOrNull() ?: 0,
                    direccion = direccion,
                    telefono = telefono,
                    tieneOtrasMascotas = tieneOtrasMascotas,
                    motivo = motivo,
                    tiempoDisponible = tiempoDisponible
                )

                val response = RetrofitClient.adopcionApi.enviarSolicitud(request)
                
                if (response.success) {
                    saveSuccess = true
                    onSuccess()
                } else {
                    errorMessage = response.message ?: "Error al enviar la solicitud"
                }
            } catch (e: Exception) {
                errorMessage = "Error de conexión: ${e.localizedMessage}"
            } finally {
                isSaving = false
            }
        }
    }
}
