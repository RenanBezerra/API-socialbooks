package br.com.gft.socialbooks.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.gft.socialbooks.domain.Livro;
import br.com.gft.socialbooks.repository.LivrosRepository;

@RestController
@RequestMapping("/livros")
public class LivrosResources {

	@Autowired
	private LivrosRepository livrosRepository;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Livro> listar() {
	
		return livrosRepository.findAll(); 
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public void salvar(@RequestBody Livro livro) {
		livrosRepository.save(livro);
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public ResponseEntity<?> buscar(@PathVariable("id") Long id) {
		Optional<Livro> livro = livrosRepository.findById(id);
		
		if(livro == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(livro);
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
	public void deletar(@PathVariable("id") Long id) {
		livrosRepository.deleteById(id);
	}
	
	@RequestMapping(value ="/{id}", method = RequestMethod.PUT)
	public void atualizar(@RequestBody Livro livro, @PathVariable("id") Long id) {
		livro.setId(id);
		livrosRepository.save(livro);
	}
	
	
}
