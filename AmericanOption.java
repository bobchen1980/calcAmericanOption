import java.applet.*;
import java.text.*;
import java.util.*;
import java.math.*;
import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;



public class AmericanOption extends JApplet implements ActionListener,
                                                        FocusListener
{
	
	// Panels
	private JPanel mainPanel = null;
	private JPanel inputPanel = null;
	private JPanel outputPanel = null;
	private JPanel buttonPanel = null;
	private JPanel dataPanel = null;
	private JScrollPane tablePanel = null;
	private JPanel valuePanel  = null;
	private JLabel outputJLabel = null ;
	private DataTableModel dataTable = null;
	
	// Buttons
	private ButtonGroup graphGroup = null;
	private ButtonGroup callGroup = null;
	private JRadioButton callButton = null;
	private JRadioButton putButton = null;
	private JButton calculateButton = null;
	private JButton resetButton     = null;
	
	// Strings	
	private final String EMPTY = "   ";
	private final String CALL = "Call";
	private final String PUT = "Put";
	private final String CALCULATE = "Calculate";
	private final String INPUT = "Input";
	private final String OUTPUT = "Output";
	private final String VALUE = "American option value calulated by input parameters";
	private final String RESET = "Reset";	
	private final String STRIKE_K_LABEL = "Strike price";
   private final String DIVIDEND_LABEL = "Dividend yield";
   private final String STOCK_LABEL = "Stock price";
   private final String INTEREST_LABEL = "Interest rate";
   private final String VOLATILITY_LABEL = "Volatility";
   private final String MATURITY_LABEL = "Maturity";
   private final String M_STEPS_LABEL = "Steps in M";
   private final String N_STEPS_LABEL = "Steps in N";
	private final String NOT_A_NUMBER = " Enter a number";
	private final String NOT_INTEGER = " Enter an integer number";
	private final String NOT_POSITIVE = "Enter a positive number";
   private DecimalFormat numberFormatter = null;
	
	// Text Fields
	private JTextField strike_KField = null;
	private JTextField dividend_Field = null;
	private JTextField stock_Field = null;
	private JTextField interest_Field = null;
	private JTextField volatility_Field = null;
	private JTextField maturity_Field = null;
	private JTextField m_steps_Field = null;
	private JTextField n_steps_Field = null;	
	
	// Constants                           
	private final double STOCK = 100.0;
	private final double STRIKE_K = 100.0;
	private final double INTEREST = 0.1;
	private double MATURITY = 0.75;
	private double VOLATILITY = 0.3;
	private double DIVIDEND = 0.05;
	private int M_STEPS = 20;
	private int N_STEPS = 12;
	
	// Numerical Variables
	private double stock = STOCK;
	private double strike_K = STRIKE_K;
	private double interest = INTEREST;
   private double maturity = MATURITY;
   private double volatility = VOLATILITY;
   private double dividend = DIVIDEND;
   private int m1 = M_STEPS;
   private int n1 = N_STEPS;

   // Boolean Variable
	private boolean bCall = true;
	
	// Method of Action Listener
	public void	focusGained(FocusEvent e) {
    }
    // Error Messages
	public void	focusLost(FocusEvent e) { 
		Object source = e.getSource();
		
	   if (source == stock_Field){ 
		   stock = readPositive(stock_Field,
				   stock,
                    stock_Field.getToolTipText());
		    		return;
		   }
	   if (source == strike_KField){
		   strike_K = readPositive(strike_KField,
				   strike_K,
				   strike_KField.getToolTipText());
		    		return;	   }
	   if (source == dividend_Field){
		   dividend = readDouble(dividend_Field,
				   dividend,
				   dividend_Field.getToolTipText());
		    		return;	   }
	   if (source == interest_Field){
		   interest = readDouble(interest_Field,
				   interest,
				   interest_Field.getToolTipText());
		    		return;	   }
	   if (source == volatility_Field){
		   volatility = readDouble(volatility_Field,
				   volatility,
				   volatility_Field.getToolTipText());
		    		return;	   }
	   if (source == maturity_Field){
		   maturity = readDouble(maturity_Field,
				   maturity,
				   maturity_Field.getToolTipText());
		    		return;	   }	   
	   if (source == m_steps_Field){
		   m1 = readInt(m_steps_Field,
				   m1,
				   m_steps_Field.getToolTipText());
		    		return;	   }
	   if (source == n_steps_Field){
		   n1 = readInt(n_steps_Field,
				   n1,
				   n_steps_Field.getToolTipText());
		    		return;	   }   
	}
	
	private double readPositive(JTextField field,
		            double oldValue,
		            String title)
		{
		boolean isOK = true;
		double newValue = 1;
		try{
		newValue = Double.parseDouble(field.getText());
		}
		catch (NumberFormatException e){
		JOptionPane.showMessageDialog(null,
		                     NOT_A_NUMBER,
		                     title,
		                     JOptionPane.ERROR_MESSAGE);
		isOK = false;
		}
		if (newValue <=0){
		JOptionPane.showMessageDialog(null,
		                     NOT_POSITIVE,
		                     title,
		                     JOptionPane.ERROR_MESSAGE);
		isOK = false;
		}
		if (isOK){
		return newValue;
		}
		else {
		field.setText(numberFormatter.format(oldValue));
		return oldValue;
		}
		}
		
		//read double numbers
		private double readDouble(JTextField field,
		          double oldValue,
		          String title){
		boolean isOK = true;
		double newValue = 1;
		try {
		newValue = Double.parseDouble(field.getText());
		}
		catch (NumberFormatException e){
		JOptionPane.showMessageDialog(null,
		                   NOT_A_NUMBER,
		                   title,
		                   JOptionPane.ERROR_MESSAGE);
		isOK = false;
		}
		if (isOK) {
		return newValue;
		}
		else {
		field.setText(numberFormatter.format(oldValue));
		return oldValue;
		}
		}
		
		//Read integer numbers
		private int readInt(JTextField field,
		    int oldValue,
		    String title) {
		boolean isOK = true;
		int newValue = 1;
		try {
		newValue = Integer.parseInt(field.getText());
		}
		catch (NumberFormatException e){
		JOptionPane.showMessageDialog(null,
		                      NOT_INTEGER,
		                      title,
		                      JOptionPane.ERROR_MESSAGE);
		isOK = false;
		}
		if (newValue <=0) {
		JOptionPane.showMessageDialog(null,
		                     NOT_POSITIVE,
		                     title,
		                     JOptionPane.ERROR_MESSAGE);
		isOK = false;
		}
		if (isOK) {
		return newValue;
		}
		else {
		field.setText(numberFormatter.format(oldValue));
		return oldValue;
		}
		}
	
   public void	actionPerformed(ActionEvent e) {
	   
		Object source = e.getSource();
	
		if (source == callButton) {
			bCall = true;
			outputJLabel.setText("Press Calculate Button for Call");
			return;
		}
		
		if (source == putButton) {
			bCall = false;
			outputJLabel.setText("Press Calculate Button for Put");
			return;
		}
		
		if (source == resetButton){
		  Reset();
		  return;
		}		
		
		if (source == calculateButton){
			Calculate();
		return;
		}		
	}
	
	// initialising
	public void init () {
		// Initialise formatter
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		numberFormatter = new DecimalFormat("###.####",symbols);
		// get content pane
		Container contentPane = getContentPane();	
		// create main panel
		mainPanel = new JPanel();
		// set box layout
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.X_AXIS));
		// add main panel to content pane
		contentPane.add(mainPanel,BorderLayout.CENTER);
		// create input panel 
		inputPanel = new JPanel();
		inputPanel.setBorder(new TitledBorder(INPUT));
		inputPanel.setLayout(new BoxLayout(inputPanel,BoxLayout.Y_AXIS));		
     	// add it to the main panel
		mainPanel.add(inputPanel);
		// create output panel 
		outputPanel = new JPanel();
		outputPanel.setBorder(new TitledBorder(OUTPUT));
		outputPanel.setLayout(new BoxLayout(outputPanel,BoxLayout.Y_AXIS));
	   // add it to the main panel
		mainPanel.add(outputPanel);
		// create button panel 
		buttonPanel = new JPanel();
		// add it to the input panel
		outputPanel.add(buttonPanel);	
		valuePanel = new JPanel();
		outputPanel.add(valuePanel);
		outputJLabel = new JLabel(EMPTY);
		valuePanel.add(outputJLabel);
		valuePanel.setBorder(new TitledBorder(VALUE));
		// add Table
		if (dataTable != null) dataTable = null;
		dataTable = new DataTableModel();
		JTable table = new JTable(dataTable);
		tablePanel = new JScrollPane(table);
		tablePanel.setBorder(new TitledBorder("American option caculated by changing parameters each iteration"));
		outputPanel.add(tablePanel);
		// create button group
		callGroup = new ButtonGroup();
		//	create call radio button
		callButton = new JRadioButton(CALL);
		// add action listener
		callButton.addActionListener(this);
		//	press it
		callButton.setSelected(true);
		//	add it to call group
		callGroup.add(callButton);
		//	add it to button panel
		buttonPanel.add(callButton);
		//	create put radio button
		putButton = new JRadioButton(PUT);
		//	add action listener
		putButton.addActionListener(this);
		//	add it to call group
		callGroup.add(putButton);
		//	add it to button panel
		buttonPanel.add(putButton);
		//	create calculate button
		calculateButton = new JButton(CALCULATE);
		//	add action listener
		calculateButton.addActionListener(this);
		//	add it to button panel
		buttonPanel.add(calculateButton);
		//	create reset button
		resetButton = new JButton(RESET);
		//	add action listener
		resetButton.addActionListener(this);
		//	add it to button panel
		buttonPanel.add(resetButton);
      

		// create data panel
		dataPanel = new JPanel(new GridLayout(0,2));
		// add it to input panel
		inputPanel.add(dataPanel,BorderLayout.CENTER);
  
	// add labels
  	JLabel  label = new JLabel(STRIKE_K_LABEL);
  	dataPanel.add(label);
  	// create Strike field
  	strike_KField = new JTextField();
  	strike_KField.addFocusListener(this);      
  	// add it to data panel
  	dataPanel.add(strike_KField);
  	// create stock label
  	label = new JLabel(STOCK_LABEL);
  	dataPanel.add(label);
  	// create stock field
  	stock_Field = new JTextField();
  	stock_Field.addFocusListener(this);
  	// add it to data panel
  	dataPanel.add(stock_Field);
  	// create interest label
  	label = new JLabel(INTEREST_LABEL);
  	dataPanel.add(label);
  	// create stock field
  	interest_Field = new JTextField();
  	interest_Field.addFocusListener(this);    
  	// add it to data panel
  	dataPanel.add(interest_Field);
  	// create volatility label
  	label = new JLabel(VOLATILITY_LABEL);
  	dataPanel.add(label);
  	// create volatility field
  	volatility_Field = new JTextField();
  	volatility_Field.addFocusListener(this);   
  	// add it to data panel
  	dataPanel.add(volatility_Field);
  	// create dividend label 
  	label = new JLabel(DIVIDEND_LABEL);
  	dataPanel.add(label);
  	// create dividend field
  	dividend_Field = new JTextField();
  	dividend_Field.addFocusListener(this); 
  	// add it to data panel
  	dataPanel.add(dividend_Field);
  	// create maturity label
  	label = new JLabel(MATURITY_LABEL);
  	dataPanel.add(label);
   // create maturity field
  	maturity_Field = new JTextField();
  	maturity_Field.addFocusListener(this);
  	// add it to data panel
  	dataPanel.add(maturity_Field);
  	// create m1 label
  	label = new JLabel(M_STEPS_LABEL);
  	dataPanel.add(label);
  	// create m1 field
  	m_steps_Field = new JTextField();
  	m_steps_Field.addFocusListener(this);
  	// add it to data panel
  	dataPanel.add(m_steps_Field);
  	// create n1 label
  	label = new JLabel(N_STEPS_LABEL);
  	dataPanel.add(label);
  	// create n1 field
  	n_steps_Field = new JTextField();
  	n_steps_Field.addFocusListener(this);
  
  	// add it to data panel
  	dataPanel.add(n_steps_Field);

	// Reset to Defaults
	Reset();
     }
	 public void Reset() {
	     strike_KField.setText("100");
		  dividend_Field.setText("0.05"); 
		  stock_Field.setText("100"); 
		  interest_Field.setText("0.1"); 
		  volatility_Field.setText("0.3"); 
		  maturity_Field.setText("0.75"); 
		  m_steps_Field.setText("20"); 
		  n_steps_Field.setText("12");   	  
	 }
	 public void getVariables() {
		 try
		 {
			 stock = Double.parseDouble(stock_Field.getText());
			 strike_K = Double.parseDouble(strike_KField.getText());
			 dividend = Double.parseDouble(dividend_Field.getText());
			 interest = Double.parseDouble(interest_Field.getText());
			 volatility = Double.parseDouble(volatility_Field.getText());
			 maturity = Double.parseDouble(maturity_Field.getText());
			 m1 = Integer.parseInt(m_steps_Field.getText());
			 n1 = Integer.parseInt(n_steps_Field.getText());
			 
		 }catch(Exception ex)
		 {
			 return;
		 }
		 
	 }
	//逐次逼近求解方程
	 //	% 
	 //	%    | b1  c1  0	...	|
	 //	%    | a2  b2  c2  0   		|
	 //	% A= | 0   a3  b3  c3 0		|;	A*U=r; 
	 //	%    | ...			|	Solve for U when A and r are given,
	 //	%    | 0   0		 aN  bN |	and A is tridiagonal.
	public double[] tridag(double[] a, double[] b,double[] c, double[] r) 
		{
			if (b[0] == 0.0) {
				return null;
			}
			int n = b.length;
			double[] gam = new double[n];
			double[] u = new double[n];
			double bet = b[0];
			u[0] = r[0]/bet;
			for (int j=1; j<n; j++) {
				gam[j] = c[j-1]/bet;
				bet = b[j]-a[j]*gam[j];
				if (bet == 0.0) {
				  gam = u = null;
					return null;
				}
				u[j] = (r[j]-a[j]*u[j-1])/bet;
			}
			for (int j=n-2; j>=0; j--) {
				u[j] -= gam[j+1]*u[j+1];
			}
			gam = null;
			return u;
		}
	 
	 // Calculation
	 public void Calculate() {
		 getVariables();
		 //在这里按照100次迭代，通过改变不同的参数分别计算美式期权在每次迭代的值
		 double [][]iter_result = new double[7][100];
		 for (int i = 0; i< 100; i++) {
			try{	
				iter_result[1][i]= CalculateAmericanPrice(stock, (double)i, maturity, interest, volatility, dividend, m1, n1, bCall) ;
				iter_result[2][i]=CalculateAmericanPrice((double)i, strike_K, maturity, interest, volatility, dividend, m1, n1, bCall);
				iter_result[3][i]=CalculateAmericanPrice(stock, strike_K, maturity, i, volatility, dividend, m1, n1, bCall);
				iter_result[4][i]=CalculateAmericanPrice(stock, strike_K, maturity, interest, i, dividend, m1, n1, bCall);
				iter_result[5][i]=CalculateAmericanPrice(stock, strike_K, maturity, interest, volatility, i, m1, n1, bCall);
				iter_result[6][i]=CalculateAmericanPrice(stock, strike_K, i, interest, volatility, dividend, m1, n1, bCall);
			}catch(Exception e){}
		 }	
		dataTable.setData(7, 100, iter_result);
				 
		 //这是计算给定所有参数的美式期权值
	  double americanoption = CalculateAmericanPrice(stock, strike_K, maturity, interest, volatility, dividend, m1, n1, bCall);
		outputJLabel.setText(String.valueOf( numberFormatter.format(americanoption) ));
		 
	 }
	//这里是计算给定值的美式期权	 
	 public double CalculateAmericanPrice (double stock, 
					double strike_K,
					double maturity,
					double interest,
					double volatility,
					double dividend,
					int m1,
					int n1,
					boolean bCall)		 
	 {
		 // Variables
		 double[]	xi   = new double[m1+1];
		 double[]	a  = new double[m1+1];
		 double[]	b  = new double[m1+1];
		 double[]	c  = new double[m1+1];
		 double[][]	d  = new double[n1+1][m1+1];
		 double[][]	v  = new double[n1+1][m1+1];
		 double[]   e  = new double[m1];
		 double	xi_star = stock / (stock + strike_K);
		 double v_star = 0;	
		//计算a b c xi
		for (int m=0;m<m1+1;m++) { 
			
				xi[m]=((double) m) / m1;
				
				a[m]=((interest-dividend)*(m)*(1-xi[m])-volatility*volatility*(m)*(m)*(1-xi[m])*(1-xi[m]))*maturity/(n1*4);
				
				b[m]=1+(volatility*volatility*(m)*(m)*(1-xi[m])*(1-xi[m])+interest*(1-xi[m])+dividend*xi[m])*maturity/(n1*2);
				
				c[m]=(-(interest-dividend)*(m)*(1-xi[m])-volatility*volatility*(m)*(m)*(1-xi[m])*(1-xi[m]))*maturity/(n1*4);
		}

		// 计算v[0][]
		for (int m=0;m<m1+1;m++) { 	
			
				v[0][m] = bCall?Math.max((2*xi[m]-1),0):Math.max(-(2*xi[m]-1),0);
			}
				
		for (int n=0; n<n1; n++) {
			for (int m=0; m<m1+1; m++) {
				d[n][m] = 0;
				if (m>0) {
						d[n][m] -= a[m]*v[n][m-1];
				}
				d[n][m] += (2-b[m])*v[n][m];
				if(m<m1) {
					   d[n][m] -= c[m]*v[n][m+1];
				}
			}
			v[n+1]=tridag(a,b,c,d[n]);
		}
		//计算xi_star-xi[j]
		for (int j=0;j<m1;j++)
			{ 
				e[j]=Math.abs(xi_star-xi[j]);  
			}
		//求最小的xi_star-xi[m]
		double xi_min = e[1];
		int m = 0;
			
		for (int i=2;i<m1-1;i++)  
		{ 
			if (e[i] < xi_min) 
				{ 
					xi_min=e[i];
					m=i;
				}
			}
		//求v_star
		if (e[m] == 0) 
		{ 
				v_star = v[n1][m];
		}
		else 
		{ 
			v_star = (xi[m]-xi_star)*(xi[m+1]-xi_star)*v[n1][m-1]*(m1*m1)/2.0+
			(xi_star-xi[m-1])*(xi[m+1]-xi_star)*v[n1][m]*(m1*m1)+
			(xi_star-xi[m-1])*(xi_star-xi[m])*v[n1][m+1]*(m1*m1)/2.0;
		}
		 
		double v_price = (stock+strike_K) * v_star;

		double americanprice = Math.max(Math.max(v_price, bCall?(2*xi[m]-1):(1-2*xi[m])),0);
		
		return americanprice;

	 }

}

