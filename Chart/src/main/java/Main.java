import org.jfree.ui.RefineryUtilities;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main( String[] args ) throws IOException {
        XYLinePrint chart = new XYLinePrint("Charts",
            "Лучше иметь друга, чем друг друга");
        System.out.println("Do you want to save this chart(s)? yes/no");
        Scanner in = new Scanner(System.in);
        if(in.next().equals("yes") )
            chart.saveAsJPEG( "Лучше иметь друга, чем друг друга");
        chart.pack( );
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );

    }
}
