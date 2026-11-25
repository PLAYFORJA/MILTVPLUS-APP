# MILTVPLUS — instrucciones maestras para agentes de desarrollo

> Este archivo es el traspaso oficial del proyecto. Leerlo completo antes de modificar código. El repositorio es la fuente de verdad; no reconstruir la app desde cero ni inventar archivos, funciones, APIs, licencias o resultados.

## 0. Mandato de trabajo y responsabilidad

Actuás como ingeniero/a senior responsable de llevar MILTVPLUS desde su prototipo Android TV hasta una aplicación robusta, mantenible, probada y publicable. El titular no quiere explicaciones vagas ni promesas: quiere cambios reales en el repositorio, compilaciones verificadas y reportes honestos.

Reglas obligatorias:
1. Inspeccioná todo el repositorio y el historial relevante antes de editar.
2. Conservá el trabajo existente salvo que haya una razón técnica documentada para cambiarlo.
3. No afirmes que algo está terminado, funciona, compila o está probado sin evidencia reproducible.
4. Antes de cada entrega ejecutá las verificaciones pertinentes. Si no podés ejecutar algo, decilo claramente y dejá el comando exacto.
5. Hacé cambios pequeños, coherentes y revisables. No mezcles una reescritura completa con cambios cosméticos.
6. No dejes botones decorativos ni acciones falsas. Si una función no existe, mostrar estado “próximamente/no disponible” y deshabilitarla con explicación.
7. No agregues streams, canales, películas, series, eventos, imágenes, marcas ni datos de disponibilidad inventados.
8. No incluyas credenciales, tokens, contraseñas, claves privadas ni URLs secretas en el cliente Android, commits, logs o documentación pública.
9. Nunca ocultes una compilación fallida. Corregí el error, repetí el build y reportá el resultado final.
10. Si el titular informa que una modificación rompió algo, reproducí el fallo y hacete cargo de la corrección; no culpes al usuario ni le pidas que depure código.
11. Antes de reemplazar arquitectura, explicar impacto, plan de migración y riesgos; mantener un punto de restauración Git.
12. No pedir al titular que programe manualmente. El agente debe inspeccionar, editar, compilar, probar y documentar todo lo que las herramientas permitan.

## 1. Identidad y objetivo de producto

- Nombre: **MILTVPLUS** (siempre en mayúsculas).
- Lema: **Tu pantalla. Tu mundo.**
- Producto: plataforma de entretenimiento para Android TV y Google TV, diseñada primero para mando a distancia y pantalla 16:9.
- Personalidad: profesional, cinematográfica, clara, confiable, premium sin parecer una copia de Netflix/Disney/Prime ni una plantilla genérica.
- No confundir “interfaz de streaming” con un servicio de streaming real. La app necesita catálogo autorizado, backend, derechos, fuentes de reproducción y operación.
- Primera plataforma: Android TV / Google TV. No ampliar a Samsung Tizen, LG webOS, VIDAA, Apple TV ni móviles como objetivo principal hasta estabilizar Android TV.

## 2. Estado real confirmado del repositorio

Repositorio: https://github.com/PLAYFORJA/MILTVPLUS-APP  
Rama inspeccionada: `main`  
Commit inspeccionado: `8df5af47baa0b328c2d8cf5e32208687fe74b4d7`  
Paquete Android: `com.miltvplus.app`

Estructura que existe:
- `.github/workflows/android.yml`
- `README.md`
- `BRAND_GUIDE.md`
- `DESIGN_SYSTEM.md`
- `PRODUCT_PLAN.md`
- `SECURITY_AND_CONTENT.md`
- `build.gradle.kts`
- `settings.gradle.kts`
- `gradle.properties`
- `app/build.gradle.kts`
- `app/src/main/AndroidManifest.xml`
- `app/src/main/java/com/miltvplus/app/MainActivity.kt`
- `app/src/main/java/com/miltvplus/app/data/CatalogRepository.kt`
- `app/src/main/java/com/miltvplus/app/data/MediaItem.kt`

