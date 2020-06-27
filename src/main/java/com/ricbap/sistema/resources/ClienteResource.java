package com.ricbap.sistema.resources;

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

import com.ricbap.sistema.domain.Cliente;
import com.ricbap.sistema.dto.ClienteDTO;
import com.ricbap.sistema.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService clienteService;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente cliente = clienteService.buscar(id);
		return ResponseEntity.ok().body(cliente);
	}
	/*
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> inserir(@Valid @RequestBody ClienteDTO clienteDto) {
		
		Cliente cliente = clienteService.fromDTO(clienteDto);
		
		cliente = clienteService.inserir(cliente);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(cliente.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}*/
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizar(@Valid @RequestBody ClienteDTO clienteDto, @PathVariable Integer id) {
		Cliente cliente = clienteService.fromDTO(clienteDto);
		cliente.setId(id);
		cliente = clienteService.atualizar(cliente);
		return ResponseEntity.noContent().build();		
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> remover(@PathVariable Integer id) {
		clienteService.remover(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> buscarTodas() {
		List<Cliente> listaCliente = clienteService.buscarTodas();
		List<ClienteDTO> listaDTO = listaCliente.stream()
				.map(c -> new ClienteDTO(c)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}
	
	@RequestMapping(value = "/pagina", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
			
			@RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
			@RequestParam(value = "linhasPorPagina", defaultValue = "24") Integer linhasPorPagina,
			@RequestParam(value = "campos", defaultValue = "nome") String campos,
			@RequestParam(value = "direcao", defaultValue = "ASC") String direcao) {
		
		Page<Cliente> listaCliente = clienteService.findPage(pagina, linhasPorPagina, campos, direcao);
		Page<ClienteDTO> listaDTO = listaCliente.map(c -> new ClienteDTO(c));
		return ResponseEntity.ok().body(listaDTO);
	}

}
