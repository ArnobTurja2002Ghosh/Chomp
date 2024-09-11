import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.io.*;
import java.lang.Thread;
import javax.swing.*;

public class WindowDemo1 extends JFrame implements ActionListener, MouseListener
{
	// gui components that are contained in this frame:
	private JPanel bottomPanel, panel1, panel2, panel3, panel4;	// top and bottom panels in the main window
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
	public WindowDemo1()
	{
        this.setSize(500, 500);
		panel1= new GridSquare(0, 0);
        panel1.addMouseListener(this);
        panel1.add(new JLabel("Useful Tips chocolate bar"));

        panel2 =new GridSquare(1, 0);
        panel2.addMouseListener(this);
        panel2.add(new JLabel("The Game of Chomp chocolate bar"));

        panel3 =new GridSquare(0, 1);
        panel3.addMouseListener(this);
        panel3.add(new JLabel("Lecture slide chocolate bar"));

        panel4 =new GridSquare(1, 1);
        panel4.addMouseListener(this);
        panel4.add(new JLabel("Random size chocolate bar"));

        getContentPane().setLayout(new GridLayout(2, 2));
        getContentPane().add(panel1);
        getContentPane().add(panel2);
        getContentPane().add(panel3);
        getContentPane().add(panel4);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

    public void mouseClicked(MouseEvent mevt)
	{
		Object selected = mevt.getSource();
        if (selected instanceof GridSquare)
		{
            GridSquare square = (GridSquare) selected;
            if(square.getXcoord()==0 && square.getYcoord()==0){WindowDemo demo = new WindowDemo(5, 8);}
            else if(square.getXcoord()==1 && square.getYcoord()==0){WindowDemo demo = new WindowDemo(4, 5);}
            else if(square.getXcoord()==0 && square.getYcoord()==1){WindowDemo demo = new WindowDemo(4, 7);}
            else if(square.getXcoord()==1 && square.getYcoord()==1){WindowDemo demo = new WindowDemo((int)(2+Math.random()*6), (int)(4+Math.random()*4));}
            setVisible(false);
        }
        System.out.println('h');
	}
    public void actionPerformed(ActionEvent aevt){}
    public void mouseExited(MouseEvent arg0) {
        Object selected = arg0.getSource();
		
		/*
		 * I'm using instanceof here so that I can easily cover the selection of any of the gridsquares
		 * with just one piece of code.
		 * In a real system you'll probably have one piece of action code per selectable item.
		 * Later in the course we'll see that the Command Holder pattern is a much smarter way to handle actions.
		 */
		
		// if a gridsquare is selected then switch its color
		if (selected instanceof GridSquare )
		{
            GridSquare square = (GridSquare) selected;
			square.setBackground(Color.GRAY);;
		} 
    }
	public void mousePressed(MouseEvent arg0) {}
    public void mouseEntered(MouseEvent arg0){
        Object selected = arg0.getSource();
		
		/*
		 * I'm using instanceof here so that I can easily cover the selection of any of the gridsquares
		 * with just one piece of code.
		 * In a real system you'll probably have one piece of action code per selectable item.
		 * Later in the course we'll see that the Command Holder pattern is a much smarter way to handle actions.
		 */
		
		// if a gridsquare is selected then switch its color
		if (selected instanceof GridSquare )
		{
            GridSquare square = (GridSquare) selected;
			square.switchColor();
		} 
		
    }
	public void mouseReleased(MouseEvent arg0) {}
}