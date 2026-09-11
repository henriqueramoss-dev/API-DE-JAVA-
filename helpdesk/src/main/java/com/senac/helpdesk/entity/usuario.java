package com.senac.helpdesk.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class usuario {

    @Id
    @GeneratedValue
    private Long id;

    private String nome;

    private  String email;

    public usuario() {
    }

    
}
