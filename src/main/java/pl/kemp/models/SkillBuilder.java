package pl.kemp.models;

public class SkillBuilder {
    private long id;
    private String skillName;

    public SkillBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public SkillBuilder setSkillName(String skillName) {
        this.skillName = skillName;
        return this;
    }

    public Skill build() {
        return new Skill(id, skillName);
    }
}