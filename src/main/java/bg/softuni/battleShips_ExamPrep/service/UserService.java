package bg.softuni.battleShips_ExamPrep.service;

import bg.softuni.battleShips_ExamPrep.model.entity.UserEntity;
import bg.softuni.battleShips_ExamPrep.model.service.UserServiceModel;
import bg.softuni.battleShips_ExamPrep.repository.UserRepository;
import bg.softuni.battleShips_ExamPrep.security.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CurrentUser currentUser;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.currentUser = currentUser;
    }

    public void registerUser(UserServiceModel userServiceModel) {
        UserEntity user = modelMapper.map(userServiceModel, UserEntity.class);

        userRepository.save(user);
    }

    public UserServiceModel findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password)
                .map(userEntity -> modelMapper.map(userEntity, UserServiceModel.class))
                .orElse(null);
    }

    public void loginUser(Long id, String username) {
        currentUser.setId(id);
        currentUser.setUsername(username);
    }

    public UserEntity findById(Long id) {
        return userRepository.findById(id)
                .orElse(null);
    }

}
