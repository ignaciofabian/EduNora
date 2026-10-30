package cl.duoc.tutorvirtual.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import cl.duoc.tutorvirtual.data.model.Perfil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.perfilStore by preferencesDataStore(
    name = "perfil_estudiante"
)

class PerfilRepository(
    private val context: Context
) {
    companion object {
        private val NOMBRE = stringPreferencesKey("nombre")
        private val RUT = stringPreferencesKey("rut")
        private val CORREO = stringPreferencesKey("correo")
        private val NIVEL = stringPreferencesKey("nivel")
        private val PROFESOR = stringPreferencesKey("profesor")
    }

    val perfil: Flow<Perfil> = context.perfilStore.data.map { p ->
        Perfil(
            nombre = p[NOMBRE] ?: "",
            rut = p[RUT] ?: "",
            correo = p[CORREO] ?: "",
            nivel = p[NIVEL] ?: "",
            profesorGuia = p[PROFESOR] ?: ""
        )
    }

    suspend fun guardar(perfil: Perfil) {
        context.perfilStore.edit { p ->
            p[NOMBRE] = perfil.nombre
            p[RUT] = perfil.rut
            p[CORREO] = perfil.correo
            p[NIVEL] = perfil.nivel
            p[PROFESOR] = perfil.profesorGuia
        }
    }
}