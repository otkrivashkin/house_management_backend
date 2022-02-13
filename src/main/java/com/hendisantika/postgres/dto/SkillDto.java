package com.hendisantika.postgres.dto;

import com.hendisantika.postgres.entity.Skill;
import com.hendisantika.postgres.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SkillDto implements Serializable {
    private final Long id;
    private List<Long> agentIds = Collections.emptyList();
    private final String description;

    public SkillDto(Long id, List<Long> agentIds, String description) {
        this.id = id;
        this.agentIds = agentIds;
        this.description = description;
    }

    public SkillDto(Skill skill) {
        this.id = skill.getId();
        this.description = skill.getDescription();
        if (skill.getAgents() != null) {
            this.agentIds = skill.getAgents().stream().map(User::getId).collect(Collectors.toList());
        }

    }
}
