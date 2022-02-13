package com.hendisantika.postgres.dto;

import com.hendisantika.postgres.entity.Image;
import lombok.Data;

import java.io.Serializable;

@Data
public class ImageDto implements Serializable {
    private final Long id;
    private final String name;
    private final byte[] data;

    public ImageDto(Long id, String name, byte[] data) {
        this.id = id;
        this.name = name;
        this.data = data;
    }

    public ImageDto(Image image) {
        this.id = image.getId();
        this.name = image.getName();
        this.data = image.getData();
    }
}
