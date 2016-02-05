package ch4;

import examples.Album;

import java.util.IntSummaryStatistics;
import java.util.function.Supplier;

/**
 * Created by huay on 5/02/2016.
 */
public class Logger {
    public void mydebug(Supplier<String> message) {
        if (isDebugEnabled()){
            debug(message.get());
        }
    }

    public boolean isDebugEnabled() {
        return true;
    }
    public void debug(String mess) {

    }

    public static void printTrackLengthStatistics(Album album) {
        IntSummaryStatistics trackLengthStats =
                album.getTracks()
                     .mapToInt(track -> track.getLength())
                     .summaryStatistics();
        System.out.printf("Max: %d, Min: %d, Ave: %f, Sum: %d",
                           trackLengthStats.getMax(),
                           trackLengthStats.getMin(),
                           trackLengthStats.getAverage(),
                           trackLengthStats.getSum());
    }
}
