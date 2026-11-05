package cl.duoc.tutorvirtual

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import cl.duoc.tutorvirtual.data.model.Perfil
import cl.duoc.tutorvirtual.data.preferences.PerfilRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PerfilRepositoryTest {

    @Test
    fun guardarYRecuperarPerfil() = runBlocking {

        val contexto =
            InstrumentationRegistry.getInstrumentation().targetContext

        val repository = PerfilRepository(contexto)

        val perfil = Perfil(
            nombre = "Estudiante Prueba",
            rut = "11111111-1",
            correo = "test@ejemplo.cl",
            nivel = "TT I",
            profesorGuia = "Profesor Prueba"
        )

        repository.guardar(perfil)

        val recuperado = repository.perfil.first()

        assertEquals(perfil, recuperado)
    }
}

@Test
fun perfilPersisteEnNuevaInstancia() = runBlocking {
    val contexto =
        InstrumentationRegistry.getInstrumentation().targetContext

    val primerRepository = PerfilRepository(contexto)

    val perfil = Perfil(
        nombre = "Persistencia Prueba",
        rut = "11111111-1",
        correo = "persistencia@ejemplo.cl",
        nivel = "TT II",
        profesorGuia = "Profesor Prueba"
    )

    primerRepository.guardar(perfil)

    val segundoRepository = PerfilRepository(contexto)

    val recuperado = segundoRepository.perfil.first()

    assertEquals(perfil, recuperado)
}