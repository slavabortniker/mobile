import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//variant 3
//N3350 Bortniker V.

public class MobileFirst {

    public static double Tarif(double in_dur, double out_dur, int sms) {
        double price = 0.0;
        if (out_dur > 20.0) price += (out_dur - 20.0) * 2.0;
        price += in_dur * 0.0;
        price += sms * 2;
        price *= 100.0;
        price = Math.round(price)/100.0;
        return price;
    }

    public static void main(String[] args) throws IOException {
        double incomingCallDur = 0.0, outgoingCallDur = 0.0, price;
        int smsCount = 0;
        String csvFilePath = args[0];
        BufferedReader bufreader= new BufferedReader(new FileReader(csvFilePath));
        String line;
        while ((line = bufreader.readLine()) != null) {
            String[] callDetail = line.split(",");
            if (callDetail[1].equals("915783624")) {
                outgoingCallDur = Double.parseDouble(callDetail[3]);
                smsCount = Integer.parseInt(callDetail[4]);
            }
            if (callDetail[2].equals("915783624")) {
                incomingCallDur = Double.parseDouble(callDetail[3]);
            }
        }
        price = Tarif(incomingCallDur, outgoingCallDur, smsCount);
        System.out.println("Price = " + price + " roubles");
    }
}
