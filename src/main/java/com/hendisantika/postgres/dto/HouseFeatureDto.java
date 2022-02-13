package com.hendisantika.postgres.dto;

import com.hendisantika.postgres.entity.House;
import com.hendisantika.postgres.entity.HouseFeature;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class HouseFeatureDto implements Serializable {
    private final Long id;
    private final String name;
    private List<Long> houseIds;

    public HouseFeatureDto(Long id, String name, List<Long> houseIds) {
        this.id = id;
        this.name = name;
        this.houseIds = houseIds;
    }

    public HouseFeatureDto(HouseFeature feature) {
        this.id = feature.getId();
        this.name = feature.getName();
        if (feature.getHouses() != null) {
            this.houseIds = feature.getHouses().stream().map(House::getId).collect(Collectors.toList());
        }
    }
}
