package controlhabitos.controlhabitos.Config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        boolean estaLogueado = (session != null && session.getAttribute("usuarioLogueado") != null);

        if (!estaLogueado) {
            response.sendRedirect("/index");  // Redirige al login
            return false;
        }

        return true; // Deja continuar si está logueado
    }
}
