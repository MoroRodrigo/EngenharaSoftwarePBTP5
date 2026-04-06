package org.example.services;

import org.example.domain.Produto;
import org.example.exceptions.NegocioException;
import org.example.repositories.ProdutoRepository;
import org.springframework.stereotype.Service;

@Service
public class ProdutoCommandService {
    private final ProdutoRepository repository;

    public ProdutoCommandService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public void registrarProduto(String nome, double preco, String categoriaStr) {
        try {
            var categoria = org.example.domain.CategoriaProduto.valueOf(categoriaStr);
            Produto produto = new Produto(null, nome, preco, categoria);
            repository.salvar(produto);
        } catch (IllegalArgumentException e) {
            throw new NegocioException("Categoria inválida fornecida.");
        }
    }

    public void removerProduto(Long id) {
        repository.deletar(id);
    }
}
