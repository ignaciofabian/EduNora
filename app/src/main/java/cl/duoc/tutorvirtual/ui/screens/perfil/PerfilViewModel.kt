package cl.duoc.tutorvirtual.ui.screens.perfil

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import cl.duoc.tutorvirtual.data.model.Perfil
import cl.duoc.tutorvirtual.data.preferences.PerfilRepository
import cl.duoc.tutorvirtual.ui.components.CampoForm
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PerfilUiState(
    val nombre: CampoForm = CampoForm(),
    val rut: CampoForm = CampoForm(),
    val correo: CampoForm = CampoForm(),
    val nivel: CampoForm = CampoForm(),
    val profesorGuia: CampoForm = CampoForm(),
    val guardando: Boolean = false,
    val intentosFallidos: Int = 0          // lo usa la animación de la tarjeta 15
) {
    val esValido: Boolean
        get() = listOf(nombre, rut, correo, nivel, profesorGuia).all { it.esValido }
}

class PerfilViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = PerfilRepository(app)

    private val _ui = MutableStateFlow(PerfilUiState())
    val ui: StateFlow<PerfilUiState> = _ui.asStateFlow()

    private val _mensajes = MutableSharedFlow<String>()
    val mensajes = _mensajes.asSharedFlow()

    init {
        viewModelScope.launch {
            val p = repo.perfil.first()       // carga lo guardado una sola vez
            _ui.value = PerfilUiState(
                nombre = CampoForm(p.nombre), rut = CampoForm(p.rut), correo = CampoForm(p.correo),
                nivel = CampoForm(p.nivel), profesorGuia = CampoForm(p.profesorGuia)
            )
        }
    }

    fun onNombre(v: String) = _ui.update { it.copy(nombre = CampoForm(v, Validadores.nombre(v))) }
    fun onRut(v: String) = _ui.update { it.copy(rut = CampoForm(v, Validadores.rut(v))) }
    fun onCorreo(v: String) = _ui.update { it.copy(correo = CampoForm(v, Validadores.correo(v))) }
    fun onNivel(v: String) = _ui.update { it.copy(nivel = CampoForm(v, Validadores.seleccion(v, "un nivel"))) }
    fun onProfesor(v: String) =
        _ui.update { it.copy(profesorGuia = CampoForm(v, Validadores.nombre(v))) }

    fun guardar(trim: Any.() -> String) {
        val s = _ui.value
        // Se valida todo de nuevo por si hay campos que el usuario nunca tocó
        val revisado = s.copy(
            nombre = s.nombre.copy(error = Validadores.nombre(s.nombre.valor)),
            rut = s.rut.copy(error = Validadores.rut(s.rut.valor)),
            correo = s.correo.copy(error = Validadores.correo(s.correo.valor)),
            nivel = s.nivel.copy(error = Validadores.seleccion(s.nivel.valor, "un nivel")),
            profesorGuia = s.profesorGuia.copy(error = Validadores.nombre(s.profesorGuia.valor))
        )
        if (!revisado.esValido) {
            _ui.value = revisado.copy(intentosFallidos = s.intentosFallidos + 1)
            viewModelScope.launch { _mensajes.emit("Revisa los campos marcados en rojo") }
            return
        }
        viewModelScope.launch {
            _ui.value = revisado.copy(guardando = true)
            repo.guardar(
                Perfil(
                    revisado.nombre.valor.trim(),
                    revisado.rut.valor.trim(),
                    revisado.correo.valor.trim(),
                    revisado.nivel.valor,
                    revisado.profesorGuia.valor.trim()
                )
            )
            _ui.update { it.copy(guardando = false) }
            _mensajes.emit("Perfil guardado")
        }
    }
}