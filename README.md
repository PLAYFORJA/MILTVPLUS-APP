# MILTVPLUS

**Tu entretenimiento, tu mundo.**

MILTVPLUS es un proyecto de aplicación de entretenimiento para Android TV y Google TV.

## Estado del proyecto

Primera base Android creada: pantalla de inicio en negro y violeta, navegación enfocada al control remoto y flujo de compilación automática de APK con GitHub Actions.

## Tecnologías

- Kotlin
- Jetpack Compose
- Android TV / Google TV
- AndroidX Media3 (base para reproducción multimedia)
- Gradle y GitHub Actions

## Compilar

Cada push a `main` ejecuta el flujo **Android TV APK**. También se puede iniciar manualmente desde la pestaña **Actions** de GitHub. Al finalizar, descargar el artefacto `MILTVPLUS-AndroidTV-debug`.

## Próximas etapas

1. Probar la compilación y navegación en Android TV.
2. Integrar reproductor con streams HLS/DASH propios o licenciados.
3. Crear catálogo, perfiles y backend.
4. Preparar versión firmada para distribución.

**Importante:** esta versión inicial no incluye canales, películas ni series reales. Solo debe conectarse contenido propio o con autorización/licencia correspondiente.
