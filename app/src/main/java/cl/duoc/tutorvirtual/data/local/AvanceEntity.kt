package cl.duoc.tutorvirtual.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import cl.duoc.tutorvirtual.data.model.EstadoHito

@Entity(tableName = "avances")
data class AvanceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val etapaId: Int,
    val titulo: String,
    val descripcion: String,
    val fechaEntrega: String,
    val estado: EstadoHito = EstadoHito.POR_INICIAR,
    val fotoUri: String? = null,
    val recordatorioActivo: Boolean = false
)