package com.exercicio.favoritos.servicos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import org.json.JSONObject;

import com.exercicio.favoritos.dto.FavoritoClienteDTO;

public class ProdutoDados {
    private static final String baseURL = "http://challenge-api.luizalabs.com/api/product/%s/";
    public static FavoritoClienteDTO resgataDadosProduto(String id){
        try {
            String content = ProdutoDados.getResponse(String.format(baseURL, id));
            JSONObject jo = new JSONObject(content);
            FavoritoClienteDTO res = new FavoritoClienteDTO(id, jo.getString("title"),jo.getString("image")
            ,jo.getFloat("price"));    
            return res;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getResponse(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            try {
                // Set up the connection
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");

                // Read the response
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    return response.toString();
                }
            } finally {
                connection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
