package com.hendisantika.postgres.controller;

import com.hendisantika.postgres.dto.SkillDto;
import com.hendisantika.postgres.entity.Skill;
import com.hendisantika.postgres.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("skills")
@CrossOrigin(origins = "http://localhost:4200")
public class SkillController {

    @Autowired
    private SkillRepository skillRepository;

    @Transactional
    @GetMapping
    public List<SkillDto> getSkills() {
        return skillRepository.findAll().stream().map(SkillDto::new).collect(Collectors.toList());
    }

    @GetMapping("/{skillId}")
    public Skill getSkillById(@PathVariable Long skillId) {
        return skillRepository.getOne(skillId);
    }

    @Transactional
    @PostMapping
    public Skill createSkill(@RequestBody SkillDto skillDto) {

        Skill skill = new Skill();
        skill.setDescription(skillDto.getDescription());

        return skillRepository.save(skill);
    }

    @Transactional
    @PutMapping("/{skillId}")
    public Skill updateSkill(@PathVariable Long skillId,
                           @RequestBody SkillDto skillDto) {
        Skill existingSkill = skillRepository.getOne(skillId);

        existingSkill.setDescription(skillDto.getDescription());

        return skillRepository.save(existingSkill);
    }

    @Transactional
    @DeleteMapping("/{skillId}")
    public void deleteSkillById(@PathVariable Long skillId) {
        skillRepository.deleteById(skillId);
    }
}
