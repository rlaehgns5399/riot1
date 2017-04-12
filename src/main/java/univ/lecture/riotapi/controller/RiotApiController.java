package univ.lecture.riotapi.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import univ.lecture.riotapi.model.Summoner;
import univ.lecture.riotapi.model.CalcApp;
import univ.lecture.riotapi.model.Operator;

import java.io.UnsupportedEncodingException;
import java.util.Map;

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
    private String riotApiEndpoint = "https://demo2446904.mockable.io/api/v1/answer/";

    @Value("${riot.api.key}")
    private String riotApiKey;

    @RequestMapping(value = "/calc/{name}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public double queryCalc(@PathVariable("name") String exp) throws UnsupportedEncodingException {
        final String url = riotApiEndpoint;
        
        CalcApp p = new CalcApp();
        String[] expArray = exp.split(" ");
        System.out.println("expression: " + exp);
        for(int i = 0; i < expArray.length; i++){
        	System.out.println("expArray[" + i + "] : " + expArray[i]);
        }
        double result = p.calc(expArray);
        System.out.println("result: " + result);
        String response = restTemplate.getForObject(url, String.class);
        Map<String, Object> parsedMap = new JacksonJsonParser().parseMap(response);

        parsedMap.forEach((key, value) -> log.info(String.format("key [%s] type [%s] value [%s]", key, value.getClass(), value)));

        /**
        Map<String, Object> summonerDetail = (Map<String, Object>) parsedMap.values().toArray()[0];
        String queriedName = (String)summonerDetail.get("name");
        int queriedLevel = (Integer)summonerDetail.get("summonerLevel");
        Summoner summoner = new Summoner(queriedName, queriedLevel);
		*/
        return result;
    }
}
