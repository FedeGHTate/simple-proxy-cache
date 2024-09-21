package sharumaki.h.f.Cache;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class ProductsHandler implements HttpHandler {

    private final CacheMemory cacheMemory;
    private final String resource;

    private int STATUS_OK = 200;
    private int STATUS_ERROR = 500;
    public ProductsHandler(String resource, CacheMemory cacheMemory) {
        this.resource = resource;
        this.cacheMemory = cacheMemory;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String response;
        int httpStatus = STATUS_OK;

        Optional<String> optContent = cacheMemory.getContent(resource);

        if(optContent.isPresent()) {
            System.out.println("X-Cache: HIT");
            response = optContent.get();
        } else {

            System.out.println("X-Cache: MISS");
            try {
                response = this.getData();
                httpStatus = STATUS_OK;
                this.cacheMemory.add(resource,response);
            } catch (Exception e) {
                response = "{\"error\": \"Unable to fetch data\"}";
                httpStatus = STATUS_ERROR;
            }

        }

        exchange.sendResponseHeaders(httpStatus, response.length());
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
    }

    private String getData() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://dummyjson.com/products?limit=10"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
