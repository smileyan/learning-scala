package ch3;

import examples.Artist;

import java.util.List;

/**
 * Created by huay on 4/02/2016.
 */
public class Question2 {
    public static int countBandMembersInternal(List<Artist> artists) {
        return artists.stream().map(artist -> artist.getMembers().count())
                               .reduce(0L, (a, b) -> a + b)
                               .intValue();
    }
}
