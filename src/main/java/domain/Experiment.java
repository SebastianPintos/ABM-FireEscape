package domain;

public class Experiment {
    public int id;
    public String name;
    public int populationAlive;
    public int populationDead;
    public int collisions;
    public int duration;

    public Experiment(String name, int populationAlive, int populationDead, int collisions, int duration){
        this.name = name;
        this.populationAlive = populationAlive;
        this.populationDead = populationDead;
        this.collisions = collisions;
        this.duration = duration;
    }

    public Experiment(int id, String name, int populationAlive, int populationDead, int collisions, int duration){
        this.id = id;
        this.name = name;
        this.populationAlive = populationAlive;
        this.populationDead = populationDead;
        this.collisions = collisions;
        this.duration = duration;
    }
}
