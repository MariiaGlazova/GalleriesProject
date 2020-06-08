package entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "gameStudios")
@Table(name = "gameStudios")
public class GameStudio {

    @Id
    @Setter
    @Getter
    public int id;

    public GameStudio(int id, String name, int people) {
        this.id = id;
        this.name = name;
        this.people = people;
    }

    @Setter
    @Getter
    public String name;

    @Setter
    @Getter
    public int people;

}
