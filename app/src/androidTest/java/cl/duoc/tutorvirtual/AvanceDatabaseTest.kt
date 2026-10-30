package cl.duoc.tutorvirtual

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import cl.duoc.tutorvirtual.data.local.AppDatabase
import cl.duoc.tutorvirtual.data.local.AvanceEntity
import cl.duoc.tutorvirtual.data.model.EstadoHito
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AvanceDatabaseTest {

    // PRUEBA 1: Guardar y recuperar un avance
    @Test
    fun guardarYRecuperarAvance() = runBlocking {

        val contexto =
            ApplicationProvider.getApplicationContext<Context>()

        val db = Room.inMemoryDatabaseBuilder(
            contexto,
            AppDatabase::class.java
        ).build()

        try {
            val dao = db.avanceDao()

            val avance = AvanceEntity(
                etapaId = 1,
                titulo = "Avance de prueba",
                descripcion = "Prueba Room",
                fechaEntrega = "2026-10-30",
                estado = EstadoHito.EN_DESARROLLO
            )

            val id = dao.guardar(avance)
            val recuperado = dao.buscarPorId(id)

            assertNotNull(recuperado)
            assertEquals("Avance de prueba", recuperado?.titulo)
            assertEquals(
                EstadoHito.EN_DESARROLLO,
                recuperado?.estado
            )

            val lista = dao.observarTodos().first()
            assertEquals(1, lista.size)

        } finally {
            db.close()
        }
    }

    // PRUEBA 2: Comprobar persistencia al reabrir SQLite
    @Test
    fun avancePersisteAlReabrirBaseDeDatos() = runBlocking {

        val contexto =
            InstrumentationRegistry.getInstrumentation().targetContext

        val nombreBD = "test-persistencia-room.db"

        contexto.deleteDatabase(nombreBD)

        try {
            val db1 = Room.databaseBuilder(
                contexto,
                AppDatabase::class.java,
                nombreBD
            ).build()

            val id = try {
                db1.avanceDao().guardar(
                    AvanceEntity(
                        etapaId = 1,
                        titulo = "Avance persistente",
                        descripcion = "Prueba de persistencia",
                        fechaEntrega = "2026-10-30"
                    )
                )
            } finally {
                db1.close()
            }

            val db2 = Room.databaseBuilder(
                contexto,
                AppDatabase::class.java,
                nombreBD
            ).build()

            try {
                val recuperado =
                    db2.avanceDao().buscarPorId(id)

                assertNotNull(recuperado)
                assertEquals(
                    "Avance persistente",
                    recuperado?.titulo
                )
            } finally {
                db2.close()
            }

        } finally {
            contexto.deleteDatabase(nombreBD)
        }
    }
}