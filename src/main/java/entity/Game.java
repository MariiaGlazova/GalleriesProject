package entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Game {
    @Id
    @Setter
    @Getter
    public int id;

    public Game(int id, int gameStudioId, String calling, int programmers) {
        this.id = id;
        this.gameStudioId = gameStudioId;
        this.calling = calling;
        this.programmers = programmers;
    }

    @Setter
    @Getter
    public int gameStudioId;

    @Setter
    @Getter
    public String calling;

    @Setter
    @Getter
    public int programmers;
}
