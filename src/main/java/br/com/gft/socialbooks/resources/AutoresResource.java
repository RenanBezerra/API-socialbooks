package br.com.gft.socialbooks.resources;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Autores")
@RestController
@RequestMapping("/autores")
public class AutoresResource {
	
	@Autowired
	 private AutoresService autoresService;
	
	@ApiOperation("Lista os autores")
	@GetMapping(produces = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE
	})
	public ResponseEntity<List<Autor>> listar(){
		List<Autor>autores = autoresService.listar();
		return ResponseEntity.status(HttpStatus.OK).body(autores);
	}
	@ApiOperation("Salva os autores")
	@PostMapping()
	public ResponseEntity<Void> salvar (@ApiParam(name="corpo",value="Representação de um novo autor")@Valid @RequestBody Autor autor){
		autor = autoresService.salvar(autor);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(autor.getId()).toUri();
			return ResponseEntity.created(uri).build();
		
	}
	@ApiOperation("Procura os autores")
	@GetMapping(value = "/{id}")
	public ResponseEntity<Optional<Autor>> buscar(@ApiParam(value="ID de um Autor",example="1")@PathVariable("id")Long id){
		Optional<Autor> autor = autoresService.buscar(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(autor);
	}
}
