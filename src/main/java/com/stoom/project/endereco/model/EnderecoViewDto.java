package com.stoom.project.endereco.model;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class EnderecoViewDto {
    private String streetName;
    private String number;
    private String complement;
    private String neighbourhood;
    private String city;
    private String state;
    private String country;

    public EnderecoViewDto(Endereco endereco) {
        this.streetName = endereco.getStreetName();
        this.number = endereco.getNumber();
        this.complement = endereco.getComplement();
        this.neighbourhood = endereco.getNeighbourhood();
        this.city = endereco.getCity();
        this.state = endereco.getState();
        this.country = endereco.getCountry();
    }

    public String getStreetName() {
        return streetName;
    }

    public String getNumber() {
        return number;
    }

    public String getComplement() {
        return complement;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public static List<EnderecoViewDto> converter(List<Endereco> listEndereco) {
        return listEndereco.stream().map(EnderecoViewDto::new).collect(Collectors.toList());
    }

    public static Page<EnderecoViewDto> converterPage(Page<Endereco> listEndereco) {
        return listEndereco.map(EnderecoViewDto::new);
    }
}
