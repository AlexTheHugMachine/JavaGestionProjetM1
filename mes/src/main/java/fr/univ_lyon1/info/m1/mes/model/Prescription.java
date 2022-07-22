package fr.univ_lyon1.info.m1.mes.model;

public class Prescription {
    private final HealthProfessional hp;
    private final String content;

    public String getContent() {
        return content;
    }

    public HealthProfessional getHealthProfessional() {
        return hp;
    }

    public Prescription(final HealthProfessional hp, final String content) {
        this.hp = hp;
        this.content = content;
    }
}
