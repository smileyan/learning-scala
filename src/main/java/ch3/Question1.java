package ch3;

import examples.Album;
import examples.Artist;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by huay on 4/02/2016.
 */
public class Question1 {
    public static int addUp(Stream<Integer> numbers) {
        return numbers.reduce(0, (x, y) -> x + y);
    }

    public static List<String> getNamesAndOrigins(List<Artist> artists) {
        return artists.stream()
                       .flatMap(artist -> Stream.of(artist.getName(), artist.getNationality()))
                       .collect(Collectors.toList());
    }

    public static List<Album> getAlbumsWithAtMostThreeTracks(List<Album> albums) {
        return albums.stream().filter(album -> album.getTrackList().size() <= 3)
                       .collect(Collectors.toList());
    }
}
