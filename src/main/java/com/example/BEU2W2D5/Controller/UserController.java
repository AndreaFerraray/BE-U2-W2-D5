package com.example.BEU2W2D5.Controller;

import com.example.BEU2W2D5.Exceptions.BadRequest;
import com.example.BEU2W2D5.Service.DispositivoService;
import com.example.BEU2W2D5.Service.UserService;
import com.example.BEU2W2D5.entities.Dispositivo;
import com.example.BEU2W2D5.entities.Payload.UserPayload;
import com.example.BEU2W2D5.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private DispositivoService dispositivoService;
    @GetMapping()
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "5")int size, @RequestParam(defaultValue = "id") String order){
        return userService.getAllUsers(page,size>20?5:size,order);
    }
    @GetMapping("/{id}")
    public User getSingleUser(@PathVariable int id){
        return userService.getSingleUser(id);
    }
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody @Validated UserPayload userPayload, BindingResult validation){
        if(validation.hasErrors()){
            throw new BadRequest(validation.getAllErrors());
        }
        return userService.createUser(userPayload);
    }
    @PutMapping("/{id}")
    public User modifyUser(@RequestBody @Validated UserPayload userPayload,BindingResult validation,@PathVariable int id){
        if(validation.hasErrors()){
            throw new BadRequest(validation.getAllErrors());
        }
        return userService.modifyUser(userPayload,id);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable int id)
    {
        userService.deleteUser(id,dispositivoService);
    }


    @PutMapping("/{id}/image")
    public User modifyImage(@PathVariable int id,@RequestParam("immagineProfilo") MultipartFile file) throws IOException {
        return userService.modifyImage(file,id);
    }


    @GetMapping("/{id}/dispositivi")
    public List<Dispositivo> getDispositiviById(@PathVariable int id){
        return userService.getDispositiviById(id);
    }
}
