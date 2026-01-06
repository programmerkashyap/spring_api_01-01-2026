package com.example.crud_old.controller;

import com.example.crud_old.models.Products;
import com.example.crud_old.repository.ProductsRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductsController {

    private static final Logger log = LoggerFactory.getLogger(ProductsController.class);
    private ProductsRepository productsRepository;
    public ProductsController(ProductsRepository productsRepository)
    {
        this.productsRepository = productsRepository;
    }

    @Operation(summary = "To Create New Product")
    @PostMapping(value="/products", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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

    @GetMapping("/products")
    public List<Products> showall()
    {
        return productsRepository.findAll();
    }

    @PutMapping("/products")
    public Products update(
            @RequestParam("id") String id,
            @RequestParam("name") String name,
            @RequestParam("price") String price,
            @RequestParam("discount") String discount,
            @RequestParam("description") String description,
            @RequestParam(value = "picture", required = false) MultipartFile picture
    )
    {
        if(picture == null)
        {
            // get old data first
            Products olddata = productsRepository.findById(Long.parseLong(id)).orElse(null);

            // update new data into database
            Products p1 = new Products();
            p1.setId(Long.parseLong(id));
            p1.setName(name);
            p1.setPrice(Long.parseLong(price));
            p1.setDiscount(Long.parseLong(discount));
            p1.setDescription(description);
            p1.setPicture(olddata.getPicture()); // set old picture
            return productsRepository.save(p1);
        }
        else
        {

            // save picture in folder
            String folder = "uploads/";
            String filename = System.currentTimeMillis()+"_"+picture.getOriginalFilename();
            Path path = Paths.get(folder, filename);
            try
            {
                Files.write(path, picture.getBytes());
            }
            catch (Exception ex)
            {
                log.error("Failed to Upload - "+ex.getMessage());
            }

            // save data into database
            Products p1 = new Products();
            p1.setId(Long.parseLong(id));
            p1.setName(name);
            p1.setPrice(Long.parseLong(price));
            p1.setDiscount(Long.parseLong(discount));
            p1.setDescription(description);

            p1.setPicture(filename); // set new picture

            return productsRepository.save(p1);

        }

    }

    @DeleteMapping("/products/{id}")
    public String delete(@PathVariable Long id)
    {
        Products data = productsRepository.findById(id).orElse(null);
        if(data!=null)
        {
            productsRepository.deleteById(id);
            return "Data deleted";
        }
        else
        {
            return "Data not deleted";
        }

    }

    @GetMapping("/products/{id}")
    public Products getsingle(@PathVariable Long id)
    {
        return productsRepository.findById(id).orElse(null);
    }

}
