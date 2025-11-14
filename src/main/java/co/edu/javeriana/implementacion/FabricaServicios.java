package co.edu.javeriana.implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FabricaServicios {

    @Autowired
    private ApplicationContext applicationContext;

    public static final String PROXY_AUDITABLE = "AUDITABLE";
    public static final String PROXY_SIN_AUDITORIA = "SIN_AUDITORIA";

    public InterfaceProcesos CrearEjecucionProceso(String tipoProxy) {
        if (PROXY_SIN_AUDITORIA.equals(tipoProxy)) {
            return applicationContext.getBean(ProxyProcesosSinAuditoria.class);
        } else {
            // Por defecto retorna el proxy auditable
            return applicationContext.getBean(ProxyProcesos.class);
        }
    }

    // Metodo estático mantenido para compatibilidad
    public static InterfaceProcesos CrearEjecucionProceso() {
        // Este metodo ahora requiere Spring context, por lo que se recomienda usar el metodo de instancia
        throw new UnsupportedOperationException("Use el método de instancia con tipo de proxy especificado");
    }
}
