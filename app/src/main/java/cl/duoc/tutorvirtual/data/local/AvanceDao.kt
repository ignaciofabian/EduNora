package cl.duoc.tutorvirtual.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AvanceDao {

    @Query("SELECT * FROM avances ORDER BY fechaEntrega")
    fun observarTodos(): Flow<List<AvanceEntity>>

    @Query("SELECT * FROM avances WHERE id = :id")
    suspend fun buscarPorId(id: Long): AvanceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardar(avance: AvanceEntity): Long

    @Delete
    suspend fun eliminar(avance: AvanceEntity)
}