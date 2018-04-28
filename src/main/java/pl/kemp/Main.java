package pl.kemp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;


@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);


    }
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @PostConstruct
    public void init() {
        jdbcTemplate.execute("DROP TABLE skills IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE skills(" +
                "id IDENTITY, skillName VARCHAR(255))");
        jdbcTemplate.execute("INSERT INTO skills (skillName) values('tokarz'),('piekarz')");

        jdbcTemplate.execute("DROP TABLE users IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE users(" +
                "id VARCHAR(255) primary key,email  VARCHAR(255),name VARCHAR(255),password VARCHAR (255))");
        jdbcTemplate.execute("INSERT INTO users (id,email,name,password) values('cedee57e-abcd-43c6-bc19-81f693a0763a','janjan@poczta.pl','janjan','8SqDwwMeYv')");

        jdbcTemplate.execute("DROP TABLE details IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE details(" +
                "id VARCHAR(255) primary key," +
                "fieldOfStudy  VARCHAR(255)," +
                "firstName VARCHAR(255)," +
                "lastName VARCHAR (255)," +
                "university VARCHAR (255)," +
                "userId VARCHAR (255) UNIQUE NOT NULL ," +
                "yearOfStudy INT," +
                "foreign  key(userId) references users(id))");
        jdbcTemplate.execute("INSERT INTO details (id,userId) values('cedee57e-abcd-43c6-bc19-81f693a07ssa','cedee57e-abcd-43c6-bc19-81f693a0763a')");

        jdbcTemplate.execute("DROP TABLE usersSkills IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE usersSkills(" +
                "userId VARCHAR(255)," +
                "skillId LONG," +
                "foreign key(userId) references users(id)," +
                "foreign key(skillId) references skills(id)," +
                "primary  key(userId,skillID))");
        jdbcTemplate.execute("INSERT INTO usersSkills (userId,skillId) values('cedee57e-abcd-43c6-bc19-81f693a0763a',1)");

    }
}
