package com.xandrules.pagamento.controller;

import com.xandrules.pagamento.data.vo.VendaVO;
import com.xandrules.pagamento.services.VendaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/venda")
public class VendaController {

    private final VendaService vendaService;
    private final PagedResourcesAssembler<VendaVO> assembler;

    public VendaController(VendaService vendaService, PagedResourcesAssembler<VendaVO> assembler) {
        this.vendaService = vendaService;
        this.assembler = assembler;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public VendaVO findById(Long id){
        VendaVO vendaVO = vendaService.findById(id);
        vendaVO.add(linkTo(methodOn(VendaController.class).findById(id)).withSelfRel());
        return vendaVO;
    }

    @GetMapping( produces = {"application/json"})
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "limit", defaultValue = "10") int limit,
                                     @RequestParam(value = "direction", defaultValue = "asc") String direction){

        var sortDirection = "desc".equalsIgnoreCase(direction)? Sort.Direction.DESC: Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page,limit,Sort.by(sortDirection, "data") );
        Page<VendaVO> vendas = vendaService.findAll(pageable);
        vendas.stream()
                .forEach(v -> v.add(linkTo(methodOn(VendaController.class)
                        .findById(v.getId()))
                        .withSelfRel()));

        PagedModel<EntityModel<VendaVO>> pagedModel = assembler.toModel(vendas);

        return new ResponseEntity<>(pagedModel, HttpStatus.OK);

    }


    @PostMapping(produces = {"application/json"}, consumes = { "application/json"})
    public VendaVO create(@RequestBody VendaVO vendaVO){
        VendaVO venda = vendaService.create(vendaVO);
        venda.add(linkTo(methodOn(VendaController.class).findById(venda.getId())).withSelfRel());

        return venda;
    }
}
