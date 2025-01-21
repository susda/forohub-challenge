package Dominio.Topico;

import Dominio.Curso.Curso;
import Dominio.Respuesta.Respuesta;
import Dominio.Usuario.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Table(name = "topico")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    private String status;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;
    @OneToMany(mappedBy = "topico")
    private List<Respuesta> respuestas;

    public Topico(TopicoRegistro datosRegistroTopico) {
        this.titulo=datosRegistroTopico.titulo();
        this.mensaje=datosRegistroTopico.mensaje();
        LocalDate localDate = LocalDate.now();
        this.fechaCreacion = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        this.status=datosRegistroTopico.status();
        this.autor=datosRegistroTopico.autor();
        this.curso=datosRegistroTopico.curso();
    }

    public void actualizarDatos(TopicoActualizar datosActualizarTopico){
        if (datosActualizarTopico.titulo()!=null){
            this.titulo=datosActualizarTopico.titulo();
        }
        if(datosActualizarTopico.mensaje()!=null){
            this.mensaje=datosActualizarTopico.mensaje();
        }
        if (datosActualizarTopico.status()!=null){
            this.status= datosActualizarTopico.status();
        }
    }
}