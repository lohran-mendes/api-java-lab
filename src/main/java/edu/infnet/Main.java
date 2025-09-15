package edu.infnet;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) {
        try {
            FakeStoreClient client = new FakeStoreClient();

            String todosOsPosts = client.getAllProdutos();
            System.out.println("\nTodos os Posts: \n" + todosOsPosts);

            String primeiroPost = client.getProdutobyId(1);
            System.out.println("\nPrimeiro Post: \n" + primeiroPost);

            String produtoInserido = client.insertProduct("{}");
            System.out.println("\nProduto inserido: \n" + produtoInserido);

            String produtoDeletado = client.deleteProdutobyId(1);
            System.out.println("\nProduto deletado: \n" + produtoDeletado);

            String produtoAtualizado = client.updateProduct(1, "{}");
            System.out.println("\nProduto atualizado: \n" + produtoAtualizado);

        } catch (IOException | URISyntaxException e) {
            System.out.println(e.getMessage());
        }

    }
}