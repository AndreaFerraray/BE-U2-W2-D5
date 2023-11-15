package com.example.BEU2W2D5.Repository;

import com.example.BEU2W2D5.entities.Dispositivo;
import com.example.BEU2W2D5.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    //CORRISPONDENZA DISPOSITIVO UTENTE
    @Query
            (value = "select u.listaDispositivi from User u where u.id=:id")
    Optional<List<Dispositivo>> findDispositivoById(int id);

}