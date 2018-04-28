package pl.kemp.repository.implementation;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.kemp.models.Detail;
import pl.kemp.models.Skill;
import pl.kemp.models.User;
import pl.kemp.models.dto.*;
import pl.kemp.repository.UserRepository;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void addUser(UserNewDTO newUser) {
        try {
            jdbcTemplate.update("INSERT INTO users(id, email, password) VALUES(?,?,?)", generateUUID(newUser), newUser.getEmail(),newUser.getName());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            //TODO throw new Exception
        }
    }

    @Override
    public UserCreatedDTO getUserById(String id) {
        User result = getUser(id);
        ModelMapper modelMapper = new ModelMapper();
        //modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        UserCreatedDTO userCreatedDTO = modelMapper.map(result, UserCreatedDTO.class);
        return userCreatedDTO;
    }

    @Override
    @Transactional
    public DetailsFullDTO getAllUserDetails(String userId) {
        ModelMapper modelMapper = new ModelMapper();
        Detail detail = getDetailsByUserId(userId);
        DetailsFullDTO detailsFullDTO = modelMapper.map(detail,DetailsFullDTO.class);
        UserFullDTO userFullDTO = getUserFullDTOById(userId);
        detailsFullDTO.setUser(userFullDTO);

        return detailsFullDTO;
    }

    private User getUser(String id) {
        String query ="SELECT id, name, email, password FROM users WHERE id=?";
        return (User) jdbcTemplate.queryForObject(query, new Object[] { id },new BeanPropertyRowMapper(User.class));
    }
    private Detail getDetailsByUserId(String id) {
        String query ="SELECT id, fieldOfStudy, firstName, lastName, university, yearOfStudy FROM details where userId=? ";
        return (Detail) jdbcTemplate.queryForObject(query, new Object[] { id },new BeanPropertyRowMapper(Detail.class));
    }
    private List<Skill> getSkillsByUserId(String id) {
        String query ="SELECT skills.id, skills.skillName FROM skills " +
                "JOIN usersSkills on skills.id=usersSkills.skillId " +
                "JOIN users on users.id = usersSkills.userId where users.id=? ";

        List<Skill> result = jdbcTemplate.query(query,new Object[] { id },new BeanPropertyRowMapper(Skill.class));
        return result;
    }
    @Override
    @Transactional
    public UserFullDTO updateUserDetails(DetailsNewDTO details,String loggedUserId) {
        jdbcTemplate.update("Update details SET fieldOfStudy=?, firstName=?, lastName=?, university=?, yearOfStudy=? " +
                        " Where details.userId=?",
                details.getFieldOfStudy(),details.getFirstName(),details.getLastName(),details.getUniversity(),details.getYearOfStudy(),loggedUserId);
        return getUserFullDTOById(loggedUserId);
    }

    @Override
    public DetailsDTO getUserDetails(String id) {
        ModelMapper modelMapper= new ModelMapper();
        Detail detail = getDetailsByUserId(id);
        DetailsDTO detailsDTO = modelMapper.map(detail,DetailsDTO.class);
        return detailsDTO;
    }


    @Override
    @Transactional
    public UserFullDTO addSkillToUser(SaveSkillsRequest skill) {
        String query = "INSERT INTO usersSkills(userId, skillId) VALUES(?,?)";
        List<Long> skillsList = skill.getSkillsIds();

        jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, skill.getUserId());
                ps.setLong(2, skillsList.get(i));
            }
            public int getBatchSize() {
                return skillsList.size();
            }
        } );

        return getUserFullDTOById(skill.getUserId());
    }

    private UserFullDTO getUserFullDTOById(String userId) {
        ModelMapper modelMapper= new ModelMapper();
        User user = getUser(userId);
        UserFullDTO userFullDTO = modelMapper.map(user,UserFullDTO.class);
        java.lang.reflect.Type targetListType = new TypeToken<List<SkillDTO>>() {}.getType();
        List<Skill> skills= getSkillsByUserId(userId);
        List<SkillDTO> skillsDTOs = modelMapper.map(skills, targetListType);
        userFullDTO.setSkills(skillsDTOs);
        return userFullDTO;
    }

    private String generateUUID(UserNewDTO newUser) throws UnsupportedEncodingException {
        String source = newUser.getEmail() +newUser.getName();
        byte[] bytes = source.getBytes("UTF-8");
        UUID uuid = UUID.nameUUIDFromBytes(bytes);
        return uuid.toString();
    }
}
