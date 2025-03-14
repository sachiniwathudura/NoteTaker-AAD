package lk.ijse.gdse.aad68.notetaker.controller;

import lk.ijse.gdse.aad68.notetaker.CustomObj.UserResponse;
import lk.ijse.gdse.aad68.notetaker.dto.impl.UserDTO;
import lk.ijse.gdse.aad68.notetaker.exception.UserNotFoundException;
import lk.ijse.gdse.aad68.notetaker.service.UserService;
import lk.ijse.gdse.aad68.notetaker.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;
    //Save User
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveUser(
         @RequestPart("firstName") String firstName,
         @RequestPart ("lastName") String lastName,
         @RequestPart ("email") String email,
         @RequestPart ("password") String password,
         @RequestPart ("profilePic") String profilePic) {

        // Handle profile pic
        String base64ProfilePic = AppUtil.toBase64ProfilePic(profilePic);
        // build the user object,multi part awa data tika dto ekt set krgnnwa
        UserDTO buildUserDTO = new UserDTO();
        buildUserDTO.setFirstName(firstName);
        buildUserDTO.setLastName(lastName);
        buildUserDTO.setEmail(email);
        buildUserDTO.setPassword(password);
        buildUserDTO.setProfilePic(base64ProfilePic);
        //send to the service layer
        return new ResponseEntity<>(userService.saveUser(buildUserDTO), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable ("id") String userId) {
        try {
            userService.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public UserResponse getSelectedUser(@PathVariable("id") String userId){
        return userService.getSelectedUser(userId);

    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }
    @PatchMapping(value = "/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateUser(
//        return userService.updateUser(id,userDTO)? new ResponseEntity<>(HttpStatus.NO_CONTENT):new ResponseEntity<>(HttpStatus.NOT_FOUND);
        @PathVariable("id") String id,
        @RequestPart("updatefirstName") String updatefirstName,
        @RequestPart ("updatelastName") String updatelastName,
        @RequestPart ("updateemail") String updateemail,
        @RequestPart ("updatepassword") String updatepassword,
        @RequestPart ("updateprofilePic") String updateprofilePic) {

        try {
            // Handle profile pic
            String base64ProfilePic = AppUtil.toBase64ProfilePic(updateprofilePic);
            // build the user object,multi part awa data tika dto ekt set krgnnwa
            var updateUser = new UserDTO();
            updateUser.setUserId(id);
            updateUser.setFirstName(updatefirstName);
            updateUser.setLastName(updatelastName);
            updateUser.setEmail(updateemail);
            updateUser.setPassword(updatepassword);
            updateUser.setProfilePic(base64ProfilePic);
             userService.updateUser(updateUser);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (UserNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        }

}
