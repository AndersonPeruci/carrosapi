package com.example.carrosapi.domain.dto;

import com.example.carrosapi.domain.Carro;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class CarroDTO {

    private Long id;
    private String nome;
    private String tipo;
    private String descricao;
    private String urlFoto;
    private String urlVideo;
    private String latitude;
    private String longitude;

    public static CarroDTO create (Carro carro){
        return new ModelMapper().map(carro, CarroDTO.class);
    }
}
