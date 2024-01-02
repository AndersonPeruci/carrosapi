package com.example.carrosapi;

import com.example.carrosapi.domain.Carro;
import com.example.carrosapi.domain.CarroService;
import com.example.carrosapi.domain.dto.CarroDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CarrosServiceTests {

	@Autowired
	private CarroService carroService;

	@Test
	void testCarrosApi() {
		Carro carro = new Carro();
		carro.setNome("Test Car");
		carro.setTipo("Test");

		CarroDTO carroDTO = carroService.insert(carro);
		assertNotNull(carroDTO);

		Long id = carroDTO.getId();
		assertNotNull(id);

		//Busca o objeto
		CarroDTO insertedCar = carroService.getCarroById(id);
		assertEquals("Test Car", insertedCar.getNome());
		assertEquals("Test", insertedCar.getTipo());

		//Deleta o dado de teste inserido
		carroService.delete(id);

	}

	@Test
	public void testGetAllCars(){
		List<CarroDTO> listCarros = carroService.getCarros();
		assertEquals(30, listCarros.size());
	}

	@Test
	public void testGetCar(){
		CarroDTO optCarro = carroService.getCarroById(11L);
		assertEquals("Ferrari FF", optCarro.getNome());
	}

	@Test
	public void testGetCarByTipo(){
		assertEquals(10,carroService.getCarroByTipo("esportivos").size());
		assertEquals(10,carroService.getCarroByTipo("luxo").size());
		assertEquals(10,carroService.getCarroByTipo("classicos").size());

		assertEquals(0,carroService.getCarroByTipo("test").size());
	}

}