Tecnologías y configuración que están declaradas:
- Kotlin 2.0.21
- Android Gradle Plugin 8.7.3
- Gradle 8.9 en CI
- Java/JVM 17
- compileSdk 35, minSdk 23, targetSdk 35
- Jetpack Compose BOM 2024.12.01, Material 3 y Compose Foundation
- AndroidX TV Material 1.0.0
- AndroidX Media3 ExoPlayer/UI 1.5.1
- applicationId/namespace `com.miltvplus.app`
- INTERNET permission
- Leanback y touchscreen opcionales
- launcher MAIN y LEANBACK_LAUNCHER
- workflow GitHub Actions genera artefacto debug si el build termina correctamente.

Estado funcional confirmado por inspección:
- Hay una sola Activity principal muy monolítica: `MainActivity.kt` (~20 KB), con pantallas/composables y navegación local.
- Hay navegación visual a Inicio, TV en vivo, Modo Cine, Deportes, Mi lista, Buscar, Mi cuenta y Configuración.
- Inicio y Modo Cine muestran contenido de demostración. Los títulos visibles son ficticios/de muestra.
- TV en vivo y Deportes muestran estados informativos; no hay señales reales integradas.
- Las fichas de contenido son de demostración; reproducir y tráiler están deshabilitados.
- Me gusta y Mi lista se guardan localmente en SharedPreferences.
- Buscar filtra una lista de títulos demo en memoria; no consulta un backend.
- Mi cuenta es informativa: no existe registro, inicio de sesión, recuperación ni backend de identidad.
- Ajustes de reproducción/notificaciones/subtítulos son locales y de demostración.
- `CatalogRepository` existe, pero `DemoCatalogRepository` devuelve listas vacías.
- `MediaItem` contempla URL de póster/tráiler/reproducción y bandera isLicensed; canPlay/canShowTrailer requieren licencia y URL.
- Media3 está declarado como dependencia, pero no hay integración de reproducción funcional confirmada.
- No se encontró backend, API remota, PostgreSQL, panel administrativo, proveedor de identidad ni contenido con derechos configurado.
- No hay APK de release firmado ni prueba documentada en televisor físico.

### Incidente de compilación que debe corregirse primero

El agente anterior introdujo errores en `MainActivity.kt`:
- Añadió scroll vertical y después importó `verticalScroll` desde un paquete incorrecto.
- En commits intermedios también hubo llamadas incompatibles a `ActionButton`; se modificó la firma y se corrigieron algunos call sites.
- En el commit actual `8df5af47baa0b328c2d8cf5e32208687fe74b4d7`, GitHub Actions run **45607305460** terminó en **failure**. El log de `compileDebugKotlin` muestra:
  - `MainActivity.kt:21:43 Unresolved reference 'verticalScroll'`
  - `MainActivity.kt:137:18 Unresolved reference 'verticalScroll'`
- El commit anterior `c43649b9e68b32e6f47cae532abf74c646ce77f9`, run **45607275327**, también falló por el mismo import.
- La importación correcta de Compose Foundation para el modificador es `androidx.compose.foundation.verticalScroll`, no `androidx.compose.foundation.layout.verticalScroll`. Confirmar versión/API y corregir import; no hacer una sustitución a ciegas.
- No hay build exitoso para el HEAD actual. Existe un run anterior exitoso en un commit previo (`88a7e2b70740757dbbc4d3c96b40f647c79dcdb9`, run 45605373031), pero no demuestra que el HEAD actual compile.
- Primer objetivo: corregir el import, ejecutar `gradle assembleDebug` (CI) y comprobar que el run nuevo termina en success. Revisar los logs completos y no declarar listo hasta obtener éxito.

