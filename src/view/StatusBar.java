package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.GameModel;
import controller.Main;

public class StatusBar extends JPanel implements ViewObserver{

	private JLabel leftLabel;
	private JLabel midLabel;
	private JLabel rightLabel;
	public StatusBar(){
		GameModel.getInstance().addView(this);
		leftLabel = new JLabel();
		midLabel = new JLabel();
		rightLabel = new JLabel();
		this.setLayout(new GridLayout(1,3));
		this.add(leftLabel);
		this.add(midLabel);
		this.add(rightLabel);
		update();
	}
	@Override
	public void update() {
		leftLabel.setText(GameModel.getInstance().getGameTypeDescription());
		midLabel.setText(GameModel.getInstance().getStatusDescription());
		rightLabel.setText("Game ID: "+GameModel.getInstance().getGameId());
		
	}
	
}
