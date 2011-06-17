package heartsgame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class myMouseListener extends MouseAdapter {

    private int[] sinal;
    private boolean isMousePress;

    public myMouseListener(int[] signal) {
        this.sinal = signal;
    }

    public myMouseListener() {
        sinal = new int[10];
    }


    public void mousePressed(MouseEvent e) {
        //sinal[0] = Integer.parseInt(e.getComponent().getName());
        if (Integer.parseInt(e.getComponent().getName())== MouseEvent.MOUSE_PRESSED)
            isMousePress =true;
        else
            isMousePress = false;
    }

    /**
     * @return the isMousePress
     */
    public boolean IsMousePress() {
        return isMousePress;
    }


}
