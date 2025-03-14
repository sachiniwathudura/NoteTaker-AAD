package lk.ijse.gdse.aad68.notetaker.dto.impl;

import lk.ijse.gdse.aad68.notetaker.CustomObj.UserResponse;
import lk.ijse.gdse.aad68.notetaker.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO implements SuperDTO, UserResponse {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String profilePic;
    private List<NoteDTO> notes;
}
