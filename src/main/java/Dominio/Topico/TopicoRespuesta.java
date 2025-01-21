package Dominio.Topico;

import Dominio.Curso.CursoRespuesta;
import Dominio.Usuario.UsuarioRespuesta;
import java.util.Date;

public record TopicoRespuesta(
        Long id, String titulo,
        String mensaje,
        Date fechaCreacion,
        UsuarioRespuesta autor,
        CursoRespuesta curso)
{
}