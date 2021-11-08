package com.previred.test.challenges.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;

public class PatchJSonPeriod {


    public String patchJSonPeriod(String json) throws Exception{
        JSONObject jsonInitPeriod,jsonPatchedPeriod;
        JSONArray missingDates;
        String result;

        //Parseamos el string con el json
        jsonInitPeriod = parseJSONString(json);
        //Calculamos las fechas faltantes o perdidas
        missingDates= calculateMissingDates(jsonInitPeriod);
        //Construimos las respuesta
        jsonPatchedPeriod = (JSONObject) buildResponse(jsonInitPeriod,missingDates);
        result = jsonPatchedPeriod.toJSONString();
        return result;
    }
    /**
     * Parseo del archivo json a objeto del tipo JSONOBject
     * @param json estructura JSON
     * @return Objecto JSONObject, con contenido del archivo json
     * @throws Exception Erro en el parseo del archivo.
     */
    private JSONObject parseJSONString(String json) throws Exception{
        JSONObject jsonObject = null;
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(json);
            jsonObject = (JSONObject) obj;
        } catch (ParseException e) {
            throw new Exception("Error al parsear contenido del archivo json. "+e.getMessage());
        } catch (Exception e) {
            throw new Exception("Error al parsear contenido del archivo json. "+e.getMessage());
        }

        return jsonObject;
    }

    /**
     * Calculamos las fechas faltante en el objecto JSONObject
     * @param jsonInitPeriod Objecto JSon inicial
     * @return Arreglo con las fechas faltantes
     * @throws Exception
     */
    //TODO: Validar que fecha init sea menor que fecha fin
    //TODO: Validar si las fechas en jsonInitDAtes, no son vacias y tienen el formato correcto
    private JSONArray calculateMissingDates(JSONObject jsonInitPeriod) throws Exception{
        JSONArray result = null;

        try {
            LocalDate periodInitDate,periodFinalDate,bufferDate;

            periodInitDate = parseStringDate((String) jsonInitPeriod.get("fechaCreacion"));
            periodFinalDate = parseStringDate((String) jsonInitPeriod.get("fechaFin"));
            JSONArray jsonInitDates = (org.json.simple.JSONArray) jsonInitPeriod.get("fechas");

            Period age = Period.between(periodInitDate, periodFinalDate);
            Calendar calendar = Calendar.getInstance();

            bufferDate = periodInitDate;
            result = new JSONArray();
            for(int i=0;i<(age.getYears()*12)+age.getMonths()+1;i++){
                if (!jsonInitDates.contains((String) bufferDate.toString())){
                    result.add(bufferDate.toString());
                }
                bufferDate = bufferDate.plusMonths(1);
            }

        }catch(Exception e){
            throw new Exception("Error al calcular fechas faltantes en json con periodos. "+e.getMessage());
        }

        return result;
    }

    /**
     * Parsea un facha en formato 'yyyy-MM-dd'. Incluye validaciones:
     * No sea nulo.
     * No sea un string vacio.
     * Que cumpla el formato 'yyyy-MM-dd'
     *
     * @param date Fecha en formato 'yyyy-MM-dd'.
     * @return fecha parseada
     * @throws Exception
     */
    private LocalDate parseStringDate(String date) throws Exception{
        LocalDate result;

        if (date == null){
            throw new Exception("Fecha nula");
        }
        else if (date.length() < 1){
            throw new Exception("Fecha vacia");
        }
        else{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            try {
                result=LocalDate.parse(date, formatter);;
            }catch(DateTimeParseException e){
                throw new Exception("fecha con formato invalido '"+date+"', se espera fecha con formato: 'yyyy-MM-dd'.");
            }
        }
        return  result;
    }


    /**
     * Construye la repuesta actualizando las fechas faltantes en el JSONObject inicial.
     * @param jsonInitPeriod JSONObject con el archivo o input inicial.
     * @param missingDates JSONArray con arreglo de fachas faltantes.
     * @return JSONObject con el fechas faltantes.
     */
    private Object buildResponse(JSONObject jsonInitPeriod, JSONArray missingDates){
        jsonInitPeriod.put("fechas",missingDates);
        return jsonInitPeriod;
    }

}
