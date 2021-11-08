package com.previred.test.challenges;

import com.previred.test.challenges.util.PatchJSonPeriod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Se tomo como base el codigo del cliente Rest de la siguiente pagina
 * https://medium.com/swlh/getting-json-data-from-a-restful-api-using-java-b327aafb3751
 */
public class Challenge2 {
    private static final String apiURL = "http://127.0.0.1:8080/periodos/api";

    public static void main(String[] args) {
        Challenge2 challenge = new Challenge2();
        PatchJSonPeriod patcher = new PatchJSonPeriod();
        String result;
        try {
            String apiResponse = challenge.callRestAPIPeriodos(apiURL);
            result = patcher.patchJSonPeriod(apiResponse);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Llama la API que calcula periodos y retorna la respuesta de la api
     * @return
     */
    private String callRestAPIPeriodos(String apiURL) throws Exception{
        String result = null;

        try {
            URL url = new URL(apiURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            conn.setRequestProperty("Accept","application/json");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.connect();

            int responsecode = 0;
            //Getting the response code
            responsecode = conn.getResponseCode();

            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {
                result = readResponseAPI(conn);
            }
        }catch(java.net.MalformedURLException e){
            throw new Exception("Error al llamar a la API: "+apiURL+". "+e.getMessage());
        } catch (ProtocolException e) {
            throw new Exception("Error al llamar a la API: "+apiURL+". "+e.getMessage());
        } catch (IOException e) {
            throw new Exception("Error al llamar a la API: "+apiURL+". "+e.getMessage());
        } catch (Exception e) {
            throw new Exception("Error al llamar a la API: "+apiURL+". "+e.getMessage());
        }

        return result;
    }

    private String readResponseAPI(HttpURLConnection conn ) throws  Exception{
        String response = null;
        String inputLine;

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder apiResponse = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                apiResponse.append(inputLine);
            }

            response = apiResponse.toString();

        } catch (IOException e) {
            throw new Exception("Error al leer respuesta de la API. "+ e.getMessage());
        } catch (Exception e) {
            throw new Exception("Error al leer respuesta de la API. "+ e.getMessage());
        }

        return response;
    }
}
