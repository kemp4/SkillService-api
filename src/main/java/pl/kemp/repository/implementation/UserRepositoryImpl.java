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

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void addUser(UserNewDTO newUser, String userId, String password) {

        jdbcTemplate.update("INSERT INTO users(id, email, name, password) VALUES(?,?,?,?)", userId, newUser.getEmail(),newUser.getName(),password);

    }

    @Override
    public UserCreatedDTO getUserById(String id) {
        User result = getUser(id);
        ModelMapper modelMapper = new ModelMapper();
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
    private Detail getDetailsById(String id) {
        String query ="SELECT id, fieldOfStudy, firstName, lastName, university, yearOfStudy FROM details WHERE id=? ";
        return (Detail) jdbcTemplate.queryForObject(query, new Object[] { id },new BeanPropertyRowMapper(Detail.class));
    }
    private Detail getDetailsByUserId(String userId) {
        String query ="SELECT id, fieldOfStudy, firstName, lastName, university, yearOfStudy FROM details WHERE userId=? ";
        return (Detail) jdbcTemplate.queryForObject(query, new Object[] { userId },new BeanPropertyRowMapper(Detail.class));
    }
    private List<Skill> getSkillsByUserId(String id) {
        String query ="SELECT skills.id, skills.skillName FROM skills " +
                "JOIN usersSkills ON skills.id=usersSkills.skillId " +
                "WHERE usersSkills.userId=? ";

        List<Skill> result = jdbcTemplate.query(query,new Object[] { id },new BeanPropertyRowMapper(Skill.class));
        return result;
    }
    @Override
    @Transactional
    public String updateUserDetails(DetailsNewDTO details,String userId) {
            String query="Update details SET fieldOfStudy=?, firstName=?, lastName=?, university=?, yearOfStudy=? " +
                    " Where details.userId=?";
            jdbcTemplate.update(query,
                    details.getFieldOfStudy(), details.getFirstName(), details.getLastName(), details.getUniversity(), details.getYearOfStudy(), userId);
            String detailsIdQuery = "SELECT id FROM details WHERE userID =?";
        return jdbcTemplate.queryForObject(detailsIdQuery, new Object[]{userId}, String.class);
    }
    @Override
    public void insertUserDetails(String detailId, String userId, DetailsNewDTO details) {
        String query = "Insert into details(id ,fieldOfStudy, firstName, lastName, university, yearOfStudy, userId) " +
                " values (?,?,?,?,?,?,?) ";
        jdbcTemplate.update(query, detailId, details.getFieldOfStudy(), details.getFirstName(), details.getLastName(), details.getUniversity(), details.getYearOfStudy(), userId);
    }

    @Override
    public String getUserIdByName(String username) {
        String query="SELECT id FROM users WHERE name =?";
        return jdbcTemplate.queryForObject(query, new Object[]{username}, String.class);
    }

    @Override
    public boolean areDetailsForUserCreated(String userId) {
        String countQuery="SELECT COUNT(id) FROM details WHERE userId =?";
        Long isCreated = jdbcTemplate.queryForObject(countQuery, new Object[]{userId}, Long.class);
        return isCreated.equals(1L);
    }


    @Override
    public DetailsDTO getUserDetails(String id) {
        ModelMapper modelMapper= new ModelMapper();
        Detail detail = getDetailsById(id);
        DetailsDTO detailsDTO = modelMapper.map(detail,DetailsDTO.class);
        return detailsDTO;
    }


    @Override
    @Transactional
    public UserFullDTO addSkillToUser(SaveSkillsRequest skill) {
        String sql = "INSERT INTO usersSkills(userId, skillId) VALUES(?,?)";
        List<Long> skillsList = skill.getSkillsIds();
        String deleteSql = "DELETE FROM usersSkills WHERE userId=?";
        jdbcTemplate.update(deleteSql,new Object[]{skill.getUserId()});
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
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

}
