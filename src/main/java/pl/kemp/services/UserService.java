package pl.kemp.services;

import pl.kemp.models.dto.*;

public interface UserService {
    String createUser(UserNewDTO newUser);

    UserCreatedDTO getUserById(String id);

    DetailsFullDTO getAllUserDetailsById(String userId);

    String changeUserDetails(DetailsNewDTO details);

    DetailsDTO getUserDetailsById(String id);

    UserFullDTO addSkillToUser(SaveSkillsRequest skill);
}
