# MILTVPLUS — Sistema de diseño v2

**Estado:** nueva dirección visual en desarrollo. Reemplaza el borrador anterior; no se considera diseño final hasta revisión en Android TV real.

## Principios
1. Producto primero: cada pantalla resuelve una tarea concreta.
2. Identidad reconocible: isotipo M geométrico y logotipo consistente, sin símbolo de play genérico.
3. Cine sin ruido: arte protagonista, metadatos claros y espacio negativo.
4. TV de verdad: todo se opera con D-pad, foco visible y textos legibles a distancia.
5. Honestidad funcional: diferenciar datos reales, demostraciones, contenido no disponible y contenido reproducible.

## Tokens
- Fondo Obsidian: #08070D
- Superficie Graphite: #121019
- Panel Plum: #1B1726
- Elevado: #262033
- Acción Violet: #8B5CF6
- Acento Orchid: #B58CFF
- Texto: #F7F5FA
- Secundario: #B7B0C4
- Borde: #393344
- Éxito: #55C99A
- Advertencia: #F2B766

## Marca
- Isotipo: M geométrica continua, angular y ascendente.
- Logotipo horizontal: MILTVPLUS en mayúsculas, peso 800, tracking moderado.
- No usar triángulo de play como identidad principal.
- No usar glow, cromados o 3D como recurso permanente.
- Crear versiones launcher, horizontal, monocroma y pequeña.

## Layout para TV
- Lienzo 16:9.
- Navegación lateral consistente en todas las secciones.
- Área principal amplia, con encabezado y contenido alineados.
- Hero solo cuando exista contenido editorial real y autorizado.
- Filas de tarjetas con proporción estable y foco D-pad destacado.
- No llenar la pantalla con paneles pequeños ni textos largos.

## Componentes y estados
- Botón primario: violeta sólido.
- Botón secundario: superficie elevada con borde.
- Tarjeta de contenido: arte 2:3, título, año/género si existen.
- Tarjeta de categoría: icono propio + nombre; foco con borde y leve elevación.
- Estado vacío: explicar por qué está vacío y ofrecer un siguiente paso.
- Estado de carga: skeleton sin saltos de layout.
- Error: mensaje comprensible y acción de reintento cuando aplique.
- Foco, selección y deshabilitado deben ser visualmente distintos.

## Pantallas objetivo
1. Inicio: hero editorial, continuar viendo si hay historial, colecciones y accesos.
2. TV en vivo: señales verificadas, categorías y guía solo con datos autorizados.
3. Cine y series: catálogo con filtros, géneros y colecciones.
4. Deportes: disciplinas y eventos/canales con derechos confirmados.
5. Ficha: arte, sinopsis, metadatos, reparto y acciones disponibles.
6. Reproductor: transporte, audio, subtítulos, calidad, errores recuperables.
7. Mi lista: guardados sincronizables con cuenta cuando exista backend.
8. Mi cuenta: registro propio, acceso, recuperación y perfiles.
9. Ajustes: preferencias de reproducción, idioma, subtítulos, privacidad y cuenta.
10. Buscar: búsqueda global con teclado de TV, filtros y estados sin resultados.

## Requisitos de calidad
- Navegación completa por mando, sin depender de touch.
- Orden de foco coherente entre navegación, encabezado y contenido.
- Acciones con resultado real; no dejar botones decorativos.
- Preferencias persistentes y almacenamiento seguro.
- Ninguna señal, película, serie, tráiler o evento se habilita sin derechos y URL autorizada.
- Mockups conceptuales no son prueba de implementación ni de funcionamiento.

## Plan de refinamiento
- Fase A: marca, tokens y componentes base.
- Fase B: rediseño de Inicio y navegación.
- Fase C: catálogo, detalle, lista y búsqueda.
- Fase D: TV, deportes y reproductor.
- Fase E: cuenta/backend, persistencia, accesibilidad y QA de TV.
