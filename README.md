# Mini Gestor de Clientes (Demo Técnica)

Este proyecto es una demostración práctica de dos funcionalidades clave en el desarrollo web moderno: **Autocompletado de Búsqueda optimizado (Frontend)** y **Envío de Correos Asíncrono (Backend)**. 

Está compuesto por un frontend en React (Vite) y un backend en Java (Spring Boot) con una base de datos en memoria (H2).

## 🚀 Funcionalidades Destacadas

1. **Búsqueda Predictiva con Debounce (React):** Evita la saturación del servidor retrasando la petición HTTP hasta que el usuario deja de escribir (300ms).
2. **Envío de Correos No Bloqueante (Spring Boot):** Utiliza la anotación `@Async` para delegar el envío del correo electrónico a un hilo secundario, permitiendo que la interfaz de usuario responda inmediatamente sin esperar la resolución del servidor SMTP.

---

## 📋 Requisitos Previos

* **Java 17** o superior.
* **Node.js** (v18 o superior) y **npm**.
* Una cuenta de Gmail con **Verificación en 2 pasos** activada (para generar la contraseña de aplicación).

---

## ⚙️ Configuración y Ejecución del Backend (Spring Boot)

El backend expone la API REST en el puerto `8081` y gestiona el envío de correos.

### 1. Configurar las variables de entorno (Correo)

Para que el envío de correos funcione, necesitas configurar las credenciales de un servidor SMTP (por defecto, configurado para Gmail).

1. Navega a la carpeta del backend:
   ```bash
   cd presentacion
   ```
2. Renombra el archivo `.env.example` a `.env`:
   ```bash
   mv .env.example .env
   ```
   *(En Windows puedes renombrarlo directamente desde el explorador de archivos o tu editor de código).*
3. Edita el archivo `.env` con tus credenciales:
   ```env
   MAIL_USERNAME=tu_correo@gmail.com
   MAIL_PASSWORD=tu_contraseña_de_aplicacion
   ```
   > **⚠️ Importante sobre Gmail:** No debes usar tu contraseña habitual. Debes ir a la configuración de seguridad de tu cuenta de Google, activar la "Verificación en 2 pasos" y generar una **Contraseña de aplicación** de 16 caracteres (sin espacios).

### 2. Levantar el servidor

Ejecuta el proyecto utilizando el wrapper de Maven incluido:

**En Linux/macOS:**
```bash
./mvnw spring-boot:run
```

**En Windows:**
```cmd
mvnw.cmd spring-boot:run
```

El servidor arrancará en `http://localhost:8081`. La base de datos H2 se inicializará automáticamente con 7 clientes de prueba. Puedes verificar la consola H2 en `http://localhost:8081/h2-console` (JDBC URL: `jdbc:h2:mem:clientdb`, User: `sa`, Password: *vacío*).

---

## 💻 Configuración y Ejecución del Frontend (React)

El frontend consume la API en el puerto 8081 y maneja la interfaz gráfica.

1. Abre una nueva terminal y navega a la carpeta del frontend:
   ```bash
   cd front
   ```
2. Instala las dependencias:
   ```bash
   npm install
   ```
3. Inicia el servidor de desarrollo:
   ```bash
   npm run dev
   ```
4. Abre la URL que indica la consola (generalmente `http://localhost:5173`).

---

## 🧪 Guía para la Demostración Práctica

Para la presentación, sigue este flujo:

1. **Mostrar el Autocompletado (Frontend):**
   * Abre la pestaña *Network* (Red) en las herramientas de desarrollador del navegador.
   * Escribe "mar" rápidamente en la barra de búsqueda.
   * **Explicación:** Muestra a la audiencia cómo, a pesar de haber presionado 3 teclas, solo se realizó **1 petición HTTP** a `/api/clients/search?q=mar`. Esto demuestra el patrón *Debounce* en acción.
2. **Mostrar el Envío Asíncrono (Backend):**
   * Selecciona un cliente de la lista.
   * Completa el asunto y el mensaje y presiona "Enviar Notificación".
   * **Explicación visual (Front):** La interfaz mostrará "Enviando..." y responderá con éxito casi al instante (código HTTP 202 Accepted).
   * **Explicación técnica (Back):** Abre la consola de Spring Boot. Muestra los logs donde se evidencia que un hilo distinto (ej. `task-1`) tomó el trabajo del correo y simuló el retraso de 3 segundos, mientras que el hilo principal (HTTP) quedó liberado inmediatamente para seguir atendiendo a otros usuarios.
