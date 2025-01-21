package Controlador;

import Dominio.Curso.CursoRespuesta;
import Dominio.Topico.*;
import Dominio.Usuario.UsuarioRespuesta;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import java.net.URI;
import java.util.Optional;
@RestController
@RequestMapping("/topico")
public class ControladorTopico {

    private final TopicoRepo topicoRepositorio;

    public ControladorTopico(TopicoRepo topicoRepository) {
        this.topicoRepositorio = topicoRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<TopicoRespuesta> nuevoTopico(
            @RequestBody @Valid TopicoRegistro datosRegistroTopico, UriComponentsBuilder uriComponentsBuilder){
        Topico topico = topicoRepositorio.save(new Topico(datosRegistroTopico));
        TopicoRespuesta topicoRespuesta=
                new TopicoRespuesta(
                topico.getId(),topico.getTitulo(),
                topico.getMensaje(),topico.getFechaCreacion(),
                new UsuarioRespuesta(topico.getAutor().getNombre(),
                topico.getAutor().getCorreo()),
                new CursoRespuesta(topico.getCurso().getNombre()
                ,topico.getCurso().getCategoria()));
        URI url = uriComponentsBuilder.path("/topico/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(url).body(topicoRespuesta);
    }

    @GetMapping
    public ResponseEntity<Page<TopicoListado>> listadoTopico(
            @PageableDefault(size = 5) Pageable pag){

        return ResponseEntity.ok(topicoRepositorio.findAll(pag).map(TopicoListado::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<TopicoRespuesta> actualizarTopico(
            @RequestBody @Valid TopicoActualizar datosActualizarTopico)
    {
        Topico topico= topicoRepositorio.getReferenceById(datosActualizarTopico.id());
        topico.actualizarDatos(datosActualizarTopico);

        return ResponseEntity.ok(new TopicoRespuesta(topico.getId()
                , topico.getTitulo(),topico.getMensaje(),
                topico.getFechaCreacion(),
                new UsuarioRespuesta(topico.getAutor().getNombre()
                        ,topico.getAutor().getCorreo()),
                new CursoRespuesta(topico.getCurso().getNombre()
                        ,topico.getCurso().getCategoria())));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id){
        Optional<Topico> topicoOptional = topicoRepositorio.findById(id);
        if (topicoOptional.isPresent()) {
            topicoRepositorio.delete(topicoOptional.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoRespuesta> obtenerTopicoId(@PathVariable Long id)
    {
        Topico topico = topicoRepositorio.getReferenceById(id);

        var datosTopico=new TopicoRespuesta(
                topico.getId(), topico.getTitulo(),topico.getMensaje(),
                topico.getFechaCreacion(),
                new UsuarioRespuesta(topico.getAutor().getNombre()
                        ,topico.getAutor().getCorreo()),
                new CursoRespuesta(topico.getCurso().getNombre(),
                        topico.getCurso().getCategoria()));

        return ResponseEntity.ok(datosTopico);
    }
}
