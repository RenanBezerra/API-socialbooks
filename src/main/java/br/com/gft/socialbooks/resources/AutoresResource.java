package br.com.gft.socialbooks.resources;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.gft.socialbooks.domain.Autor;
import br.com.gft.socialbooks.services.AutoresService;

@RestController
@RequestMapping("/autores")
public class AutoresResource {
	
	@Autowired
	 private AutoresService autoresService;
	
	@GetMapping()
	public ResponseEntity<List<Autor>> listar(){
		List<Autor>autores = autoresService.listar();
		return ResponseEntity.status(HttpStatus.OK).body(autores);
	}
	
	@PostMapping()
	public ResponseEntity<Void> salvar (@RequestBody Autor autor){
		autor = autoresService.salvar(autor);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(autor.getId()).toUri();
			return ResponseEntity.created(uri).build();
		
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Optional<Autor>> buscar(@PathVariable("id")Long id){
		Optional<Autor> autor = autoresService.buscar(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(autor);
	}
}
