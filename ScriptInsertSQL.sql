use ControlHabitosDB

INSERT INTO Usuario (nombre, correo, contraseña, rol, verificado, token_verificacion) VALUES 
('Juan Pérez', 'juan.perez@email.com', 'ContraseñaSegura123', 'usuario', 1, NEWID()),
('María García', 'maria.garcia@email.com', 'MariaG2024!', 'usuario', 1, NEWID()),
('Carlos López', 'carlos.lopez@email.com', 'CLopez#456', 'usuario', 1, NEWID()),
('Ana Martínez', 'ana.martinez@email.com', 'AnaM789$', 'usuario', 1, NEWID()),
('Pedro Sánchez', 'pedro.sanchez@email.com', 'P$nchez2024', 'usuario', 1, NEWID()),
('Prueba', 'prueba123@gmail.com', '123', 'usuario', 1, NEWID()),
('Admin', 'admin@gmail.com', 'admin', 'administrador', 1, NEWID());

INSERT INTO Categoria_Habito (nombre_categoria) VALUES 
('Salud y Bienestar'),
('Productividad'),
('Desarrollo Personal'),
('Finanzas'),
('Relaciones'),
('Ocio y Creatividad');

INSERT INTO Habito (id_usuario, nombre_habito, descripcion, frecuencia, prioridad, recordatorio, fecha_inicio) VALUES 
(1, 'Ejercicio matutino', '30 minutos de ejercicio al despertar', 'diaria', 'alta', '07:00:00', '2024-01-15'),
(1, 'Lectura nocturna', 'Leer 20 páginas antes de dormir', 'diaria', 'media', '21:30:00', '2024-01-20'),
(2, 'Meditación', 'Meditar 10 minutos por la mañana', 'diaria', 'alta', '08:00:00', '2024-02-01'),
(3, 'Registro de gastos', 'Anotar todos los gastos del día', 'diaria', 'media', '20:00:00', '2024-01-10'),
(4, 'Aprendizaje de idiomas', 'Practicar inglés 15 minutos', 'diaria', 'alta', '18:00:00', '2024-02-05'),
(5, 'Revisión semanal', 'Planificar la semana cada domingo', 'semanal', 'alta', '10:00:00', '2024-01-07'),
(2, 'Llamar a familiares', 'Contactar con familiares lejanos', 'semanal', 'baja', '19:00:00', '2024-02-10'),
(3, 'Escritura creativa', 'Escribir 500 palabras', 'personalizada', 'media', NULL, '2024-01-25');

-- Para hábito 1 (Ejercicio matutino)
INSERT INTO Registro_Habito (id_habito, fecha, completado, nota_opcional) VALUES 
(1, DATEADD(day, -1, GETDATE()), 1, 'Buen entrenamiento'),
(1, DATEADD(day, -2, GETDATE()), 1, NULL),
(1, DATEADD(day, -3, GETDATE()), 0, 'Me sentí cansado'),
(1, DATEADD(day, -4, GETDATE()), 1, NULL),
(1, DATEADD(day, -5, GETDATE()), 1, 'Entrenamiento intenso');

-- Para hábito 2 (Lectura nocturna)
INSERT INTO Registro_Habito (id_habito, fecha, completado, nota_opcional) VALUES 
(2, DATEADD(day, -1, GETDATE()), 1, 'Libro interesante'),
(2, DATEADD(day, -2, GETDATE()), 1, NULL),
(2, DATEADD(day, -3, GETDATE()), 0, 'Me quedé dormido'),
(2, DATEADD(day, -4, GETDATE()), 1, '20 páginas completas'),
(2, DATEADD(day, -5, GETDATE()), 1, NULL);

-- Para hábito 3 (Meditación)
INSERT INTO Registro_Habito (id_habito, fecha, completado, nota_opcional) VALUES 
(3, DATEADD(day, -1, GETDATE()), 1, 'Sesión tranquila'),
(3, DATEADD(day, -2, GETDATE()), 1, NULL),
(3, DATEADD(day, -3, GETDATE()), 1, 'Usé aplicación guiada'),
(3, DATEADD(day, -4, GETDATE()), 0, 'Olvidé hacerlo'),
(3, DATEADD(day, -5, GETDATE()), 1, NULL);

INSERT INTO Habito_Categoria (id_habito, id_categoria) VALUES 
(1, 1), -- Ejercicio -> Salud
(2, 3), -- Lectura -> Desarrollo Personal
(3, 1), -- Meditación -> Salud
(3, 3), -- Meditación -> Desarrollo Personal
(4, 4), -- Gastos -> Finanzas
(5, 3), -- Idiomas -> Desarrollo Personal
(6, 2), -- Revisión -> Productividad
(7, 5), -- Familiares -> Relaciones
(8, 6); -- Escritura -> Ocio