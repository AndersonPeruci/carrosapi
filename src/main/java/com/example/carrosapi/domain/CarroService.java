package com.example.carrosapi.domain;

import com.example.carrosapi.domain.dto.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.carrosapi.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarroService {

    @Autowired
    private CarroRepository rep;

    public List<CarroDTO> getCarros() {
        return rep.findAll().stream().map(CarroDTO::create).collect(Collectors.toList());
    }

    public CarroDTO getCarroById(Long id) {
        Optional<Carro> carro = rep.findById(id);
        Optional<CarroDTO> dto = carro.map(CarroDTO::create);
        return rep.findById(id).map(CarroDTO::create)
                .orElseThrow(() -> new ObjectNotFoundException("Carro nao encontrado"));
    }
    public List<CarroDTO> getCarroByTipo(String tipo) {
        return rep.findByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
    }
    public CarroDTO insert(Carro carro) {
        Assert.isNull(carro.getId(), "Nao foi possivel atualizar o registro");

        return CarroDTO.create(rep.save(carro));
    }
    public CarroDTO update(Long id, Carro newCarroData) {
        Assert.notNull(id, "Nao foi possivel atualizar o registro");

        //Busca o dado na base de dados e converte para Carro
        Carro carUpdated = Carro.create(getCarroById(id));

        //Atribuir os novos valores
        carUpdated.setNome(newCarroData.getNome());
        carUpdated.setTipo(newCarroData.getTipo());

        //Atualiza o dado na base de dados
        return CarroDTO.create(rep.save(carUpdated));
    }
    public void delete(Long id) {
        rep.deleteById(id);
    }

}
