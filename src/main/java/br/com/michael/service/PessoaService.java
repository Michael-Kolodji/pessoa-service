package br.com.michael.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.michael.model.PessoaModel;
import br.com.michael.model.ResponseModel;
import br.com.michael.repository.PessoaRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins= "http://localhost:4200")
@RestController
@RequestMapping("/service")
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@ApiOperation(value="Cadastrar uma nova pessoa", response=ResponseModel.class, 
					notes="Essa operação salva um novo registro com as informações de uma pessoa.")
	@ApiResponses(value= {
					@ApiResponse(code=200, message="Retorna um ResponseModel com uma mensagem de sucesso", response=ResponseModel.class),
					@ApiResponse(code=500, message="Caso aconteça algum erro vamos retornar um ResponseModel com a Exception", response=ResponseModel.class)
	})
	@RequestMapping(value="/pessoa", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseModel salvar(@RequestBody PessoaModel pessoa) {
		
		try {
			this.pessoaRepository.save(pessoa);
			
			return new ResponseModel(1, "Registro salvo com sucesso!");
			
		} catch (Exception e) {
			return new ResponseModel(0, e.getMessage());
		}
	}
	
	@ApiOperation(value="Atualizar um cadastro de pessoa já existente", response=ResponseModel.class,
					notes="Essa operação atualiza o registro existente com novas informações.")
	@ApiResponses(value= {
			@ApiResponse(code=200, message="Retorna um ResponseModel com uma mensagem de sucesso", response=ResponseModel.class),
			@ApiResponse(code=500, message="Caso aconteça algum erro vamos retornar um ResponseModel com a Exception", response=ResponseModel.class)
	})
	@RequestMapping(value="/pessoa", method = RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseModel atualizar(@RequestBody PessoaModel pessoa){
		try {
			
			this.pessoaRepository.save(pessoa);
			
			return new ResponseModel(1, "registro atualizado com sucesso!");
			
		} catch (Exception e) {
			return new ResponseModel(0, e.getMessage());
		}
	}
	
	/**
	 * CONSULTAR TODAS AS PESSOAS
	 * @return
	 */
	@RequestMapping(value="/pessoa", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody List<PessoaModel> consultar(){
 
		return this.pessoaRepository.findAll();
	}
 
	/**
	 * BUSCAR UMA PESSOA PELO CÓDIGO
	 * @param codigo
	 * @return
	 */
	@RequestMapping(value="/pessoa/id/{codigo}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody PessoaModel buscar(@PathVariable("codigo") Integer codigo){
 
		return this.pessoaRepository.findById(codigo);
	}
	
	/***
	 * EXCLUIR UM REGISTRO PELO CÓDIGO
	 * @param codigo
	 * @return
	 */
	@RequestMapping(value="/pessoa/{codigo}", method = RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseModel excluir(@PathVariable("codigo") Integer codigo){
		
		PessoaModel pessoaModel = this.pessoaRepository.findById(codigo);
		
		try {
			this.pessoaRepository.delete(pessoaModel);
			
			return new ResponseModel(1, "Registro excluido com sucesso!");
			
		} catch (Exception e) {
			return new ResponseModel(0, e.getMessage());
		}
	}
	
}
