package com.example.carrosapi.domain;

import com.example.carrosapi.domain.dto.CarroDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.modelmapper.ModelMapper;

@Entity
//@Getter @Setter (Substituido por @Data)
//@ToString (Substituido por @Data)
//@EqualsAndHashCode (Substituido por @Data)
@Data
@NoArgsConstructor
public class Carro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String tipo;
    private String descricao;
    private String urlFoto;
    private String urlVideo;
    private String latitude;
    private String longitude;

    public static Carro create (CarroDTO carroDTO){
        return new ModelMapper().map(carroDTO, Carro.class);
    }



}
