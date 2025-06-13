# Prueba Técnica - Gestión de Clientes y Direcciones

Este proyecto es una API REST desarrollada con Spring Boot que permite gestionar clientes y sus direcciones. Está estructurada con buenas prácticas y enfoque profesional, incluyendo mapeo con MapStruct, arquitectura en capas, control de excepciones y pruebas.

## 🧩 Características principales

- Crear, consultar, actualizar y eliminar clientes.
- Crear, consultar, actualizar y eliminar direcciones asociadas a un cliente.
- Validaciones con Bean Validation (`@Valid`).
- Manejo de excepciones centralizado.
- Pruebas con Postman y pruebas unitarias.
- Arquitectura limpia y uso de clases base reutilizables para reducir la duplicación de código.

## 🛠️ Tecnologías utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Lombok
- MapStruct
- JUnit 5
- Maven
- Postman (colección incluida)

## 🧱 Estructura del proyecto

├── application/
│ ├── controller/
│ ├── dto/
│ ├── mapper/
│ ├── processor/
│ ├── service/
│ └── service/impl/
├── domain/
│ └── model/
├── config/
├── exception/
└── resources/


## ⚙️ Configuración

### 1. Clona el repositorio


git clone https://github.com/tu-usuario/tu-repositorio.git
cd tu-repositorio

2. Configura el application.properties
Asegúrate de que src/main/resources/application.properties tenga tu configuración de base de datos:

spring.datasource.url=jdbc:postgresql://localhost:5432/oriontek
spring.datasource.username=postgres
spring.datasource.password=8294199236
spring.datasource.driver-class-name=org.postgresql.Driver

3. Ejecuta el proyecto
Puedes ejecutar desde tu IDE o desde la terminal:

mvn spring-boot:run

🔌 Endpoints disponibles
Método	Endpoint	Descripción
GET	/api/v1/customers	Listar todos los clientes
GET	/api/v1/customers/{id}	Obtener cliente por ID
POST	/api/v1/customers	Crear cliente
PUT	/api/v1/customers/{id}	Actualizar cliente
DELETE	/api/v1/customers/{id}	Eliminar cliente
POST	/api/v1/addresses/customer/{id}	Crear dirección para un cliente
GET	/api/v1/addresses	Listar direcciones
...	...	(otros disponibles en la colección)

🧪 Pruebas con Postman
Incluye la colección Postman para probar los endpoints fácilmente.

Abre Postman.

Importa el archivo postman_collection.json que está en la raíz del proyecto.

Usa los endpoints organizados por carpeta.
