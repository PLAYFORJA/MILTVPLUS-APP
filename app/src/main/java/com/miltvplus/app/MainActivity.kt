package com.miltvplus.app

import android.os.Bundle
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Background = Color(0xFF08070D)
private val Panel = Color(0xFF121019)
private val PanelRaised = Color(0xFF262033)
private val Violet = Color(0xFF8B5CF6)
private val Orchid = Color(0xFFB58CFF)
private val SoftText = Color(0xFFB7B0C4)
private val Border = Color(0xFF393344)

private val navigation = listOf("Inicio", "TV en vivo", "Modo Cine", "Deportes", "Mi lista", "Buscar", "Mi cuenta", "Configuración")
private val demoMovies = listOf(
    "Ecos de Medianoche", "El Último Horizonte", "Código Aurora",
    "La Ciudad Sumergida", "Horizonte Cero", "Crónicas del Sur"
)
private val sportsCategories = listOf("Fútbol", "Básquet", "Tenis", "Automovilismo", "Rugby", "Otros")
private val liveCategories = listOf("Todos", "Noticias", "Públicos", "Infantiles", "Entretenimiento", "Cultura")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Background) {
                    MiltvPlusApp()
                }
            }
        }
    }
}

@Composable
private fun BrandMark() {
    Box(
        modifier = Modifier
            .width(52.dp)
            .height(52.dp)
            .background(
                Brush.linearGradient(listOf(Color(0xFF6D3BEA), Color(0xFFB58CFF))),
                RoundedCornerShape(15.dp)
            )
            .border(1.dp, Orchid.copy(alpha = 0.8f), RoundedCornerShape(15.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text("M", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Black, letterSpacing = (-2).sp)
    }
}

@Composable
private fun MiltvPlusApp() {
    val context = LocalContext.current
    val preferences = remember(context) {
        context.getSharedPreferences("miltvplus_preferences", Context.MODE_PRIVATE)
    }
    var selected by remember { mutableStateOf("Inicio") }
    var selectedTitle by remember { mutableStateOf<String?>(null) }
    val watchlist = remember {
        mutableStateListOf<String>().apply {
            addAll(preferences.getStringSet("watchlist", emptySet()) ?: emptySet())
        }
    }
    val liked = remember {
        mutableStateListOf<String>().apply {
            addAll(preferences.getStringSet("liked_titles", emptySet()) ?: emptySet())
        }
    }

    Row(modifier = Modifier.fillMaxSize().background(Background).padding(22.dp)) {
        Column(modifier = Modifier.width(190.dp).fillMaxSize()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                BrandMark()
                Spacer(Modifier.width(11.dp))
                Column {
                    Text("MILTVPLUS", color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Black, letterSpacing = 1.1.sp)
                    Text("ENTERTAINMENT", color = Orchid, fontSize = 8.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                }
            }
            Spacer(Modifier.height(7.dp))
            Text("Tu pantalla. Tu mundo.", color = SoftText, fontSize = 10.sp)
            Spacer(Modifier.height(28.dp))
            navigation.forEach { item ->
                NavigationItem(item, selected == item) {
                    selected = item
                    selectedTitle = null
                }
            }
        }

        Column(
            modifier = Modifier.weight(1f).fillMaxSize().padding(start = 22.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
                Text("MILTVPLUS  ·  ARGENTINA", color = SoftText, fontSize = 12.sp)
                Spacer(Modifier.width(18.dp))
                Text("●  ", color = Violet, fontSize = 12.sp)
                Text("Cuenta MILTVPLUS", color = Color.White, fontSize = 12.sp)
            }
            Spacer(Modifier.height(18.dp))
            val openedTitle = selectedTitle
            if (openedTitle != null) {
                DetailScreen(
                    title = openedTitle,
                    liked = liked.contains(openedTitle),
                    saved = watchlist.contains(openedTitle),
                    onLike = {
                        if (liked.contains(openedTitle)) liked.remove(openedTitle) else liked.add(openedTitle)
                        preferences.edit().putStringSet("liked_titles", liked.toSet()).apply()
                    },
                    onSave = {
                        if (watchlist.contains(openedTitle)) watchlist.remove(openedTitle) else watchlist.add(openedTitle)
                        preferences.edit().putStringSet("watchlist", watchlist.toSet()).apply()
                    },
                    onBack = { selectedTitle = null }
                )
            } else {
                when (selected) {
                    "Inicio" -> HomeScreen(onOpen = { selectedTitle = it }, onSection = { selected = it }, watchlistCount = watchlist.size)
                    "TV en vivo" -> LiveScreen()
                    "Modo Cine" -> CinemaScreen(onOpen = { selectedTitle = it })
                    "Deportes" -> SportsScreen()
                    "Mi lista" -> WatchlistScreen(watchlist, onOpen = { selectedTitle = it })
                    "Buscar" -> SearchScreen(onOpen = { selectedTitle = it })
                    "Mi cuenta" -> AccountScreen()
                    "Configuración" -> SettingsScreen(preferences)
                }
            }
        }
    }
}

@Composable
private fun NavigationItem(label: String, active: Boolean, onClick: () -> Unit) {
    var focused by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp)
            .background(if (active || focused) Violet.copy(alpha = 0.30f) else Color.Transparent, RoundedCornerShape(10.dp))
            .border(if (focused) 2.dp else 1.dp, if (active || focused) Violet else Color.Transparent, RoundedCornerShape(10.dp))
            .onFocusChanged { focused = it.isFocused }
            .clickable(onClick = onClick).focusable().padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(if (active) "◆" else "◇", color = Violet, fontSize = 15.sp)
        Spacer(Modifier.width(12.dp))
        Text(label, color = if (active || focused) Color.White else SoftText, fontSize = 14.sp, fontWeight = if (active) FontWeight.Bold else FontWeight.Normal)
    }
}

