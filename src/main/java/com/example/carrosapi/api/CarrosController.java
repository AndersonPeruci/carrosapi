package com.example.carrosapi.api;

import com.example.carrosapi.domain.Carro;
import com.example.carrosapi.domain.CarroService;
import com.example.carrosapi.domain.dto.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {
    @Autowired
    private CarroService carroService;

    @GetMapping
    public ResponseEntity<List<CarroDTO>> get(){
        return ResponseEntity.ok(carroService.getCarros());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarroDTO> get(@PathVariable("id") Long id){
        return ResponseEntity.ok(carroService.getCarroById(id));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<CarroDTO>> get(@PathVariable("tipo") String tipo){
        List<CarroDTO> cars = carroService.getCarroByTipo(tipo);

        return !cars.isEmpty() ? ResponseEntity.ok(cars) : ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<String> post(@RequestBody Carro carro){
        CarroDTO carroDTO = carroService.insert(carro);
        return ResponseEntity.created(getUri(carroDTO.getId())).build();
    }

    private URI getUri(Long id){
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> put(@PathVariable("id") Long id, @RequestBody Carro carro){
        try{
            CarroDTO carroDTO = carroService.update(id, carro);

            return carroDTO != null ?
                    ResponseEntity.noContent().header("response",  "Carro atualizado com sucesso: " + carroDTO.getId()).build() :
                    ResponseEntity.notFound().build();
        } catch (Exception ex){
            return ResponseEntity.badRequest().build();
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        carroService.delete(id);

        return ResponseEntity.ok("Carro deletado com sucesso: " + id);
    }
}
