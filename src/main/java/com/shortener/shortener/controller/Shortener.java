package com.shortener.shortener.controller;


import com.shortener.shortener.service.ShortenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller //app controller in mvc design pattern
public class Shortener {

    private final ShortenService shortenService;


    @Autowired  // Shorten service constructor
    public Shortener(ShortenService shortenService) {
        this.shortenService = shortenService;
    }

    @Value("${baseurl}")
    private String baseUrl;   //url in application.properties file in resources directory

    @GetMapping("/")
    /*
    a GET method that returns the index page, shortened url or error message
     */
    public ModelAndView index(@RequestParam(required = false) String error, @RequestParam(required = false) String shortened) {
        ModelAndView modelAndView = new ModelAndView("index");
//        if (!Objects.equals(error, " ")) {
            modelAndView.addObject("error", error);
            modelAndView.addObject("shortened", shortened);
//        } else if (!Objects.equals(shortened, " ")) {
//            modelAndView.addObject("shortened", shortened);
//        }

        return modelAndView;
    }

    @PostMapping("/shrink")
    /*
    a POST method that validates url, shortens the url and returns the new url or error message.
    This method redirects to the index method
     */
    public ModelAndView shrink(@RequestParam String url, RedirectAttributes redirect) {
        ModelAndView modelAndView = new ModelAndView();
        if (Objects.equals(url, " ") || !shortenService.validateUrl(url)) {
            redirect.addAttribute("error", "enter a valid url");
            modelAndView.setViewName("redirect:/");

        } else {
            redirect.addAttribute("shortened", baseUrl.concat(shortenService.shortenUrl(url)));
            modelAndView.setViewName("redirect:/");
        }

        return modelAndView;
    }

    @PostMapping("/api/shrink")
    /*
    a POST API method that validates url, shortens the url and returns the new url or error message.
    */
    @ResponseBody
    public List<String> shrinkApi(@RequestParam String url) {

        List<String> response= new ArrayList<>();

        if (Objects.equals(url, " ") || !shortenService.validateUrl(url)) {
            response.add("enter a valid url");
            return response;
        } else {
            response.add(baseUrl.concat(shortenService.shortenUrl(url)));
            return response;
        }

    }

    @PostMapping("/api/original")
    /*
    a POST method that retrieves the original url
     */
    @ResponseBody
    public List<String> original(@RequestParam String url)
    {
        List<String> response= new ArrayList<>();
        if(Objects.equals(url, " "))
        {
            response.add("missing parameters");
            return response;
        }
        response.add(shortenService.getUrl(url.substring(baseUrl.length())));
        return response;
    }

}
