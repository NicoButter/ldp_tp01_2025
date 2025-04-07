# 🎵 Gestor de Playlists Musicales - UNPA 2025 

![Java](https://img.shields.io/badge/Java-JDK%2017%2B-blue)
![MySQL](https://img.shields.io/badge/DB-MySQL%20%7C%20MariaDB-orange)
![License](https://img.shields.io/badge/License-MIT-green)

Este proyecto corresponde al **Ejercicio 1 del Trabajo Práctico 1** de la asignatura *Laboratorio de Programación - 2025* de la Universidad Nacional de la Patagonia Austral (UNPA-UARG).  
Es una aplicación en **Java** que gestiona playlists de música, según mi caso de estudio, utilizando **MySQL** o **MariaDB**, (yo usé MariaDB) como base de datos y el conector **JDBC** para la interacción.

## 📌 Tabla de Contenidos  
- [Funcionalidades](#-funcionalidades)  
- [Requisitos](#%EF%B8%8F-requisitos)  
- [Instalación](#-instalación)  
  - [Driver JDBC](#driver-jdbc)  
  - [IDE](#-configuración-en-ide)  
- [Base de Datos](#-base-de-datos)  
  - [Estructura](#1-estructura-de-la-base-de-datos)  
  - [Conexión](#2-configuración-en-java)  
- [Ejecución](#-ejecución)  
- [Contacto](#-contacto)  

---

## 📌 Funcionalidades

✔️ CRUD de **Playlists**: Agregar, listar, editar y eliminar playlists.  
✔️ CRUD de **Géneros**: Gestionar géneros musicales.  
✔️ **Consultas avanzadas**: Filtrar, ordenar y obtener estadísticas.  
✔️ Uso de **sentencias preparadas** y **funciones SQL** para mejorar seguridad y eficiencia.  

## ⚙️ Requisitos  
- **Java**: JDK 8+ (recomendado JDK 17).  
- **Base de datos**: MySQL o MariaDB.  
- **Driver JDBC**: [Descargar Connector/J](https://dev.mysql.com/downloads/connector/j/).  
- **IDE**: Eclipse, VS Code (con extensiones Java).  
- **SO**: Windows o Linux, openSUSE en lo posible :)

---

## 🔧 Instalación  

### Driver JDBC  
#### Linux (MariaDB)  

```bash
wget https://downloads.mariadb.com/Connectors/java/connector-java-2.7.6.tar.gz
tar -xvzf connector-java-2.7.6.tar.gz
mkdir -p lib
cp mariadb-java-client-2.7.6.jar lib/
export CLASSPATH=$CLASSPATH:/ruta/a/tu/proyecto/lib/mariadb-java-client-2.7.6.jar
```

#### En **Windows**(MySQL)

1. Descargate el `.zip` desde [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/).  
2. Extrae el archivo (`mysql-connector-j-8.0.33.jar`) y movelo a la carpeta `lib\` de tu proyecto.  
3. Configurá el classpath en CMD:  
   ```batch
   set CLASSPATH=%CLASSPATH%;C:\ruta\a\tu\proyecto\lib\mysql-connector-j-8.0.33.jar
   ```

## 🖥️ Configuración en IDE

### 🔹 Configuración en Eclipse y VS Code -> NO ES UN IDE :|

#### **Eclipse**
1. Clic derecho en el proyecto → **Properties** → **Java Build Path**.  
2. Ir a **Libraries** → **Add External JARs...** → Selecciona `mysql-connector-j-8.0.33.jar`.  
3. Asegurate de que aparezca en **Referenced Libraries**.

#### **VS Code**
1. Instalá las extensiones **Java Extension Pack** y **Debugger for Java**.  
2. En el archivo `.vscode/settings.json`, agregá:
   ```json
   {
       "java.project.referencedLibraries": ["lib/mysql-connector-j-8.0.33.jar"]
   }
   ```

## 🗃️ Base de Datos

## 🛠 Configuración de la base de datos

### **1️⃣ Create la base de datos y tablas**

### 1️⃣ Estructura


```sql
CREATE DATABASE spotify_db;
USE spotify_db;

CREATE TABLE generos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE playlist (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    interprete VARCHAR(100) NOT NULL,
    cantidad_temas INT NOT NULL,
    duracion_total DOUBLE NOT NULL,
    id_genero INT,
    FOREIGN KEY (id_genero) REFERENCES generos(id)
);
```

### **2️⃣ Tenés que configurar la conexión en Java**

Create o editá la clase `DatabaseConfig.java`:

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String URL = "jdbc:mysql://localhost:3306/spotify_db";
    private static final String USER = "tu_usuario"; // Cambia esto
    private static final String PASSWORD = "tu_contraseña"; // Cambia esto

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
```

## ▶️ Ejecución

### Desde **terminal**:
```bash
javac *.java
java Main
```

### Desde **Eclipse/VS Code**:
Ejecuta la clase `Main.java` como aplicación Java.

## 📢 Notas adicionales

- 📅 Proyecto académico para UNPA-UARG (2025).
- Este proyecto forma parte del **TP1 de Laboratorio de Programación 2025** (UNPA).  
- Compatible con **MySQL** y **MariaDB** (tenés que ajusta el driver si usas otra base de datos).  
- Asegurate tener el puerto **3306** abierto si usás una BD remota.  

### ¿Dudas o sugerencias? ¡Escribime!

✉️ Email: nicobutter@gmail.com
🚀 ¡Gracias por tu interés!
