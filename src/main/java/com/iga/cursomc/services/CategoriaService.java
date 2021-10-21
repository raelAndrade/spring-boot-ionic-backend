package com.iga.cursomc.services;

import com.iga.cursomc.domain.Categoria;
import com.iga.cursomc.dto.CategoriaDTO;
import com.iga.cursomc.repositories.CategoriaRepository;
import com.iga.cursomc.services.exceptions.DataIntegrityException;
import com.iga.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	// Método para buscar a categoria pelo o id
	public Optional<Categoria> find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
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
		Optional<Categoria> newObj = find(obj.getId()); // o find irá buscar a categoria pelo ID
		updateData(newObj, obj);// atualiza o novo objeto que foi criado (newObj), com base no objeto que veio como argumento (obj)
		return repo.save(newObj.get());
	}

	// Método para deletar pelo id
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produto");
		}
	}

	public List<Categoria> findAll() {
		return repo.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		// PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Pageable pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(), objDto.getNome());
	}

	// Método auxiliar para atualizar os dados do cliente criado
	private void updateData(Optional<Categoria> newObj, Categoria obj) {
		// seta os valores do cliente (newObj) que veio do banco de dados, e atualiza os valores para os novos clientes (obj)
		newObj.get().setNome(obj.getNome());
	}
}
