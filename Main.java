import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import java.util.Vector;
import scasim.core.*;
import scasim.gui.*;
import scasim.util.*;

import scasim.amp.*;
import scasim.acm.*;

import com.mxgraph.swing.mxGraphComponent;

public class Main extends JPanel implements ActionListener
{
    private SCAUtils utils = new SCAUtils();
    
    private JFrame frame;
    private JTabbedPane tabpane;
    private JMenuBar menu;
    private JMenu file;
    private JMenuItem openFile;
    private JMenuItem resetView;
    private JMenu help;
    private JMenuItem about;
    private JMenuItem helpGeneral;
    
    private Algorithm_View algorithm_View;
    private Config_View config_View;
    private GraphDrawer drawer = new GraphDrawer();

    private String helpManual = "Manual v0.2.";
    
    public Main(JFrame frame)
    {
	super(new BorderLayout());
	
	this.frame = frame;
	
	tabpane = new JTabbedPane();
	
	algorithm_View = new Algorithm_View();
	algorithm_View.setTabbedPane(tabpane);
	config_View = new Config_View();		
	
	menu = new JMenuBar();
	file = new JMenu("File");
	openFile = new JMenuItem("Load Graph");
	resetView = new JMenuItem("Reset View");
	openFile.addActionListener(this);
	resetView.addActionListener(this);
	file.add(openFile);		
	file.add(resetView);
	
	help = new JMenu("Help");
	about = new JMenuItem("About...");
	helpGeneral = new JMenuItem("Manual");
	
	about.addActionListener(this);
	helpGeneral.addActionListener(this);
	
	help.add(about);
	help.add(helpGeneral);
	algorithm_View.setPanel();
	algorithm_View.setFrame(frame);
	algorithm_View.setDrawer(drawer);
	tabpane.addTab("Algorithm", null, algorithm_View, "Algorithm view");
	tabpane.addTab("Config Algorithm", null, config_View, "Algorithm configuration");
	
	menu.add(file);
	menu.add(help);
	
	this.add(menu, BorderLayout.NORTH);
	this.add(tabpane, BorderLayout.CENTER);
    }
    
    public void actionPerformed(ActionEvent e)
    {
	if(e.getActionCommand().equals("Load Graph"))
	    {
		Explorer explorer = new Explorer();
		String path = explorer.showDialog(this);
		System.out.println("DEBUG: The graph selected is: "+path);
		if(!(path.equals("")))
		    {
			Vector graphNodes = utils.parseFile(path);
			algorithm_View.setGraphNodes(graphNodes);
			drawer.paintGraph(graphNodes);
			for(int i = 0; i < graphNodes.size(); i++)
			    {
				System.out.print("Service "+(i+1)+":    ");
				Vector si = (Vector)graphNodes.elementAt(i);
				for(int j = 0; j< si.size(); j++)
				    {
					String siValue= ((ServiceImplementation) si.elementAt(j)).getId() + "-" + ((ServiceImplementation) si.elementAt(j)).getSid();
					System.out.print(siValue);
					System.out.print("    ");
				    }
				System.out.println();
			    }
			algorithm_View.setGraphPanel(drawer.getGComponent());
		    }
	    }
	if(e.getActionCommand().equals("Reset View"))
	    {
		
	    }
	if(e.getActionCommand().equals("About..."))
	    {
		JOptionPane.showMessageDialog(frame, "Version v0.2\nPatricia Uriol\nFelipe Ibañez\nDREQUIEM\nUC3M");
	    }
	if(e.getActionCommand().equals("Manual"))
	    {
		JOptionPane.showMessageDialog(frame, helpManual);
	    }
    }
    
    private static void createAndShowMain() 
    {
	JFrame.setDefaultLookAndFeelDecorated(true);
	JFrame frame = new JFrame("RT Prune Algorithm Simulator");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setLocationRelativeTo(null);
	Main newContentPane = new Main(frame);
	newContentPane.setOpaque(true);
	frame.setContentPane(newContentPane);
	frame.pack();
	frame.setExtendedState(Frame.MAXIMIZED_BOTH); 
	frame.setVisible(true);
    }
    
    public static void main(String args[])
    {
	javax.swing.SwingUtilities.invokeLater(new Runnable()
	    {
		public void run() 
		{
		    createAndShowMain();
		}
	    });
    }
}
