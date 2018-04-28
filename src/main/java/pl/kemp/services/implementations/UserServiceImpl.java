package pl.kemp.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kemp.models.dto.*;
import pl.kemp.repository.UserRepository;
import pl.kemp.services.UserService;


@Service
public class UserServiceImpl implements UserService {

@Autowired
    UserRepository userRepository;

    @Override
    public void createUser(UserNewDTO newUser) {
        userRepository.addUser(newUser);
    }

    @Override
    public UserCreatedDTO getUserById(String id) {
        return userRepository.getUserById(id);
    }

    @Override
    public DetailsFullDTO getAllUserDetailsById(String userId) {
        return userRepository.getAllUserDetails(userId);

    }

    @Override
    public UserFullDTO changeUserDetails(DetailsNewDTO details) {
        return userRepository.updateUserDetails(details,"cedee57e-abcd-43c6-bc19-81f693a0763a");
    }

    @Override
    public DetailsDTO getUserDetailsById(String id) {
        return userRepository.getUserDetails(id);
    }

    @Override
    public UserFullDTO addSkillToUser(SaveSkillsRequest skill) {
        return userRepository.addSkillToUser(skill);
    }
}
