package com.stoom.project.endereco.repository;

import com.stoom.project.endereco.model.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    List<Endereco> findByCountry(String country);

    Page<Endereco> findByCountry(String country, Pageable paginacao);
}
