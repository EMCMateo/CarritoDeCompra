# 🛒 Práctica de Laboratorio 01: Sistema de Carrito de Compras con Persistencia Avanzada en Java

![img.png](img.png)
![img_1.png](img_1.png)
![img_2.png](img_2.png)
![img_3.png](img_3.png)
![img_4.png](img_4.png)
![img_5.png](img_5.png)
![img_6.png](img_6.png)

---

## 📋 Información General

- **Título:** Práctica de laboratorio 04: Desarrollo de una aplicación para la gestión de archivos y persistencia de datos
- **Carrera:** Computación
- **Asignatura:** Programación Orientada a Objetos
- **Estudiantes:** Mateo Eduardo Molina Chamba
- **Profesor:** Ing. Gabriel León
- **Fecha:** 17/07/2025
- **Repositorio GitHub:** [CarritoDeCompra](https://github.com/EMCMateo/CarritoDeCompra.git)

---

## 🎯 Objetivos de la Práctica

- Aplicar validaciones de datos robustas mediante excepciones propias y de Java.
- Validar correctamente la cédula ecuatoriana y contraseñas según requisitos de seguridad.
- Implementar almacenamiento configurable en memoria, archivos de texto o archivos binarios.
- Generar el ejecutable `.jar` del sistema y la documentación Javadoc mediante Maven.
- Aplicar el patrón MVC en el diseño de sistemas con interfaces gráficas.
- Implementar el patrón DAO para desacoplar la lógica de acceso a datos.
- Aplicar principios SOLID en el diseño orientado a objetos.
- Construir interfaces gráficas avanzadas usando Java Swing (`JDesktopPane`, `JInternalFrame`, `JTable`, `JComboBox`, `JMenu`, `JOptionPane`).
- Implementar internacionalización mediante `ResourceBundle`.
- Formatear datos numéricos y fechas según configuración regional.

---

## 📝 Descripción del Sistema

### 🧩 Sistema de Gestión de Compras ERP con Arquitectura Flexible

La aplicación simula un sistema de carrito de compras para entornos empresariales con control de roles y persistencia flexible.

#### ✅ Funcionalidades Generales

- Registro y autenticación de usuarios con recuperación de contraseña vía preguntas de seguridad.
- Gestión CRUD de productos (artículos, paquetes y servicios).
- Administración de carritos de compra:
    - **Usuarios:** gestionan solo sus carritos.
    - **Administradores:** gestionan todos los carritos y productos.
- Control de acceso y roles (Cliente, Administrador).
- Visualización y manipulación de datos con interfaz gráfica avanzada tipo MDI.
- Soporte multilenguaje: Español, Inglés e Italiano.
- Formateo de fechas y moneda según configuración regional.

#### 🔧 Mejoras Implementadas

- **Validaciones Avanzadas:**
    - Cédula ecuatoriana validada con algoritmo oficial (último dígito).
    - Contraseñas con requisitos de seguridad: mínimo 6 caracteres, una mayúscula, una minúscula y un carácter especial (`@_-`).

- **Persistencia Configurable:**
    - Al iniciar, el usuario elige si desea usar persistencia en:
        - Memoria
        - Archivos de texto (`.txt`)
        - Archivos binarios (`.dat`)
    - Se puede especificar la ruta del archivo de persistencia.

- **Manejo de Excepciones Profesional:**
    - `ValidacionException`: errores de negocio o validaciones (ej. cédula inválida, contraseña débil).
    - `PersistenciaException`: errores de entrada/salida desacoplados de la lógica de negocio.

---

## 🏗️ Arquitectura

### 🔍 Modelo

- `Usuario`, `Producto`, `Carrito`, `PreguntaSeguridad`, `RespuestaSeguridad`, `Rol`.
- Cada modelo encapsula su lógica de validación interna lanzando excepciones si los datos son inválidos.

### 🗃️ DAO (Data Access Object)

- **Interfaces:**
    - `ProductoDAO`
    - `UsuarioDAO`
    - `CarritoDAO`
    - `PreguntaDAO`

- **Implementaciones:**
    - `Memoria`: `UsuarioDAOMemoria` `PreguntaDAOMemoria` `CarritoDAOMemoria` `ProductoDAOMemoria`
    - `Archivo de Texto`: `UsuarioDAOArchivoTexto` `ProductoDAOArchivoTexto` `CarritoDAOArchivoTexto` `PreguntaDAOArchivoTexto`
    - `Archivo Binario`: `UsuarioDAOArchivoBinario` `ProductoDAOArchivoBinario` `CarritoDAOArchivoBinario` `PreguntaDAOArchivoBinario`

### 🎮 Controladores

- Encargados de la lógica de negocio, validación, sesión e internacionalización.
- Manejan excepciones lanzadas desde los modelos o DAOs y muestran mensajes claros al usuario.
- Utilizan `MensajeInternacionalizacionHandler` para traducción de mensajes.

### 🖼️ Vistas

- Interfaz construida con `JInternalFrame` sobre un `JDesktopPane` (diseño MDI).
- Componentes Swing avanzados: `JTable`, `JComboBox`, `JMenu`, `JOptionPane`, íconos personalizados.

---

## ⚙️ Principios SOLID Aplicados

- **SRP (Responsabilidad Única):**
    - Cada clase tiene un propósito claro (ej. `UsuarioController` gestiona usuarios, `UsuarioDAOArchivoTexto` solo persistencia en texto).

- **OCP (Abierto/Cerrado):**
    - El sistema permite agregar nuevas formas de persistencia (como bases de datos) sin modificar código existente.

- **DIP (Inversión de Dependencias):**
    - Los controladores dependen de interfaces, no de implementaciones específicas, facilitando pruebas e intercambiabilidad.

---

## 🚀 Ejecución y Generación de Entregables

El proyecto está gestionado con **Apache Maven**, lo que permite automatizar compilación, documentación y empaquetado.

### 🔧 Requisitos

- Java JDK 17 o superior
- Apache Maven (o usar Maven integrado en IntelliJ o VS Code)

### 1. Clonar el Repositorio

```bash
git clone https://github.com/EMCMateo/CarritoDeCompra.git
cd CarritoDeCompra

```


## ✅ Resultados Obtenidos

- Se desarrolló una aplicación modular, robusta, mantenible y escalable.
- Uso efectivo de patrones de diseño (MVC y DAO).
- Sistema internacionalizado y adaptable a múltiples regiones.
- Persistencia configurable y validaciones avanzadas mejoraron la experiencia de usuario.
- Entregables generados automáticamente (.jar y Javadoc) con Maven.

---

## 💡 Conclusiones

- La correcta aplicación de MVC y DAO genera sistemas limpios y fácilmente ampliables.
- Los principios SOLID son claves para sistemas profesionales y mantenibles.
- El manejo centralizado de excepciones mejora la estabilidad y confiabilidad de la aplicación.
- La internacionalización mejora la usabilidad en entornos multilingües.
- Java Swing continúa siendo una herramienta poderosa en desarrollo de escritorios MDI.

---

## 📌 Recomendaciones

- Migrar a una base de datos relacional como PostgreSQL o MySQL para mayor persistencia.
- Evolucionar la interfaz con JavaFX o tecnologías web.
- Integrar un sistema de logging persistente como Log4j para auditoría y depuración.
