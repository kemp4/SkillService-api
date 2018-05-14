package pl.kemp.repository;

import pl.kemp.models.dto.*;

public interface UserRepository {
    void addUser(UserNewDTO newUser, String userId, String password);

    UserCreatedDTO getUserById(String id);

    DetailsFullDTO getAllUserDetails(String userId);

    String updateUserDetails(DetailsNewDTO details,String loggedUsername);

    DetailsDTO getUserDetails(String id);

    UserFullDTO addSkillToUser(SaveSkillsRequest skill);

    void insertUserDetails(String detailId, String userId, DetailsNewDTO details);

    String getUserIdByName(String username);

    boolean areDetailsForUserCreated(String userId);
}
