package com.xandrules.pagamento.data.vo;

import com.xandrules.pagamento.entity.Venda;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class VendaVO extends RepresentationModel<VendaVO> implements Serializable {

    private Long id;
    private Date data;
    private List<ProdutoVendaVO> produtos;
    private Double valorTotal;

    public static VendaVO create(Venda venda){
        return new ModelMapper().map(venda, VendaVO.class);
    }
}
