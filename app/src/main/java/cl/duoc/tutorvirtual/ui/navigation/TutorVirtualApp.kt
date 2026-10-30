package cl.duoc.tutorvirtual.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cl.duoc.tutorvirtual.ui.screens.navegacion.InicioScreen

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
                        onClick = {
                            nav.navigate(destino.ruta) {
                                popUpTo(nav.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
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
            composable(Destino.INICIO.ruta) { InicioScreen(onIr = { ruta -> nav.navigate(ruta) }) }
            composable(Destino.GUIA.ruta) { GuiaScreen(onEtapa = { id -> nav.navigate(Rutas.etapa(id)) }) }
            composable(
                Rutas.ETAPA,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { e -> EtapaDetalleScreen(etapaId = e.arguments?.getInt("id") ?: 1) }
            composable(Destino.FAQ.ruta) { FaqScreen() }
            composable(Destino.AVANCES.ruta) {
                AvancesScreen(
                    onNuevo = { nav.navigate(Rutas.formAvance()) },
                    onEditar = { id -> nav.navigate(Rutas.formAvance(id)) }
                )
            }
            composable(
                Rutas.FORM_AVANCE,
                arguments = listOf(navArgument("id") { type = NavType.LongType; defaultValue = -1L })
            ) { AvanceFormScreen(onListo = { nav.popBackStack() }) }
            composable(Destino.PERFIL.ruta) { PerfilScreen() }
        }
    }
}

