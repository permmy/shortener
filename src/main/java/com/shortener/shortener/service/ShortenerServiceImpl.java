package com.shortener.shortener.service;

public interface ShortenerServiceImpl {

    String shortenUrl(String url);

    String getUrl(String hashedUrl);

    Boolean validateUrl(String url);
}
