package br.com.gft.socialbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gft.socialbooks.domain.Comentario;

public interface ComentariosRepository extends JpaRepository<Comentario, Long>{

}
