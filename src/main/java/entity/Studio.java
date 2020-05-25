package entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Studio.All", query = "SELECT s FROM Studio s"),
        @NamedQuery(name = "Studio.getById", query = "SELECT s FROM Studio s WHERE s.studioId = : studioId"),
})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "studioId", scope = Studio.class)
public class Studio {

    public Studio(Long studioId, String studioName) {
        this.studioId = studioId;
        this.studioName = studioName;
    }

    @Id
    @Column()
    @Getter
    @Setter
    private Long studioId;

    @Column(nullable = false)
    @Getter
    @Setter
    private String studioName;

    @Getter
    @Setter
    @OneToOne(mappedBy = "studio")
    private Painter painter;

    @Override
    public String toString() {
        return "Studio{" +
                "studioId=" + studioId +
                ", studioName='" + studioName + '\'' +
                '}';
    }
}
