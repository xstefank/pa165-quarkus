package cz.muni.pa165.model;

public class Avenger {

    public String name;
    public String civilName;
    public boolean snapped;

    public Avenger(String name, String civilName, boolean snapped) {
        this.name = name;
        this.civilName = civilName;
        this.snapped = snapped;
    }

    @Override
    public String toString() {
        return "Avenger{" +
            "name='" + name + '\'' +
            ", civilName='" + civilName + '\'' +
            ", snapped=" + snapped +
            '}';
    }
}

