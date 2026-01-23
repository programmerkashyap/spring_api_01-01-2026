package com.example.crud_old.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/files")
public class FilesController {

    @GetMapping("/uploads/{filename}")
    public ResponseEntity<Resource> getfile(@PathVariable String filename) throws IOException
    {
        String folder = "uploads/";
        Path path = Paths.get(folder).resolve(filename).normalize();

        Resource resource = new UrlResource(path.toUri());
        if(resource.exists())
        {
           return ResponseEntity.ok().
                   contentType(MediaType.IMAGE_PNG).
                   body(resource);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

}
