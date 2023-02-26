package com.paymentchain.customer.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.paymentchain.customer.entity.Customer;
import com.paymentchain.customer.entity.CustomerProduct;
import com.paymentchain.customer.repository.CustomerRepository;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.time.Duration;
import java.util.Collections;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@RestController
@RequestMapping("/customer")
public class CustomerRestController {
    
    @Autowired
    CustomerRepository customerRepository;
    
    private final WebClient.Builder webClientBuilder;
    
    public CustomerRestController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }
    
    
    HttpClient client = HttpClient.create()
            //Connection Timeout is
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .option(EpollChannelOption.TCP_KEEPIDLE, 300)
            .option(EpollChannelOption.TCP_KEEPINTVL, 60)
            //response timeout
            .responseTimeout(Duration.ofSeconds(1))
            .doOnConnected(connection ->{
                connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
            });
    
    
    @GetMapping()
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Optional<Customer> get(@PathVariable String id) {
        return customerRepository.findById(Long.valueOf(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable String id, @RequestBody Customer input) {
        Customer customer = customerRepository.findById(Long.valueOf(id)).get();
        
        if (customer != null) {
            customer.setCode(input.getCode());
            customer.setName(input.getName());
            
            Customer save = customerRepository.save(input);
            return ResponseEntity.ok(save);
        } 
        
        return null;
    }
    
    @PostMapping
    public ResponseEntity<?> post(@RequestBody Customer input) {
        
        input.getProducts().forEach(x -> x.setCustomer(input));
        
        Customer save = customerRepository.save(input);
        return ResponseEntity.ok(save);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        Customer customer = customerRepository.findById(Long.valueOf(id)).get();
        
        if (customer != null) {
            customerRepository.delete(customer);
        }
        
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/full")
    public Customer getByCode(@RequestParam String code){
        
        Customer customer = customerRepository.findByCode(code);
        List<CustomerProduct> products = customer.getProducts();
        products.forEach(x -> {
            String productName = getProductName(x.getProductId());
            x.setProductName(productName);
        });
        
        return customer;
    }
    
    
    private String getProductName(long id){
        
        WebClient build = webClientBuilder.clientConnector(new ReactorClientHttpConnector(client))
                                          .baseUrl("http://localhost:8082/product")
                                          .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                          .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8082/product"))
                                          .build();
        
        JsonNode block = build.method(HttpMethod.GET).uri("/" + id)
                              .retrieve().bodyToMono(JsonNode.class).block();
        
        String name = block.get("name").asText();                
        
        return name;
    }
    
}
