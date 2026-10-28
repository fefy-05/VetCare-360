# VetCare 360 — Ashley — HU-001, HU-002 y HU-003

## Alcance
- HU-001 Registro de usuarios: formulario, validación de correo, duplicados y contraseña mínima.
- HU-002 Inicio de sesión: autenticación real con Spring Security y cierre de sesión.
- HU-003 Cambio de contraseña: contraseña actual, nueva, confirmación y validaciones.
- Interfaz Bootstrap 5 + Thymeleaf, persistencia MySQL/JPA y hash BCrypt.

## Requisitos
Java 21, Maven, MySQL 8+, Apache NetBeans con soporte Maven.

## Instalación
1. Ejecutar `database.sql` en MySQL Workbench.
2. Definir `DB_USER` y `DB_PASSWORD` como variables de entorno (o configurar credenciales locales en application.properties; no subir contraseñas a GitHub).
3. Abrir la carpeta del proyecto en NetBeans.
4. Ejecutar la clase `VetCare360Application` o `mvn spring-boot:run`.
5. Entrar a `http://localhost:8080/registro` y crear un usuario.
6. Iniciar sesión en `/login`, entrar a `/inicio` y probar `/cambiar-clave`.

## Pruebas manuales
- Registro correcto y persistencia tras reinicio.
- Rechazo de correo duplicado, correo inválido y contraseña menor a 8 caracteres.
- Inicio de sesión correcto y rechazo de credenciales incorrectas.
- Acceso no autenticado a `/inicio` redirige a login.
- Cambio de contraseña correcto; rechazo de contraseña actual incorrecta o confirmación distinta.
- Cierre de sesión y comprobación de que la sesión anterior ya no permite entrar.

## Integración
Los nuevos usuarios se crean con rol CLIENTE. La administración y asignación de roles se integrarán con el módulo de Daniel. No crear cuentas ADMIN desde registro público.

## Observaciones
Es una implementación de desarrollo, sin recuperación de contraseña por correo, verificación de email ni medidas completas de producción.
No se ha ejecutado en el equipo del estudiante: deben comprobarse compilación, MySQL y pruebas funcionales.
