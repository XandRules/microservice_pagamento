package com.xandrules.pagamento.data.vo;

import com.xandrules.pagamento.entity.ProdutoVenda;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ProdutoVendaVO extends RepresentationModel<ProdutoVendaVO> implements Serializable {

    private Long id;
    private Long idProduto;
    private Integer quantidade;

    public static ProdutoVendaVO create(ProdutoVenda produtoVenda){
        return new ModelMapper().map(produtoVenda, ProdutoVendaVO.class);
    }
}
