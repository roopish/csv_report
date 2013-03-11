package edu.mit.star.csv_report;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Main extends JFrame{

	/**
	 * @param args
	 */
	private static final long serialVersionUID = 1L;
	JTextArea input;
	JButton calculate;
	JPanel report;
	JTextArea output;
	String[] dataRow;  //stores array of each row
	HashMap<String,Float> name_hrs = new HashMap<String,Float>(); //stores key=name,time=value
	HashMap<String,Integer> name_events = new HashMap<String,Integer>(); //stores key=name,events=value
	HashMap<String,Float> averages = new HashMap<String,Float>(); //stores key=name;average=value
	ClickListener cl = new ClickListener();  //listener for button click
	
	@Override
	public void addNotify()
	{
		super.addNotify();
		Container c = getContentPane();
		c.setLayout(new BoxLayout(c, BoxLayout.PAGE_AXIS));
		input = new JTextArea(12, 40);
		input.setBorder(BorderFactory.createTitledBorder("Input"));
		calculate = new JButton("Calculate");
		calculate.addActionListener(cl);
		report = new JPanel();
		report.setBorder(BorderFactory.createTitledBorder("Report"));
		output = new JTextArea(12,40);  //created same area as input
		c.add(input);
		c.add(calculate);
		c.add(report);
		c.add(output);
		
	}
		@SuppressWarnings("unused")
		class ClickListener implements ActionListener {
	   

		    @Override
			public void actionPerformed(ActionEvent arg0) {
				// get each row
		    	dataRow = input.getText().split("\\n");
				String[] values;
				
				for(int i=0;i<dataRow.length;i++)
				{
					//get each column for every row
					values=dataRow[i].split(",");
					
					String name = values[0].toLowerCase();
					//first letter upper case
					name = Character.toString(name.charAt(0)).toUpperCase()+name.substring(1);
					float time = Float.parseFloat(values[1]);
					String events = values[2];				    
				if(name_hrs.containsKey(name)) // if key already exists in name_hrs increment events,add time
				{
					
					name_hrs.put(name, name_hrs.get(name) + time);
					name_events.put(name, name_events.get(name)+1);
				}
				else //new record add time, set event to 1
				{
					name_hrs.put(name, time);
					name_events.put(name, new Integer(1));
				}
				//calculate average
				averages.put(name, (float) (name_hrs.get(name) / name_events.get(name)));
				
		    }
				//iterator to display data from Hashmap
                Iterator<String> i = averages.keySet().iterator();
                int events=0;
                Font font = new Font("Verdana", Font.PLAIN, 12);
                output.setForeground(Color.BLUE);
                output.setFont(font);
                output.setText("NAME\t NO_OF_EVENTS \tAVG_TIME\n\n");
                
                while (i.hasNext()) {
                	String key = (String)i.next();

                	Float val = (Float)averages.get(key);
                	events = name_events.get(key);
                	output.append(key +"\t" + events +"\t\t"+val+"\n");     
                }
		}
		}
	
	public static void main(String[] args) {
		
		try
		{
			SwingUtilities.invokeAndWait(new Runnable()
			{

				@Override
				public void run()
				{
					Main m = (new Main());
					m.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					m.pack();
					m.setVisible(true);
				}
			});
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
	}
	}


