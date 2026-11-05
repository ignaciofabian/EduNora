package cl.duoc.tutorvirtual

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cl.duoc.tutorvirtual.ui.navigation.TutorVirtualApp
import cl.duoc.tutorvirtual.ui.theme.TutorVirtualESUCARTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TutorVirtualESUCARTheme {
                TutorVirtualApp()
            }
        }
    }
}
