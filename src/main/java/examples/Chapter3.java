package examples;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by huay on 31/01/2016.
 */
public class Chapter3 {
    public List<Artist> artistsFromLondon(List<Artist> allArtists) {
        return allArtists.stream()
                         .filter(artist -> artist.isFrom("London"))
                         .collect(Collectors.toList());
    }
    public long internalCountArtistsFromLondon(List<Artist> allArtists){
        return allArtists.stream()
                         .filter(artist -> artist.isFrom("London"))
                         .count();
    }
}
