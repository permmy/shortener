package com.shortener.shortener.service;

import com.shortener.shortener.encoder.HashUrl;
import org.springframework.stereotype.Service;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;


@Service
/*
this a where the app logic is. its then injected to
the controller using springboot dependency injection
 */
public class ShortenService implements ShortenerServiceImpl {

    private HashMap<Integer,String> shorten= null;

    private static AtomicInteger at = new AtomicInteger(0);

    @Override
    /*
    this method shortens the url and saves it to a HashMap data structure
    (simulating an in-memory database)
     */
    public String shortenUrl(String url) {
        this.shorten= new HashMap<>();
        shorten.put(at.incrementAndGet(), url);
         //auto-generates an new id for every new url
        shorten.forEach((k,v) -> System.out.println("key: "+k+" value: "+v));

        return HashUrl.base64Encode(String.valueOf(at.get()));
    }

    @Override
    /*
    this method decodes the hashed url key and returns the original url
    from the HashMap
     */
    public String getUrl(String hashedUrl) {
        Integer id = Integer.parseInt(HashUrl.base64Decode(hashedUrl));
        if(shorten.containsKey(id))
        {
            return shorten.get(id);
        }

        return "url not available";
    }

    @Override
    /*
    this method validates the url
     */
    public Boolean validateUrl(String url) {

        Boolean check = true;
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            check = false;
           // e.printStackTrace();
           // e.getMessage();

        }
        return check;
    }

}
