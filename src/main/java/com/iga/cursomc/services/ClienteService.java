package com.iga.cursomc.services;

import com.iga.cursomc.domain.Cidade;
import com.iga.cursomc.domain.Cliente;
import com.iga.cursomc.domain.Endereco;
import com.iga.cursomc.domain.enums.TipoCliente;
import com.iga.cursomc.dto.ClienteDTO;
import com.iga.cursomc.dto.ClienteNewDTO;
import com.iga.cursomc.repositories.ClienteRepository;
import com.iga.cursomc.repositories.EnderecoRepository;
import com.iga.cursomc.services.exceptions.DataIntegrityException;
import com.iga.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;

	@Autowired
	private EnderecoRepository enderecoRepository;

	// Método para buscar a categoria pelo o id
	public Optional<Cliente> find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado!: Id: " + id + ", Tipo: " + Cliente.class.getName());
		}
		return obj;
	}

	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	// Método para atualizar a categoria pelo ID
	public Cliente update(Cliente obj) {
		Optional<Cliente> newObj = find(obj.getId()); // o find irá buscar a categoria pelo ID
		updateData(newObj, obj);// atualiza o novo objeto que foi criado (newObj), com base no objeto que veio como argumento (obj)
		return repo.save(newObj.get());
	}

	// Método para deletar pelo id
	public void delete(Integer id) {
		find(id); // pega o id do cliente
		try {
			repo.deleteById(id); // deleta o cliente pelo id que foi pego no método find
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
		}
	}
	
	// Método que lista todos os clientes
	public List<Cliente> findAll() {
		return repo.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		// PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Pageable pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
		//throw new UnsupportedOperationException();
	}

	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if(objDto.getTelefone2() != null){
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if(objDto.getTelefone3() != null){
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}

	// Método auxiliar para atualizar os dados do cliente criado
	private void updateData(Optional<Cliente> newObj, Cliente obj) {
		// seta os valores do cliente (newObj) que veio do banco de dados, e atualiza os valores para os novos clientes (obj)
		newObj.get().setNome(obj.getNome());
		newObj.get().setEmail(obj.getEmail());
	}
}
