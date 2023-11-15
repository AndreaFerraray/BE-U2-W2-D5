package com.example.BEU2W2D5.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "Users")


public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nome",nullable = false)
    private String nome;
    @Column(name = "cognome",nullable = false)
    private String cognome;
    @Column(name = "email",nullable = false)
    private String email;
    @Column(name = "username")
    private String username;
    @Column(name = "immagine")
    private String imageUrl;


    @JsonIgnore
    @OneToMany(mappedBy = "user")
    List<Dispositivo> listaDispositivi;

}
