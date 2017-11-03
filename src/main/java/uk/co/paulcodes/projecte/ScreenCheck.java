package uk.co.paulcodes.projecte;

/**
 * Created by paulb on 01/11/2017.
 */
public class ScreenCheck implements Runnable {
    int i = 1;
    int index = 0;
    public void run() {
        while(true) {
            Main.getJedis().select(0);
            if(Main.getJedis().get(Main.clientID) != null) {
                Main.currentIndex = Integer.parseInt(Main.getJedis().get(Main.clientID));
            }else{
                Main.currentIndex = 3;
            }
            System.out.println(Main.currentIndex);
            Main.updateFrame();
            Main.generate();
            i++;
            try{
                Thread.sleep(3000);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
