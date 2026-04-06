package org.example.repositories;

import org.example.domain.Produto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProdutoRepository {
    private final List<Produto> produtos = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Produto salvar(Produto produto) {
        Produto novoProduto = new Produto(idGenerator.getAndIncrement(), produto.nome(), produto.precoBase(), produto.categoria());
        produtos.add(novoProduto);
        return novoProduto;
    }

    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos);
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtos.stream().filter(p -> p.id().equals(id)).findFirst();
    }

    public void deletar(Long id) {
        produtos.removeIf(p -> p.id().equals(id));
    }
}
