import java.awt.EventQueue;

public class Driver {
	/* ~~~ LAUNCH THE APPLICATION: ~~~ */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Minesweeper_MainWindow window = new Minesweeper_MainWindow();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
