package com.gft.agenda.api.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gft.agenda.model.Contato;
import com.gft.agenda.repository.ContatoRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/contatos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ContatoController {

    private final ContatoRepository contatoRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contato save(@RequestBody Contato contato) {
        return contatoRepository.save(contato);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        contatoRepository.deleteById(id);
    }

    @GetMapping
    public List<Contato> list() {
        return contatoRepository.findAll();
    }

    @PatchMapping("/{id}/favorito")
    public void favorite(@PathVariable Integer id) {
        Optional<Contato> contato = contatoRepository.findById(id);
        contato.ifPresent(c -> {
            boolean favorito = c.getFavorito() == Boolean.TRUE;
            c.setFavorito(!favorito);
            contatoRepository.save(c);
        });
    }

    @PutMapping("/{id}/foto")
    public byte[] addPhoto(@PathVariable Integer id, @RequestParam("foto") Part arquivo) {

        Optional<Contato> contato = contatoRepository.findById(id);
        return contato.map(c -> {
            try {
                InputStream is = arquivo.getInputStream();
                byte[] bytes = new byte[(int) arquivo.getSize()];
                IOUtils.readFully(is, bytes);
                c.setFoto(bytes);
                contatoRepository.save(c);
                is.close();
                return bytes;
            } catch (IOException e) {
                return null;
            }
        }).orElse(null);
    }
}