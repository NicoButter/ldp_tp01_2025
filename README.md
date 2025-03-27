# 🎵 Gestor de Playlists Musicales - UNPA 2025 

![Java](https://img.shields.io/badge/Java-JDK%2017%2B-blue)
![MySQL](https://img.shields.io/badge/DB-MySQL%20%7C%20MariaDB-orange)
![License](https://img.shields.io/badge/License-MIT-green)

Este proyecto corresponde al **Ejercicio 1 del Trabajo Práctico 1** de la asignatura *Laboratorio de Programación - 2025* de la Universidad Nacional de la Patagonia Austral (UNPA-UARG).  
Es una aplicación en **Java** que gestiona playlists de música, según mi caso de estudio, utilizando **MySQL** o **MariaDB** como base de datos y el conector **JDBC** para la interacción.

## 📌 Funcionalidades

✔️ CRUD de **Playlists**: Agregar, listar, editar y eliminar playlists.  
✔️ CRUD de **Géneros**: Gestionar géneros musicales.  
✔️ **Consultas avanzadas**: Filtrar, ordenar y obtener estadísticas.  
✔️ Uso de **sentencias preparadas** y **funciones SQL** para mejorar seguridad y eficiencia.  

## 📋 Requisitos previos

- **Java**: JDK 8 o superior (recomendado JDK 17).
- **Base de datos**: MySQL o MariaDB instalado.
- **JDBC Driver**: [Descargar desde MySQL](https://dev.mysql.com/downloads/connector/j/).
- **IDE/TextEditor recomendado**: Eclipse o Visual Studio Code.
- **SO compatible**: Windows y Linux.

## 🔧 Instalación

### 🔹 Instalación del driver JDBC

#### En **Linux** (MariaDB por defecto)
En la mayoría de las distribuciones Linux, MariaDB viene preinstalado o está disponible en los repositorios oficiales.

```bash
# Descargar y extraer el conector
wget https://downloads.mariadb.com/Connectors/java/connector-java-2.7.6.tar.gz

tar -xvzf connector-java-2.7.6.tar.gz

# Mover el .jar a la carpeta del proyecto
mkdir -p lib
cp mariadb-java-client-2.7.6.jar lib/

# Configurar el classpath (si ejecutás desde terminal)
export CLASSPATH=$CLASSPATH:/ruta/a/tu/proyecto/lib/mariadb-java-client-2.7.6.jar
```

#### En **Windows**
1. Descarga el `.zip` desde [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/).  
2. Extrae el archivo (`mysql-connector-j-8.0.33.jar`) y muévelo a la carpeta `lib\` de tu proyecto.  
3. Configura el classpath en CMD:  
   ```batch
   set CLASSPATH=%CLASSPATH%;C:\ruta\a\tu\proyecto\lib\mysql-connector-j-8.0.33.jar
   ```

### 🔹 Configuración en Eclipse y VS Code

#### **Eclipse**
1. Clic derecho en el proyecto → **Properties** → **Java Build Path**.  
2. Ir a **Libraries** → **Add External JARs...** → Selecciona `mysql-connector-j-8.0.33.jar`.  
3. Asegúrate de que aparezca en **Referenced Libraries**.

#### **VS Code**
1. Instala las extensiones **Java Extension Pack** y **Debugger for Java**.  
2. En el archivo `.vscode/settings.json`, agrega:
   ```json
   {
       "java.project.referencedLibraries": ["lib/mysql-connector-j-8.0.33.jar"]
   }
   ```

## 🛠 Configuración de la base de datos

### **1️⃣ Crear la base de datos y tablas**

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

### **2️⃣ Configurar la conexión en Java**

Crea o edita la clase `DatabaseConfig.java`:

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

- Este proyecto forma parte del **TP1 de Laboratorio de Programación 2025** (UNPA).  
- Compatible con **MySQL** y **MariaDB** (tenés que ajusta el driver si usas otra base de datos).  
- Asegúrate de tener el puerto **3306** abierto si usas una BD remota.  

📧 **Contacto**: [nicobutter@gmail.com](mailto:nicobutter@gmail.com)  

🚀 ¡Gracias por revisar este proyecto! Si tenés dudas, preguntame. 
