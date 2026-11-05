package cl.duoc.tutorvirtual.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cl.duoc.tutorvirtual.ui.screens.guia.GuiaMetodologicaScreen
import cl.duoc.tutorvirtual.ui.screens.navegacion.InicioScreen
import cl.duoc.tutorvirtual.ui.screens.navegacion.SeccionProvisionalScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorVirtualApp() {
    val nav = rememberNavController()
    val entrada by nav.currentBackStackEntryAsState()
    val rutaActual = entrada?.destination?.route
    val esPrincipal = Destino.entries.any { it.ruta == rutaActual }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Tutor Virtual ESUCAR") },
                navigationIcon = {
                    if (!esPrincipal) {
                        IconButton(onClick = { nav.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                        }
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                Destino.entries.forEach { destino ->
                    NavigationBarItem(
                        selected = rutaActual == destino.ruta,
                        onClick = { nav.irA(destino) },
                        icon = { Icon(destino.icono, contentDescription = null) },
                        label = { Text(destino.titulo) }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = nav,
            startDestination = Destino.INICIO.ruta,
            modifier = Modifier.padding(padding),
            enterTransition = {
                fadeIn(tween(250)) + slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(250))
            },
            exitTransition = { fadeOut(tween(200)) },
            popEnterTransition = {
                fadeIn(tween(250)) + slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(250))
            },
            popExitTransition = { fadeOut(tween(200)) }
        ) {
            composable(Destino.INICIO.ruta) { InicioScreen(onIr = { destino -> nav.irA(destino) }) }
            composable(Destino.GUIA.ruta) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(20.dp)
                ) {
                    GuiaMetodologicaScreen()
                }
            }
            composable(Destino.FAQ.ruta) { SeccionProvisionalScreen(Destino.FAQ) }
            composable(Destino.AVANCES.ruta) { SeccionProvisionalScreen(Destino.AVANCES) }
            composable(Destino.PERFIL.ruta) { SeccionProvisionalScreen(Destino.PERFIL) }
        }
    }
}

/** Navega a una sección principal conservando el estado de cada pestaña. */
private fun NavController.irA(destino: Destino) {
    navigate(destino.ruta) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
