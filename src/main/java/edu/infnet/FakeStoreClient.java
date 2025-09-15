package edu.infnet;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class FakeStoreClient {

    String url = "https://fakestoreapi.com";

    private HttpsURLConnection gerarConexaoHttp(String resource, String method) throws IOException, URISyntaxException {
        URI uri = new URI(url + resource);
        URL url = uri.toURL();

        HttpsURLConnection conexao = (HttpsURLConnection) url.openConnection();
        conexao.setRequestMethod(method);
        conexao.setRequestProperty("Accept", "application/json");
        conexao.setConnectTimeout(5000);

        return conexao;
    }


    private String tratarResposta(HttpsURLConnection conexao) throws IOException {
        StringBuilder sbResponse = new StringBuilder();

        InputStream inputStream = conexao.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String responseLine;

        while ((responseLine = bufferedReader.readLine()) != null) {
            sbResponse.append(responseLine.trim());
        }
        return sbResponse.toString();
    }

    private HttpsURLConnection configurarBody(HttpsURLConnection conexao, String body) throws IOException {
        conexao.setDoOutput(true);
        OutputStream outputStream = conexao.getOutputStream();
        byte[] bBody = body.getBytes();
        outputStream.write(bBody);

        return conexao;
    }

    public String getAllProdutos() throws IOException, URISyntaxException {
        HttpsURLConnection conexao = gerarConexaoHttp("/products", "GET");

        int responseCode = conexao.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            return tratarResposta(conexao);
        } else {
            System.out.println("Falha na conexao com o código: " + responseCode);
            return null;
        }
    }

    public String getProdutobyId(int id) throws IOException, URISyntaxException {
        HttpsURLConnection conexao = gerarConexaoHttp("/products/" + id, "GET");

        int responseCode = conexao.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            return tratarResposta(conexao);
        } else {
            System.out.println("Falha na conexao com o código: " + responseCode);
            return null;
        }
    }

    public String deleteProdutobyId(int id) throws IOException, URISyntaxException {
        HttpsURLConnection conexao = gerarConexaoHttp("/products/" + id, "DELETE");

        int responseCode = conexao.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            return tratarResposta(conexao);
        } else {
            System.out.println("Falha na conexao com o código: " + responseCode);
            return null;
        }
    }


    public String insertProduct(String body) throws IOException, URISyntaxException {
        HttpsURLConnection conexao = configurarBody(gerarConexaoHttp("/products", "POST"), body);

        int responseCode = conexao.getResponseCode();

        if (responseCode == HttpsURLConnection.HTTP_OK || responseCode == HttpsURLConnection.HTTP_CREATED) {
            return tratarResposta(conexao);
        } else {
            System.out.println("Falha na conexao com o código: " + responseCode);
            return null;
        }
    }

    public String updateProduct(int id, String body) throws IOException, URISyntaxException {
        HttpsURLConnection conexao = configurarBody(gerarConexaoHttp("/products/" + id, "PUT"), body);

        int responseCode = conexao.getResponseCode();

        if (responseCode == HttpsURLConnection.HTTP_OK) {
            return tratarResposta(conexao);
        } else {
            System.out.println("Falha na conexao com o código: " + responseCode);
            return null;
        }
    }
}
