import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

public class MobileSecond {
    public static void main(String[] args) throws IOException {
        double price;
        int Count = 0;
        ArrayList<Integer> trafficArr = new ArrayList<>();
        ArrayList<Integer> timeArr = new ArrayList<>();
        String csvFile = args[0];
        BufferedReader br;
        String line;
        br = new BufferedReader(new FileReader(csvFile));
        while ((line = br.readLine()) != null) {
            String[] info = line.split(",");
            if (info.length > 1) {
                if (info[3].equals("192.168.250.27") || info[4].equals("192.168.250.27")) {
                    Count += Integer.parseInt(info[12]);
                    trafficArr.add(Count);
                    StringBuilder getterTime = new StringBuilder(info[1]);
                    getterTime.delete(0, getterTime.indexOf(" ") + 1);
                    timeArr.add(LocalTime.parse(getterTime).toSecondOfDay()-41400);
                }
            }
        }
        int[] arrForXLine = new int[timeArr.size()];
        for (int i = 0; i < timeArr.size(); i++) arrForXLine[i] = timeArr.get(i);
        int[] arrForYLine = new int[trafficArr.size()];
        for (int i = 0; i < trafficArr.size(); i++) arrForYLine[i] = trafficArr.get(i);
        System.out.println("Traffic Count: " + Count + " bytes = " + Math.round(Count*100/Math.pow(2.0, 20.0))/100.0 + " Mb");
        price = Price(Count);
        System.out.println("Price: " + price + "R.");
        new Graph(arrForXLine, arrForYLine);
    }

    public static double Price(int trafficCount) {
        double price = (double) trafficCount / Math.pow(2.0, 20.0);
        price *= 100;
        price = Math.round(price) / 100.0;
        return price;
    }
}

class Graph extends javax.swing.JFrame {

    private int[] x;
    private int[] y;
    private int[] changedY;
    private Dimension size = new Dimension(1000, 700);
    private Dimension start = new Dimension(50, 670);

    public Graph(int[] times, int[] trafficArr) {
        this.x = times;
        this.y = trafficArr;
        changedY = new int[y.length];
        arrForGraphic();
        init();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, size.width, size.height);
        g.setColor(Color.GRAY);
        LocalTime startedTime = LocalTime.parse("11:30:00");
        for (int i = 1; i <= 18; i++) {
            if (i % 3 == 0) g.drawString(startedTime.plusSeconds((long) i * 200).toString(), start.width - 15 + start.width * i, start.height + (start.width - 25));
            g.drawString(String.valueOf(i * 500000), 10, start.height - start.width * i);
        }
        g.drawLine(start.width, start.height - 650, start.width, start.height);
        g.drawString("11:30", start.width-15, start.height+start.width-25);
        g.drawLine(start.width, start.height, start.width + 900, start.height);
        g.drawPolyline(x, changedY, changedY.length-1);
    }

    private void init() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(size);
        setResizable(false);
        setTitle("График зависимости трафика от времени");
        setVisible(true);
    }

    private void arrForGraphic() {
        for (int i = 0; i < x.length; i++) {
            x[i] /= 4;
            x[i] += start.width;
            y[i] /= 10000;
            changedY[i] = start.height - (y[i]);
        }
    }
}