Historial de cambios fallidos que debe quedar reconocido:
- `3409aea1389e084147db23aa98b08798091bb0a5`: introdujo uso de scroll sin import correcto.
- `da981312c85a32102421f70841b7457f725cd01f`: cambió ActionButton y dejó una llamada posicional incompatible.
- `c43649b9e68b32e6f47cae532abf74c646ce77f9`: intento de arreglar imports; importó verticalScroll desde ubicación incorrecta.
- `8df5af47baa0b328c2d8cf5e32208687fe74b4d7`: ajustó orden de parámetros de ActionButton, pero CI continúa fallando por verticalScroll.
El nuevo agente debe asumir estos errores como deuda técnica introducida por el trabajo previo, auditarlos y repararlos. No borrar la historia ni fingir que el build está correcto.

## 3. Marca y sistema visual obligatorio

Consultar también `BRAND_GUIDE.md` y `DESIGN_SYSTEM.md`. Tokens vigentes:
- Obsidian fondo principal: `#08070D`
- Graphite superficie: `#121019`
- Plum panel: `#1B1726`
- Elevated tarjeta elevada: `#262033`
- Violet acción principal: `#8B5CF6`
- Orchid acento secundario: `#B58CFF`
- White texto principal: `#F7F5FA`
- Muted texto secundario: `#B7B0C4`
- Divider/borde: `#393344`
- Success: `#55C99A`
- Warning: `#F2B766`

Marca:
- Diseñar isotipo propio: M geométrica continua, angular y ascendente, que sugiera pantalla/señal/movimiento.
- Logotipo horizontal MILTVPLUS en mayúsculas, tipografía sans moderna y peso fuerte.
- Variantes necesarias: isotipo launcher, logotipo horizontal, monocromo claro/oscuro y tamaño pequeño.
- No usar triángulo Play genérico como identidad principal.
- No usar glow, cromado, bisel ni 3D como muleta estética.
- No imitar marcas de plataformas existentes.
- No reutilizar icono genérico de letra dentro de cuadrado como logo final.
- Crear recursos vectoriales Android (VectorDrawable) y exportables apropiados; mantener consistencia en launcher, pantalla de carga y navegación.
- Validar legibilidad real en TV a distancia, en icono de launcher y fondos claros/oscuros.
- No declarar marca final hasta revisar los recursos visualmente y probarlos en dispositivo.

Tipografía y layout:
- Sans serif legible, pesos 400/500/600/700/800.
- Pantalla de referencia 16:9; safe margins generosos; diseño pensado para 1080p y escalado.
- Jerarquía clara, pocos elementos por vista, metadatos legibles.
- Arte de contenido 2:3, hero panorámico cuando exista arte autorizado.
- Cards con estados default/focus/pressed/disabled/loading.
- El foco del D-pad debe distinguirse por borde/escala/elevación, no solo color.
- Evitar scroll global como solución indiscriminada: cada sección debe tener comportamiento intencional y navegación de foco correcta.

## 4. Mapa de pantallas y comportamiento requerido

Implementar todas las pantallas con estados de carga, vacío, error, datos demo claramente etiquetados y contenido real solo al conectar backend/licencias. Cada botón debe tener comportamiento definido.

### 4.1 Splash / inicio de aplicación
- Mostrar isotipo y wordmark de marca con animación sutil, breve y no bloqueante.
- Resolver estado de sesión local/remota y estado de conectividad.
- No fingir login ni mostrar cuenta autenticada si no hay sesión.
- Manejar restauración de estado y navegación al Home.

### 4.2 Inicio
- Navegación lateral fija y consistente.
- Cabecera con marca, estado de cuenta y acciones globales.
- Hero editorial: arte autorizado, título, sinopsis corta, clasificación/metadata y máximo dos CTAs.
- Bloque Continuar viendo solo si hay historial real.
- Carruseles/filas: Recomendados, Películas, Series, Nuevos agregados, Mi lista; ocultar secciones sin datos en vez de inventar.
- Accesos a TV en vivo, Cine y series, Deportes y Mi lista.
- Si el backend no está conectado, mostrar una vista de demostración marcada como tal.
- Foco D-pad: navegación lateral ↔ contenido, restauración de foco al volver.

