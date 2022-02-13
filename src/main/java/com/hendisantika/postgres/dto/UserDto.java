package com.hendisantika.postgres.dto;

import com.hendisantika.postgres.entity.Gender;
import com.hendisantika.postgres.entity.Position;
import com.hendisantika.postgres.entity.Skill;
import com.hendisantika.postgres.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDto implements Serializable {
    private final Long id;
    private final String name;
    private final Gender gender;
    private final String phoneNumber;
    private final String email;
    private final String address;
    private final String password;
    private final Position position;
    private final String comments;
    private Long agentId;
    private List<Long> skillIds = Collections.emptyList();
    private  Long imageId;
    private ImageDto image;
    private List<SkillDto> skills;

    public UserDto(Long id, String name, Gender gender, String phoneNumber, String email, String address, String password, Position position, String comments, Long agentId, Long imageId, ImageDto image) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.password = password;
        this.position = position;
        this.comments = comments;
        this.agentId = agentId;
        this.imageId = imageId;
        this.image = image;
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.gender = user.getGender();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.address = user.getAddress();
        this.password = user.getPassword();
        this.position = user.getPosition();
        this.comments = user.getComments();
        if (user.getAgent() != null) {
            this.agentId = user.getAgent().getId();
        }

        if (user.getImage() != null) {
            this.imageId = user.getImage().getId();
            this.image = new ImageDto(user.getImage());
        }

        if (user.getSkills() != null) {
            this.skills = user.getSkills().stream().map(SkillDto::new).collect(Collectors.toList());
            this.skillIds = user.getSkills().stream().map(Skill::getId).collect(Collectors.toList());
        }
    }

    public User toEntity() {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setGender(gender);
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        user.setAddress(address);
        user.setPassword(password);
        user.setPosition(position);
        user.setComments(comments);
        return user;
    }
}
