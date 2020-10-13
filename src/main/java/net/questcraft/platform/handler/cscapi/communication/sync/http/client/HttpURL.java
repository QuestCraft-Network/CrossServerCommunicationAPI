package net.questcraft.platform.handler.cscapi.communication.sync.http.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class HttpURL {
    private final Map<String, String> params;
    private final String path;

    public HttpURL(String path) {
        this.path = path;
        this.params = new HashMap<>();
    }

    public HttpURL(Map<String, String> params, String path) {
        this.params = params;
        this.path = path;
    }

    public HttpURL(Builder builder) {
        this.params = builder.params;
        this.path = builder.path;
    }

    public String connect() throws IOException {
        URL url = new URL(this.createURL());
        URLConnection connection = url.openConnection();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()));

        String currentInput;
        StringBuilder input = new StringBuilder();
        while ((currentInput = reader.readLine()) != null) input.append(currentInput);

        return input.toString();
    }


    private String createURL() {
        return this.params.keySet().stream().map(key -> key + "=" + getEncode(this.params.get(key)))
                .collect(Collectors.joining("&", this.path + "?", ""));
    }

    private String getEncode(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            Logger.getLogger("HttpURL").log(Level.WARNING, "Error while encoding String: " + value);
            return value;
        }
    }


    public static class Builder {
        private String path;
        private Map<String, String> params;

        public Builder() {
            this.params = new HashMap<>();
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder param(String k, String v) {
            this.params.put(k, v);
            return this;
        }

        public Builder params(Map<String, String> params) {
            this.params.putAll(params);
            return this;
        }

        public HttpURL build() {
            return new HttpURL(this);
        }
    }
}
