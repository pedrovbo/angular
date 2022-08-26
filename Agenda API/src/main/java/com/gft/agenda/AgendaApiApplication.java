package com.gft.agenda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.gft.agenda.model.Contato;
import com.gft.agenda.repository.ContatoRepository;

@SpringBootApplication
public class AgendaApiApplication {

	@Bean
	public CommandLineRunner commandLineRunner(@Autowired ContatoRepository repository) {
		return args -> {
			Contato contato = Contato.builder()
					.nome("ciclano")
					.email("ciclano@email.com")
					.favorito(false)
					.build();
			repository.save(contato);
		};

	}

	public static void main(String[] args) {
		SpringApplication.run(AgendaApiApplication.class, args);
	}

}
