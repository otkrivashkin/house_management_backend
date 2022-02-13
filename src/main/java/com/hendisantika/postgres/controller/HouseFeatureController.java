package com.hendisantika.postgres.controller;

import com.hendisantika.postgres.dto.HouseFeatureDto;
import com.hendisantika.postgres.entity.House;
import com.hendisantika.postgres.entity.HouseFeature;
import com.hendisantika.postgres.repository.HouseFeatureRepository;
import com.hendisantika.postgres.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("houseFeatures")
@CrossOrigin(origins = "http://localhost:4200")
public class HouseFeatureController {

    @Autowired
    private HouseFeatureRepository houseFeatureRepository;

    @Autowired
    private HouseRepository houseRepository;

    @GetMapping
    public List<HouseFeatureDto> getHouseFeatures() {
        return houseFeatureRepository.findAll().stream().map(HouseFeatureDto::new).collect(Collectors.toList());
    }

    @GetMapping("/{houseFeatureId}")
    public HouseFeature getHouseFeatureById(@PathVariable Long houseFeatureId) {
        return houseFeatureRepository.getOne(houseFeatureId);
    }

    @Transactional
    @PostMapping
    public HouseFeatureDto createHouseFeature(@RequestBody HouseFeatureDto houseFeatureDto) {

        HouseFeature houseFeature = new HouseFeature();
        houseFeature.setName(houseFeatureDto.getName());

        return new HouseFeatureDto(houseFeatureRepository.save(houseFeature));
    }

    @Transactional
    @PutMapping("/{houseFeatureId}")
    public HouseFeatureDto updateHouseFeature(@PathVariable Long houseFeatureId,
                           @RequestBody HouseFeatureDto houseFeatureDto) {
        HouseFeature existingHouseFeature = houseFeatureRepository.getOne(houseFeatureId);

        existingHouseFeature.setName(houseFeatureDto.getName());

        return new HouseFeatureDto(houseFeatureRepository.save(existingHouseFeature));
    }

    @Transactional
    @DeleteMapping("/{houseFeatureId}")
    public void deleteHouseFeatureById(@PathVariable Long houseFeatureId) {

        HouseFeature houseFeature = houseFeatureRepository.findById(houseFeatureId).orElse(null);

        List<Long> houseIds = houseFeature.getHouses().stream().map(House::getId).collect(Collectors.toList());

        List<House> houses = houseRepository.findByIdIn(houseIds);
        for (House house: houses) {
            house.removeFeature(houseFeature);
            houseRepository.save(house);
        }

        houseFeatureRepository.deleteById(houseFeatureId);
    }
}
