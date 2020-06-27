package com.ricbap.sistema.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ricbap.sistema.domain.Categoria;
import com.ricbap.sistema.repositories.CategoriaRepository;
import com.ricbap.sistema.services.exceptions.DataIntegrityException;
import com.ricbap.sistema.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> categoria = categoriaRepository.findById(id);
		return categoria.orElseThrow(() -> {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id +
					", Tipo: " + Categoria.class.getName());			
		});
	}
	
	public Categoria inserir(Categoria categoria) {
		categoria.setId(null);
		return categoriaRepository.save(categoria);
	}
	
	public Categoria atualizar(Categoria categoria) {
		buscar(categoria.getId());
		return categoriaRepository.save(categoria);
	}
	
	public void remover(Integer id) {
		buscar(id);
		try {
			categoriaRepository.deleteById(id);		
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir uma Categoria que possui produtos");
		}
	}
	
	public List<Categoria> buscarTodas() {
		return categoriaRepository.findAll();
	}

}
