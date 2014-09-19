package Gallhp;

import java.awt.EventQueue;

public class Demo {

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	new GallhpView();
            }
		});
	}
}