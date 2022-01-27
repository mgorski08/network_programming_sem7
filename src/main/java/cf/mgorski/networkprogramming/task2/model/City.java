package cf.mgorski.networkprogramming.task2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class City {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    @Column(
            name = "id",
            nullable = false
    )
    private Long id;
    @Column(
            name = "name",
            nullable = false
    )
    private String name;
    @Column(
            name = "open_weather_id"
    )
    private Long openWeatherId;

    public City() {
    }

    public Long getOpenWeatherId() {
        return this.openWeatherId;
    }

    public void setOpenWeatherId(Long openWeatherId) {
        this.openWeatherId = openWeatherId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
