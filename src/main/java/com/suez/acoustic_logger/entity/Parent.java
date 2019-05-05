package com.suez.acoustic_logger.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "created"}, ignoreUnknown = true)
public class Parent {

    //private String href;
    @Id
    @Column(name = "parent_id")
    private String id;
    private String name;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    @JsonIgnore
    private List<AcousticLogger> loggers;
}
