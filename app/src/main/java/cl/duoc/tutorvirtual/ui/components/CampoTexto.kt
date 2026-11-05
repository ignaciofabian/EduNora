package cl.duoc.tutorvirtual.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun CampoTexto(
    etiqueta: String,
    campo: CampoForm,
    onCambio: (String) -> Unit,
    icono: ImageVector,
    modifier: Modifier = Modifier,
    teclado: KeyboardType = KeyboardType.Text,
    lineas: Int = 1
) {
    OutlinedTextField(
        value = campo.valor,
        onValueChange = onCambio,
        label = { Text(etiqueta) },
        leadingIcon = { Icon(icono, contentDescription = null) },
        trailingIcon = {
            when {
                campo.error != null -> Icon(
                    Icons.Filled.Error, contentDescription = "Error",
                    tint = MaterialTheme.colorScheme.error
                )
                campo.esValido -> Icon(
                    Icons.Filled.CheckCircle, contentDescription = "Campo válido",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        isError = campo.error != null,
        supportingText = { campo.error?.let { Text(it) } },
        singleLine = lineas == 1,
        minLines = lineas,
        keyboardOptions = KeyboardOptions(keyboardType = teclado, imeAction = ImeAction.Next),
        modifier = modifier.fillMaxWidth()
    )
}

annotation class CampoForm {
    val error: Any
    val valor: Any
    val esValido: Boolean
}
