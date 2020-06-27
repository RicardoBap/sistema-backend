package com.ricbap.sistema.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ricbap.sistema.domain.Categoria;
import com.ricbap.sistema.domain.Produto;
import com.ricbap.sistema.repositories.CategoriaRepository;
import com.ricbap.sistema.repositories.ProdutoRepository;
import com.ricbap.sistema.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRespository;
	
	public Produto buscar(Integer id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		return produto.orElseThrow(() -> {
			throw new ObjectNotFoundException("Produto não encontrado! Id: " + id +
					", Tipo: " + Produto.class.getName());			
		});
	}
	
	public Page<Produto> search(String nome, List<Integer> ids,Integer pagina, Integer linhasPorPagina, String campos, String direcao) {
		List<Categoria> categorias = categoriaRespository.findAllById(ids);
		PageRequest pageRequest = PageRequest.of(pagina, linhasPorPagina, Direction.valueOf(direcao), campos);
		//return produtoRepository.search(nome, categorias, pageRequest);
		return produtoRepository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}

}
