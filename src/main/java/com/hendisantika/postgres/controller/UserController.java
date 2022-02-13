package com.hendisantika.postgres.controller;

import com.hendisantika.postgres.dto.UserDto;
import com.hendisantika.postgres.entity.*;
import com.hendisantika.postgres.repository.HouseRepository;
import com.hendisantika.postgres.repository.ImageRepository;
import com.hendisantika.postgres.repository.SkillRepository;
import com.hendisantika.postgres.repository.UserRepository;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private HouseRepository houseRepository;

    @Transactional
    @GetMapping
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream().map(UserDto::new).collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId) {

        return userRepository.findById(userId).orElse(null);
    }

    @Transactional
    @GetMapping("/agents")
    public List<UserDto> getAllAgents() {
        List<User> users = userRepository.findByPosition(Position.AGENT);
        return users.stream().map(UserDto::new).collect(Collectors.toList());
    }

    @Transactional
    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {

        User user = userDto.toEntity();

        if (Position.AGENT.equals(userDto.getPosition()) && userDto.getSkillIds() != null) {
            List<Skill> skills = skillRepository.findByIdIn(userDto.getSkillIds());
            user.setSkills(skills);
        }

        if (Position.CLIENT.equals(userDto.getPosition()) && userDto.getAgentId() != null) {
            User agent = userRepository.findById(userDto.getAgentId()).orElse(null);
            user.setAgent(agent);
        }

        if (userDto.getImageId() != null) {
            Image image = imageRepository.findById(userDto.getImageId()).orElse(null);
            user.setImage(image);
        }

        User savedUser = userRepository.save(user);
        return new UserDto(savedUser);
    }

    @Transactional
    @PutMapping("/{userId}")
    public UserDto updateUser(@PathVariable Long userId,
                           @RequestBody UserDto userDto) {
        User existingUser = userRepository.getOne(userId);

        if (Position.AGENT.equals(userDto.getPosition()) && userDto.getSkillIds() != null) {
            List<Skill> skills = skillRepository.findByIdIn(userDto.getSkillIds());
            existingUser.setSkills(skills);
        }

        if (Position.CLIENT.equals(userDto.getPosition()) && userDto.getAgentId() != null) {
            User agent = userRepository.findById(userDto.getAgentId()).orElse(null);
            existingUser.setAgent(agent);
        }

        if (userDto.getImageId() != null) {
            Image image = imageRepository.findById(userDto.getImageId()).orElse(null);
            existingUser.setImage(image);
        }

        existingUser.setAddress(userDto.getAddress());
        existingUser.setName(userDto.getName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setGender(userDto.getGender());
        existingUser.setPassword(userDto.getPassword());
        existingUser.setPosition(userDto.getPosition());
        existingUser.setPhoneNumber(userDto.getPhoneNumber());

        User savedUser = userRepository.save(existingUser);
        return new UserDto(savedUser);
    }

    @Transactional
    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            if (Position.AGENT.equals(user.getPosition())) {
                List<User> clients = userRepository.findByPosition(Position.CLIENT);
                for (User client : clients) {
                    client.setAgent(null);
                    userRepository.save(client);
                }
            }

            List<House> byAgent = houseRepository.findByAgent(user);
            List<House> byClient = houseRepository.findByClient(user);
            List<House> houses = Stream.concat(byAgent.stream(), byClient.stream()).collect(Collectors.toList());
            for (House house: houses) {
                if (Position.AGENT.equals(user.getPosition())) {
                    house.setAgent(null);
                }
                if (Position.CLIENT.equals(user.getPosition())) {
                    house.setClient(null);
                }
                houseRepository.save(house);
            }

        }
        userRepository.deleteById(userId);
    }
}
