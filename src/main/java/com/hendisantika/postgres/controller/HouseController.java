package com.hendisantika.postgres.controller;

import com.hendisantika.postgres.dto.HouseDto;
import com.hendisantika.postgres.entity.*;
import com.hendisantika.postgres.repository.HouseFeatureRepository;
import com.hendisantika.postgres.repository.HouseRepository;
import com.hendisantika.postgres.repository.ImageRepository;
import com.hendisantika.postgres.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("houses")
@CrossOrigin(origins = "http://localhost:4200")
public class HouseController {

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private HouseFeatureRepository houseFeatureRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<HouseDto> getHouses() {
        return houseRepository.findAll().stream().map(HouseDto::new).collect(Collectors.toList());
    }

    @GetMapping("/{houseId}")
    public House getHouseById(@PathVariable Long houseId) {

        return houseRepository.findById(houseId).orElse(null);
    }

    @Transactional
    @PostMapping
    public HouseDto createHouse(@RequestBody HouseDto houseDto) {

        House house = houseDto.toEntity();

        if (houseDto.getClientId() != null) {
            User client = userRepository.findById(houseDto.getClientId()).orElse(null);
            house.setClient(client);
        }

        if (houseDto.getAgentId() != null) {
            User agent = userRepository.findById(houseDto.getAgentId()).orElse(null);
            house.setAgent(agent);
        }

        if (houseDto.getFeatureIds() != null) {
            List<HouseFeature> features = houseFeatureRepository.findByIdIn(houseDto.getFeatureIds());
            house.setFeatures(features);
        }

        if (houseDto.getImageIds() != null) {
            List<Image> images = imageRepository.findByIdIn(houseDto.getImageIds());
            house.setImages(images);
        }

        House savedHouse = houseRepository.save(house);
        return new HouseDto(savedHouse);
    }

    @Transactional
    @PutMapping("/{houseId}")
    public HouseDto updateHouse(@PathVariable Long houseId,
                           @RequestBody HouseDto houseDto) {
        House existingHouse = houseRepository.getOne(houseId);

        if (houseDto.getClientId() != null) {
            User client = userRepository.findById(houseDto.getClientId()).orElse(null);
            existingHouse.setClient(client);
        }

        if (houseDto.getAgentId() != null) {
            User agent = userRepository.findById(houseDto.getAgentId()).orElse(null);
            existingHouse.setAgent(agent);
        }

        if (houseDto.getFeatureIds() != null) {
            List<HouseFeature> features = houseFeatureRepository.findByIdIn(houseDto.getFeatureIds());
            existingHouse.setFeatures(features);
        }

        if (houseDto.getImageIds() != null) {
            List<Image> images = imageRepository.findByIdIn(houseDto.getImageIds());
            existingHouse.setImages(images);
        }

        existingHouse.setListing(houseDto.getListing());
        existingHouse.setHouseType(houseDto.getHouseType());
        existingHouse.setBathroomCount(houseDto.getBathroomCount());
        existingHouse.setBedRoomCount(houseDto.getBedRoomCount());
        existingHouse.setTotalRoomCount(houseDto.getTotalRoomCount());
        existingHouse.setParkingSpotCount(houseDto.getParkingSpotCount());
        existingHouse.setLivingArea(houseDto.getLivingArea());
        existingHouse.setCreatedAt(houseDto.getCreatedAt());
        existingHouse.setAddress(houseDto.getAddress());
        existingHouse.setPrice(houseDto.getPrice());
        existingHouse.setComments(houseDto.getComments());

        House savedHouse = houseRepository.save(existingHouse);
        return new HouseDto(savedHouse);
    }

    @Transactional
    @DeleteMapping("/{houseId}")
    public void deleteHouseById(@PathVariable Long houseId) {
        House house = houseRepository.findById(houseId).orElse(null);

        List<Long> imageIds = Objects.requireNonNull(house).getImages().stream().map(Image::getId).collect(Collectors.toList());
        List<Image> images = imageRepository.findByIdIn(imageIds);
        imageRepository.deleteAll(images);

        houseRepository.deleteById(houseId);
    }
}
