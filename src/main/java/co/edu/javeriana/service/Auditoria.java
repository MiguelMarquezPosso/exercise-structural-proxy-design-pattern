package co.edu.javeriana.service;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class Auditoria {
    public void AuditoriaServicioUsado(String user, String servicio) {
        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");
        System.out.println(user + " utilizó el servicio > "
                + servicio + ", a las " + formater.format(new Date()));
    }
}