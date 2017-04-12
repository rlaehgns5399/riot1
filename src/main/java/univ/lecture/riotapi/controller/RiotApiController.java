package univ.lecture.riotapi.controller;

import lombok.extern.log4j.Log4j;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import univ.lecture.riotapi.model.Summoner;
import univ.lecture.riotapi.model.CalcApp;
import univ.lecture.riotapi.model.Operator;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by tchi on 2017. 4. 1..
 */
@RestController
@RequestMapping("/api/v1")
@Log4j
public class RiotApiController {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${riot.api.endpoint}")
    private String riotApiEndpoint;

    @Value("${riot.api.key}")
    private String riotApiKey;
    
    @RequestMapping(value = "/calc", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Summoner queryCalc(@RequestBody String exp) throws UnsupportedEncodingException{
        final String url = riotApiEndpoint;

        CalcApp p = new CalcApp();
        String[] expArray = exp.split(" ");
        double result = p.calc(expArray);
        
        int teamId = 14;
        long now = System.currentTimeMillis(); 
        JSONObject jsonObject = new JSONObject();
        
		jsonObject.put("teamId", teamId);
		jsonObject.put("now", System.currentTimeMillis());
		jsonObject.put("result", result);
		
		String response = restTemplate.postForObject(url, jsonObject, String.class);
		System.out.println(response);
		
        Summoner summoner = new Summoner(teamId, now, result);
        return summoner;
    }
}
