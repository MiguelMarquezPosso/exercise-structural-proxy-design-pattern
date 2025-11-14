package co.edu.javeriana;

import co.edu.javeriana.implementacion.FabricaServicios;
import co.edu.javeriana.implementacion.InterfaceProcesos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ProxyApplication implements CommandLineRunner {

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(ProxyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        FabricaServicios fabrica = applicationContext.getBean(FabricaServicios.class);

        // Probar proxy con auditoría
        System.out.println("=== Probando Proxy con Auditoría ===");
        probarProxy(fabrica, FabricaServicios.PROXY_AUDITABLE, "fbolano", "pds");

        // Probar proxy sin auditoría
        System.out.println("\n=== Probando Proxy sin Auditoría ===");
        probarProxy(fabrica, FabricaServicios.PROXY_SIN_AUDITORIA, "admin", "admin123");

        // Probar con credenciales incorrectas
        System.out.println("\n=== Probando con credenciales incorrectas ===");
        probarProxy(fabrica, FabricaServicios.PROXY_AUDITABLE, "usuario", "password_incorrecto");
    }

    private void probarProxy(FabricaServicios fabrica, String tipoProxy, String usuario, String password) {
        int proceso = 1;
        InterfaceProcesos procesoActivo = fabrica.CrearEjecucionProceso(tipoProxy);
        try {
            procesoActivo.EjecutarProcesos(proceso, usuario, password);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
