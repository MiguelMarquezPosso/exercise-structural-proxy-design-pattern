package co.edu.javeriana.implementacion;

import co.edu.javeriana.service.Auditoria;
import co.edu.javeriana.service.Seguridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProxyProcesos implements InterfaceProcesos {

    @Autowired
    private Seguridad securityService;

    @Autowired
    private Auditoria audit;

    @Override
    public void EjecutarProcesos(int IdProceso, String Usuario, String Password) throws Exception {
        if (!securityService.Autorizacion(Usuario, Password)) {
            throw new Exception("El usuario '" + Usuario + "' no tiene privilegios para ejecutar el proceso");
        }

        ProcesoDefecto ejecutorProcess = new ProcesoDefecto();
        ejecutorProcess.EjecutarProcesos(IdProceso, Usuario, Password);

        audit.AuditoriaServicioUsado(Usuario, ProcesoDefecto.class.getName());
    }
}