@Composable
private fun SectionTitle(title: String, subtitle: String? = null) {
    Text(title, color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
    if (subtitle != null) {
        Spacer(Modifier.height(5.dp))
        Text(subtitle, color = SoftText, fontSize = 13.sp)
    }
}

@Composable
private fun HomeScreen(onOpen: (String) -> Unit, onSection: (String) -> Unit, watchlistCount: Int) {
    Column {
        Box(
            modifier = Modifier.fillMaxWidth().height(230.dp)
                .background(PanelRaised, RoundedCornerShape(20.dp))
                .border(1.dp, Border, RoundedCornerShape(20.dp)).padding(28.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Column {
                Text("EXPERIENCIA MILTVPLUS", color = Violet, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(10.dp))
                Text("Grandes historias\nen un solo lugar.", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Black)
                Spacer(Modifier.height(8.dp))
                Text("Tu entretenimiento, tu mundo.", color = SoftText, fontSize = 15.sp)
                Spacer(Modifier.height(14.dp))
                ActionButton("Explorar Modo Cine") { onOpen(demoMovies.first()) }
            }
        }
        Spacer(Modifier.height(22.dp))
        SectionTitle("Explorá MILTVPLUS", "Elegí una sección para empezar.")
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            listOf("TV en vivo", "Modo Cine", "Deportes", "Mi lista").forEach { item -> Tile(item) { onSection(item) } }
        }
        Spacer(Modifier.height(18.dp))
        Text("Tu lista: $watchlistCount títulos guardados", color = SoftText, fontSize = 13.sp)
        Spacer(Modifier.height(12.dp))
        Text("Catálogo de demostración · sin transmisiones ni títulos licenciados conectados", color = SoftText, fontSize = 11.sp)
    }
}

@Composable
private fun Tile(title: String, onClick: () -> Unit) {
    var focused by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.width(145.dp).height(105.dp).background(Panel, RoundedCornerShape(14.dp))
            .border(if (focused) 2.dp else 1.dp, if (focused) Violet else Border, RoundedCornerShape(14.dp))
            .onFocusChanged { focused = it.isFocused }.clickable(onClick = onClick).focusable()
            .padding(14.dp), verticalArrangement = Arrangement.Bottom
    ) {
        Text(title, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
        Text("Abrir sección", color = SoftText, fontSize = 10.sp)
    }
}

@Composable
private fun LiveScreen() {
    Column {
        SectionTitle("TV en vivo", "Catálogo preparado para señales oficiales disponibles en Argentina.")
        Spacer(Modifier.height(16.dp))
        CategoryChips(liveCategories)
        Spacer(Modifier.height(18.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            listOf("Señales públicas", "Noticias", "Cultura", "Entretenimiento").forEach { label ->
                InfoCard(label, "Señales oficiales pendientes de validación")
            }
        }
        Spacer(Modifier.height(18.dp))
        Text("Importante: la señal debe permitir retransmisión dentro de MILTVPLUS.", color = SoftText, fontSize = 12.sp)
    }
}

@Composable
private fun CinemaScreen(onOpen: (String) -> Unit) {
    Column {
        SectionTitle("Modo Cine", "Películas y series organizadas en un único catálogo.")
        Spacer(Modifier.height(14.dp))
        CategoryChips(listOf("Películas", "Series", "Estrenos", "Más vistos", "Géneros"))
        Spacer(Modifier.height(18.dp))
        Text("Catálogo de muestra", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(10.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            demoMovies.take(4).forEach { title -> PosterCard(title) { onOpen(title) } }
        }
        Spacer(Modifier.height(12.dp))
        Text("Los títulos de muestra no representan disponibilidad comercial ni derechos de exhibición.", color = SoftText, fontSize = 11.sp)
    }
}

@Composable
private fun SportsScreen() {
    Column {
        SectionTitle("Deportes", "Canales deportivos agrupados por disciplina.")
        Spacer(Modifier.height(14.dp))
        CategoryChips(sportsCategories)
        Spacer(Modifier.height(18.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            listOf("Fútbol", "Básquet", "Tenis", "Motor").forEach { InfoCard(it, "Canales autorizados pendientes") }
        }
        Spacer(Modifier.height(14.dp))
        Text("No se habilitarán señales premium sin autorización de distribución.", color = SoftText, fontSize = 12.sp)
    }
}

@Composable
private fun WatchlistScreen(items: List<String>, onOpen: (String) -> Unit) {
    Column {
        SectionTitle("Mi lista", "Tus películas y series guardadas.")
        Spacer(Modifier.height(16.dp))
        if (items.isEmpty()) {
            Text("Todavía no guardaste títulos. Explorá Modo Cine y agregá tus favoritos.", color = SoftText, fontSize = 14.sp)
        } else {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items.forEach { PosterCard(it) { onOpen(it) } }
            }
        }
    }
}

@Composable
private fun SearchScreen(onOpen: (String) -> Unit) {
    var query by remember { mutableStateOf("") }
    Column {
        SectionTitle("Buscar", "Encontrá películas, series y señales del catálogo.")
        Spacer(Modifier.height(14.dp))
        OutlinedTextField(value = query, onValueChange = { query = it }, label = { Text("Buscar en MILTVPLUS") }, singleLine = true)
        Spacer(Modifier.height(14.dp))
        val results = demoMovies.filter { it.contains(query, ignoreCase = true) }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            results.take(4).forEach { PosterCard(it) { onOpen(it) } }
        }
        if (query.isNotBlank() && results.isEmpty()) Text("No hay coincidencias en el catálogo de muestra.", color = SoftText)
    }
}

@Composable
private fun AccountScreen() {
    Column {
        SectionTitle("Mi cuenta", "Tu cuenta propia de MILTVPLUS, sin inicio de sesión obligatorio con Google.")
        Spacer(Modifier.height(18.dp))
        InfoCard("Cuenta MILTVPLUS", "Registro, acceso y recuperación se conectarán a un servidor seguro.")
        Spacer(Modifier.height(12.dp))
        Text("La autenticación real todavía no está activada. No ingreses contraseñas reales en esta versión de prueba.", color = SoftText, fontSize = 12.sp)
    }
}

@Composable
private fun SettingsScreen(preferences: android.content.SharedPreferences) {
    var autoplay by remember { mutableStateOf(preferences.getBoolean("autoplay", false)) }
    var notifications by remember { mutableStateOf(preferences.getBoolean("notifications", true)) }
    var subtitles by remember { mutableStateOf(preferences.getBoolean("subtitles", true)) }
    Column {
        SectionTitle("Configuración", "Personalizá la experiencia de reproducción y la aplicación.")
        Spacer(Modifier.height(12.dp))
        SettingRow("Idioma", "Español")
        SettingRow("Calidad de video", "Automática")
        SettingToggle("Reproducción automática", autoplay) {
            autoplay = it
            preferences.edit().putBoolean("autoplay", it).apply()
        }
        SettingToggle("Notificaciones", notifications) {
            notifications = it
            preferences.edit().putBoolean("notifications", it).apply()
        }
        SettingToggle("Subtítulos cuando estén disponibles", subtitles) {
            subtitles = it
            preferences.edit().putBoolean("subtitles", it).apply()
        }
        Spacer(Modifier.height(10.dp))
        Text("Los ajustes de esta versión son locales y de demostración.", color = SoftText, fontSize = 11.sp)
    }
}

@Composable
private fun DetailScreen(title: String, liked: Boolean, saved: Boolean, onLike: () -> Unit, onSave: () -> Unit, onBack: () -> Unit) {
    Column {
        Text("‹ Volver", color = Violet, modifier = Modifier.clickable(onClick = onBack).focusable())
        Spacer(Modifier.height(14.dp))
        SectionTitle(title, "Ficha de contenido · demostración")
        Spacer(Modifier.height(12.dp))
        Text("Descripción", color = Color.White, fontSize = 17.sp, fontWeight = FontWeight.Bold)
        Text("Esta ficha muestra cómo se presentarán la sinopsis, los datos y las acciones de cada título cuando el catálogo autorizado esté conectado.", color = SoftText, fontSize = 14.sp)
        Spacer(Modifier.height(18.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            ActionButton("Reproducir", onClick = {}, enabled = false)
            ActionButton("Ver tráiler", onClick = {}, enabled = false)
            ActionButton(if (liked) "Te gusta" else "Me gusta", onLike)
            ActionButton(if (saved) "En Mi lista" else "Guardar", onSave)
        }
        Spacer(Modifier.height(12.dp))
        Text("Reproducción y tráiler deshabilitados hasta conectar fuentes autorizadas.", color = SoftText, fontSize = 11.sp)
    }
}

@Composable
private fun CategoryChips(labels: List<String>) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        labels.forEach { label ->
            Box(modifier = Modifier.background(PanelRaised, RoundedCornerShape(20.dp)).border(1.dp, Border, RoundedCornerShape(20.dp)).padding(horizontal = 12.dp, vertical = 8.dp)) {
                Text(label, color = Color.White, fontSize = 12.sp)
            }
        }
    }
}

@Composable
private fun PosterCard(title: String, onClick: () -> Unit) {
    Column(modifier = Modifier.width(125.dp).height(140.dp).background(Panel, RoundedCornerShape(12.dp))
        .border(1.dp, Border, RoundedCornerShape(12.dp)).clickable(onClick = onClick).focusable().padding(10.dp),
        verticalArrangement = Arrangement.Bottom) {
        Text("MILTVPLUS", color = Violet, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text(title, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
        Text("Ficha de muestra", color = SoftText, fontSize = 10.sp)
    }
}

@Composable
private fun InfoCard(title: String, detail: String) {
    Column(modifier = Modifier.width(175.dp).height(105.dp).background(Panel, RoundedCornerShape(12.dp)).border(1.dp, Border, RoundedCornerShape(12.dp)).padding(12.dp),
        verticalArrangement = Arrangement.Bottom) {
        Text(title, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(5.dp))
        Text(detail, color = SoftText, fontSize = 10.sp)
    }
}

@Composable
private fun SettingRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = Color.White, fontSize = 14.sp)
        Text(value, color = SoftText, fontSize = 13.sp)
    }
}

@Composable
private fun SettingToggle(label: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(label, color = Color.White, fontSize = 14.sp)
        Switch(checked = checked, onCheckedChange = onChange)
    }
}

@Composable
private fun ActionButton(label: String, onClick: () -> Unit, enabled: Boolean = true) {
    Button(onClick = onClick, enabled = enabled, colors = ButtonDefaults.buttonColors(containerColor = Violet, contentColor = Color.White)) {
        Text(label, fontSize = 12.sp)
    }
}
