package com.stoom.project.geoCoding.service.dto;

public class CoordenadasDto {

    private Double latitude;
    private Double longitude;

    public CoordenadasDto(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
