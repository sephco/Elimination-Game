import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

@SuppressWarnings("serial")
public class EliminationGUI extends JFrame implements ActionListener {
	private int firstDice = 0;
	private int secondDice = 0;
	Random randomNum = new  Random();
	private int rollDiceCounter = 0;
	private int diceSum = 0;
	
	private JPanel contentPane;
	//North Panel
	private JPanel northPanel;
	private JLabel lblRolledDiceTitle = new JLabel("Rolled Dice: "); 
	private JLabel lblRolledDiceNumbers = new JLabel("0  0"); 
	//West Panel
	private JPanel westPanel;
	private int lowScore = 78; 
	private JLabel lblLowScoreTitle = new JLabel("Lowest Score"); 
	private JLabel lblLowScoreAmount = new JLabel(Integer.toString(lowScore)); 
	//Center Panel
	private JPanel centerPanel;
	private String[] numbersArray = {"", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
	private JButton[] numbers = new JButton[numbersArray.length];
	//East Panel
	private JPanel eastPanel;
	private int currentScore = 78;
	private JLabel lblCurrentScoreTitle = new JLabel("Current Score");
	private JLabel lblCurrentScoreAmount = new JLabel(Integer.toString(currentScore));
	//South Panel
	private JPanel southPanel;
	private JPanel southTop;
	private JPanel southBottom;
	private ButtonGroup group = new ButtonGroup(); //for radio buttons
	private JRadioButton radLightBlue = new JRadioButton("Light Blue");
	private JRadioButton radMagenta = new JRadioButton("Magenta");
	private JRadioButton colorThree = new JRadioButton("Color Three");
	private JButton rulesBtn = new JButton("Rules");
	private JButton rollBtn = new JButton("Roll");
	private JButton resetBtn = new JButton("Reset");
	
	ImageIcon icon1 = new ImageIcon("LeonardoDiCaprioHead2.png");

	// Create the frame.
	public EliminationGUI(String title) {
		super(title);
		setResizable(false);
		rollBtn.requestFocusInWindow();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 464, 235);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(5, 5));
		setContentPane(contentPane);
		
		//North panel to hold Dice Roll
		northPanel = new JPanel();
		contentPane.add(northPanel, BorderLayout.NORTH);
		lblRolledDiceTitle.setFont(new Font("Courier New", Font.PLAIN, 15)); //first font type & size
		northPanel.add(lblRolledDiceTitle); 
		northPanel.add(lblRolledDiceNumbers);
		
		//West panel to hold Lowest Score
		westPanel = new JPanel(); 
		westPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		contentPane.add(westPanel, BorderLayout.WEST);
		westPanel.add(lblLowScoreTitle);
		westPanel.add(lblLowScoreAmount);
		westPanel.setLayout(new GridLayout(2,1,5,5));
		lblLowScoreAmount.setFont(new Font("Arial", Font.PLAIN, 18)); // second font type & size
		lblLowScoreTitle.setFont(new Font("Arial", Font.PLAIN, 18));
		lblLowScoreAmount.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Center panel to hold number buttons 1-12
		centerPanel = new JPanel();
		centerPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
		contentPane.add(centerPanel, BorderLayout.CENTER);
		//set rows
		centerPanel.setLayout(new GridLayout(0, 6));
		//add buttons
		for (int count = 1; count < numbersArray.length; count++){
			numbers[count] = new JButton(numbersArray[count]);
			centerPanel.add(numbers[count]);
			numbers[count].setToolTipText("Select " + numbersArray[count]); //Add tooltips to each
			numbers[count].setEnabled(false);
			numbers[count].addActionListener(this);
		}
		
		//East panel to hold current score starting at 78
		eastPanel = new JPanel();
		eastPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		contentPane.add(eastPanel, BorderLayout.EAST);
		eastPanel.add(lblCurrentScoreTitle);
		eastPanel.add(lblCurrentScoreAmount);
		eastPanel.setLayout(new GridLayout(2,1,5,5));
		lblCurrentScoreTitle.setFont(new Font("Arial", Font.PLAIN, 18));
		lblCurrentScoreAmount.setFont(new Font("Arial", Font.PLAIN, 18));
		lblCurrentScoreAmount.setHorizontalAlignment(SwingConstants.CENTER);
		
