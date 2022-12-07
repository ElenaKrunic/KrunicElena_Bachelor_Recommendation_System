package ftn.uns.diplomski.movierecommendationservice.dto;

import ftn.uns.diplomski.movierecommendationservice.model.Watchlist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class WatchlistDTO {

	private Long watchlistId;
	private boolean makeItPublic; 
	private String comment; 
	
	public WatchlistDTO(Watchlist watchlist) {
		this.watchlistId = watchlist.getWatchlistId(); 
		this.makeItPublic = watchlist.isMakeItPublic(); 
		this.comment = watchlist.getComment(); 
	}
}
