package com.example.photos;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
public class PhotosController {

    @GetMapping("/")
    public String hello(){
        return "Hello world";
    }

    private Map<String, Photo> db = new HashMap<>(){{
        put("1",new Photo("122","Image.jpg"));
    }};
    
    @GetMapping("/photos")
    public Collection<Photo> getPhotos() {
        return db.values();
    }

    @GetMapping("/photos/{id}")
    public Photo getPhotos(@PathVariable String id) {
        Photo photo =  db.get(id);
        if(photo == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return photo;
    }
    
    @DeleteMapping("/photos/{id}")
    public void deletePhotos(@PathVariable String id) {
        Photo photo =  db.remove(id);
        if(photo == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/photos")
    public String postPhotos(@RequestBody @Valid Photo photo) {
        photo.setId(UUID.randomUUID().toString());
        String key = photo.getId();
        db.put(key,photo);
        return key;
    }
    
}
