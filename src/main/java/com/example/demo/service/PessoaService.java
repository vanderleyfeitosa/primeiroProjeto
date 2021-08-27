package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.model.Pessoa;
import com.example.demo.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository  pessoaRepository ;
	public Pessoa atualizar( Long codigo, Pessoa pessoa) {
		
		buscarPessoaPeloCodigo(codigo);
		 BeanUtils.copyProperties(pessoa, "codigo");
	
		return pessoaRepository.save(pessoa);
	}
	
	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);
		pessoaSalva.setAtivo(ativo);
		pessoaRepository.save(pessoaSalva);
		
	}
		private Pessoa buscarPessoaPeloCodigo(Long codigo) {
			Optional<Pessoa> pessoaSalva = pessoaRepository.findById(codigo);
			if(pessoaSalva.isEmpty()) {
				throw new EmptyResultDataAccessException(1);
			}
			return pessoaSalva.get();
			
	}

}
