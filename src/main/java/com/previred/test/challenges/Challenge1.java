package com.previred.test.challenges;

import com.previred.test.challenges.util.PatchJSonPeriod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 *
 *
 */
public class Challenge1 {

    private static final String AYUDA_USO = "\nPara validar un archivo json con periodos de fechas, se debe invocar de la siguiente forma Desafio1 < nombre_archivo.extension";

    public static void main(String[] args) throws Exception
    {
        PatchJSonPeriod jsonPatcher;
        Challenge1 challenge = new Challenge1();
        String json, result;

        //Leemos entrada estandar
        json = challenge.readStandarInput();
        //Parseamos el archivo a un objeto Java
        jsonPatcher = new PatchJSonPeriod();
        result = jsonPatcher.patchJSonPeriod(json);
        //Imprimimos la respuesta
        System.out.println(result);

    }


    /**
     *
     * Leectura del standard input
     * @return Json recibido por standar input
     * @throws Exception
     */
    private String readStandarInput()  throws Exception{
        StringBuilder fileContent = new StringBuilder();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            while((line = reader.readLine()) != null){
                fileContent.append(line);
            }
        } catch (IOException e) {
            throw new Exception("Error al leer el standar input o entrada por consola. "+e.getMessage());
        }

        
        return fileContent.toString();
    }



}
