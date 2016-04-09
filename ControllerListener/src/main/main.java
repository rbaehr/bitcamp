package main;

import de.hardcode.jxinput.directinput.DirectInputDevice;

import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;

/**
 * Created by jeremy on 4/9/16.
 */
public class main {

    public static void main(String args[]){
        CodeSource codeSource = main.class.getProtectionDomain().getCodeSource();
        try {
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            String jarDir = jarFile.getParentFile().getPath();
            System.load(jarDir + "\\jxinput.dll");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        DirectInputDevice joy = new DirectInputDevice(1);
        System.out.println("Listening to controller 1");
        int butts = joy.getNumberOfButtons();
        int axs = joy.getNumberOfAxes();
        boolean buttons[] = new boolean[butts];
        double axes[] = new double[axs];
        System.out.println("Number of buttons found: " + butts);
        System.out.println("Number of axes found: " + axs);
        long timeOffset = System.currentTimeMillis();
        while(true) {
            for (int i = 0; i < axs; i++) {
                if (Math.abs(axes[i]) > .5 && Math.abs(joy.getAxis(i).getValue()) < .5) {
                    axes[i] = 0;
                    System.out.println("[" + ot(timeOffset) + "] "+"Axis " + i + " is at 0");
                } else if (axes[i] < 0 && joy.getAxis(i).getValue() > .5) {
                    axes[i] = .5;
                    System.out.println("[" + ot(timeOffset) + "] "+"Axis " + i + " is at +1");
                } else if (axes[i] > 0 && joy.getAxis(i).getValue() < -.5) {
                    axes[i] = -.5;
                    System.out.println("[" + ot(timeOffset) + "] "+"Axis " + i + " is at -1");
                }
            }

            for (int i = 0; i < butts; i++) {
                if (buttons[i] != joy.getButton(i).getState()) {
                    buttons[i] = !buttons[i];
                    System.out.println("[" + ot(timeOffset) + "] "+"Button " + i + " is " + buttons[i]);
                }
            }
        }
    }

    public static long ot(long offset){
        return System.currentTimeMillis() - offset;
    }
}
