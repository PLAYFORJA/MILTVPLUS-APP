package com.miltvplus.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Background = Color(0xFF090610)
private val Panel = Color(0xFF191126)
private val Violet = Color(0xFF9B5CFF)
private val SoftText = Color(0xFFC9BDD8)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Background) {
                    HomeScreen()
                }
            }
        }
    }
}

@Composable
private fun HomeScreen() {
    val sections = listOf("Inicio", "TV en vivo", "Películas", "Series", "Deportes", "Mi lista")
    var selected by remember { mutableStateOf("Inicio") }

    Column(
        modifier = Modifier.fillMaxSize().background(Background).padding(horizontal = 42.dp, vertical = 28.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("MILTV", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Black)
            Text("PLUS", color = Violet, fontSize = 30.sp, fontWeight = FontWeight.Black)
            Spacer(Modifier.width(34.dp))
            sections.forEach { section ->
                NavItem(section, selected == section) { selected = section }
            }
        }

        Spacer(Modifier.height(34.dp))
        Box(
            modifier = Modifier.fillMaxWidth().height(230.dp)
                .background(Panel, RoundedCornerShape(22.dp))
                .border(1.dp, Color(0xFF352344), RoundedCornerShape(22.dp))
                .padding(30.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Column {
                Text("TU ENTRETENIMIENTO, TU MUNDO", color = Violet, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(12.dp))
                Text("Bienvenido a MILTVPLUS", color = Color.White, fontSize = 34.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text("Películas, series y televisión en un solo lugar.", color = SoftText, fontSize = 17.sp)
                Spacer(Modifier.height(16.dp))
                Text("PRÓXIMAMENTE · CATÁLOGO EN PREPARACIÓN", color = SoftText, fontSize = 11.sp)
            }
        }

        Spacer(Modifier.height(28.dp))
        Text(if (selected == "Inicio") "Explorá MILTVPLUS" else selected, color = Color.White, fontSize = 23.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(14.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(listOf("TV en vivo", "Películas", "Series", "Deportes", "Mi lista")) { item ->
                ContentTile(item)
            }
        }
        Spacer(Modifier.height(22.dp))
        Text("Estamos preparando tu catálogo. Próximamente vas a poder disfrutar contenido propio y autorizado.",
            color = SoftText, fontSize = 13.sp)
    }
}

@Composable
private fun NavItem(label: String, active: Boolean, onClick: () -> Unit) {
    var focused by remember { mutableStateOf(false) }
    Text(
        text = label,
        color = if (active || focused) Color.White else SoftText,
        fontSize = 14.sp,
        fontWeight = if (active || focused) FontWeight.Bold else FontWeight.Normal,
        modifier = Modifier.padding(horizontal = 9.dp, vertical = 10.dp)
            .background(if (active || focused) Violet.copy(alpha = 0.28f) else Color.Transparent, RoundedCornerShape(8.dp))
            .onFocusChanged { focused = it.isFocused }
            .clickable { onClick() }
            .focusable()
            .padding(horizontal = 7.dp, vertical = 5.dp)
    )
}

@Composable
private fun ContentTile(title: String) {
    var focused by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.width(155.dp).height(112.dp)
            .background(Panel, RoundedCornerShape(14.dp))
            .border(if (focused) 2.dp else 1.dp, if (focused) Violet else Color(0xFF352344), RoundedCornerShape(14.dp))
            .onFocusChanged { focused = it.isFocused }
            .focusable()
            .padding(14.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(title, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Text("Próximamente", color = SoftText, fontSize = 11.sp)
    }
}
