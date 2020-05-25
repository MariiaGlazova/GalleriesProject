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
        @NamedQuery(name = "Gallery.All", query = "SELECT g FROM Gallery g "),
        @NamedQuery(name = "Gallery.getById", query = "SELECT g FROM Gallery g WHERE g.galleryId = :galleryId"),
})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "galleryId", scope = Gallery.class)
public class Gallery {

    public Gallery(Long galleryId, String galleryName) {
        this.galleryId = galleryId;
        this.galleryName = galleryName;
    }

    @Id
    @Getter
    @Setter
    @Column
    private Long galleryId;

    @Getter
    @Setter
    @Column(nullable = false)
    private String galleryName;

    @Getter
    @Setter
    private String galleryCity;

    @Getter
    @Setter
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "GALLERIES_PAINTERS",
            joinColumns = @JoinColumn(name = "GALLERY_ID"),
            inverseJoinColumns = @JoinColumn(name = "PAINTER_ID")
    )
    private List<Painter> painters = new ArrayList<>();

    @Getter
    @Setter
    @OneToMany(mappedBy = "gallery", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Painting> paintings;

    @Override
    public String toString() {
        return "Gallery{" +
                "galleryId=" + galleryId +
                ", galleryName='" + galleryName + '\'' +
                ", painters=" + painters +
                ", galleryCity='" + galleryCity + '\'' +
                ", paintings=" + paintings +
                '}';
    }
}