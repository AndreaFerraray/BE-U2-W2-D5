package com.example.BEU2W2D5.Service;


import com.example.BEU2W2D5.Exceptions.NotFound;
import com.example.BEU2W2D5.Exceptions.SingleBadRequest;
import com.example.BEU2W2D5.Exceptions.Unauthorized;
import com.example.BEU2W2D5.Repository.DispositivoRepository;
import com.example.BEU2W2D5.entities.Dispositivo;
import com.example.BEU2W2D5.entities.Payload.DispositivoPayload;
import com.example.BEU2W2D5.entities.Payload.SetDispositivoPayload;
import com.example.BEU2W2D5.entities.Payload.StateDispositivoPayload;
import com.example.BEU2W2D5.entities.Stato;
import com.example.BEU2W2D5.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class DispositivoService {
    @Autowired
    private static DispositivoRepository dispositivoRepository;

    @Autowired
    static UserService userService;




    public Page<Dispositivo> getAllDispositivi(int page, int size, String order){
        Pageable p= PageRequest.of(page,size, Sort.by(order));
        return dispositivoRepository.findAll(p);

    }

    public static Dispositivo getSingleDispositivo(int id){
        return dispositivoRepository.findById(id).orElseThrow(()->new NotFound("elemento non trovato"));
    }

    public Dispositivo createDispositivo(DispositivoPayload d){
        Dispositivo dispositivo=Dispositivo.builder().tipo(d.tipo()).stato(Stato.DISPONIBILE).build();
        return dispositivoRepository.save(dispositivo);
    }

    public Dispositivo setUserDispositivo(SetDispositivoPayload dis, int id){
        Dispositivo dispositivo=this.getSingleDispositivo(id);
        User user=userService.getSingleUser(dis.user_id());
        if(dis.stato().equals("DISPONIBILE")||dis.stato().equals("ASSEGNATO")||dis.stato().equals("DISMESSO")||dis.stato().equals("MANUTENZIONE")) {

            if (dispositivo.getStato() == Stato.DISPONIBILE && Stato.valueOf(dis.stato()) == Stato.ASSEGNATO) {
                dispositivo.setStato(Stato.valueOf(dis.stato()));
                dispositivo.setUser(user);
                dispositivoRepository.save(dispositivo);
                return dispositivo;
            } else throw new Unauthorized("Dispositivo già assegnato o  non DISPONIBILE");
        }
        else
            throw new SingleBadRequest("Stato scelto inesistente");

    }

    public void deleteDispositivo(int id){
        Dispositivo dis=this.getSingleDispositivo(id);
        dispositivoRepository.delete(dis);
    }


    public static Dispositivo setStato(StateDispositivoPayload body, int id){
        Dispositivo dis=getSingleDispositivo(id);
        if(body.stato().equals("DISPONIBILE")||body.stato().equals("ASSEGNATO")||body.stato().equals("DISMESSO")||body.stato().equals("MANUTENZIONE")) {
            switch (Stato.valueOf(body.stato())) {
                case ASSEGNATO -> {
                    throw new Unauthorized("EndPoint sbagliato non puoi assegnare uno user qui");
                }
                case DISMESSO -> {
                    dis.setUser(null);
                    dis.setStato(Stato.DISMESSO);
                    dispositivoRepository.save(dis);
                    return dis;
                }
                case DISPONIBILE -> {
                    dis.setUser(null);
                    dis.setStato(Stato.DISPONIBILE);
                    dispositivoRepository.save(dis);
                    return dis;
                }
                case MANUTENZIONE -> {
                    dis.setUser(null);
                    dis.setStato(Stato.MANUTENZIONE);
                    dispositivoRepository.save(dis);
                    return dis;
                }
            }
            throw new SingleBadRequest("Stato scelto inesistente");
        }
        else
            throw new SingleBadRequest("Stato scelto inesistente");
    }

}
