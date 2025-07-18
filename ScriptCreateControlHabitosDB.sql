-- Crear base de datos
CREATE DATABASE ControlHabitosDB;
GO

USE ControlHabitosDB;
GO

-- Tabla Usuario
CREATE TABLE Usuario (
    id_usuario INT IDENTITY(1,1) PRIMARY KEY,
    nombre NVARCHAR(100) NOT NULL,
    correo NVARCHAR(100) NOT NULL UNIQUE,
    contraseña NVARCHAR(255) NOT NULL,
	rol NVARCHAR(20) CHECK (rol IN ('administrador', 'usuario')),
    fecha_creacion DATETIME DEFAULT GETDATE(),
	verificado BIT DEFAULT 0 NOT NULL,
	token_verificacion VARCHAR(255)
);
GO

-- Tabla Categoria_Habito
CREATE TABLE Categoria_Habito (
    id_categoria INT IDENTITY(1,1) PRIMARY KEY,
    nombre_categoria NVARCHAR(100) NOT NULL
);
GO

-- Tabla Habito
CREATE TABLE Habito (
    id_habito INT IDENTITY(1,1) PRIMARY KEY,
    id_usuario INT NOT NULL,
    nombre_habito NVARCHAR(100) NOT NULL,
    descripcion NVARCHAR(500),
    frecuencia NVARCHAR(50) CHECK (frecuencia IN ('diaria', 'semanal', 'personalizada')),
    prioridad NVARCHAR(20) CHECK (prioridad IN ('alta', 'media', 'baja')),
    recordatorio TIME NULL,
    activo BIT DEFAULT 1,
    fecha_inicio DATE NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);
GO

-- Tabla Registro_Habito
CREATE TABLE Registro_Habito (
    id_registro INT IDENTITY(1,1) PRIMARY KEY,
    id_habito INT NOT NULL,
    fecha DATE NOT NULL,
    completado BIT NOT NULL,
    nota_opcional NVARCHAR(500),
    FOREIGN KEY (id_habito) REFERENCES Habito(id_habito)
);
GO

-- Tabla intermedia
CREATE TABLE Habito_Categoria (
    id_habito INT NOT NULL,
    id_categoria INT NOT NULL,
    PRIMARY KEY (id_habito, id_categoria),
    FOREIGN KEY (id_habito) REFERENCES Habito(id_habito),
    FOREIGN KEY (id_categoria) REFERENCES Categoria_Habito(id_categoria)
);
GO
CREATE TABLE Historial_Habito (
    id_historial INT IDENTITY(1,1) PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_habito INT NOT NULL,
    fecha_completado DATETIME NOT NULL DEFAULT GETDATE(),
    nota NVARCHAR(500),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),
    FOREIGN KEY (id_habito) REFERENCES Habito(id_habito)
);
GO
CREATE TABLE auditoria_habito (
    id_auditoria INT IDENTITY(1,1) PRIMARY KEY,
    id_habito INT NOT NULL,
    id_usuario INT NOT NULL,
    tipo_accion VARCHAR(50) NOT NULL,
    fecha_accion DATETIME NOT NULL,
    CONSTRAINT fk_habito FOREIGN KEY (id_habito) REFERENCES habito(id_habito),
    CONSTRAINT fk_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);
GO
