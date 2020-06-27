package com.ricbap.sistema.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ricbap.sistema.domain.Produto;
import com.ricbap.sistema.dto.ProdutoDTO;
import com.ricbap.sistema.resources.utils.URL;
import com.ricbap.sistema.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService produtoService;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		Produto produto = produtoService.buscar(id);
		return ResponseEntity.ok().body(produto);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			
			@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "categorias", defaultValue = "0") String categorias,
			@RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
			@RequestParam(value = "linhasPorPagina", defaultValue = "24") Integer linhasPorPagina,
			@RequestParam(value = "campos", defaultValue = "nome") String campos,
			@RequestParam(value = "direcao", defaultValue = "ASC") String direcao) {
		
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decoderIntList(categorias);
		Page<Produto> listaProduto = produtoService.search(nomeDecoded, ids, pagina, linhasPorPagina, campos, direcao);
		Page<ProdutoDTO> listaDTO = listaProduto.map(c -> new ProdutoDTO(c));
		return ResponseEntity.ok().body(listaDTO);
	}

}
