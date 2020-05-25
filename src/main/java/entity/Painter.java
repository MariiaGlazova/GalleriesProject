package entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Painter.All", query = "SELECT p FROM Painter p"),
        @NamedQuery(name = "Painter.getById", query = "SELECT p FROM Painter p WHERE p.painterId = : painterId"),
})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "painterId", scope = Painter.class)
public class Painter {

    public Painter(Long painterId, String painterName) {
        this.painterId = painterId;
        this.painterName = painterName;
    }

    @Id
    @Column
    @Getter
    @Setter
    private Long painterId;

    @Getter
    @Setter
    @Column(nullable = false)
    private String painterName;

    @Getter
    @Setter
    @ManyToMany(mappedBy = "painters", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Gallery> galleries;

    @Setter
    @Getter
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "studio_id")
    private Studio studio;

    @Getter
    @Setter
    @ManyToMany
    @JoinTable(
            name = "PAINTERS_PAINTING",
            joinColumns = @JoinColumn(name = "PAINTERS_ID"),
            inverseJoinColumns = @JoinColumn(name = "PAINTING_ID")
    )
    private List<Painting> paintings = new ArrayList<>();

    @Override
    public String toString() {
        return "Painter{" +
                "painterId=" + painterId +
                ", painterName='" + painterName + '\'' +
                ", studio='" + studio + '\'' +
                '}';
    }
}
