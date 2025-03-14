package lk.ijse.gdse.aad68.notetaker.service;

import jakarta.transaction.Transactional;
import lk.ijse.gdse.aad68.notetaker.CustomObj.UserErrorResponse;
import lk.ijse.gdse.aad68.notetaker.CustomObj.UserResponse;
import lk.ijse.gdse.aad68.notetaker.dao.UserDao;
import lk.ijse.gdse.aad68.notetaker.dto.impl.UserDTO;
import lk.ijse.gdse.aad68.notetaker.entity.UserEntity;
import lk.ijse.gdse.aad68.notetaker.exception.UserNotFoundException;
import lk.ijse.gdse.aad68.notetaker.util.AppUtil;
import lk.ijse.gdse.aad68.notetaker.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceIMPL implements UserService{
    @Autowired
    private final UserDao userDao;
    @Autowired
    private final Mapping mapping;
    @Override
    public String saveUser(UserDTO userDTO) {
        userDTO.setUserId(AppUtil.createUserId());
        UserEntity savedUser = userDao.save(mapping.convertToUserEntity(userDTO));
        if (savedUser != null && savedUser.getUserId() != null){
            return "User saved successfully";
        }
        else {
            return "save failed";
        }


    }

    @Override
    public void updateUser(UserDTO userDTO) {
        Optional<UserEntity> tmpUser = userDao.findById(userDTO.getUserId());//optional danne ai -> null point expception return wen ek handle krnna
        if(!tmpUser.isPresent()){
            throw new UserNotFoundException("User not found");
        }else {
            tmpUser.get().setFirstName(userDTO.getFirstName());
            tmpUser.get().setLastName(userDTO.getLastName());
            tmpUser.get().setEmail(userDTO.getEmail());
            tmpUser.get().setPassword(userDTO.getPassword());
            tmpUser.get().setProfilePic(userDTO.getProfilePic());

        }

    }

    @Override
    public void deleteUser(String userId) {
        Optional<UserEntity> selectedUserId = userDao.findById(userId);
        if(!selectedUserId.isPresent()){
            throw new UserNotFoundException("User not found");
        }else {
            userDao.deleteById(userId);
        }
    }

    @Override
    public UserResponse getSelectedUser(String userId) {
        if(userDao.existsById(userId)){
            UserEntity userEntityByUserId = userDao.getUserEntityByUserId(userId);
            return mapping.convertToUserDTO(userEntityByUserId);
        }else {
            return new UserErrorResponse(0,"user not found");
        }

    }

    @Override
    public List<UserDTO> getAllUsers() {
//        List<UserEntity> getAllUsers = userDao.findAll();
//        return mapping.convertUserToList(getAllUsers);
        List<UserEntity> getAllUsers = userDao.findAll();
        return mapping.convertUserToList(getAllUsers);
    }
}
