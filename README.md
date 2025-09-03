# Task Management API (To-Do List)

Una API REST desarrollada con Spring Boot para la gestión de tareas (To-Do List).

## Características

- ✅ Crear tareas (POST /tasks)
- ✅ Listar todas las tareas (GET /tasks)
- ✅ Obtener tarea por ID (GET /tasks/{id})
- ✅ Actualizar tarea (PUT /tasks/{id})
- ✅ Eliminar tarea (DELETE /tasks/{id})
- ✅ Documentación automática con Swagger/OpenAPI
- ✅ Base de datos H2 en memoria
- ✅ Validación de datos
- ✅ Tests unitarios
- ✅ Dockerizado

## Estructura de una Tarea

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440001",
  "title": "Título de la tarea",
  "description": "Descripción opcional",
  "dueDate": "2024-01-15T10:00:00",
  "status": "pending",
  "createdAt": "2024-01-01T09:00:00",
  "updatedAt": "2024-01-01T09:00:00"
}
```

### Estados de Tarea
- `pending`: Pendiente
- `in-progress`: En progreso
- `completed`: Completada

## Ejecución con Docker

### Prerrequisitos
- Docker instalado
- Docker Compose (opcional)

### Pasos

1. **Clonar el repositorio**
   ```bash
   git clone <repository-url>
   cd todo-api
   ```

2. **Construir la imagen Docker**
   ```bash
   docker build -t todo-api .
   ```

3. **Ejecutar el contenedor**
   ```bash
   docker run -p 8080:8080 todo-api
   ```

4. **Acceder a la aplicación**
   - API: http://localhost:8080
   - Swagger UI: http://localhost:8080/swagger-ui.html
   - H2 Console: http://localhost:8080/h2-console

## Ejecución Local

### Prerrequisitos
- Java 17+
- Maven 3.6+

### Pasos

1. **Clonar el repositorio**
   ```bash
   git clone <repository-url>
   cd todo-api
   ```

2. **Ejecutar la aplicación**
   ```bash
   ./mvnw spring-boot:run
   ```

3. **Acceder a la aplicación**
   - API: http://localhost:8080
   - Swagger UI: http://localhost:8080/swagger-ui.html
   - H2 Console: http://localhost:8080/h2-console

## Ejemplos de Uso

### Crear una tarea
```bash
curl -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Completar documentación",
    "description": "Escribir documentación completa del proyecto",
    "dueDate": "2024-01-15T10:00:00",
    "status": "pending"
  }'
```

### Obtener todas las tareas
```bash
curl -X GET http://localhost:8080/tasks
```

### Obtener una tarea por ID
```bash
curl -X GET http://localhost:8080/tasks/550e8400-e29b-41d4-a716-446655440001
```

### Actualizar una tarea
```bash
curl -X PUT http://localhost:8080/tasks/550e8400-e29b-41d4-a716-446655440001 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Completar documentación",
    "description": "Escribir documentación completa del proyecto",
    "dueDate": "2024-01-15T10:00:00",
    "status": "in-progress"
  }'
```

### Eliminar una tarea
```bash
curl -X DELETE http://localhost:8080/tasks/550e8400-e29b-41d4-a716-446655440001
```

## Testing

Ejecutar los tests:
```bash
./mvnw test
```

## Arquitectura

El proyecto sigue principios SOLID y buenas prácticas:

- **Single Responsibility**: Cada clase tiene una responsabilidad específica
- **Open/Closed**: Extensible sin modificar código existente
- **Liskov Substitution**: Interfaces bien definidas
- **Interface Segregation**: Interfaces específicas y cohesivas
- **Dependency Inversion**: Dependencias inyectadas

### Estructura del Proyecto
```
src/
├── main/
│   ├── java/com/example/todoapi/
│   │   ├── config/          # Configuraciones
│   │   ├── controller/      # Controladores REST
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── entity/         # Entidades JPA
│   │   ├── repository/     # Repositorios de datos
│   │   ├── service/        # Lógica de negocio
│   │   └── TodoApiApplication.java
│   └── resources/
│       ├── application.yml # Configuración de la aplicación
│       └── data.sql       # Datos de prueba
└── test/
    └── java/com/example/todoapi/
        └── controller/     # Tests unitarios
```

## Retos de Aprendizaje Rápido

### ¿Qué pasos tomé para aprender Swagger si no lo habías usado antes?
- Investigué la documentación oficial de SpringDoc OpenAPI
- Aprendí sobre las anotaciones `@Operation`, `@ApiResponse`, `@Tag`
- Configuré la documentación automática con `OpenApiConfig`
- Practiqué con ejemplos reales en los controladores

### ¿Cómo resolví dudas sobre Docker?
- Consulté la documentación oficial de Docker
- Aprendí sobre multi-stage builds para optimizar el tamaño de la imagen
- Entendí la importancia de copiar primero el `pom.xml` para aprovechar el cache de Docker
- Configuré correctamente el `WORKDIR` y los `EXPOSE` ports

### ¿Qué desafíos encontré?
- **Configuración de H2**: Tuve que ajustar la configuración para que funcione correctamente con JPA
- **Validación de UUIDs**: Implementé validación personalizada para los IDs
- **Manejo de fechas**: Configuré el formato ISO8601 para las fechas
- **Tests de integración**: Aprendí a usar `@WebMvcTest` para tests de controladores

### ¿Qué mejoraría si tuviera más tiempo?
- **Seguridad**: Implementaría autenticación JWT básica
- **Paginación**: Agregaría paginación al endpoint de listar tareas
- **Filtros**: Implementaría filtros por estado y fecha
- **Logging**: Agregaría logging estructurado con Logback
- **Métricas**: Implementaría métricas con Micrometer
- **Cache**: Agregaría cache con Redis para mejorar performance
- **CI/CD**: Configuraría GitHub Actions para automatizar tests y deployment

## Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **H2 Database**
- **SpringDoc OpenAPI 3**
- **Maven**
- **Docker**
- **JUnit 5**
- **Mockito**

