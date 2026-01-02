package com.example.crud_old.controller;

import com.example.crud_old.models.Products;
import com.example.crud_old.repository.ProductsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api")
public class ProductsController {

    private static final Logger log = LoggerFactory.getLogger(ProductsController.class);
    private ProductsRepository productsRepository;
    public ProductsController(ProductsRepository productsRepository)
    {
        this.productsRepository = productsRepository;
    }

    @PostMapping("/products")
    public Products create(
            @RequestParam("name") String name,
            @RequestParam("price") String price,
            @RequestParam("discount") String discount,
            @RequestParam("description") String description,
            @RequestParam("picture") MultipartFile picture
            )
    {

        // file uploading
        String folder = "uploads/";
        String filename = picture.getOriginalFilename();
        Path path = Paths.get(folder, filename);
        try {
            Files.write(path, picture.getBytes());
        } catch (Exception e) {
            log.info("Upload:- "+e.getMessage());
        }

        // store data in database
        Products data = new Products();
        data.setName(name);
        data.setPrice(Long.parseLong(price));
        data.setDiscount(Long.parseLong(discount));
        data.setDescription(description);
        data.setPicture(filename);

        // save
        return productsRepository.save(data);

    }

}
