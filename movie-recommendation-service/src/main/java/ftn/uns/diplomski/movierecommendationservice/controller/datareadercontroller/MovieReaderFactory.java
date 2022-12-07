package ftn.uns.diplomski.movierecommendationservice.controller.datareadercontroller;

public class MovieReaderFactory extends ReaderFactory {
	
	public Reader getReader(String filename) {
		return new MovieReader(filename);
	}
}
