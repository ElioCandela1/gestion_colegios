package com.sistema_colegios.gestion_colegios.Model.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCredenciales(String destino, String username, String password) {

        SimpleMailMessage mensaje = new SimpleMailMessage();

        mensaje.setTo(destino);
        mensaje.setSubject("Credenciales de acceso al Sistema Escolar");

        mensaje.setText("""
                Estimado usuario:

                Se ha creado su cuenta en el Sistema de Gestión Escolar.

                Usuario: %s
                Contraseña: %s


                Atentamente,
                Administración del Colegio.
                """.formatted(username, password));

        mailSender.send(mensaje);
    }
}