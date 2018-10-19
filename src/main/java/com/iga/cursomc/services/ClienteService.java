package com.iga.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iga.cursomc.domain.Cliente;
import com.iga.cursomc.repositories.ClienteRepository;
import com.iga.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	// Método para buscar a categoria pelo o id
	public Cliente buscar(Integer id) {
		Cliente obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado!: Id: " + id
					+ ", Tipo: " + Cliente.class.getName());
		}
		return obj;
	}
	
	
}
