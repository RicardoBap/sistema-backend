package com.ricbap.sistema.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ricbap.sistema.domain.Categoria;
import com.ricbap.sistema.dto.CategoriaDTO;
import com.ricbap.sistema.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Categoria> buscar(@PathVariable Integer id) {
		Categoria categoria = categoriaService.buscar(id);
		return ResponseEntity.ok().body(categoria);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> inserir(@Valid @RequestBody CategoriaDTO categoriaDto) {
		
		Categoria categoria = categoriaService.fromDTO(categoriaDto);
		
		categoria = categoriaService.inserir(categoria);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(categoria.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizar(@Valid @RequestBody CategoriaDTO categoriaDto, @PathVariable Integer id) {
		Categoria categoria = categoriaService.fromDTO(categoriaDto);
		categoria.setId(id);
		categoria = categoriaService.atualizar(categoria);
		return ResponseEntity.noContent().build();		
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> remover(@PathVariable Integer id) {
		categoriaService.remover(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> buscarTodas() {
		List<Categoria> listaCategoria = categoriaService.buscarTodas();
		List<CategoriaDTO> listaDTO = listaCategoria.stream()
				.map(c -> new CategoriaDTO(c)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}
	
	@RequestMapping(value = "/pagina", method = RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			
			@RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
			@RequestParam(value = "linhasPorPagina", defaultValue = "24") Integer linhasPorPagina,
			@RequestParam(value = "campos", defaultValue = "nome") String campos,
			@RequestParam(value = "direcao", defaultValue = "ASC") String direcao) {
		
		Page<Categoria> listaCategoria = categoriaService.findPage(pagina, linhasPorPagina, campos, direcao);
		Page<CategoriaDTO> listaDTO = listaCategoria.map(c -> new CategoriaDTO(c));
		return ResponseEntity.ok().body(listaDTO);
	}
	

}
