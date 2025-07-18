package controlhabitos.controlhabitos.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/menu", "/habitos", "/historial", "/AdminPage") // Protege estas rutas
                .excludePathPatterns("/index", "/login", "/registro", "/recuperar-contrasena", "/css/**", "/js/**");
    }
}
