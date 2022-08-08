package com.xandrules.pagamento.data.vo;

import com.xandrules.pagamento.entity.Produto;
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
public class ProdutoVO extends RepresentationModel<ProdutoVO> implements Serializable {

    private Long id;

    private Integer estoque;

    public static ProdutoVO create(Produto produto){
        return new ModelMapper().map(produto, ProdutoVO.class);
    }
}
