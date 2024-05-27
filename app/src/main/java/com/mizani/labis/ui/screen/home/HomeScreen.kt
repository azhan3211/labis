package com.mizani.labis.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mizani.labis.domain.model.dto.OrderDto
import com.mizani.labis.domain.model.dto.OrdersDto
import com.mizani.labis.ui.screen.home.navigation.HomeNavigation
import com.mizani.labis.ui.screen.home.navigation.HomeRoute
import java.util.Date

@Composable
fun HomeScreen(
    storeChanged: Boolean = false,
    resetStoreChanged: (() -> Unit) = {},
    navigateToStoreActivity: (() -> Unit)? = null,
    navigateToOrderActivity: ((List<OrdersDto>) -> Unit)? = null,
    navigateToReportActivity: (Date, Date, String) -> Unit = { _, _, _ -> }
) {

    val label = arrayListOf("Home", "Laporan", "Akun")
    val icons =
        arrayListOf(Icons.Outlined.Fastfood, Icons.Outlined.Book, Icons.Outlined.AccountCircle)
    var selectedIndex by rememberSaveable { mutableStateOf(0) }

    val routes = arrayListOf(
        HomeRoute.MenuScreen.route,
        HomeRoute.ReportScreen.route,
        HomeRoute.ProfileScreen.route
    )

    val navController = rememberNavController()

    Scaffold(
        content = {
            Box(
                Modifier
                    .padding(bottom = 58.dp)
                    .fillMaxSize()
                    .padding(it.calculateTopPadding())
            ) {
                HomeNavigation(
                    storeChanged = storeChanged,
                    navController = navController,
                    resetStoreChanged = resetStoreChanged,
                    navigateToStoreActivity = navigateToStoreActivity,
                    navigateToOrderActivity = { orders ->
                        navigateToOrderActivity?.invoke(orders)
                    },
                    navigateToReportActivity = { startDate, endDate, category ->
                        navigateToReportActivity.invoke(startDate, endDate, category)
                    }
                )
            }
        },
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.White
            ) {
                repeat(label.size) { index ->
                    BottomNavigationItem(
                        icon = {
                            Icon(icons[index], contentDescription = "")
                        },
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                            val routeName = routes[index]
                            navigateTo(
                                navController,
                                routeName
                            )
                        },
                        label = { Text(text = label[index]) },
                    )
                }
            }
        }
    )
}

private fun navigateTo(
    navController: NavHostController,
    routeName: String
) {
    navController.navigate(routeName) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

@Preview
@Composable
private fun Preview() {
    HomeScreen()
}