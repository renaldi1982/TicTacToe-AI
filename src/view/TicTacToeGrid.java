package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import model.GameModel;
import controller.GameController;
import controller.Main;

public class TicTacToeGrid extends JPanel implements ViewObserver{

	private ArrayList<JButton> buttons;
	public TicTacToeGrid(){
		GameModel.getInstance().addView(this);
		
		this.setLayout(new GridLayout(3, 3));
		buttons = new ArrayList<JButton>();
		for(int i=0; i<9; i++){
			JButton button = new JButton();
			button.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					GameController.getInstance().move(buttons.indexOf(e.getSource()));
					
				}});
			this.add(button);
			buttons.add(button);
		}
		update();
	}
	@Override
	public void update() {
		if(GameModel.getInstance().isGameInProgress()){
			for(int i=0; i<buttons.size(); i++){
				buttons.get(i).setText(GameModel.getInstance().getGrid()[i]+"");
				if(GameModel.getInstance().isMoveValid(i, GameModel.getInstance().getPlayerNumber())){
					buttons.get(i).setEnabled(true);
				}
				else{
					buttons.get(i).setEnabled(false);
				}
				
			}
		}
		else{
			for(int i=0; i<buttons.size(); i++){
				buttons.get(i).setText("");
				buttons.get(i).setEnabled(false);
			}
		}
	}
}
