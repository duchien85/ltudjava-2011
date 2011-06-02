import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;




public class myMouseListener extends MouseAdapter{
	private int[] sinal;
	public myMouseListener(int[] signal){
		this.sinal=signal;
	}
	
	public void mousePressed(MouseEvent e) {				
		sinal[0]=Integer.parseInt(e.getComponent().getName());
	}
	
}
