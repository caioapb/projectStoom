package com.stoom.project.endereco.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.StringJoiner;

public class EnderecoFormDto {

    @NotNull @NotEmpty
    private String streetName;
    @NotNull @NotEmpty
    private String number;
    private String complement;
    @NotNull @NotEmpty
    private String neighbourhood;
    @NotNull @NotEmpty
    private String city;
    @NotNull @NotEmpty
    private String state;
    @NotNull @NotEmpty
    private String country;
    @NotNull @NotEmpty
    private String zipcode;
    private Double latitude;
    private Double longitude;

    public EnderecoFormDto() {
    }

    public EnderecoFormDto(@NotNull @NotEmpty String streetName, @NotNull @NotEmpty String number, String complement, @NotNull @NotEmpty String neighbourhood, @NotNull @NotEmpty String city, @NotNull @NotEmpty String state, @NotNull @NotEmpty String country, @NotNull @NotEmpty String zipcode, Double latitude, Double longitude) {
        this.streetName = streetName;
        this.number = number;
        this.complement = complement;
        this.neighbourhood = neighbourhood;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipcode = zipcode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public EnderecoFormDto(Endereco endereco) {
        this.streetName = endereco.getStreetName();
        this.number = endereco.getNumber();
        this.complement = endereco.getComplement();
        this.city = endereco.getCity();
        this.state = endereco.getState();
        this.neighbourhood = endereco.getNeighbourhood();
        this.zipcode = endereco.getZipcode();
        this.country = endereco.getCountry();
    }


    public Endereco convert() {
        return new Endereco(
            this.streetName,
            this.number,
            this.complement,
            this.neighbourhood,
            this.city,
            this.state,
            this.country,
            this.zipcode,
            this.latitude,
            this.longitude);
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

    public String getZipcode() {
        return zipcode;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",");

        return sj
            .add(this.streetName.replaceAll(" ", "%20"))
            .add(this.number.replaceAll(" ", "%20"))
            .add(this.city.replaceAll(" ", "%20"))
            .add(this.state.replaceAll(" ", "%20"))
            .add(this.country.replaceAll(" ", "%20")).toString();
    }
}
