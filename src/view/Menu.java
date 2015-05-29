package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.GameController;
import controller.Main;
import model.GameModel;

public class Menu extends JPanel implements ActionListener, ViewObserver{

	//0=human vs human
		//1=human vs AI
		//2=AI vs Server opponent
		//3=human vs Server opponent
	
		private JRadioButton humanVsHumanButton;
		private JRadioButton humanVsAIButton;
		private JRadioButton humanVsServerButton;
		private JRadioButton aiVsServerButton;
		
		private JRadioButton joinButton;
		private JRadioButton hostButton;
		
		private JTextField gameIdField;
		private JLabel gameIdFieldLabel;
		private JButton newGameButton;
		private JButton endGameButton;
	
		public Menu(){
			humanVsHumanButton = new JRadioButton("Human -vs- Human");
			humanVsHumanButton.addActionListener(this);
			humanVsHumanButton.setSelected(true);
			humanVsAIButton = new JRadioButton("Human -vs- AI");
			humanVsAIButton.addActionListener(this);
			humanVsServerButton = new JRadioButton("Human -vs- Server Opponent");
			humanVsServerButton.addActionListener(this);
			aiVsServerButton = new JRadioButton("AI -vs- Server Opponent");
			aiVsServerButton.addActionListener(this);
			
			
			//Group the radio buttons.
		    ButtonGroup group = new ButtonGroup();
		    group.add(humanVsHumanButton);
		    group.add(humanVsAIButton);
		    group.add(humanVsServerButton);
		    group.add(aiVsServerButton);
		    
		    JPanel buttonsPanel = new JPanel(new GridLayout(9,1));
		    buttonsPanel.add(humanVsHumanButton);
		    buttonsPanel.add(humanVsAIButton);
		    buttonsPanel.add(humanVsServerButton);
		    buttonsPanel.add(aiVsServerButton);
		    
		    gameIdField = new JTextField(6);
		    gameIdField.setEnabled(false);
		    gameIdFieldLabel = new JLabel("GameId");
		    gameIdFieldLabel.setEnabled(false);
		    JPanel fieldContainer = new JPanel();
		    fieldContainer.add(gameIdFieldLabel);
		    fieldContainer.add(gameIdField);
		   
		    
		    
		    joinButton = new JRadioButton("Join Game");
		    joinButton.setBorder(new EmptyBorder (0, 40, 0, 0));
		    joinButton.addActionListener(this);
		    joinButton.setSelected(true);
		    hostButton = new JRadioButton("Host Game");
		    hostButton.setBorder(new EmptyBorder (0, 40, 0, 0));
		    hostButton.addActionListener(this);
			ButtonGroup group1 = new ButtonGroup();
		    group1.add(joinButton);
		    group1.add(hostButton);
		    buttonsPanel.add(joinButton);
		    buttonsPanel.add(fieldContainer);
		    buttonsPanel.add(hostButton);
		    
		    newGameButton = new JButton("Start New Game");
		    newGameButton.addActionListener(this);
		    buttonsPanel.add(newGameButton);
		    
		    endGameButton = new JButton("End Game");
		    endGameButton.addActionListener(this);
		    buttonsPanel.add(endGameButton);
		    
		    
		    this.add(buttonsPanel, BorderLayout.NORTH);
		    update();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == newGameButton){
				
				if(humanVsHumanButton.isSelected()){
					GameController.getInstance().newHumanVsHuman();
				}
				else if(humanVsAIButton.isSelected()){
					GameController.getInstance().newHumanVsAI();
				}
				else if(humanVsServerButton.isSelected()){
					if(joinButton.isSelected()){
						GameController.getInstance().joinHumanVsServer(gameIdField.getText());
					}
					else{
						GameController.getInstance().hostHumanVsServer();
					}
				}
				else if(aiVsServerButton.isSelected()){
					if(joinButton.isSelected()){
						GameController.getInstance().joinAiVsServer(gameIdField.getText());
					}
					else{
						GameController.getInstance().hostAiVsServer();
					}
				}
			}
			else if(e.getSource() == endGameButton){
				GameController.getInstance().endGame();
			}
			
			update();
			
			
		}

		@Override
		public void update() {
			if(GameModel.getInstance().isGameInProgress()){
				humanVsHumanButton.setEnabled(false);
				humanVsAIButton.setEnabled(false);
				humanVsServerButton.setEnabled(false);
				aiVsServerButton.setEnabled(false);
				joinButton.setEnabled(false);
				hostButton.setEnabled(false);
				gameIdField.setEnabled(false);
				gameIdFieldLabel.setEnabled(false);
				newGameButton.setEnabled(false);
				endGameButton.setEnabled(true);
			}
			else{
				humanVsHumanButton.setEnabled(true);
				humanVsAIButton.setEnabled(true);
				humanVsServerButton.setEnabled(true);
				aiVsServerButton.setEnabled(true);
				newGameButton.setEnabled(true);
				endGameButton.setEnabled(false);
				
				if(humanVsServerButton.isSelected() || aiVsServerButton.isSelected()){
					joinButton.setEnabled(true);
					hostButton.setEnabled(true);
					
				}
				else{
					joinButton.setEnabled(false);
					hostButton.setEnabled(false);
				}
				if(joinButton.isEnabled() && joinButton.isSelected()){
					gameIdField.setEnabled(true);
					gameIdFieldLabel.setEnabled(true);
				}
				else{
					gameIdField.setEnabled(false);
					gameIdFieldLabel.setEnabled(false);
				}
				
			}
			
			
			
			
		}
}
