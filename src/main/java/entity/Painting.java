package entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Painting.All", query = "SELECT p FROM Painting p"),
        @NamedQuery(name = "Painting.getById", query = "SELECT p FROM Painting p WHERE p.paintingId = : paintingId"),
})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "paintingId", scope = Painting.class)
public class Painting {

    public Painting(Long paintingId, String paintingName) {
        this.paintingId = paintingId;
        this.paintingName = paintingName;
    }

    @Id
    @Column
    @Getter
    @Setter
    private Long paintingId;

    @Getter
    @Setter
    @Column(nullable = false)
    private String paintingName;

    @Getter
    @Setter
    @ManyToMany(mappedBy = "paintings")
    private List<Painter> painters;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Gallery gallery;


    @Override
    public String toString() {
        return "Painting{" +
                "paintingId=" + paintingId +
                ", paintingName='" + paintingName + '\'' +
                '}';
    }
}