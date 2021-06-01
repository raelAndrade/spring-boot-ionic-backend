package com.iga.cursomc.services;

import com.iga.cursomc.domain.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iga.cursomc.domain.Pedido;
import com.iga.cursomc.repositories.PedidoRepository;
import com.iga.cursomc.services.exceptions.ObjectNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	// Método para buscar a categoria pelo o id
	public Optional<Pedido> find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado!: Id: " + id
					+ ", Tipo: " + Pedido.class.getName());
		}
		return obj;
	}

	public List<Pedido> findAll() {
		return repo.findAll();
	}
}
