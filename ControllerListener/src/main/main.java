package main;


import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

import java.io.*;
import java.lang.reflect.Field;
import java.security.CodeSource;
import java.util.HashMap;


/**
 * Created by jeremy on 4/9/16.
 */
public class main {

    static double triggerLimit = .5;

    public static void main(String args[]){
        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter out = null;
        try {
            fw = new FileWriter("control.log", false);
            bw = new BufferedWriter(fw);
            out = new PrintWriter(bw);
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println("[00000] Begin log");
        out.flush();
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
        while(true){
            xbox.poll();
            long eventT = ot(time);
            while(eq.getNextEvent(event)){
                if(event.getComponent().isAnalog()){
                    String name = event.getComponent().getName();
                    if(axes.containsKey(name)){
                        if(event.getValue() > triggerLimit && axes.get(name) < triggerLimit){
                            axes.put(name, triggerLimit);
                        }else if(event.getValue() < -triggerLimit && axes.get(name) > -triggerLimit){
                            axes.put(name, -triggerLimit);
                        }else if(Math.abs(event.getValue()) < triggerLimit && Math.abs(axes.get(name)) >= triggerLimit){
                            axes.put(name, 0.0);
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
                }catch(NullPointerException ex){}
            }
            out.flush();
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
