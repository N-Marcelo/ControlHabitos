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
    public void enviarRecordatorio(String correo, String nombre, String habito,String mensaje) {
        SimpleMailMessage mensajeRecordatorio = new SimpleMailMessage();
        mensajeRecordatorio.setTo(correo);
        mensajeRecordatorio.setSubject("Recordatorio de tus habitos");
        mensajeRecordatorio.setText("Hola " + nombre + ", Tienes un recordatorio de tu " + habito + " Y un mensaje programado: " + mensaje);

        mailSender.send(mensajeRecordatorio);
    }

    public void enviarRecuperarContraseña(String correo, String nombre, String token) {
        String url = "http://localhost:8080/recuperarContraseña?token=" + token;

        SimpleMailMessage mensajeRecuperarContraseña = new SimpleMailMessage();
        mensajeRecuperarContraseña.setTo(correo);
        mensajeRecuperarContraseña.setSubject("Recupera tu contraseña");
        mensajeRecuperarContraseña.setText("Hola " + nombre + ", haz clic en el siguietne enlace para recuperar tu contraseña:\n\n" + url);

        mailSender.send(mensajeRecuperarContraseña);
    }
}
