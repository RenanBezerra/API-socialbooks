package br.com.gft.socialbooks.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;


@Entity
public class Livro {
 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonInclude(Include.NON_NULL)
	@ApiModelProperty(value = "ID do Livro",example = "1")
	private Long id;
	
	@NotEmpty(message = "O campo nome não pode ser vazio.")
	@ApiModelProperty(example = "Pedro")
	private String nome;
	
	@JsonInclude(Include.NON_NULL)
	@JsonFormat(pattern = "dd/MM/yyyy")
	@NotNull(message = "Campo publicacao é de preenchimento obrigatório")
	@ApiModelProperty(example = "01/01/2020")
	private Date publicacao;
	 
	@JsonInclude(Include.NON_NULL)
	@NotNull(message = "Campo editora é de preenchimento obrigatório")
	@ApiModelProperty(example = "editora Abril")
	private String editora;
	
	@JsonInclude(Include.NON_NULL)
	@NotEmpty(message= "Oresumo deve ser preenchido")
	@Size(max = 1500, message = "O resumo não pode conter mais de 1500 caracteres.")
	@ApiModelProperty(example = "conclusão.....")
	private String resumo;
	
	@JsonInclude(Include.NON_EMPTY)
	@OneToMany(mappedBy = "livro")
	private List<Comentario> comentarios;
	
	
	@ManyToOne
	@JoinColumn(name ="AUTOR_ID")
	@JsonInclude(Include.NON_NULL)
	private Autor autor;
	
	
	public Livro() {
		
	}
	
	public Livro(String nome) {
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getPublicacao() {
		return publicacao;
	}

	public void setPublicacao(Date publicacao) {
		this.publicacao = publicacao;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public String getResumo() {
		return resumo;
	}

	public void setResumo(String resumo) {
		this.resumo = resumo;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	
	
	
}
