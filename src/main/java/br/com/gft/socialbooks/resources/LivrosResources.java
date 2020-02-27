package br.com.gft.socialbooks.resources;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.gft.socialbooks.domain.Comentario;
import br.com.gft.socialbooks.domain.Livro;
import br.com.gft.socialbooks.services.LivrosService;

@RestController
@RequestMapping("/livros")
public class LivrosResources {

	@Autowired
	private LivrosService livrosService;
	
	
	@GetMapping()
	public ResponseEntity<List<Livro>> listar() {
	
		return ResponseEntity.status(HttpStatus.OK).body(livrosService.listar()); 
	}
	
	@PostMapping()
	public ResponseEntity<Void> salvar(@RequestBody Livro livro) {
		
		livro = livrosService.salvar(livro);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(livro.getId()).toUri();
	
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> buscar(@PathVariable("id") Long id) {
		Optional<Livro> livro = null;
			livro = livrosService.buscar(id);			
		return ResponseEntity.status(HttpStatus.OK).body(livro);

		
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
			livrosService.deletar(id);
		return ResponseEntity.noContent().build();
		
	}
	
	@PutMapping(value ="/{id}")
	public ResponseEntity<Void> atualizar(@RequestBody Livro livro, @PathVariable("id") Long id) {
		livro.setId(id);
			livrosService.atualizar(livro);

		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(value = "/{id}/comentarios")
	public ResponseEntity<Void> adicionarComentario(@PathVariable("id") Long livroId,
			@RequestBody Comentario comentario) {
		
		livrosService.salvarComentario(livroId, comentario);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		
		return ResponseEntity.created(uri).build();
		
	}
	@GetMapping(value = "/{id}/comentarios")
	public ResponseEntity<List<Comentario>> listarComentarios(
				@PathVariable("id")Long livroId){
		
		List<Comentario> comentarios = livrosService.listarComentarios(livroId);
		
		return ResponseEntity.status(HttpStatus.OK).body(comentarios);
	}
	
}
