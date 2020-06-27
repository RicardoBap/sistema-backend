package com.ricbap.sistema.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ricbap.sistema.domain.Cliente;
import com.ricbap.sistema.dto.ClienteDTO;
import com.ricbap.sistema.repositories.ClienteRepository;
import com.ricbap.sistema.services.exceptions.DataIntegrityException;
import com.ricbap.sistema.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.orElseThrow(() -> {
			throw new ObjectNotFoundException("Cliente não encontrado! Id: " + id +
					", Tipo: " + Cliente.class.getName());			
		});
	}
	
	public Cliente atualizar(Cliente cliente) {
		Cliente novoCliente = buscar(cliente.getId());
		atualizaDados(novoCliente, cliente);
		return clienteRepository.save(novoCliente);
	}
	
	private void atualizaDados(Cliente novoCliente, Cliente cliente) {
		novoCliente.setNome(cliente.getNome());
		novoCliente.setEmail(cliente.getEmail());
	}

	public void remover(Integer id) {
		buscar(id);
		try {
			clienteRepository.deleteById(id);		
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir por que há entidades relacionadas");
		}
	}
	
	public List<Cliente> buscarTodas() {
		return clienteRepository.findAll();
	}
	
	public Page<Cliente> findPage(Integer pagina, Integer linhasPorPagina, String campos, String direcao) {
		PageRequest pageRequest = PageRequest.of(pagina, linhasPorPagina, Direction.valueOf(direcao), campos);
		return clienteRepository.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO clienteDto) {
		return new Cliente(clienteDto.getId(), clienteDto.getNome(), clienteDto.getEmail(), null, null);
	}

}
