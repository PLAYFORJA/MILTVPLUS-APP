# MILTVPLUS

**Tu pantalla. Tu mundo.**

Identidad visual v2 en desarrollo: monograma geométrico M, logotipo tipográfico propio y sistema Obsidian / Graphite / Violet.

Aplicación de entretenimiento en desarrollo para Android TV y Google TV.

## Estado actual

- Proyecto Android Kotlin + Jetpack Compose.
- Navegación inicial por Inicio, TV en vivo, Modo Cine, Deportes, Mi lista, Buscar, Mi cuenta y Configuración.
- Fichas de muestra con acciones locales de Me gusta y Mi lista.
- Modelos tipados de catálogo y contrato de repositorio.
- Workflow de GitHub Actions para intentar compilar un APK de depuración.
- Nueva guía de marca y sistema de diseño v2 aplicados a la identidad inicial de Android TV.

**Importante:** el proyecto todavía no es un servicio de streaming terminado. El catálogo visible es demostrativo; no hay señales de TV, eventos deportivos, películas ni series conectadas para reproducción. No se debe presentar la compilación como verificada hasta revisar el resultado de Actions e instalar y probar el APK.

## Documentación

- [Guía de marca](BRAND_GUIDE.md)
- [Sistema visual v2 y pantallas objetivo](DESIGN_SYSTEM.md)
- [Plan de producto y arquitectura](PRODUCT_PLAN.md)
- [Seguridad, cuentas y derechos de contenido](SECURITY_AND_CONTENT.md)

## Tecnologías

- Kotlin, Jetpack Compose y AndroidX Media3.
- Android TV / Google TV.
- Gradle y GitHub Actions.
- API HTTPS y PostgreSQL previstos para la siguiente fase.

## Contenido y derechos

MILTVPLUS solo habilitará señales, películas, series, trailers y deportes con autorización suficiente para el uso previsto y los territorios correspondientes. Que una señal sea gratuita o abierta al público no implica automáticamente permiso para retransmitirla en una aplicación de terceros.

## Compilación

El workflow **Android TV APK** está configurado para ejecutarse al subir cambios a `main` y manualmente desde **Actions**. Si finaliza correctamente, genera el artefacto `MILTVPLUS-AndroidTV-debug`. El resultado debe comprobarse en GitHub Actions y probarse en un Android TV real o emulador compatible.
