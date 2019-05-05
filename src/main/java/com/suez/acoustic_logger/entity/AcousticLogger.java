package com.suez.acoustic_logger.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data// all service methods like toString, Getters and Setters, etc.
@NoArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "created"}, ignoreUnknown = true)
public class AcousticLogger {

    @Id
    private String id;
    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;
    private String name;
    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Parent parent;
    //private Noise noise;
    //private Activation activation;
    // private Events events;

/*
    //@Data
    private class Noise {
        private String href;
    }

    //@Data
    private class Activation {
        private String href;
    }

    //@Data
    private class Events {
        public String href;
    }
    */
}