### 4.3 TV en vivo
- Lista/carrusel de señales que el backend confirme como disponibles para el territorio.
- Categorías, logo oficial permitido, nombre, idioma, género, estado de disponibilidad y programa actual solo si hay EPG autorizado.
- Guía de programación (EPG) opcional cuando existan datos y derechos.
- Filtros funcionales; estado sin señales con explicación.
- No mostrar canales falsos ni streams copiados de internet.
- Seleccionar canal abre ficha o reproductor únicamente si la fuente está autorizada y vigente.

### 4.4 Cine y series / Modo Cine
- Separar Películas y Series; géneros, colecciones y filtros funcionales.
- Búsqueda y ordenamiento desde el repositorio de datos.
- Cards con póster autorizado, título, año, género y badge de disponibilidad si la API lo informa.
- Paginación o carga incremental; no cargar el catálogo entero en memoria.
- Estados vacío, carga, error y reintento.

### 4.5 Deportes
- Disciplinas (fútbol, básquet, tenis, automovilismo, rugby y otras solo si el backend ofrece datos).
- Eventos, calendario, resultados o canales solo si hay proveedor de datos autorizado.
- No prometer transmisiones de ligas/eventos por el mero hecho de mostrar su nombre.
- Diferenciar “información del evento” de “reproducción en vivo”.

### 4.6 Ficha de contenido
- Arte principal y póster, título, descripción, género, año, duración, clasificación por edad, idioma, reparto/director cuando existan metadatos fiables.
- Acciones: Reproducir, Tráiler, Mi lista, Me gusta; compartir solo si se define.
- Reproducir/tráiler deshabilitados cuando no haya licencia válida, URL válida, territorio/ventana habilitados.
- Volver al origen conservando posición/foco.
- No mostrar metadatos inventados.

### 4.7 Reproductor
- Integrar AndroidX Media3 ExoPlayer de verdad; lifecycle correcto, release del player, audio focus, red y errores.
- HLS/DASH según formato autorizado. No incluir URLs de terceros no autorizadas.
- Controles: play/pause, seek si el stream lo permite, progreso, tiempo, audio, subtítulos, selector de calidad si soportado, volver.
- Estados: buffering, reproducción, pausa, fin, sin conexión, URL expirada, geo-block, DRM/codec no soportado y reintento.
- Manejar rotación/configuración y cierre de pantalla.
- DRM solo con proveedor, licencias y configuración válidos; no intentar evadir DRM.
- Telemetría mínima y sin datos sensibles.

### 4.8 Mi lista
- Lista vacía útil y clara.
- Guardar/quitar elementos; persistencia local con Room/DataStore (migrar desde SharedPreferences si corresponde).
- Preparar sincronización futura con cuenta, sin bloquear la versión local.
- Evitar duplicados; ordenar/filtrar; navegación y foco.

### 4.9 Buscar
- Búsqueda global en catálogo de películas, series y señales habilitadas.
- Teclado compatible con Android TV/IME y navegación D-pad.
- Debounce/cancelación de consultas remotas, estado de carga, resultados, sin resultados, error.
- Filtros y sugerencias solo si se soportan.
- No limitar búsqueda a una lista hardcodeada demo.

### 4.10 Mi cuenta
- Cuenta propia MILTVPLUS por correo/contraseña si el titular decide activar cuentas.
- Registro, inicio, cerrar sesión, recuperar contraseña, verificación de correo y perfil.
- Backend HTTPS con proveedor de identidad confiable; contraseñas nunca en texto plano.
- Nunca pedir credenciales reales mientras el backend sea demo.
- Si no hay backend, indicar claramente “Cuenta no disponible todavía” y no fingir una sesión.

### 4.11 Configuración
- Idioma (solo opciones implementadas), calidad automática/manual si el reproductor la soporta, autoplay, subtítulos preferidos, notificaciones, privacidad, accesibilidad, versión, términos, privacidad y cerrar sesión.
- Persistencia con DataStore; cambios efectivos, no controles decorativos.
- Ajustes de cuenta solo cuando haya sesión/backend.

