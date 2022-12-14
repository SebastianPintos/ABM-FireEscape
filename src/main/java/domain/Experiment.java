package domain;

public class Experiment {
    public int id;
    public String name;
    public int firePits;
    public int fireStrength;
    public int populationAlive;
    public int populationDead;
    public int collisions;
    public int duration;
    public Object[] scapes;
    public Object[] deaths;

    public Experiment(String name, int firePits, int fireStrength, int populationAlive, int populationDead, int collisions, int duration){
        this.name = name;
        this.firePits = firePits;
        this.fireStrength = fireStrength;
        this.populationAlive = populationAlive;
        this.populationDead = populationDead;
        this.collisions = collisions;
        this.duration = duration;
    }
}
