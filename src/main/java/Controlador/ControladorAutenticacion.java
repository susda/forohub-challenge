package Controlador;

import Dominio.Usuario.Usuario;
import Dominio.Usuario.UsuarioAutenticacion;
import Seguridad.SeguridadTokenJWTT;
import Seguridad.ServicioToken;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class ControladorAutenticacion {

    final
    AuthenticationManager authenticationManager;

    final
    TokenService tokenService;

    public ControladorAutenticacion(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<SeguridadTokenJWTT> autenticarUsuario(
            @RequestBody @Valid UsuarioAutenticacion datosAutenticacion){
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacion.nombre(),
                datosAutenticacion.clave());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTtoken = ServicioToken.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new SeguridadTokenJWTT(JWTtoken));

    }
}