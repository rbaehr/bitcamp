package main;


import net.java.games.input.*;
import sun.security.x509.CertificatePolicyMap;

import java.io.*;
import java.lang.reflect.Field;
import java.security.CodeSource;
import java.util.HashMap;
import java.util.TreeMap;


/**
 * Created by jeremy on 4/9/16.
 */
public class main {

    static class ButtonMap{
        HashMap<Component.Identifier, Double> tmap;

        public ButtonMap(){
            tmap = new HashMap<>();
            tmap.put(Component.Identifier.Button._0, 0.0);
           // tmap.put(Component.Identifier.Button._2, 0.0);
            tmap.put(Component.Identifier.Button._5, 0.0);
            tmap.put(Component.Identifier.Axis.X, 0.0);
            tmap.put(Component.Identifier.Axis.Y, 0.0);
            tmap.put(Component.Identifier.Axis.RZ, 0.0);
        }

        public boolean put(Component.Identifier ci, double d ){
            if(tmap.containsKey(ci)){
                tmap.put(ci, d);
                return true;
            }else{
                return false;
            }
        }

        public String get(long time){
            StringBuilder s = new StringBuilder(Long.toString(time) + ",");
            s.append(tmap.get(Component.Identifier.Button._0) + ",");
           // s.append(tmap.get(Component.Identifier.Button._2) + ",");
            s.append(tmap.get(Component.Identifier.Button._5) + ",");
            s.append(tmap.get(Component.Identifier.Axis.X) + ",");
            s.append(tmap.get(Component.Identifier.Axis.Y) + ",");
            s.append(tmap.get(Component.Identifier.Axis.RZ));

            return s.toString();
        }
    }

    static double triggerLimit = .5;

    public static void main(String args[]){
        FileWriter fw = null, fc = null;
        BufferedWriter bw = null, bc = null;
        PrintWriter out = null, outc = null;
        try {
            fw = new FileWriter("control.log", false);
            fc = new FileWriter("controldoc.csv", false);
            bw = new BufferedWriter(fw);
            bc = new BufferedWriter(fc);
            out = new PrintWriter(bw);
            outc = new PrintWriter(bc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println("[0000] Begin log");
        outc.println("time, 1, 6, x, y, z");
        outc.println("0, 0, 0, 0, 0, 0");
        out.flush();
        outc.flush();
        CodeSource codeSource = main.class.getProtectionDomain().getCodeSource();
        File jarFile = null;
        try {
            jarFile = new File(codeSource.getLocation().toURI().getPath());
            String jarDir = jarFile.getParentFile().getPath();
            setLibraryPath(jarDir + System.getProperty("file.separator") + "controller");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Controller[] ca = ControllerEnvironment.getDefaultEnvironment().getControllers();
        Controller xbox = null;
        for(Controller c : ca){
            if(c.getType().equals(Controller.Type.GAMEPAD)){
                xbox = c;
                break;
            }
        }
        if(xbox == null){
            System.out.println("Controller not found!");
            System.exit(-1);
        }

        EventQueue eq = xbox.getEventQueue();
        Event event = new Event();
        Long time = System.currentTimeMillis();
        HashMap<String, Double> axes =  new HashMap<>();
        ButtonMap map = new ButtonMap();
        while(true){
            xbox.poll();
            long eventT = ot(time);
            while(eq.getNextEvent(event)){
                map.put(event.getComponent().getIdentifier(), event.getValue());
                outc.println(map.get(eventT));

                if(event.getComponent().isAnalog()){
                    String name = event.getComponent().getName();
                    if(axes.containsKey(name)){
                        if(event.getValue() > triggerLimit && axes.get(name) < triggerLimit){
                            axes.put(name, triggerLimit);
                            //map.put(event.getComponent().getIdentifier(), 1.0);
                        }else if(event.getValue() < -triggerLimit && axes.get(name) > -triggerLimit){
                            axes.put(name, -triggerLimit);
                           // map.put(event.getComponent().getIdentifier(), -1.0);
                        }else if(Math.abs(event.getValue()) < triggerLimit && Math.abs(axes.get(name)) >= triggerLimit){
                            axes.put(name, 0.0);
                           // map.put(event.getComponent().getIdentifier(), 0.0);
                        }else{
                            //don't print
                            continue;
                        }
                    }else{
                        axes.put(name, 0.0);
                    }
                }

                String output = "[" + eventT + "] " + event.getComponent().getName() + " : " + event.getValue();
                System.out.println(output);
                try {
                    out.println(output);
                }catch(NullPointerException ex){
                    ex.printStackTrace();
                }
            }
            out.flush();
            outc.flush();
        }

    }

    public static long ot(long offset){
        return System.currentTimeMillis() - offset;
    }

    /**
     * Sets the java library path to the specified path
     *
     * @param path the new library path
     * @throws Exception
     */
    public static void setLibraryPath(String path) throws Exception {
        System.setProperty("java.library.path", path);

        //set sys_paths to null
        final Field sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
        sysPathsField.setAccessible(true);
        sysPathsField.set(null, null);
    }





}
