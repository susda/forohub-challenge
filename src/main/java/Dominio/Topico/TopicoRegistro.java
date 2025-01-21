package Dominio.Topico;

import Dominio.Curso.Curso;
import Dominio.Usuario.Usuario;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

public record TopicoRegistro(
        @NotBlank
        String titulo,

        @NotBlank
        String mensaje,

        @NotBlank
        String status,

        @NotNull
        Usuario autor,

        @NotNull
        Curso curso
) {
}