### 4.12 Acerca de / ayuda / soporte
- Versión, build, identificador de aplicación, licencias de software de terceros, política de privacidad, términos y canal de soporte real.
- No inventar domicilio, correo ni empresa titular.
- Agregar pantalla de ayuda y diagnóstico básico (conectividad/versión) sin exponer información sensible.

## 5. Arquitectura técnica objetivo

Refactorizar progresivamente la Activity monolítica. Arquitectura sugerida (adaptar a lo que ya exista, documentar decisiones):
- `app/`
  - `core/designsystem/`: colores, tipografía, shapes, spacing, componentes y foco TV.
  - `core/navigation/`: rutas, NavHost, manejo de back y foco.
  - `core/network/`: cliente HTTPS, serialización, interceptores sin secretos.
  - `data/model/`: DTOs y modelos de dominio.
  - `data/remote/`: API services y mappers.
  - `data/local/`: Room/DataStore y migraciones.
  - `data/repository/`: implementaciones de repositorios.
  - `domain/`: casos de uso y reglas.
  - `feature/home/`, `live/`, `cinema/`, `sports/`, `detail/`, `player/`, `watchlist/`, `search/`, `account/`, `settings/`, `about/`.
  - `di/`: composición de dependencias, si se adopta DI.
- UI con Jetpack Compose; estado con ViewModel + StateFlow; una fuente de verdad por pantalla.
- Separar DTO, dominio y UI. No poner networking o reglas de negocio dentro de composables.
- Repositorios con interfaces y estados tipados; no devolver listas vacías sin distinguir loading/error/no data.
- Coroutines y cancelación; manejo de errores explícito.
- Pruebas unitarias para casos de uso, mappers, validación de licencia y estado.
- Pruebas UI/instrumentadas donde sean viables.

No añadir librerías sin justificar compatibilidad con Kotlin, AGP, compileSdk y Android TV. Revisar release notes/compatibilidad antes de actualizar versiones. Mantener dependencias explícitas y evitar versiones dinámicas.

## 6. Modelo de contenido y derechos

Extender `MediaItem` sin romper compatibilidad innecesariamente. El dominio debe poder representar:
- id estable, título, tipo, descripción, género/categoría, año, duración, poster/backdrop, clasificación, idiomas, reparto/director, proveedor/titular.
- disponibilidad por territorio, ventana de inicio/fin, licencia, tipo de uso, autorización para reproducción/tráiler, URL o manifest, DRM si aplica.
- estado publicado/oculto y fecha de actualización.
- Para señales: logo, país/territorio, idioma, EPG y autorización de retransmisión.

La regla de autorización no puede depender solo de un booleano local controlado por la app. El backend debe decidir la disponibilidad, validar el usuario/territorio/ventana y emitir URLs firmadas de corta duración cuando corresponda. El cliente debe fallar cerrado: si faltan derechos, territorio, URL o vigencia, no reproducir.

No insertar listas M3U pirateadas, enlaces de IPTV ajenos, capturas de streams, canales de pago sin contrato ni contenido obtenido por scraping que viole términos. Señal abierta/gratuita no significa automáticamente permiso de retransmisión.

## 7. Backend y servicios a definir

No hay backend actualmente. Antes de implementarlo, presentar una propuesta con costos/operación y pedir decisión del titular sobre proveedor y dominio. Arquitectura prevista:
- API HTTPS versionada, por ejemplo `/v1`.
- PostgreSQL administrado detrás de API; nunca conectar el APK directamente a PostgreSQL ni incluir service-role keys.
- Almacenamiento/CDN para arte e imágenes con URLs HTTPS.
- Servicio de catálogo y disponibilidad por país.
- Servicio de autenticación si se activa cuenta.
- Panel de administración protegido para gestionar catálogo, derechos, territorios, ventanas, señales, usuarios y publicaciones.
- Logs/observabilidad, backups, rate limiting, protección contra abuso y monitoreo.
- Configuración por entornos dev/staging/prod.
- Secretos en gestor seguro del proveedor/CI; nunca en Git.
- Política de retención y eliminación de datos.

