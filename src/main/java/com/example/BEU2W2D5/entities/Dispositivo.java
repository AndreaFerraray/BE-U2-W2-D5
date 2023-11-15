package com.example.BEU2W2D5.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.metamodel.mapping.NonAggregatedIdentifierMapping;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "dispositivi")
public class Dispositivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "stato")
    @Enumerated(EnumType.STRING)
    private Stato stato;

    @ManyToOne
    @JoinColumn
    private User user;}
