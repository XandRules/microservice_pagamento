package com.xandrules.pagamento.services;

import com.xandrules.pagamento.data.vo.VendaVO;
import com.xandrules.pagamento.entity.ProdutoVenda;
import com.xandrules.pagamento.entity.Venda;
import com.xandrules.pagamento.exception.ResourceNotFoundException;
import com.xandrules.pagamento.repository.ProdutoVendaRepository;
import com.xandrules.pagamento.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VendaService {
    private final VendaRepository vendaRepository;
    private final ProdutoVendaRepository produtoVendaRepository;

    @Autowired
    public VendaService(VendaRepository vendaRepository, ProdutoVendaRepository produtoVendaRepository) {
        this.vendaRepository = vendaRepository;

        this.produtoVendaRepository = produtoVendaRepository;
    }

    public VendaVO create(VendaVO vendaVO){
        var venda = vendaRepository.save(Venda.create(vendaVO));

        List<ProdutoVenda> produtosSalvos = new ArrayList<>();

        vendaVO.getProdutos().forEach(produto -> {
            ProdutoVenda produtoVenda = ProdutoVenda.create(produto);
            produtoVenda.setVenda(venda);
            produtosSalvos.add(produtoVendaRepository.save(produtoVenda));

        });
        venda.setProdutos(produtosSalvos);
        return VendaVO.create(venda);
    }

    public Page<VendaVO> findAll(Pageable pageable){
        var page = vendaRepository.findAll(pageable);

       return page.map(this::convertToVendaVO);
    }

    private VendaVO convertToVendaVO(Venda venda){
        return VendaVO.create(venda);
    }

    public VendaVO findById(Long id){
        var entity = vendaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum dado encontrado para esse id"));;

        return VendaVO.create(entity);
    }
}
