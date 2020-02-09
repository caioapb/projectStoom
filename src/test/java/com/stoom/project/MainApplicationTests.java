package com.stoom.project;

import com.stoom.project.endereco.model.Endereco;
import com.stoom.project.endereco.model.EnderecoFormDto;
import com.stoom.project.endereco.model.EnderecoViewDto;
import com.stoom.project.endereco.service.EnderecoService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

//Classe de testes unitarios
// O database é inicializado com um endereço nos EUA
@SpringBootTest
class MainApplicationTests {

	@Autowired
	private EnderecoService enderecoService;

	@Test
	void contextLoads() {

	}

	@Test
	// Resultado esperado: inserir um novo registro buscando a latitude e longitude a partir do GoogleGeoCoding
	public void createShouldPersistData(){
		EnderecoFormDto enderecoForm = new EnderecoFormDto(
			"Rua dos Aimorés", "335","Apartamento 1 Torre 2","Jardim Santa Genebra",
			"Campinas","São Paulo","Brasil","13081-030", null, null);

		Endereco inserido = enderecoService.insert(enderecoForm);

		Assertions.assertThat(inserido).isNotNull();
		Assertions.assertThat(inserido.getLongitude()).isNotNull();
		Assertions.assertThat(inserido.getLatitude()).isNotNull();
	}

	@Test
	// Resultado esperado:
	// 	► listEndereco com 1 registro
	//	► listEndereco2 com 1 registro
	//	► listEndereco3 sem registro
	public void shouldFind(){
		List<EnderecoViewDto> listEndereco = enderecoService.find(null);
		List<EnderecoViewDto> listEndereco2 = enderecoService.find("Estados Unidos");
		List<EnderecoViewDto> listEndereco3 = enderecoService.find("Brasil");

		Assertions.assertThat(listEndereco).isNotEmpty();
		Assertions.assertThat(listEndereco2).isNotEmpty();
		Assertions.assertThat(listEndereco3).isEmpty();

	}

	@Test
	// Resultado esperado:
	// 	► listEndereco com 1 registro
	//	► listEndereco2 com 1 registro
	//	► listEndereco3 sem registro
	//	► listEndereco4 sem registro
	public void shouldFindPaginado(){
		Pageable p1 = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
		Pageable p2 = PageRequest.of(1, 10, Sort.Direction.ASC, "id");
		Page<EnderecoViewDto> listEndereco = enderecoService.findPaginado(null, p1);
		Page<EnderecoViewDto> listEndereco2 = enderecoService.findPaginado("Estados Unidos", p1);
		Page<EnderecoViewDto> listEndereco3 = enderecoService.findPaginado("Estados Unidos", p2);
		Page<EnderecoViewDto> listEndereco4 = enderecoService.findPaginado("Brasil", p1);

		Assertions.assertThat(listEndereco).isNotEmpty();
		Assertions.assertThat(listEndereco2).isNotEmpty();
		Assertions.assertThat(listEndereco3).isEmpty();
		Assertions.assertThat(listEndereco4).isEmpty();

	}

	@Test
	// Resultado esperado:
	// 	► endereco prente
	// 	► endereco2 ausente
	public void shouldFindById(){
		Optional<Endereco> endereco = enderecoService.findById(1l);
		Optional<Endereco> endereco2 = enderecoService.findById(2l);

		Assertions.assertThat(endereco).isPresent();
		Assertions.assertThat(endereco2).isEmpty();
	}

	@Test
	// Resultado esperado:
	//	► atualizado deve conter os campos iguais ao original, exceto o país
	public void updateShouldPersistData(){
		Optional<Endereco> original = enderecoService.findById(1l);
		EnderecoFormDto enderecoForm = new EnderecoFormDto(original.get());
		enderecoForm.setCountry("Brasil");

		Optional<Endereco> atualizado = enderecoService.update(1l, enderecoForm);

		Assertions.assertThat(original.get().getId()).isEqualTo(atualizado.get().getId());
		Assertions.assertThat(original.get().getCountry()).isNotEqualTo(atualizado.get().getCountry());
	}

	@Test
	// Resultado esperado:
	// 	► Não deve encontrar o registro
	public void deleteShouldPersistData(){
		enderecoService.delete(1l);

		Assertions.assertThat(enderecoService.findById(1l)).isEmpty();
	}


}
