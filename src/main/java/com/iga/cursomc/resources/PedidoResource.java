package com.iga.cursomc.resources;

import com.iga.cursomc.domain.Categoria;
import com.iga.cursomc.dto.CategoriaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iga.cursomc.domain.Pedido;
import com.iga.cursomc.services.PedidoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService service;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Optional<Pedido>> find(@PathVariable Integer id) {
		Optional<Pedido> obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Pedido>> findAll() {
		List<Pedido> list = service.findAll();
		// List<Pedido> listDto = list.stream().map(obj -> new Pedido(obj)).collect(Collectors.toList());
		// return ResponseEntity.ok().body(listDto);
		return ResponseEntity.ok().body(list);
	}
}