// Table
class DataTableModel extends AbstractTableModel {
	  Vector cache; 
	  int colCount =7;
	  int rowCount =100;
	  String[] headers;
	  DecimalFormat numberFormatter;
	  
	  public DataTableModel() {
	    cache = new Vector();
	    try {
		      headers = new String[colCount];
		      headers [0] = "Iterations";
		      headers [1] = "Strike";
		      headers [2] = "Stock price";		      
		      headers [3] = "Interest";	
		      headers [4] = "Volatility";			      
		      headers [5] = "Dividend";	
		      headers [6] = "Maturity";		
		      //for (int h = 1; h < colCount; h++) {
		      //  headers[h] = String.valueOf(h-1);
		      //}
			  DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			  symbols.setDecimalSeparator('.');
			  numberFormatter = new DecimalFormat("##.##",symbols);
			  
		      for(int m=0;m<rowCount;m++) {
		        String[][] record = new String[rowCount][colCount];
		        record[m][0] = String.valueOf(m); 
		        for (int n = 1; n < colCount; n++) {
		          record[m][n] = String.valueOf(0);
		        }
		        cache.addElement(record[m]);
		      }
		      fireTableChanged(null); 
		    } catch (Exception e) {
		      cache = new Vector(); 
		      e.printStackTrace();
		    }	    
	  }

	  public String getColumnName(int m) {
	    return headers[m];
	  }

	  public int getColumnCount() {
	    return colCount;
	  }

	  public int getRowCount() {
	    return cache.size();
	  }
	  public Object getValueAt(int row, int col) {
	    return ((String[]) cache.elementAt(row))[col];
	  }
	  public void setData(int nColumn,int nRow,double [][]rowData) {
		    cache = new Vector();
		    try {
		      colCount = nColumn;
		      rowCount = nRow ;

		      for(int m=0;m<rowCount;m++) {
		        String[][] record = new String[rowCount][colCount];
		        record[m][0] = String.valueOf(m); 
		        for (int n = 1; n < colCount; n++) {
		          record[m][n] = numberFormatter.format(rowData[n][m]);
		        }
		        cache.addElement(record[m]);
		      }
		      fireTableChanged(null); 
		    } catch (Exception e) {
		      cache = new Vector(); 
		      e.printStackTrace();
		    }
		  }
	}

	








