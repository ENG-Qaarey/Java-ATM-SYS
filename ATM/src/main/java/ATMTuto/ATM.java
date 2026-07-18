
package ATMTuto;

/**
 *
 * @author Shashi
 */
public class ATM {

    public static void main(String[] args) {
        SplashScreen splash = new SplashScreen();
        splash.setVisible(true);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        splash.dispose();
        new Login().setVisible(true);
    }
}
