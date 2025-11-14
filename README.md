# Sistema de Ejecución con Patrón Proxy y Factory
## 📋 Descripción
Implementación de un sistema de ejecución de procesos que utiliza el patrón Proxy para controlar el acceso y auditoría, combinado con un Factory Method que permite seleccionar dinámicamente entre diferentes tipos de proxies mediante parámetros.

## 🎯 Objetivo
Permitir que una aplicación ejecute procesos con diferentes niveles de seguridad y auditoría, manteniendo el control de acceso y registro de actividades sin modificar el código del cliente.

## 🏗️ Patrones Implementados
### 🛡️ Patrón Proxy
Interfaz: InterfaceProcesos

Proxy Auditor: ProxyProcesos (autenticación + auditoría)

Proxy Simple: ProxyProcesosSinAuditoria (solo autenticación)

Objeto Real: ProcesoDefecto

### 🏭 Factory Method
Fábrica: FabricaServicios

Selección: Parámetro para elegir tipo de proxy

## 📁 Estructura del Proyecto
```
proxy-pattern/
├── src/main/java/
│   ├── patronproxy/
│   │   └── PatronProxyMain.java          # Clase principal Spring Boot
│   ├── implementacion/
│   │   ├── InterfaceProcesos.java        # Interfaz común procesos
│   │   ├── ProcesoDefecto.java           # Objeto real
│   │   ├── ProxyProcesos.java            # Proxy con auditoría
│   │   ├── ProxyProcesosSinAuditoria.java # Proxy sin auditoría
│   │   └── FabricaServicios.java         # Factory de proxies
│   ├── servicios/
│   │   ├── Seguridad.java                # Autenticación con PostgreSQL
│   │   └── Auditoria.java                # Registro de auditoría
│   ├── entidades/
│   │   └── Usuario.java                  # Entidad JPA
│   ├── repositorios/
│   │   └── UsuarioRepository.java        # Repository Spring Data
│   └── config/
│       ├── DatabaseConfig.java           # Configuración BD
│       └── DatabaseConnectionTest.java   # Prueba conexión
├── src/main/resources/
│   └── application.properties            # Configuración Spring
├── pom.xml                              # Dependencias Maven
├── Dockerfile                           # Contenedor Docker
└── .env                                 # Variables entorno Supabase
```

## ⚙️ Configuración
### 🔧 Variables de Entorno (.env)
SERVER_PORT=8080
DB_URL=jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:6543/postgres?sslmode=require
DB_USER=postgres.adejrftvkjjxcnjjangm
DB_PASSWORD=igj6gBlY8RiwZAK8

## 🐳 Ejecución con Docker
docker build -t proxy .

docker run --env-file .env -p 8080:8080 proxy

## 🎮 Uso
El sistema automáticamente ejecuta pruebas que demuestran:

## Proxy con Auditoría
```
InterfaceProcesos proceso = fabrica.CrearEjecucionProceso("AUDITABLE");
proceso.EjecutarProcesos(1, "fbolano", "pds");
// Resultado: Autentica + Ejecuta + Audita
```

Proxy sin Auditoría
```
InterfaceProcesos proceso = fabrica.CrearEjecucionProceso("SIN_AUDITORIA");
proceso.EjecutarProcesos(1, "admin", "admin123");
// Resultado: Solo autentica + ejecuta
```

# 🔄 Cambio de Comportamiento
Para cambiar el tipo de proxy, simplemente modifica el parámetro en el Factory:
```
// Proxy con autenticación y auditoría
fabrica.CrearEjecucionProceso(FabricaServicios.PROXY_AUDITABLE);

// Proxy solo con autenticación  
fabrica.CrearEjecucionProceso(FabricaServicios.PROXY_SIN_AUDITORIA);
```

🔐 Flujo de Ejecución
1. Cliente solicita proceso al Factory

2. Factory devuelve proxy según parámetro

3. Proxy autentica usuario contra PostgreSQL

4. Proxy Auditor registra ejecución

5. Objeto Real ejecuta el proceso

6. Cliente recibe resultado sin conocer detalles de seguridad/auditoría

