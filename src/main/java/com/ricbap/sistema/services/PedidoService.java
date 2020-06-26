package com.ricbap.sistema.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ricbap.sistema.domain.Pedido;
import com.ricbap.sistema.repositories.PedidoRepository;
import com.ricbap.sistema.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> pedido = pedidoRepository.findById(id);
		return pedido.orElseThrow(() -> {
			throw new ObjectNotFoundException("Pedido não encontrado! Id: " + id +
					", Tipo: " + Pedido.class.getName());			
		});
	}

}
