package data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
public class Painter {

    public Painter(String name, Studio studio, List<Painting> paintings, Gallery gallery) {
        this.name = name;
        this.studio = studio;
        this.paintings = paintings;
        this.gallery = gallery;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Getter
    @OneToOne
    @JoinColumn(name = "studio_id")
    private Studio studio;

    @Getter
    @Setter
    @OneToMany
    @JoinColumn(name = "painting_id")
    private List<Painting> paintings = new ArrayList<>();

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "gallery_id")
    private Gallery gallery;

    @Override
    public String toString() {
        return "Painter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", studio='" + studio + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Painter painter = (Painter) o;
        return Objects.equals(id, painter.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
