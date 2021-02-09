package root.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeDto {

    private Integer id;
    private String name;
    private Integer categoryId;

}
