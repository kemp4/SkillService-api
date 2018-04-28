package pl.kemp.services;

import pl.kemp.models.dto.*;

public interface UserService {
    void createUser(UserNewDTO newUser);

    UserCreatedDTO getUserById(String id);

    DetailsFullDTO getAllUserDetailsById(String userId);

    UserFullDTO changeUserDetails(DetailsNewDTO details);

    DetailsDTO getUserDetailsById(String id);

    UserFullDTO addSkillToUser(SaveSkillsRequest skill);
}
