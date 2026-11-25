# Seguridad, cuentas y derechos de contenido

## Cuentas MILTVPLUS

La cuenta será propia de MILTVPLUS, sin obligación de iniciar sesión con Google. El diseño previsto contempla registro e inicio de sesión con correo y contraseña, recuperación de acceso y cierre de sesión.

La autenticación debe implementarse en un backend HTTPS. Las contraseñas nunca se guardan en texto plano: usar hash seguro con sal mediante un proveedor de identidad o una implementación revisada. La app almacenará únicamente tokens de sesión protegidos y no incluirá secretos de servidor.

## Canales de televisión y deportes

Que una señal sea abierta o gratuita para verla en televisión no significa que MILTVPLUS tenga permiso para retransmitirla dentro de su propia aplicación. Antes de habilitar cada señal se debe verificar:
- titular del canal y autorización para retransmisión por internet;
- territorios y vigencia;
- uso de logotipo, guía de programación y metadatos;
- restricciones de publicidad, grabación y reproducción bajo demanda.

La app puede incluir un catálogo de señales disponibles oficialmente, enlaces o reproductores solo cuando sus términos permitan ese uso. No se incorporarán listas piratas, capturas de streams ni señales de pago sin autorización.

## Películas, series y trailers

Cada ficha debe registrar proveedor/titular, licencia, territorios, ventana de exhibición, formatos permitidos y fecha de vencimiento. Trailer, reproducción, compartir y “Me gusta” son acciones separadas. El botón Reproducir solo se habilita cuando el contenido tiene una fuente autorizada y válida.

## Datos personales

Antes del lanzamiento se requiere política de privacidad, términos de uso, canal de soporte y reglas de retención/eliminación de datos. Recopilar solo lo necesario y proteger la información de las cuentas.
