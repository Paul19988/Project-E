package uk.co.paulcodes.projecte;

/**
 * Created by paulb on 01/11/2017.
 */
public class Loaded implements Runnable {
    public void run() {
        while(true) {
            Main.minimize();
            try{
                Thread.sleep(3000);
            }catch(Exception e) {
                e.printStackTrace();
            }
            Main.maximize();
            try{
                Thread.sleep(3000);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
