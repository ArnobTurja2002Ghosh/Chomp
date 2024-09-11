import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.io.*;
import java.lang.Thread;
import javax.swing.*;

/*
 *  The main window of the gui.
 *  Notice that it extends JFrame - so we can add our own components.
 *  Notice that it implements ActionListener - so we can handle user input.
 *  This version also implements MouseListener to show equivalent functionality (compare with the other demo).
 *  @author mhatcher
 */
public class WindowDemo extends JFrame implements ActionListener, MouseListener
{
	// gui components that are contained in this frame:
	private JPanel topPanel, bottomPanel;	// top and bottom panels in the main window
	private JLabel instructionLabel;		// a text label to tell the user what to do
	private JLabel infoLabel, playerLabel;            // a text label to show the coordinate of the selected square
    private JButton topButton;				// a 'reset' button to appear in the top panel
	//private JButton break1;
	private GridSquare [][] gridSquares;	// squares to appear in grid formation in the bottom panel
	private int rows,columns;				// the size of the grid
	int player;
	int[] selected1 = null;
	String[] players;
	/*
	 *  constructor method takes as input how many rows and columns of gridsquares to create
	 *  it then creates the panels, their subcomponents and puts them all together in the main frame
	 *  it makes sure that action listeners are added to selectable items
	 *  it makes sure that the gui will be visible
	 */
	public WindowDemo(int rows, int columns)
	{
		this.rows = rows;
		this.columns = columns;
		this.setSize(100*columns,100*rows);
		
		// first create the panels
		topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(rows, columns, 1,1));
		//bottomPanel.setBackground(Color.WHITE);
		//bottomPanel.setSize(100*columns,100*rows);
		// then create the components for each panel and add them to it
		
		// for the top panel:
		instructionLabel = new JLabel("Click a legal square!");
        infoLabel = new JLabel("No square clicked yet.");
		topButton = new JButton("play again");
		//break1=new JButton("Break");
		players= Math.random() < 0.5 ? new String[]{"user", "computer"}: new String[]{"computer", "user"};
		playerLabel = new JLabel(players[player] + "'s turn");

		//break1.addActionListener(this);
		topButton.addActionListener(this);			// IMPORTANT! Without this, clicking the square does nothing.
		
		topPanel.add(instructionLabel);
		//topPanel.add (topButton);
		//topPanel.add(break1);
        topPanel.add(infoLabel);
		topPanel.add(playerLabel);
		
	
		// for the bottom panel:	
		// create the squares and add them to the grid
		gridSquares = new GridSquare[columns][rows];
		for ( int y = 0; y < rows; y ++)
		{
			for ( int x = 0; x < columns; x ++)
			{
				gridSquares[x][y] = new GridSquare(x, y);
				//gridSquares[x][y].setSize(20, 20);
				//gridSquares[x][y].setColor(x + y);
				if(x==0 && y==0){
					gridSquares[x][y].setBackground(Color.GREEN);
					JLabel bl =new JLabel("S");
					bl.setFont(new Font("Arial", 0, 30));
					gridSquares[x][y].add(bl);
				} else{
					gridSquares[x][y].setBackground(new Color(102, 51, 0));
				}
				gridSquares[x][y].addMouseListener(this);		// AGAIN, don't forget this line!
				
				bottomPanel.add(gridSquares[x][y]);
			}
		}
		
		// now add the top and bottom panels to the main frame
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(topPanel, BorderLayout.NORTH);
		getContentPane().add(bottomPanel, BorderLayout.CENTER);		// needs to be center or will draw too small
		
		// housekeeping : behaviour
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		
		if(players[player].equals("computer")){new Thread(() -> {
			computer1();}).start();}
	}
	
	
	/*
	 *  handles actions performed in the gui
	 *  this method must be present to correctly implement the ActionListener interface
	 */
	public void actionPerformed(ActionEvent aevt)
	{
		// get the object that was selected in the gui
		Object selected = aevt.getSource();
				
		// if resetting the squares' colours is requested then do so
		if ( selected.equals(topButton) )
		{
			selected1=null; 
			infoLabel.setText("No square clicked yet.");
			for ( int x = 0; x < columns; x ++)
			{
				for ( int y = 0; y < rows; y ++)
				{
					if(x==0 && y==0){
						gridSquares[x][y].setBackground(Color.GREEN);
					} else{
						gridSquares[x][y].setBackground(new Color(102, 51, 0));
					}
				}
			}
			players= Math.random() < 0.5 ? new String[]{"user", "computer"}: new String[]{"computer", "user"};
			playerLabel.setText(players[player] +"'s turn.");
			topPanel.remove(topButton);
			if(players[player].equals("computer")){new Thread(() -> {
				computer1();}).start();}
			
		}
		
	}


	// Mouse Listener events
	public void mouseClicked(MouseEvent mevt)
	{
		if(selected1 != null){
			if(break1()==null){
				new Thread(() -> {
					computer1();
				}).start();
			}
		}
	}
	public String break1(){
		for(int y=this.selected1[1]; y<rows; y++){
			for ( int x = this.selected1[0]; x < columns; x ++){
				gridSquares[x][y].setBackground(Color.WHITE);
			}
		}
		player=(player+1)%2;
		playerLabel.setText(players[player] + "'s turn.");
		selected1=null;
		System.out.println("broken");
		if(gridSquares[1][0].getBackground().equals(Color.WHITE) && gridSquares[0][1].getBackground().equals(Color.WHITE) && gridSquares[1][1].getBackground().equals(Color.WHITE)){
			playerLabel.setText(players[player] + " loses. " +players[(player+1)%2]+ " wins.");
			topPanel.add(topButton);
			topPanel.remove(instructionLabel);
			return players[player];
		}
		return null;
	}
	public void computer1(){
		int[] selected = new int[]{(int) Math.random()*columns, (int) Math.random()*rows};

		while(!gridSquares[selected[0]][selected[1]].getBackground().equals(new Color(102, 51, 0))){ 
			selected = new int[]{(int) (Math.random()*columns), (int) (Math.random()*rows)};
			System.out.println(selected[0]);
		}
		selected1=selected;
		gridSquares[selected1[0]][selected1[1]].setBackground(Color.PINK);
		try{
			Thread.sleep(1000);
			
		}
		catch(Exception e){
			System.out.println(e);
		}
		break1();
	}
	// not used but must be present to fulfil MouseListener contract
	public void mouseEntered(MouseEvent arg0){
		Object selected = arg0.getSource();
		
		/*
		 * I'm using instanceof here so that I can easily cover the selection of any of the gridsquares
		 * with just one piece of code.
		 * In a real system you'll probably have one piece of action code per selectable item.
		 * Later in the course we'll see that the Command Holder pattern is a much smarter way to handle actions.
		 */
		
		// if a gridsquare is selected then switch its color
		if (selected instanceof GridSquare && players[player].equals("user"))
		{
            GridSquare square = (GridSquare) selected;
			if(square.getBackground().equals(new Color(102, 51, 0)))
			{
				square.switchColor();
				int x = square.getXcoord();
				int y = square.getYcoord();
				infoLabel.setText("("+x+","+y+") selected by user.");
				if(selected1!=null)
				{
					gridSquares[selected1[0]][selected1[1]].setBackground(new Color(102, 51, 0));
				}
				selected1= new int[]{x, y}; 
			} 
		}	
	}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
}