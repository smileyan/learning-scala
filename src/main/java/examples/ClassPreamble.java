package examples;

/**
 * Created by huay on 11/04/2016.
 */
public @interface ClassPreamble {
    String author();
    String date();
    int currentRevision() default 1;
    String lastModif() default "N/A";
}

@ClassPreamble(
        author = ";",
        date = "w",
        currentRevision = 2,
        lastModif = ""
)
class G1{

}