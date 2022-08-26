package com.gft.agenda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gft.agenda.model.Contato;

public interface ContatoRepository extends JpaRepository<Contato, Integer> {

}
