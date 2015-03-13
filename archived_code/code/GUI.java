import javax.swing.*;

public class GUI extends JFrame{
	JPanel panel;
	JButton button;
	JLabel label;

	public GUI(){

		panel = new JPanel();
		button = new JButton();
		label = new JLabel();

		panel.add(button);
		panel.add(label);
		this.add(panel);
	}
}