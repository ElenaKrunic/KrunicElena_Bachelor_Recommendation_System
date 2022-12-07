package ftn.uns.diplomski.movierecommendationservice.dto;

import ftn.uns.diplomski.movierecommendationservice.model.CustomList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class CustomListDTO {
	
	private Long customListId; 
	private String name; 
	private boolean makeItPublic; 
	private String description; 
	
	public CustomListDTO(CustomList customList) {
		this.customListId = customList.getCustomListId();
		this.name = customList.getName();
		this.makeItPublic = customList.isMakeItPublic();
		this.description = customList.getDescription();
	}
}
