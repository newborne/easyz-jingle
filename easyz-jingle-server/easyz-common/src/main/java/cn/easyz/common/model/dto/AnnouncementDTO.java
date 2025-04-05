package cn.easyz.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Announcement dto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementDTO {
    private String id;
    private String title;
    private String description;
    private String created;
}
