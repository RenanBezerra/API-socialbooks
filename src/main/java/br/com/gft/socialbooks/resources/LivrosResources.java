package br.com.gft.socialbooks.resources;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.gft.socialbooks.domain.Comentario;
import br.com.gft.socialbooks.domain.Livro;
import br.com.gft.socialbooks.services.LivrosService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags= "Livros")
@RestController
@RequestMapping("/livros")
public class LivrosResources {

	@Autowired
	private LivrosService livrosService;
	
	@ApiOperation("Lista os livros")
	@GetMapping()
	public ResponseEntity<List<Livro>> listar() {
	
		return ResponseEntity.status(HttpStatus.OK).body(livrosService.listar()); 
	}
	@ApiOperation("Salva os livros")
	@PostMapping()
	public ResponseEntity<Void> salvar(@ApiParam(name="Corpo",value="Representação de um novo livro")@Valid @RequestBody Livro livro) {
		
		livro = livrosService.salvar(livro);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(livro.getId()).toUri();
	
		return ResponseEntity.created(uri).build();
	}
	
	@ApiOperation("Procura os livros")
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> buscar(@ApiParam(value= "ID de um Livro",example = "1")@PathVariable("id") Long id) {
		Optional<Livro> livro = null;
			livro = livrosService.buscar(id);			
			
			CacheControl cacheControl = CacheControl.maxAge(20, TimeUnit.SECONDS);
		return ResponseEntity.status(HttpStatus.OK).cacheControl(cacheControl).body(livro);

		
	}
	@ApiOperation("Deleta os livros")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletar(@ApiParam(value="ID de um Livro",example="1")@PathVariable("id") Long id) {
			livrosService.deletar(id);
		return ResponseEntity.noContent().build();
		
	}
	@ApiOperation("Edita os livros")
	@PutMapping(value ="/{id}")
	public ResponseEntity<Void> atualizar(@ApiParam(name="Corpo",value="Representação de um livro com os novos dados")@RequestBody Livro livro, @PathVariable("id") Long id) {
		livro.setId(id);
			livrosService.atualizar(livro);

		return ResponseEntity.noContent().build();
	}
	@ApiOperation("Salva os comentarios")
	@PostMapping(value = "/{id}/comentarios")
	public ResponseEntity<Void> adicionarComentario(@ApiParam(name="Corpo",value="Representação de um novo livro")@PathVariable("id") Long livroId,
			@RequestBody Comentario comentario) {
		
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		comentario.setUsuario(auth.getName());
		
		livrosService.salvarComentario(livroId, comentario);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		
		return ResponseEntity.created(uri).build();
		
	}
	@ApiOperation("Lista os comentarios")
	@GetMapping(value = "/{id}/comentarios")
	public ResponseEntity<List<Comentario>> listarComentarios(
				@PathVariable("id")Long livroId){
		
		List<Comentario> comentarios = livrosService.listarComentarios(livroId);
		
		return ResponseEntity.status(HttpStatus.OK).body(comentarios);
	}
	
}
