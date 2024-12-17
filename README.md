# Backend Mobile API

Backend para la aplicación móvil de *QUICKNESS*, que proporciona funcionalidades como autenticación, gestión de usuarios y acceso a datos en tiempo real.

## Tecnologías y Stack
- **Lenguaje:** Kotlin
- **Framework:** Ktor
- **Base de datos:** Firebase
- **Autenticación:** JWT (JSON Web Token)
- **API:** RESTful

## Instalación

1. Clona el repositorio:
   ```bash
   git clone https://github.com/Quickness-dev/backend-mobile.git
   ```

2. Accede a la carpeta del proyecto:
   ```bash
   cd repo
   ```

3. Configura las variables de entorno:
   ```bash
   DB_URI=mongodb://localhost:27017/mydb
   JWT_SECRET=mysecret
   ```

### d. **Uso**

Ejemplo:
**POST /auth**
- Autenticación de usuarios.
- Request:
```json
  {
    "token": "jwt_firebase_token"
  }
  ```
- Response:
  ```json
  {
    "message": "Autenticación exitosa",
    "status": "200",
    "data": "jwt_token_generado"
  }
  ```

**POST /register**
- Registro de usuarios.
- Request:
```json
  {
    "email": "example@example.com",
    "password": "password123",
    "name": "John Doe Fulan",
    "phone": "+52 123-456-7890",
    "curp": "CURP1234567890"
  }
  ```
- Response:
  ```json
  {
    "message": "Registro exitoso",
    "status": "200",
    "data": ""
  }
  ```

