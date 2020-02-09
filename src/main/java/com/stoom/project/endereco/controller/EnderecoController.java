package com.stoom.project.endereco.controller;

import com.stoom.project.endereco.model.Endereco;
import com.stoom.project.endereco.model.EnderecoFormDto;
import com.stoom.project.endereco.model.EnderecoViewDto;
import com.stoom.project.endereco.service.EnderecoService;
import com.stoom.project.geoCoding.service.GeoCodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private GeoCodingService geoCodingService;

    @GetMapping
    public List<EnderecoViewDto> find(@RequestParam (required = false) String country) {
        return enderecoService.find(country);
    }

    @GetMapping("paginado")
    public Page<EnderecoViewDto> findPaginado(
            @RequestParam (required = false) String country,
            @PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable paginacao) {
        return enderecoService.findPaginado(country, paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> findById(@PathVariable Long id) {
        Optional<Endereco> endereco = enderecoService.findById(id);
        if (endereco.isPresent()) {
            return ResponseEntity.ok(endereco.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Endereco> insert(
            @RequestBody @Valid EnderecoFormDto form,
            UriComponentsBuilder ucb)  {
        Endereco endereco = enderecoService.insert(form);
        URI uri = ucb.path("/topicos/{id}").buildAndExpand(endereco.getId()).toUri();
        return ResponseEntity.created(uri).body(endereco);
    }

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<Endereco> update(@PathVariable Long id, @RequestBody @Valid EnderecoFormDto form) {
		Optional<Endereco> optionalEndereco = enderecoService.update(id, form);
		if (optionalEndereco.isPresent()) {
			return ResponseEntity.ok(optionalEndereco.get());
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<Endereco> optionalEndereco = enderecoService.delete(id);
		if (optionalEndereco.isPresent()) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
