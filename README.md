# 🏡 El Ceibo - Backend

Este es el backend del sistema de gestión de **El Ceibo**, una aplicación web desarrollada en **Java con Spring Boot** para la administración de socios y control de pagos. Proporciona una API REST para gestionar usuarios, socios y cuotas, integrándose con una base de datos **MySQL**.

## 🚀 Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **Spring Security**
- **JWT (JSON Web Token)**
- **MySQL**
- **Maven**

## 📂 Estructura del Proyecto

El proyecto sigue una estructura estándar de Spring Boot:

```
el-ceibo-backEnd/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── imperialnet/
│   │   │           └── el_ceibo/
│   │   │               ├── auth/             # Autenticación y manejo de usuarios
│   │   │               ├── configuration/     # Configuraciones generales del proyecto
│   │   │               ├── controller/       # Controladores REST
│   │   │               ├── dto/             # Objetos de transferencia de datos
│   │   │               ├── entity/          # Entidades JPA
│   │   │               ├── exception/       # Manejo de excepciones personalizadas
│   │   │               ├── repository/      # Interfaces de repositorio JPA
│   │   │               ├── security/        # Configuraciones de seguridad
│   │   │               ├── service/         # Servicios de negocio
│   │   │               └── ElCeiboApplication.java  # Clase principal
│   │   └── resources/
│   │       ├── application.properties    # Configuración de la aplicación
│   │       └── data.sql                  # Datos iniciales (si aplica)
│   └── test/
│       └── java/
│           └── com/
│               └── imperialnet/
│                   └── el_ceibo/
│                       └── ElCeiboApplicationTests.java  # Pruebas unitarias
└── pom.xml                               # Archivo de configuración de Maven
```

## ⚙️ Configuración y Ejecución

### Prerrequisitos

- **Java 17**: Asegúrate de tener instalado JDK 17.
- **Maven**: Gestor de dependencias para compilar y gestionar el proyecto.
- **MySQL**: Aunque Spring Boot puede crear automáticamente la base de datos, es necesario tener un servidor MySQL en ejecución.

### Instalación

1. **Clonar el repositorio**:

   ```bash
   git clone https://github.com/MichaelIllescas/el-ceibo-backEnd.git
   cd el-ceibo-backEnd
   ```

2. **Configurar la base de datos**:

   Spring Boot está configurado para crear automáticamente la base de datos al iniciar la aplicación. Asegúrate de que el servidor MySQL esté en ejecución y que las credenciales en `application.properties` sean correctas.

3. **Compilar y ejecutar la aplicación**:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

   La aplicación estará disponible en `http://localhost:8080`.

## 🔑 Seguridad

La aplicación utiliza **JWT (JSON Web Token)** para la autenticación y gestión de usuarios. La configuración de seguridad se encuentra en la carpeta `security` dentro del código fuente. Para acceder a los endpoints protegidos, es necesario autenticar y proporcionar un token válido en las peticiones.


## ✨ Autor

- **Jonathan** - [GitHub](https://github.com/MichaelIllescas)

📞 Contacto
Si tienes preguntas o sugerencias, puedes contactarme en: ✉️ Email: joni.illes@hotmail.com 🐙 GitHub: MichaelIllescas 🚀 ¡Gracias por visitar Ceibo! ⚽💙

Nos encontramos en proceso de desarrollo y mejoras de constantemente!
