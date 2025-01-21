package Dominio.Topico;

import jakarta.validation.constraints.NotNull;

public record TopicoActualizar(
        @NotNull
        Long id,
        String titulo,
        String mensaje,
        String status)
{
}