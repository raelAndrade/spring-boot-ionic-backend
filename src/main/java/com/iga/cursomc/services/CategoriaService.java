package com.iga.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.iga.cursomc.domain.Categoria;
import com.iga.cursomc.repositories.CategoriaRepository;
import com.iga.cursomc.services.exceptions.DataIntegrityException;
import com.iga.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	// Método para buscar a categoria pelo o id
	public Categoria find(Integer id) {
		Categoria obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado!: Id: " + id
					+ ", Tipo: " + Categoria.class.getName());
		}
		return obj;
	}
	
	// Método para inserir categoria
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	// Método para atualizar a categoria pelo ID
	public Categoria update(Categoria obj) {
		find(obj.getId()); // o find irá buscar a categoria pelo ID
		return repo.save(obj);
	}

	// Método para deletar pelo id
	public void delete(Integer id) {
		find(id);
		try {
			repo.delete(id);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produto");
		}
		
	}

	public List<Categoria> findAll() {
		return repo.findAll();
	}
	
	
}
