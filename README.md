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

# Proyecto Spring Boot
![image](https://github.com/user-attachments/assets/904f6ca8-1a02-49c0-80ab-33dca2e6a849)

# Aplicación web - Login
![ControlHabitos3](https://github.com/user-attachments/assets/919a85e4-8769-459e-8990-02d2b4bae5b0)

# Aplicación web - Menú
![image](https://github.com/user-attachments/assets/65a68319-3837-44fc-99a0-3949f0808a03)

# Aplicación web - Hábitos
![ControlHabitos1](https://github.com/user-attachments/assets/5b7bf940-51ce-44f1-8f7a-4b3a87e920f4)

# Aplicación web - Historial
![ControlHabitos2](https://github.com/user-attachments/assets/acddf794-9c0c-48ed-988c-20b0cf3cd583)
