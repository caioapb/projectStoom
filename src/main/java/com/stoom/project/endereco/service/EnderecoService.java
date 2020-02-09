package com.stoom.project.endereco.service;

import com.stoom.project.endereco.model.Endereco;
import com.stoom.project.endereco.model.EnderecoFormDto;
import com.stoom.project.endereco.model.EnderecoViewDto;
import com.stoom.project.endereco.repository.EnderecoRepository;
import com.stoom.project.geoCoding.service.GeoCodingService;
import com.stoom.project.geoCoding.service.dto.CoordenadasDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private GeoCodingService geoCodingService;

    //Busca os endereços cadastrados, com filtro opcional por país
    public List<EnderecoViewDto> find(String country) {
        List<Endereco> listEndereco;
        if (country == null) {
            listEndereco = enderecoRepository.findAll();
        } else {
            listEndereco = enderecoRepository.findByCountry(country);
        }
        return EnderecoViewDto.converter(listEndereco);
    }

    //Busca os endereços cadastrados de forma paginada, com filtro opcional por país
    public Page<EnderecoViewDto> findPaginado(String country, Pageable paginacao) {
        Page<Endereco> listEndereco;
        if (country == null) {
            listEndereco = enderecoRepository.findAll(paginacao);
        } else {
            listEndereco = enderecoRepository.findByCountry(country, paginacao);
        }
        return EnderecoViewDto.converterPage(listEndereco);
    }

    //Busca por um endereço
    public Optional<Endereco> findById(Long id) {
        return enderecoRepository.findById(id);
    }

    //Insere um endereço no banco
    //caso esteja faltando a latitude ou longitude, o sistema busca o mesmo na api da Google
    public Endereco insert( EnderecoFormDto form) {
        if (form.getLatitude()==null|| form.getLongitude()==null) {
            CoordenadasDto coordenadasDto = geoCodingService.searchGoogleGeocoding(form);
            form.setLatitude(coordenadasDto.getLatitude());
            form.setLongitude(coordenadasDto.getLongitude());
        }
        Endereco endereco = form.convert();
        return enderecoRepository.save(endereco);
    }

    //Autaliza um endereço
    public Optional<Endereco> update(Long id, EnderecoFormDto form) {
        Optional<Endereco> optionalEndereco = enderecoRepository.findById(id);
        if (optionalEndereco.isPresent()) {
            Endereco endereco = form.convert().setId(id);
            enderecoRepository.save(endereco);
        }
        return optionalEndereco;
    }

    //Remove um endereço
    public Optional<Endereco> delete(Long id) {
        Optional<Endereco> optionalEndereco = enderecoRepository.findById(id);
        if (optionalEndereco.isPresent()) {
            enderecoRepository.deleteById(id);
        }

        return optionalEndereco;
    }
}
