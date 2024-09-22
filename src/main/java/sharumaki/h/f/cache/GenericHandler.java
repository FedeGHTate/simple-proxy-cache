package sharumaki.h.f.cache;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class GenericHandler implements HttpHandler {

    private final CacheMemory cacheMemory;

    private final String origin;

    private int STATUS_OK = 200;
    private int STATUS_ERROR = 500;
    public GenericHandler(String origin, CacheMemory cacheMemory) {
        this.origin = origin;
        this.cacheMemory = cacheMemory;
    }

    @Override
    public void handle(HttpExchange exchange) {

        String response;
        int httpStatus = STATUS_OK;
        String resource = exchange.getRequestURI().getPath();
        String queryParams = exchange.getRequestURI().getQuery();

        String key = resource;

        if(queryParams != null) {
            key = resource + queryParams;
        }

        Optional<String> optContent = cacheMemory.getContent(key);

        if(optContent.isPresent()) {
            System.out.println(">X-Cache: HIT");
            response = optContent.get();
        } else {

            System.out.println(">X-Cache: MISS");
            try {
                response = this.getData(resource);
                httpStatus = STATUS_OK;
                this.cacheMemory.add(key,response);
            } catch (Exception e) {
                response = "{\"error\": \"Unable to fetch data\"}";
                httpStatus = STATUS_ERROR;
            }

        }

        try {
            exchange.sendResponseHeaders(httpStatus, response.getBytes().length);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private String getData(String resource) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(origin + resource))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
