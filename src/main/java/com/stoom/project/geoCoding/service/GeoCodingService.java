package com.stoom.project.geoCoding.service;

import com.stoom.project.endereco.model.EnderecoFormDto;
import com.stoom.project.geoCoding.service.dto.CoordenadasDto;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

@Service
public class GeoCodingService {

    public static String CHAVE ="AIzaSyBm3xh9oZP1ksMWcMzVaZQevWlrtb8tIgc";

    //Busca na Api do Google pela Latitude e Longitude a partir do endereço
    public CoordenadasDto searchGoogleGeocoding(EnderecoFormDto form) {
        try {
            URL url = new URL(
                "https://{baseUrl}/{formato}?address={address}&key={chave}"
                    .replace("{baseUrl}","maps.googleapis.com/maps/api/geocode")
                    .replace("{formato}", "json")
                    .replace("{address}",form.toString())
                    .replace("{chave}",CHAVE)
            );
            Scanner in = new Scanner((InputStream) url.getContent());
            StringBuilder result = new StringBuilder();
            while (in.hasNext()) {
                result.append(in.nextLine());
            }
            JSONObject json = new JSONObject(result.toString());
            JSONArray array = json.getJSONArray("results");
            JSONObject geometry = array.getJSONObject(0).getJSONObject("geometry");
            JSONObject location = geometry.getJSONObject("location");
            return new CoordenadasDto(location.getDouble("lat"), location.getDouble("lng"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new CoordenadasDto(null, null);
    }
}
