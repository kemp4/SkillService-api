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
    }
}
