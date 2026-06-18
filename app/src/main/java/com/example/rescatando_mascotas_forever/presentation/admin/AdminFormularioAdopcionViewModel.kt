package com.example.rescatando_mascotas_forever.presentation.admin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rescatando_mascotas_forever.data.network.models.SolicitudAdopcionRequest
import com.example.rescatando_mascotas_forever.domain.usecases.adopcion.SolicitarAdopcionUseCase
import kotlinx.coroutines.launch

class AdminFormularioAdopcionViewModel(
    private val solicitarAdopcionUseCase: SolicitarAdopcionUseCase
) : ViewModel() {

    var currentPage by mutableIntStateOf(1)
    val totalPages = 5

    // ID de la mascota (debe asignarse al abrir el formulario)
    var mascotaId by mutableIntStateOf(0)

    // Step 1
    var nombre by mutableStateOf("")
    var dni by mutableStateOf("")
    var edad by mutableStateOf("")

    // Step 2
    var ocupacion by mutableStateOf("")
    var telefono by mutableStateOf("")
    var direccion by mutableStateOf("") // Añadido campo dirección

    // Step 3
    var tipoVivienda by mutableStateOf("Casa")
    var tienePatio by mutableStateOf(false)
    var tieneProtecciones by mutableStateOf(false)

    // Step 4
    var integrantes by mutableStateOf("")
    var hayNinos by mutableStateOf(false)
    var estanDeAcuerdo by mutableStateOf(true)

    // Step 5
    var tieneOtrasMascotas by mutableStateOf(false)
    var experienciaPrevia by mutableStateOf("")
    var tiempoDiario by mutableStateOf("")
    var motivo by mutableStateOf("Adopción desde panel administrador")
    var presupuestoVeterinario by mutableStateOf(true)

    var isSaving by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var saveSuccess by mutableStateOf(false)

    fun isStepValid(step: Int): Boolean {
        return when (step) {
            1 -> nombre.isNotBlank() && dni.isNotBlank() && edad.isNotBlank()
            2 -> ocupacion.isNotBlank() && telefono.isNotBlank()
            3 -> true
            4 -> integrantes.isNotBlank()
            5 -> tiempoDiario.isNotBlank()
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

    fun guardarSolicitud(onSuccess: () -> Unit) {
        if (!isStepValid(5)) return

        viewModelScope.launch {
            isSaving = true
            errorMessage = null
            try {
                val request = SolicitudAdopcionRequest(
                    mascotaId = mascotaId,
                    nombreCompleto = nombre,
                    dni = dni,
                    edad = edad.toIntOrNull() ?: 0,
                    ocupacion = ocupacion,
                    direccion = direccion.ifBlank { "Dirección Admin" },
                    telefono = telefono,
                    tipoVivienda = tipoVivienda,
                    tienePatio = tienePatio,
                    integrantes = integrantes.toIntOrNull() ?: 1,
                    tieneOtrasMascotas = tieneOtrasMascotas,
                    motivo = motivo,
                    tiempoDisponible = tiempoDiario,
                    experienciaPrevia = experienciaPrevia
                )

                val result = solicitarAdopcionUseCase(request)
                
                if (result.success) {
                    saveSuccess = true
                    onSuccess()
                } else {
                    errorMessage = result.message ?: "Error al guardar la solicitud"
                }
            } catch (e: Exception) {
                errorMessage = "Error: ${e.localizedMessage}"
            } finally {
                isSaving = false
            }
        }
    }
}
