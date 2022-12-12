package ftn.uns.diplomski.movierecommendationservice.dto;

import ftn.uns.diplomski.movierecommendationservice.model.ERole;
import ftn.uns.diplomski.movierecommendationservice.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

    private Long id;
    private ERole name;

    public RoleDTO(Role role) {
        this.id = role.getId();
        this.name = role.getName();
    }

}
