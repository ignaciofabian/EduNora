package cl.duoc.tutorvirtual.data.repository

import cl.duoc.tutorvirtual.data.local.AvanceDao
import cl.duoc.tutorvirtual.data.local.AvanceEntity
import kotlinx.coroutines.flow.Flow

class AvanceRepository(
    private val dao: AvanceDao
) {
    val avances: Flow<List<AvanceEntity>> =
        dao.observarTodos()

    suspend fun obtener(id: Long): AvanceEntity? =
        dao.buscarPorId(id)

    suspend fun guardar(avance: AvanceEntity): Long =
        dao.guardar(avance)

    suspend fun eliminar(avance: AvanceEntity) =
        dao.eliminar(avance)
}