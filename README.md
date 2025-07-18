# Proyecto: Control de hábitos
Este proyecto de práctica fue desarrollado con el fin de brindar una solución tecnologóica a los usuarios quienes deseen tener una mejor gestión de sus hábitos, fue desarrollado con Spring Boot. La solución está estructurada con los principios DRY.

# Estructura del proyecto
Controlhabitos/
- Config/# Autenticador y receptor para evitar ingreso mediante URL (El usuario tiene que estar logueado)
- Controller/# Interacción entre la interfaz del usuario y la lógica del proyecto
- DTO/# Ayuda a un mejor control de la información entre capas
- Model/# Entidades mapeadas en la base de datos
- Repository/# Interfaces para accededer a la base de datos
- Service/# Lógica del proyecto, se procesa las reglas del sistema y el flujo entre repositorios y DTOs

# Base de datos
Ejecutar el archivo ScriptCreateControlHabitosDB.sql
- Para los inserts usados como prueba ejecutar ScriptInsertSQL.sql
