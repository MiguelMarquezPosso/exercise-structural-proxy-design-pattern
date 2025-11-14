package co.edu.javeriana.implementacion;

import co.edu.javeriana.service.Seguridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProxyProcesosSinAuditoria implements InterfaceProcesos {

    @Autowired
    private Seguridad seguridadService;

    @Override
    public void EjecutarProcesos(int IdProceso, String Usuario, String Password) throws Exception {
        if (!seguridadService.Autorizacion(Usuario, Password)) {
            throw new Exception("El usuario '" + Usuario + "' no tiene privilegios para ejecutar el proceso");
        }

        ProcesoDefecto ejecutorProcess = new ProcesoDefecto();
        ejecutorProcess.EjecutarProcesos(IdProceso, Usuario, Password);

        // No se realiza auditoría en este proxy
    }
}