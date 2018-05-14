package pl.kemp.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.kemp.models.dto.*;
import pl.kemp.repository.UserRepository;
import pl.kemp.services.UserService;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.UUID;



@Service
public class UserServiceImpl implements UserService {

    @Value("${api.defaultURI}")
    private String defaultURI;

    @Autowired
    UserRepository userRepository;

    @Override
    public String createUser(UserNewDTO newUser) {
        String userId = null;
        try {
            userId = generateUUID(newUser);
            userRepository.addUser(newUser,userId, generatePassword());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            //todo throw
        }
        return defaultURI +"users/"+userId;
    }

    private String generatePassword() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();

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
    public String changeUserDetails(DetailsNewDTO details) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            String userId = userRepository.getUserIdByName(username);
            boolean areCreated = userRepository.areDetailsForUserCreated(userId);
            String detailId;
            if (areCreated) {
                detailId = userRepository.updateUserDetails(details, userId);
            } else {
                detailId = generateUUID(username);
                userRepository.insertUserDetails(detailId, userId, details);
            }
            return defaultURI +"users/details/"+ detailId;

        } catch(UnsupportedEncodingException e){
                throw new RuntimeException("unsupported Encoding exception",e);
        }
    }

    @Override
    public DetailsDTO getUserDetailsById(String id) {
        return userRepository.getUserDetails(id);
    }

    @Override
    public UserFullDTO addSkillToUser(SaveSkillsRequest skill) {
        return userRepository.addSkillToUser(skill);
    }

    private String generateUUID(UserNewDTO newUser) throws UnsupportedEncodingException {
        String source = newUser.getEmail() +newUser.getName();
        return generateStringUUID(source);
    }
    private String generateUUID(String source) throws UnsupportedEncodingException {
        return generateStringUUID(source);
    }

    private String generateStringUUID(String source) throws UnsupportedEncodingException {
        byte[] bytes = source.getBytes("UTF-8");
        UUID uuid = UUID.nameUUIDFromBytes(bytes);
        return uuid.toString();
    }
}