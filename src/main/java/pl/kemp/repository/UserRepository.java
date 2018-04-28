package pl.kemp.repository;

import pl.kemp.models.dto.*;

public interface UserRepository {
    void addUser(UserNewDTO newUser);

    UserCreatedDTO getUserById(String id);

    DetailsFullDTO getAllUserDetails(String userId);

    UserFullDTO updateUserDetails(DetailsNewDTO details,String loggedUserId);

    DetailsDTO getUserDetails(String id);

    UserFullDTO addSkillToUser(SaveSkillsRequest skill);
}
