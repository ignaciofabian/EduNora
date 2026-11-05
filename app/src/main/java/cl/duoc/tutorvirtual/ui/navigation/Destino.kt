package cl.duoc.tutorvirtual.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.ui.graphics.vector.ImageVector

enum class Destino(val ruta: String, val titulo: String, val icono: ImageVector) {
    INICIO("inicio", "Inicio", Icons.Filled.Home),
    GUIA("guia", "Guía", Icons.Filled.School),
    FAQ("faq", "FAQ", Icons.Filled.Quiz),
    AVANCES("avances", "Avances", Icons.Filled.TaskAlt),
    PERFIL("perfil", "Perfil", Icons.Filled.Person)
}

object Rutas {
    const val ETAPA = "etapa/{id}"
    fun etapa(id: Int) = "etapa/$id"

    const val FORM_AVANCE = "avance/form?id={id}"
    fun formAvance(id: Long? = null) = if (id == null) "avance/form" else "avance/form?id=$id"
}