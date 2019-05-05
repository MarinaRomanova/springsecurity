package com.suez.acoustic_logger.controller;

import com.suez.acoustic_logger.entity.AcousticLogger;
import com.suez.acoustic_logger.repository.AcousticLoggerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.StyledEditorKit;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/loggers")
public class AcousticRestController {

    @Autowired
    AcousticLoggerRepository repository;

    //READ
    @GetMapping(value = {"", "/"})
    public Iterable<AcousticLogger> listLoggers(){
       return repository.findAll();
    }

    //READ by id
    @GetMapping("/{id}")
    public AcousticLogger getLogger(@PathVariable String id){
        Optional<AcousticLogger> optional = repository.findById(id);
        return optional.get();
    }

    //CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AcousticLogger addLogger(@RequestBody AcousticLogger logger){
        logger.setId(UUID.randomUUID().toString());
        return repository.save(logger);
    }

    //UPDATE
    @PutMapping("/{id}")
    public AcousticLogger updateLogger(@RequestBody AcousticLogger logger, @PathVariable String id){
        return repository.save(logger);
    }

    //DELETE
    @DeleteMapping("/{id}")
    public void deleteLogger(@PathVariable String id){
        Optional<AcousticLogger> optional = repository.findById(id);
        optional.ifPresent(logger -> repository.delete(logger));
    }

}
