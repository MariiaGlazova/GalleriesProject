package entity;

import lombok.Getter;
import lombok.Setter;

public class User {
    @Setter
    @Getter
    public String name;

    @Setter
    @Getter
    public String password;

    @Setter
    @Getter
    public String nameofDataBase;

    public User(String name, String password, String nameofDataBase) {
        this.name = name;
        this.password = password;
        this.nameofDataBase = nameofDataBase;
    }
}
