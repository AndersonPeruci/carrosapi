package com.example.carrosapi;

import com.example.carrosapi.domain.Carro;
import com.example.carrosapi.domain.dto.CarroDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = CarrosapiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarrosApiTests {
    @Autowired
    protected TestRestTemplate rest;

    private ResponseEntity<CarroDTO> getCarroById(String url){
        return rest.withBasicAuth("admin", "$2a$10$HKveMsPlst41Ie2LQgpijO691lUtZ8cLfcliAO1DD9TtZxEpaEoJe").getForEntity(url, CarroDTO.class);
    }

    private ResponseEntity<List<CarroDTO>> getCarros(String url){
        return rest.withBasicAuth("admin", "$2a$10$HKveMsPlst41Ie2LQgpijO691lUtZ8cLfcliAO1DD9TtZxEpaEoJe").exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CarroDTO>>() {
                }
        );
    }

    @Test
    public void getCarroById(){
        CarroDTO carro = getCarroById("/api/v1/carros/1").getBody();
        assertNotNull(carro);
        assertEquals("Tucker 1948", carro.getNome());
    }

    @Test
    public void getAllCarros(){
        List<CarroDTO> carro = getCarros("/api/v1/carros").getBody();
        assertNotNull(carro);
        assertEquals(30, carro.size());
    }

    @Test
    public void getCarrosByTipo(){
        List<CarroDTO> carro = getCarros("/api/v1/carros/tipo/esportivos").getBody();
        assertNotNull(carro);
        assertEquals(10, carro.size());

        carro = getCarros("/api/v1/carros/tipo/luxo").getBody();
        assertNotNull(carro);
        assertEquals(10, carro.size());

        carro = getCarros("/api/v1/carros/tipo/classicos").getBody();
        assertNotNull(carro);
        assertEquals(10, carro.size());

        //Valida o retorno para quando nao tiver conteudo
        assertEquals(HttpStatus.NO_CONTENT, getCarros("/api/v1/carros/tipo/test").getStatusCode());
    }

    @Test
    public void testGetNotFound() {
        ResponseEntity<CarroDTO> response = getCarroById("/api/v1/carros/0");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testSave() {

        Carro carro = new Carro();
        carro.setNome("Tesla Model Y");
        carro.setTipo("eletricos");

        // Insert
        ResponseEntity<Object> response = rest.withBasicAuth("admin", "$2a$10$HKveMsPlst41Ie2LQgpijO691lUtZ8cLfcliAO1DD9TtZxEpaEoJe").postForEntity("/api/v1/carros", carro, null);

        // Verifica se criou
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Buscar o objeto
        String location = Objects.requireNonNull(response.getHeaders().get("location")).get(0);
        CarroDTO carroDTO = getCarroById(location).getBody();

        assertNotNull(carroDTO);
        assertEquals("Tesla Model Y", carroDTO.getNome());
        assertEquals("eletricos", carroDTO.getTipo());

        // Deletar o objeto
        rest.withBasicAuth("admin", "$2a$10$HKveMsPlst41Ie2LQgpijO691lUtZ8cLfcliAO1DD9TtZxEpaEoJe").delete(location);

        // Verificar se deletou
        assertEquals(HttpStatus.NOT_FOUND, getCarroById(location).getStatusCode());
    }
}
