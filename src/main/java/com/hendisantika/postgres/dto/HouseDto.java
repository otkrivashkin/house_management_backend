package com.hendisantika.postgres.dto;

import com.hendisantika.postgres.entity.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class HouseDto implements Serializable {
    private final Long id;
    private Long agentId;
    private Long clientId;
    private final Listing listing;
    private final HouseType houseType;
    private final BathroomCount bathroomCount;
    private final BedRoomCount bedRoomCount;
    private final TotalRoomCount totalRoomCount;
    private final ParkingSpotCount parkingSpotCount;
    private final String livingArea;
    private final LocalDate createdAt;
    private final String address;
    private final String price;
    private final String comments;
    private List<Long> featureIds;
    private List<Long> imageIds;
    private List<ImageDto> images;
    private List<HouseFeatureDto> features;

    public HouseDto(Long id, Long agentId, Long clientId, Listing listing, HouseType houseType, BathroomCount bathroomCount, BedRoomCount bedRoomCount, TotalRoomCount totalRoomCount, ParkingSpotCount parkingSpotCount, String livingArea, LocalDate createdAt, String address, String price, String comments, List<Long> featureIds, List<Long> imageIds) {
        this.id = id;
        this.agentId = agentId;
        this.clientId = clientId;
        this.listing = listing;
        this.houseType = houseType;
        this.bathroomCount = bathroomCount;
        this.bedRoomCount = bedRoomCount;
        this.totalRoomCount = totalRoomCount;
        this.parkingSpotCount = parkingSpotCount;
        this.livingArea = livingArea;
        this.createdAt = createdAt;
        this.address = address;
        this.price = price;
        this.comments = comments;
        this.featureIds = featureIds;
        this.imageIds = imageIds;
    }

    public HouseDto(House house) {
        this.id = house.getId();

        if (house.getAgent() != null) {
            this.agentId = house.getAgent().getId();
        }

        if (house.getClient() != null) {
            this.clientId = house.getClient().getId();
        }

        this.listing = house.getListing();
        this.houseType = house.getHouseType();
        this.bathroomCount = house.getBathroomCount();
        this.bedRoomCount = house.getBedRoomCount();
        this.totalRoomCount = house.getTotalRoomCount();
        this.parkingSpotCount = house.getParkingSpotCount();
        this.livingArea = house.getLivingArea();
        this.createdAt = house.getCreatedAt();
        this.address = house.getAddress();
        this.price = house.getPrice();
        this.comments = house.getComments();
        if (house.getFeatures() != null) {
            this.featureIds = house.getFeatures().stream().map(HouseFeature::getId).collect(Collectors.toList());
            this.features = house.getFeatures().stream().map(HouseFeatureDto::new).collect(Collectors.toList());
        }
        if (house.getImages() != null) {
            this.imageIds = house.getImages().stream().map(Image::getId).collect(Collectors.toList());
            this.images = house.getImages().stream().map(ImageDto::new).collect(Collectors.toList());
        }
    }

    public House toEntity() {
        House house = new House();
        house.setId(id);
        house.setListing(listing);
        house.setHouseType(houseType);
        house.setBathroomCount(bathroomCount);
        house.setBedRoomCount(bedRoomCount);
        house.setTotalRoomCount(totalRoomCount);
        house.setParkingSpotCount(parkingSpotCount);
        house.setLivingArea(livingArea);
        house.setCreatedAt(createdAt);
        house.setAddress(address);
        house.setPrice(price);
        house.setComments(comments);
        return house;
    }
}
