package practice.task.rba.controllers;

import practice.task.rba.services.UserFileCreatorClass;
import practice.task.rba.enums.Status;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.task.rba.entities.User;
import practice.task.rba.repositories.UserRepository;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public ResponseEntity<String> addWithJson(@RequestBody User user){

        if(!(user.getOIB().matches("[0-9]+") && user.getOIB().length() == 11))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OIB je neispravan");

        if(userRepository.existsById(user.getOIB()))
            return ResponseEntity.ok().body("Korisnik sa OIB-om " + user.getOIB() + " već postoji u bazi.");
        else{
            userRepository.save(user);
            return ResponseEntity.ok().body("Korisnik je dodan u bazu");
        }
    }

    @GetMapping(path="/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> listOfUsers = userRepository.findAll();
        for(User user: listOfUsers){
            if(user.getStatus() == Status.AKTIVAN)
                UserFileCreatorClass.createFileFromUserData(user);
        }

        return ResponseEntity.ok().body(listOfUsers);
    }

    @GetMapping("/user/{OIB}")
    public ResponseEntity<?> getUserByPIN(@PathVariable(value = "OIB") String OIB) {
        Optional<User> user = userRepository.findById(OIB);

        if(user.isPresent()) {
            if(user.get().getStatus() == Status.AKTIVAN)
                UserFileCreatorClass.createFileFromUserData(user.get());

            return ResponseEntity.ok().body(user);
        }
        else
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Korisnik sa OIB-om " + OIB + " ne postoji u bazi.");
    }

    @GetMapping("/delete_file/{OIB}")
    public ResponseEntity<String> deleteUserFile(@PathVariable(value = "OIB") String OIB)
            throws ResourceNotFoundException {
        Optional<User> user = userRepository.findById(OIB);

        if(user.isPresent()) {
            if(UserFileCreatorClass.deleteFileWithUserDataIfExists(OIB)) {
                return ResponseEntity
                        .ok()
                        .body("Obrisana je datoteka korisnika sa OIB-om " + OIB + ".");
            }else {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Datoteka traženog korisnika ne postoji.");
            }
        }else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Korisnik sa OIB-om " + OIB + " ne postoji u bazi.");
        }
    }

    @DeleteMapping("/delete_user/{OIB}")
    public ResponseEntity<String> deleteUser(@PathVariable(value = "OIB") String OIB) throws Exception {
        Optional<User> user = userRepository.findById(OIB);

        if(user.isPresent()) {
            UserFileCreatorClass.deleteFileWithUserDataIfExists(OIB);
            userRepository.delete(user.get());
            return ResponseEntity
                    .ok()
                    .body("Obrisan je korisnik sa OIB-om " + OIB + " iz baze korisnika.");
        }
        else
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Korisnik sa OIB-om " + OIB + " ne postoji u bazi.");
    }
}
