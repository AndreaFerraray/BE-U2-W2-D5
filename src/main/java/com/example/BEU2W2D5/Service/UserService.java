package com.example.BEU2W2D5.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.BEU2W2D5.Exceptions.NotFound;
import com.example.BEU2W2D5.Repository.UserRepository;
import com.example.BEU2W2D5.entities.Dispositivo;
import com.example.BEU2W2D5.entities.Payload.StateDispositivoPayload;
import com.example.BEU2W2D5.entities.Payload.UserPayload;
import com.example.BEU2W2D5.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;



@Service
public class UserService {
    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private UserRepository userRepository;

    public Page<User> getAllUsers(int page, int size, String order){
        Pageable p = PageRequest.of (page,size, Sort.by(order));
        return userRepository.findAll(p);

    }

    public User getSingleUser( int id)
    {
        User user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("utente non trovato"));
        return user;
    }

    public User createUser (UserPayload userPayload){
        User user= User.builder().nome(userPayload.nome()).cognome(userPayload.cognome()).email(userPayload.email()).username(userPayload.nome()+"_"+userPayload.cognome()).imageUrl("https://picsum.photos/200/300").build();
        userRepository.save(user);
        return user;



    }
    public User modifyUser(UserPayload userPayload,int id){
        User user=this.getSingleUser(id);
        user.setNome(userPayload.nome());
        user.setCognome(userPayload.cognome());
        user.setEmail(userPayload.email());
        user.setUsername(userPayload.nome()+"_"+userPayload.cognome());
        userRepository.save(user);
        return user;
    }

    public void deleteUser(int id,DispositivoService dispositivoService){

        User user=this.getSingleUser(id);
        user.getListaDispositivi().forEach(elem->DispositivoService.setStato(new StateDispositivoPayload("DISPONIBILE"), elem.getId()));
        if(!user.getImageUrl().equals("https://picsum.photos/200/300")){
            CloudinaryService.deleteImageByUrl(user.getImageUrl());
        }
        userRepository.delete(user);
    }


    public User modifyImage(MultipartFile file, int id) throws IOException {

        User user=this.getSingleUser(id);
        if(!user.getImageUrl().equals("https://picsum.photos/200/300")){
            CloudinaryService.deleteImageByUrl(user.getImageUrl());
        }
        String url=(String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        user.setImageUrl(url);
        userRepository.save(user);
        return  user;

    }



    public List<Dispositivo> getDispositiviById(int id){
        List<Dispositivo> dispositivoList=userRepository.findDispositivoById(id).orElseThrow(()->new NotFound("Nessun dispositivo associato all'utente selezionato"));
        if(dispositivoList.isEmpty()) throw new com.example.BEU2W2D5.Exceptions.NotFound("Nessun dispositivo associato all'utente selezionato");
        return dispositivoList;
    }
}