		//South panels to hold both the color selector radio buttons (top)
		//and Rules, Roll, and Reset buttons (bottom)
		southPanel = new JPanel();
		contentPane.add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new GridLayout(2,1));
		
		//South TOP panel containing radio buttons for color selection
		southTop = new JPanel(); //to hold top row with color selector
		southTop.setLayout(new FlowLayout()); // second layout 
		southTop.setBorder(new TitledBorder(null, "Select a Color", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		group.add(radLightBlue); //Add colors to group
		group.add(radMagenta);
		group.add(colorThree);
		southTop.add(radLightBlue); //Add colors to panel
		southTop.add(radMagenta);
		southTop.add(colorThree);
		southPanel.add(southTop);
		
		//South Bottom panel containing buttons.
		southBottom = new JPanel(); //to hold bottom row with buttons
		southBottom.add(rulesBtn);
		southBottom.add(rollBtn);
		southBottom.add(resetBtn);
		southPanel.add(southBottom);
		rollBtn.requestFocusInWindow();
		
		//Register listeners
		radLightBlue.addActionListener(this);
		radMagenta.addActionListener(this);
		colorThree.addActionListener(this);
		rulesBtn.addActionListener(this);
		rollBtn.addActionListener(this);
		resetBtn.addActionListener(this);
		
		//ToolTips
		lblLowScoreTitle.setToolTipText("Lowest score");
		lblCurrentScoreTitle.setToolTipText("Current score");
		radLightBlue.setToolTipText("Select this color");
		radMagenta.setToolTipText("Select this color");
		colorThree.setToolTipText("Select this color");
		rulesBtn.setToolTipText("Read the rules");
		rollBtn.setToolTipText("Roll the dice");
		resetBtn.setToolTipText("Reset the round");
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
	    Object source = e.getSource();
	    diceSum = firstDice + secondDice;
	    if (source == rulesBtn){
			JOptionPane.showMessageDialog(this, "The Object of the game is to get the lowest score possible" +
					"\nby rolling the die and eliminating possible numbers. You may" +
					"\neliminate each dice individually or the sum of them together." +
					"\nIf you roll doubles you must take the sum of the two. The game" +
					"\nis over when you can't eliminate any more numbers.");
	    }
	    //Enable buttons if first time throwing dice
	    if (source == rollBtn){
	    	if (rollDiceCounter == 0){
	    		enableButtons();
	    		rollDiceCounter++;
	    	} 
	    	firstDice = randomNum.nextInt(6) + 1;
	    	secondDice = randomNum.nextInt(6) + 1;
	    	lblRolledDiceNumbers.setText(firstDice + "  " + secondDice);
	    	rollBtn.setEnabled(false);
	    } //end roll dice
	    if(source == resetBtn){
	    	if (JOptionPane.showConfirmDialog(this, "Are you sure you want to reset the game?", "Reset", 
	    			JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0){
		    	enableButtons();
		    	setLowScore();
		    	resetCurrentScore();
	    	}
	    }
	    
	    //Elimination method
	    for(int x = 1; x < numbers.length; x++){
	    	if(source == numbers[x]){ //Enter loop if source equals one of the numbers
			    if (diceSum != 0){ //Dice status - to make sure 1 or more the dice still has value
			    	//Begin conditional for when user ROLLS DOUBLES
			    	if (firstDice == secondDice){ 
			    		if(source == numbers[diceSum]){ //User can only select the sum of the doubles
			    			numbers[diceSum].setEnabled(false);
			    			updateCurrentScore(diceSum);
			    			firstDice = 0;
			    			secondDice = 0;
			    		}
			    		else //display message if user attempts to select number other than sum of doubles
			    			JOptionPane.showMessageDialog(this, "You must select the sum of both numbers when you draw doubles.");
			    	}//End doubles rolled portion of conditional 
			    	else if (firstDice != secondDice){ //If user does not roll doubles 
					    if (source == numbers[diceSum]){ //Allow user to select total
					    	numbers[diceSum].setEnabled(false); //Disable selected
					    	updateCurrentScore(diceSum); //Change current score
					    	firstDice = 0; //reset dice
					    	secondDice = 0;
					    }
					    else if(source == numbers[firstDice]){ //Allows user to eliminate first die
							numbers[firstDice].setEnabled(false);
							updateCurrentScore(firstDice);
							firstDice = 0;
						}
					    else if( source == numbers[secondDice]){ //Allows user to eliminate second die
					    	numbers[secondDice].setEnabled(false);
					    	updateCurrentScore(secondDice);
					    	secondDice = 0;
					    }
				    	else
				    		JOptionPane.showMessageDialog(this, "You must select either one of your dies or the sum of the two combined.");
				    }
			    }//end dice status check conditional 
	    	}//end elimination conditionals
	    }//end elimination for loop
	    //Enable Roll Button
	    if (firstDice == 0 && secondDice == 0){
	    	rollBtn.setEnabled(true);
	    }
	    //GAME OVER conditional - NOTE: Method currently causing error
	    if ((firstDice == 0 && numbers[secondDice].isEnabled() == false) || (secondDice == 0 && numbers[firstDice].isEnabled() == false) ){ 
    		int response = JOptionPane.showConfirmDialog(this, "Game over. Do you want to play again?", "Game Over", 
	    			JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, icon1);
    		if (response == 0){
		    	enableButtons();
		    	setLowScore();
		    	resetCurrentScore();
		    	rollBtn.setEnabled(true);
    		}
    		else
    			System.exit(0);
	    }
	    //Color Schemes
	    if (source == radLightBlue){
	    	setLightBlue();
	    }
	    if (source == radMagenta){
	    	setMagenta();
	    }
	    if (source == colorThree){
	    	setGreen();
	    }
	    
	}
	
	//********** METHODS **********
	//Re-enable buttons
	public void enableButtons(){
		for (int count = 1; count < numbers.length; count++){
			numbers[count].setEnabled(true);	
		}
	}
	//Set the low score
	private void setLowScore(){
		if (currentScore < lowScore)
			lowScore = currentScore;
			lblLowScoreAmount.setText(Integer.toString(lowScore));
	}
	//Reset current score
	private void resetCurrentScore(){
		currentScore = 78;
		lblCurrentScoreAmount.setText(Integer.toString(currentScore));
    	firstDice = 0;
    	secondDice = 0;
		lblRolledDiceNumbers.setText(firstDice + "  " + secondDice);
	}
	//Update current score - after user selects value
	private void updateCurrentScore(int n){
		currentScore -= n;
		lblCurrentScoreAmount.setText(Integer.toString(currentScore));
	}
	
	//COLOR SCHEMES AND RESETTING COLOR
	private void setLightBlue(){
    	resetColorScheme();
    	centerPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, Color.CYAN, Color.CYAN, null));
    	westPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, Color.CYAN, Color.CYAN, null));
    	eastPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, Color.CYAN, Color.CYAN, null));
    	southPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, Color.CYAN, Color.CYAN, null));
    	radLightBlue.setForeground(Color.CYAN);
    	radMagenta.setForeground(Color.CYAN);
    	colorThree.setForeground(Color.CYAN);
    	rulesBtn.setForeground(Color.CYAN);
    	rollBtn.setForeground(Color.CYAN);
    	resetBtn.setForeground(Color.CYAN);
	}
	private void setMagenta(){
    	resetColorScheme();
    	centerPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, Color.MAGENTA, Color.MAGENTA, null));
    	westPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, Color.MAGENTA, Color.MAGENTA, null));
    	eastPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, Color.MAGENTA, Color.MAGENTA, null));
    	southPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, Color.MAGENTA, Color.MAGENTA, null));
    	radLightBlue.setForeground(Color.MAGENTA);
    	radMagenta.setForeground(Color.MAGENTA);
    	colorThree.setForeground(Color.MAGENTA);
    	rulesBtn.setForeground(Color.MAGENTA);
    	rollBtn.setForeground(Color.MAGENTA);
    	resetBtn.setForeground(Color.MAGENTA);
	}
	private void setGreen(){
    	resetColorScheme();
		centerPanel.setBackground(new Color(0, 255, 127));
		westPanel.setBackground(new Color(0, 255, 127));
		eastPanel.setBackground(new Color(0, 255, 127));
		southPanel.setBackground(new Color(0, 255, 127));
		southBottom.setBackground(new Color(0, 255, 127));
		southTop.setBackground(new Color(0, 255, 127));
    	radLightBlue.setForeground(Color.BLACK);
    	radMagenta.setForeground(Color.BLACK);
    	colorThree.setForeground(Color.BLACK);
    	rulesBtn.setForeground(Color.BLACK);
    	rollBtn.setForeground(Color.BLACK);
    	resetBtn.setForeground(Color.BLACK);
	}
	private void resetColorScheme(){
    	Color color = UIManager.getColor ( "Panel.background" );
    	westPanel.setBackground(color);
    	centerPanel.setBackground(color);
    	eastPanel.setBackground(color);
    	southPanel.setBackground(color);
    	southBottom.setBackground(color);
    	southTop.setBackground(color);
    	centerPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, color, color, null));
    	westPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, color, color, null));
    	eastPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, color, color, null));
    	southPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, color, color, null));
    	radLightBlue.setForeground(color);
    	radMagenta.setForeground(color);
    	colorThree.setForeground(color);
    	rulesBtn.setForeground(color);
    	rollBtn.setForeground(color);
    	resetBtn.setForeground(color);
	}
}



//NOTES:

