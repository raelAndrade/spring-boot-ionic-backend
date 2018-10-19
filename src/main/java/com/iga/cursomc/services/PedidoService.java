package com.iga.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iga.cursomc.domain.Pedido;
import com.iga.cursomc.repositories.PedidoRepository;
import com.iga.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	// Método para buscar a categoria pelo o id
	public Pedido buscar(Integer id) {
		Pedido obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado!: Id: " + id
					+ ", Tipo: " + Pedido.class.getName());
		}
		return obj;
	}
	
	
}
