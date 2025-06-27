package controlhabitos.controlhabitos.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void enviarVerificacion(String correo, String nombre, String token) {
        String url = "http://localhost:8080/verificar?token=" + token;

        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(correo);
        mensaje.setSubject("Verifica tu cuenta");
        mensaje.setText("Hola " + nombre + ", haz clic en el siguiente enlace para verificar tu cuenta:\n\n" + url);

        mailSender.send(mensaje);
    }
}
