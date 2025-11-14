# Sistema de Ejecución con Patrón Proxy y Factory

## 📋 Descripción
Implementación de un sistema de ejecución de procesos que utiliza el patrón Proxy para controlar el acceso y auditoría, combinado con un Factory Method que permite seleccionar dinámicamente entre diferentes tipos de proxies mediante parámetros.

## 🎯 Objetivo
Permitir que una aplicación ejecute procesos con diferentes niveles de seguridad y auditoría, manteniendo el control de acceso y registro de actividades sin modificar el código del cliente.

## 🏗️ Patrones Implementados

### 🛡️ Patrón Proxy
**Interfaz:** InterfaceProcesos

**Proxy Auditor:** ProxyProcesos (autenticación + auditoría)

**Proxy Simple:** ProxyProcesosSinAuditoria (solo autenticación)

**Objeto Real:** ProcesoDefecto

### 🏭 Factory Method
**Fábrica:** FabricaServicios

**Selección:** Parámetro para elegir tipo de proxy

## 📁 Estructura del Proyecto
```
proxy/
├── src/main/java/co/edu/javeriana/
│   ├── implementacion/
│   │   ├── InterfaceProcesos.java        # Interfaz común procesos
│   │   ├── ProcesoDefecto.java           # Objeto real
│   │   ├── ProxyProcesos.java            # Proxy con auditoría
│   │   ├── ProxyProcesosSinAuditoria.java # Proxy sin auditoría
│   │   └── FabricaServicios.java         # Factory de proxies
│   ├── service/
│   │   ├── Seguridad.java                # Autenticación con PostgreSQL
│   │   └── Auditoria.java                # Registro de auditoría
│   ├── model/
│   │   └── Usuario.java                  # Entidad JPA
│   ├── repository/
│   │   └── UsuarioRepository.java        # Repository Spring Data
|   └── ProxyApplication.java             # Clase principal Spring Boot
├── src/main/resources/
│   └── application.properties            # Configuración Spring
├── pom.xml                               # Dependencias Maven
├── dockerfile                            # Contenedor Docker
└── .env                                  # Variables entorno Supabase
```

## ⚙️ Configuración

### 🔧 Variables de Entorno (.env)
En el archivo .env reemplazar las credenciales por las generadas al crear una base de datos PostgreSQL de Supabase.

## 🗄️ Configuración Base de Datos
Ejecutar en SQL Editor de Supabase:
```
-- Limpiar tablas existentes si las hay
DROP TABLE IF EXISTS usuarios CASCADE;

CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    activo BOOLEAN DEFAULT true,
    fecha_creacion TIMESTAMP DEFAULT NOW()
);

-- Insertar usuarios de prueba
INSERT INTO usuarios (username, password) VALUES 
('fbolano', 'pds'),
('admin', 'admin123'),
('usuario1', 'password1');
```

## 🐳 Ejecución con Docker
```
docker build -t proxy .

docker run --env-file .env -p 8080:8080 proxy
```

## 🎮 Uso
El sistema automáticamente ejecuta pruebas que demuestran:

**Proxy con Auditoría:**
```
InterfaceProcesos proceso = fabrica.CrearEjecucionProceso("AUDITABLE");
proceso.EjecutarProcesos(1, "fbolano", "pds");
// Resultado: Autentica + Ejecuta + Audita
```

**Proxy sin Auditoría:**
```
InterfaceProcesos proceso = fabrica.CrearEjecucionProceso("SIN_AUDITORIA");
proceso.EjecutarProcesos(1, "admin", "admin123");
// Resultado: Solo autentica + ejecuta
```

## 🔄 Cambio de Comportamiento
Para cambiar el tipo de proxy, simplemente modifica el parámetro en el Factory:
```
// Proxy con autenticación y auditoría
fabrica.CrearEjecucionProceso(FabricaServicios.PROXY_AUDITABLE);

// Proxy solo con autenticación  
fabrica.CrearEjecucionProceso(FabricaServicios.PROXY_SIN_AUDITORIA);
```

## 🔐 Flujo de Ejecución
1. Cliente solicita proceso al Factory

2. Factory devuelve proxy según parámetro

3. Proxy autentica usuario contra PostgreSQL

4. Proxy Auditor registra ejecución

5. Objeto Real ejecuta el proceso

6. Cliente recibe resultado sin conocer detalles de seguridad/auditoría

