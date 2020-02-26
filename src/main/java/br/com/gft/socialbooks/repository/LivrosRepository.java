package br.com.gft.socialbooks.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gft.socialbooks.domain.Livro;

public interface LivrosRepository extends JpaRepository<Livro, Long>{




}
