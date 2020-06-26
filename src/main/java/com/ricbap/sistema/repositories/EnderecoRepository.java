package com.ricbap.sistema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ricbap.sistema.domain.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

}
