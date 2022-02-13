package com.hendisantika.postgres.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "houses")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    private User agent;

    @ManyToOne(cascade = CascadeType.DETACH)
    private User client;

    @Enumerated(value = EnumType.STRING)
    @Column
    private Listing listing;

    @Enumerated(value = EnumType.STRING)
    @Column
    private HouseType houseType;

    @Enumerated(value = EnumType.STRING)
    @Column
    private BathroomCount bathroomCount;

    @Enumerated(value = EnumType.STRING)
    @Column
    private BedRoomCount bedRoomCount;

    @Enumerated(value = EnumType.STRING)
    @Column
    private TotalRoomCount totalRoomCount;

    @Enumerated(value = EnumType.STRING)
    @Column
    private ParkingSpotCount parkingSpotCount;

    @Column(name = "living_area")
    private String livingArea;

    @Column(name = "created_at")
    private LocalDate createdAt;

    private String address;

    private String price;

    private String comments;

    @ManyToMany
    private List<HouseFeature> features;

    @OneToMany
    private List<Image> images;

    public void removeFeature(HouseFeature feature) {
        features.remove(feature);
    }
}