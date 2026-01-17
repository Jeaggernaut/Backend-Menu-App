# Backend Menu App

Una aplicación backend desarrollada con **Spring Boot** para gestionar autenticación de usuarios y administración de materias.

## Características

- **Autenticación de usuarios** - Registro e inicio de sesión
- **Gestión de materias** - Crear, leer, actualizar y eliminar materias
- **Seguridad** - Configuración de seguridad implementada
- **API REST** - Endpoints bien estructurados

## Tecnologías

- Java 21
- Spring Boot
- Maven





### Clonar o descargar el proyecto
```bash
 git clone https://github.com/Jeaggernaut/Backend-Menu-App.git
```


## Endpoints Principales

### Autenticación
- `POST /api/auth/register` - Registro de nuevo usuario
- `POST /api/auth/login` - Inicio de sesión

### Materias
- `GET /api/materias` - Obtener todas las materias
- `POST /api/materias` - Crear nueva materia
- `PUT /api/materias/{id}` - Actualizar materia
- `DELETE /api/materias/{id}` - Eliminar materia

