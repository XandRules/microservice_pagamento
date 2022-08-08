package com.xandrules.pagamento.entity;

import com.xandrules.pagamento.data.vo.ProdutoVendaVO;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "produto_venda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ProdutoVenda implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "id_produto", nullable = false)
    private Long idProduto;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_venda")
    private Venda venda;

    public static ProdutoVenda create(ProdutoVendaVO produtoVendaVO){
        return new ModelMapper().map(produtoVendaVO, ProdutoVenda.class);
    }

}