Endpoints orientativos, no implementados:
- `GET /v1/home`
- `GET /v1/catalog/movies`
- `GET /v1/catalog/series`
- `GET /v1/live/channels?territory=AR`
- `GET /v1/sports/events?territory=AR`
- `GET /v1/media/{id}`
- `GET /v1/search?q=...`
- `POST /v1/media/{id}/playback-session` (valida derechos y emite sesión/URL temporal)
- `GET/PUT /v1/me/watchlist`
- Auth endpoints según proveedor elegido.
No inventar contrato definitivo sin diseñar esquema, errores, paginación, autenticación y pruebas.

## 8. Seguridad, privacidad y cumplimiento

- HTTPS obligatorio; no aceptar cleartext.
- Tokens almacenados con mecanismos seguros de Android; no SharedPreferences plano para secretos.
- No registrar passwords, tokens, URLs firmadas ni datos personales.
- Mínima recopilación de datos; consentimiento cuando corresponda.
- Política de privacidad, términos, clasificación por edad, soporte y proceso de eliminación antes de publicar.
- Verificar licencias de imágenes, música, fuentes, canales, películas, series, trailers y deportes.
- Verificar titularidad y disponibilidad marcaria de MILTVPLUS antes de lanzamiento comercial.
- No hacer promesas comerciales o claims de catálogo que no puedan demostrarse.

## 9. Android TV: mando, accesibilidad y rendimiento

- Todas las acciones operables con D-pad/OK/Back; no depender de touch.
- FocusRequester/focusProperties cuando sea necesario; no robar foco al recomponer.
- Orden de foco natural: menú lateral → cabecera → contenido → controles; retorno conserva foco/posición.
- Foco visible en cards, botones, filtros, switches, campos y diálogos.
- Pruebas con control remoto real y emulador Android TV.
- Diseño landscape 16:9, lectura a distancia, escalado de densidad y safe margins.
- Evitar grids/rows que recorten elementos o requieran desplazamiento horizontal imposible.
- LazyRow/LazyColumn para colecciones extensas; key estable.
- Carga de imágenes con caché, placeholders y fallbacks; no bloquear UI.
- Medir memoria, tiempo de inicio, jank y consumo de red.
- Tratar back, pérdida de conectividad, suspensión y reanudación.

## 10. Calidad, pruebas y Definition of Done

Antes de cada commit:
1. `gradle assembleDebug`
2. `gradle testDebugUnitTest` (crear tests si faltan; si no hay tests, dejar constancia)
3. `gradle lintDebug` si está configurado; resolver findings relevantes.
4. Revisar diff, imports, warnings, recursos, manifest y dependencias.
5. CI debe quedar verde para el commit exacto que se entrega.
6. Instalar APK en emulador/dispositivo Android TV y probar navegación básica; registrar qué dispositivo/AVD y resultado.
7. No marcar como completo lo que solo se compiló: build ≠ QA de TV ≠ servicio de streaming.

Definition of Done por feature:
- UI final según sistema de diseño.
- Estado loading/success/empty/error.
- Accesibilidad y D-pad.
- Acción conectada a caso de uso real.
- Manejo de errores y recuperación.
- Persistencia/red según corresponda.
- Tests relevantes.
- Build y CI verdes.
- Documentación actualizada.

## 11. Fases de desarrollo priorizadas

**P0 — Recuperar estabilidad (bloqueante)**
- Corregir import de verticalScroll en MainActivity.
- Ejecutar build y CI; resolver cualquier error posterior.
- Inspeccionar todos los call sites de ActionButton y demás funciones modificadas.
- Confirmar que HEAD compila y adjuntar link al run exitoso.

**P1 — Auditoría y base arquitectónica**
- Inventariar código, dependencias, recursos y deuda técnica.
- Extraer tema/diseño y componentes.
- Dividir MainActivity en navegación, ViewModels y pantallas sin cambiar comportamiento en el mismo commit.
- Introducir tests básicos.
- Resolver navegación D-pad y estados.

