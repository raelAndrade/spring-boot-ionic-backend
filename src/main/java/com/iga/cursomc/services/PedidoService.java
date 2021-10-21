package com.iga.cursomc.services;

import com.iga.cursomc.domain.ItemPedido;
import com.iga.cursomc.domain.PagamentoComBoleto;
import com.iga.cursomc.domain.Pedido;
import com.iga.cursomc.domain.enums.EstadoPagamento;
import com.iga.cursomc.repositories.ItemPedidoRepository;
import com.iga.cursomc.repositories.PagamentoRepository;
import com.iga.cursomc.repositories.PedidoRepository;
import com.iga.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	// Método para buscar a categoria pelo o id
	public Optional<Pedido> find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Pedido não encontrado!: Id: " + id
					+ ", Tipo: " + Pedido.class.getName());
		}
		return obj;
	}

	public List<Pedido> findAll() {
		return repo.findAll();
	}

	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.find(ip.getProduto().getId()).get().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;
	}
}
