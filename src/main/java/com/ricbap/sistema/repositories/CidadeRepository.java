package com.ricbap.sistema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ricbap.sistema.domain.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Integer>{

}