**P2 — Identidad y experiencia**
- Crear logo final vectorial y recursos launcher.
- Rediseñar Home, navegación lateral, cards, hero, detalle y estados vacíos.
- Revisar cada pantalla en TV 1080p.
- Implementar foco y animaciones discretas.

**P3 — Catálogo y persistencia**
- Modelos/API y datos de catálogo reales del titular/proveedor.
- Cine, series, ficha, búsqueda, filtros y Mi lista.
- Room/DataStore, paginación, caché de imágenes.
- Nunca reemplazar demo con contenido inventado.

**P4 — Reproducción**
- Integrar Media3 ExoPlayer.
- Crear playback session backend y validación de derechos/territorio/ventana.
- HLS/DASH y DRM si aplica; subtítulos/audio/controles/errores.
- Probar streams de test legalmente autorizados.

**P5 — TV en vivo y deportes**
- Contratos de distribución y fuentes oficiales.
- Catálogo, EPG, programación y eventos si el proveedor lo autoriza.
- Pruebas de estabilidad, geo-restricciones y fallos de red.

**P6 — Cuentas y backend**
- Elegir proveedor y dominio con el titular.
- API, PostgreSQL, autenticación, panel administrativo, secretos, backups, privacidad.
- No activar login falso ni exponer claves.

**P7 — Release**
- Pruebas de aceptación en dispositivos reales.
- Firma release segura (keystore no se sube a Git), versionado y pipeline.
- Política de privacidad, términos, clasificación, store listing, capturas reales y revisión de Play Console.
- Plan de soporte, incidentes y actualización.

## 12. Configuración local y comandos

Requisitos de desarrollo alineados al repo actual:
- Android Studio compatible con AGP 8.7.3.
- JDK 17.
- Android SDK Platform 35 y Build Tools compatibles.
- Gradle 8.9 (CI actual usa Gradle instalado, no wrapper confirmado).
- Android TV emulator o dispositivo físico para QA.

Comandos desde raíz:
```bash
gradle --version
gradle assembleDebug
gradle testDebugUnitTest
gradle lintDebug
```
APK debug esperado, solo si build tiene éxito:
`app/build/outputs/apk/debug/app-debug.apk`

Revisar si falta Gradle Wrapper (`gradlew`, `gradlew.bat`, `gradle/wrapper/`). Si falta, agregar wrapper oficial y ajustar CI para usar `./gradlew`, verificando versión. No asumir que el wrapper existe.

## 13. Formato obligatorio de reporte al titular

Cada iteración debe informar en español claro:
- Qué se inspeccionó.
- Qué archivos se modificaron y por qué.
- Qué comportamiento real quedó implementado.
- Comandos ejecutados.
- Resultado exacto de build/tests/lint/CI (con enlaces y commit SHA).
- Pruebas de mando/emulador/dispositivo realizadas o pendientes.
- Errores restantes, bloqueos externos y decisiones que requiere el titular.
- No usar frases “ya está todo”, “100% terminado”, “APK listo” sin evidencia.
- No trasladar al titular la tarea de depurar los errores introducidos por el agente.

## 14. Instrucción inicial para el próximo agente

Empezá ahora, sin pedir que el titular repita el contexto:
1. Leé este archivo y los cuatro documentos de producto/seguridad/marca.
2. Inspeccioná el commit HEAD y los logs del run 45607305460.
3. Repará el import `verticalScroll` y cualquier otro error de compilación actual.
4. Ejecutá build, tests y lint disponibles. Conseguí CI verde en un commit nuevo.
5. Recién después avanzá con auditoría y refactor por etapas.
6. No implementes streaming real hasta contar con fuentes autorizadas y configuración del backend.
7. Trabajá directamente en este repositorio y dejá cambios committeados, con evidencia.

**Prioridad absoluta: preservar el proyecto existente, reparar los errores previos y convertir el prototipo en una aplicación real mediante entregas verificables.**
