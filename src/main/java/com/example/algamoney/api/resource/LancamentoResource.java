package com.example.algamoney.api.resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.repository.LancamentoRepository;
import com.example.algamoney.api.service.LancamentoService;

@RestController
@RequestMapping(path = "/lancamentos")
public class LancamentoResource extends AbstractResource<Lancamento, LancamentoRepository, LancamentoService> {

}