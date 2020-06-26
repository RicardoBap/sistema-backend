package com.ricbap.sistema.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ricbap.sistema.domain.Cliente;
import com.ricbap.sistema.repositories.ClienteRepository;
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

}
