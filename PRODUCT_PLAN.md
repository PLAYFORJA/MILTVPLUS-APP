# MILTVPLUS — Producto y plan de ejecución

## Objetivo

Construir una aplicación de entretenimiento para Android TV y Google TV, con una arquitectura que permita incorporar otras plataformas en etapas posteriores.

## Alcance del primer lanzamiento

- Inicio y navegación con control remoto.
- Catálogo de películas y series con metadatos.
- TV en vivo únicamente con señales propias o licenciadas.
- Reproductor HLS/DASH con estados de carga, error y reintento.
- Mi lista persistida localmente en la primera etapa.
- Búsqueda y ficha de contenido.
- Configuración básica y pantalla Acerca de.

## Fuera del primer lanzamiento

- Samsung Tizen, LG webOS, VIDAA y Apple TV.
- Pagos, suscripciones y cuentas multiusuario hasta definir proveedor, país y obligaciones legales.
- Deportes o canales de terceros sin contratos de distribución.
- Descargas offline y recomendaciones personalizadas.

## Arquitectura propuesta

- Cliente: Kotlin, Jetpack Compose y AndroidX Media3.
- Datos: API HTTPS con contratos versionados.
- Catálogo: PostgreSQL detrás de una API; no exponer credenciales de base de datos en la app.
- Imágenes: almacenamiento/CDN con URLs HTTPS.
- Streaming: HLS/DASH autorizado, con URLs firmadas cuando corresponda.
- Observabilidad: registro de errores sin recolectar datos personales innecesarios.

## Fases

1. **Base Android TV:** estructura Gradle, navegación D-pad, identidad visual y APK de prueba.
2. **Experiencia de catálogo:** modelos, datos de muestra claramente identificados, búsqueda, fichas y Mi lista.
3. **Reproducción:** Media3, controles, subtítulos, audio, estados de error y streams de prueba autorizados.
4. **Backend:** API, catálogo, usuarios/perfiles si se decide, administración y protección de credenciales.
5. **Calidad:** pruebas en emulador y dispositivos físicos, accesibilidad, rendimiento, privacidad y seguridad.
6. **Distribución:** firma de release, ficha de tienda, política de privacidad, clasificación por edades y revisión de requisitos de Google Play.

## Dependencias que requieren al titular

- Titularidad y autorización de la marca.
- Derechos de distribución de cada canal, película, serie o evento; territorios y ventanas.
- Dominio, alojamiento y presupuesto operativo.
- Decisión sobre cuentas, cobros, suscripciones y soporte.
- Acceso del titular a Play Console y aceptación de acuerdos legales.

## Criterio de terminado

No declarar una función lista hasta que esté implementada, compilada y probada en el dispositivo objetivo. No presentar una pantalla de muestra como si reprodujera contenido real.
