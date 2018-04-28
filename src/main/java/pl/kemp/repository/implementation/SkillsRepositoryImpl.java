package pl.kemp.repository.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;
import pl.kemp.models.Skill;
import pl.kemp.models.SkillBuilder;
import pl.kemp.models.dto.SkillNewDTO;
import pl.kemp.repository.SkillsRepository;

@Repository
public class SkillsRepositoryImpl implements SkillsRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Skill> getAllSkills() {
        String query ="SELECT id, skillName FROM skills";
//        List<Skill> result= jdbcTemplate.query(
//                query,(rs,rNum)->new SkillBuilder()
//                        .setId(rs.getLong("id"))
//                        .setSkillName(rs.getString("skillName"))
//                        .build());
        List<Skill> result2 = jdbcTemplate.query(query,new BeanPropertyRowMapper(Skill.class));
        return result2;
    }

    @Override
    public void addSkill(SkillNewDTO skillNewDTO) {
        String query = "INSERT INTO skills(skillName) VALUES(?)";
        jdbcTemplate.update(query, skillNewDTO.getSkillName());

    }

}
