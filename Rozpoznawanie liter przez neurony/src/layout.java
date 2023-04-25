import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.Console;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


public class layout extends JFrame{
	
	private ArrayList<boolean[]> matrixSequence;
	private ArrayList<boolean[]> lettersSequence;
	private int lettersCount;
	private int pixelCount;
	private int[] neuronsPerLayer;
	Siec network;
	static int row=8;
	static int col=8;
	boolean saveOption = false;
	String path = "D:\\informatyka\\Java\\Projekt nr 1";
	JLabel label=new JLabel("Rozpoznanie liter R, P, M");
	JRadioButton radioButtonR = new JRadioButton("R");
	JRadioButton radioButtonP = new JRadioButton("P");
	JRadioButton radioButtonM = new JRadioButton("M");
	JRadioButton radioButtonInny = new JRadioButton("Inna litera");
	
private void initializeNetwork(int[] layers) {
		
		network = new Siec(layers[0], layers.length, layers);
		
	}


	public layout(String string) {
    	super(string);
		Toolkit kit=Toolkit.getDefaultToolkit();
		Dimension d=kit.getScreenSize();
		setBounds(d.width/6,d.height/6,d.width*2/3,d.height*2/3);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		lettersSequence = new ArrayList<boolean[]>();
    	matrixSequence = new ArrayList<boolean[]>();
		
		JPanel panel=new JPanel(new GridLayout(1,2));
		JPanel panelL=new JPanel(new BorderLayout());
		JPanel panelR=new JPanel(new GridLayout(3,1));
		JPanel panelN=new JPanel(new BorderLayout());
		JPanel panelRB=new JPanel(new GridLayout(2,2));
		JPanel panelC=new JPanel(new GridLayout(3,2));
		JPanel panelS=new JPanel(new GridLayout());
		
		ButtonGroup RButtonGroup = new ButtonGroup();
		
		
		
		final Board board = new Board(); 
		
		neuronsPerLayer = new int[3];
		neuronsPerLayer[0] = row*col;
		neuronsPerLayer[1] = 10;
		neuronsPerLayer[2] = 3;
		
		initializeNetwork(neuronsPerLayer);
		
		
		
		RButtonGroup.add(radioButtonR);
		RButtonGroup.add(radioButtonP);
		RButtonGroup.add(radioButtonM);
		RButtonGroup.add(radioButtonInny);


		panelL.add(board);
		
		
		panelN.add(label, BorderLayout.NORTH);
		panelN.add(panelRB);
		panelRB.add(radioButtonR);
		panelRB.add(radioButtonP);
		panelRB.add(radioButtonM);
		panelRB.add(radioButtonInny);
		
		
		panelR.add(panelN, BorderLayout.NORTH);
		
		
		JButton submitButton = new JButton("Dodaj do ciągu");

		submitButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(board.framed==0) {
				matrixSequence.add(board.getBooleans(row, col));
				board.grid(row, col);
				board.framing();
				lettersSequence.add(radioButtonsToArray());
				}
				if(board.framed==2) {
				matrixSequence.add(board.matrixLetter);
				lettersSequence.add(radioButtonsToArray());
				}
				
			}
		});
		
		JButton loadFileButton = new JButton("Wczytaj ciąg uczący i ucz");
		loadFileButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				loadFile(e);
			}
		});
		
		JButton saveFileButton = new JButton("Zapisz ciąg uczący");
		saveFileButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				saveOption = true;
				saveFile(e);

			}
		});
		
		JButton saveFileButton1 = new JButton("Zapisz ciąg testowy");
		saveFileButton1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				saveFile(e);

			}
		});
		
		JButton recognizeButton1 = new JButton("Testuj");
		recognizeButton1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int czyUdaloSie = 0;
                String[] trainingData = loadFile("CT.txt");
                double[] data = new double[col*row];
                for (int i = 0; i < trainingData.length; i++) {
                    for (int j = 0; j < data.length; j++) {
                        data[j] = (int) (trainingData[i].charAt(j) - '0');
                        
                    }
                    double[] wynik = network.CalculateOutput(data);
                        int[] znak = {wynik[0]>0.5?1:0, wynik[1]>0.5?1:0, wynik[2]>0.5?1:0};
                        if(znak[0]==(int)(trainingData[i].charAt(data.length)-'0') &&
                        		znak[1]==(int)(trainingData[i].charAt(data.length+1)-'0') &&
                        		znak[2]==(int)(trainingData[i].charAt(data.length+2)-'0')){
                            czyUdaloSie++;
                        }
                } 
                JOptionPane.showMessageDialog(null, czyUdaloSie/(float)trainingData.length * 100 + "%");
				
			}

			private String[] loadFile(String filename) {
				File file=new File(filename);
				String[] test;
				try {
					Scanner in= new Scanner(file);
					ArrayList<String> temp= new ArrayList<String>();
					while(in.hasNext()) {
						temp.add(in.next());
					}
					test= temp.toArray(new String[0]);
					return test;
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				return null;
			}
		});
		
		
		JButton deleteLastButton = new JButton("Usuń ostatni zapis ciągu");
		deleteLastButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(!lettersSequence.isEmpty()) {
				matrixSequence.remove(matrixSequence.size()-1);
				lettersSequence.remove(lettersSequence.size()-1);
				}
			}
		});
		
		panelC.add(recognizeButton1);
		panelC.add(saveFileButton1);
		panelC.add(saveFileButton);
		panelC.add(loadFileButton);
		panelC.add(submitButton);
		panelC.add(deleteLastButton);
		panelR.add(panelC, BorderLayout.CENTER);
		
		JButton recognizeButton = new JButton("Rozpoznaj");
		recognizeButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(board.framed==0) {
				boolean[] letterBoolMatrix = board.getBooleans(row, col);
				double[] inputLetterPix = boolArrayToDouble(letterBoolMatrix);
				double[] recognizedLetter = network.CalculateOutput(inputLetterPix);
				letterArrToLetter(recognizedLetter);
				board.grid(row, col);
				board.framing();
				board.framed=2;
				}
			}
		});
		
		JButton clearButton = new JButton("Wyczyść");
		clearButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				board.clear();
				label.setText("Rozpoznanie liter R, P, M");
			}
		});
		
		panelS.add(recognizeButton);
		panelS.add(clearButton);
		
		panelR.add(panelS, BorderLayout.SOUTH);
		
		
		panel.add(panelL);
		panel.add(panelR);
		add(panel);
		setVisible(true);
	}
	
	private void letterArrToLetter(double[] source) {
		int outCount = 0;
		int outIndex = -1;
		
		for (int i = 0; i < source.length; i++) {
			if(source[i] > 0.5) {
				outCount++;
				outIndex = i;
			}
		}
		
		if(outCount != 1)
			outIndex = -1;
		
		switch (outIndex) {
		case 0:
			label.setText("Narysowana litera: R");
			radioButtonR.setSelected(true);
			break;
		case 1:
			label.setText("Narysowana litera: P");
			radioButtonP.setSelected(true);
			break;
		case 2:
			label.setText("Narysowana litera: M");
			radioButtonM.setSelected(true);
			break;
		default:
			label.setText("To inna litera");
			radioButtonInny.setSelected(true);
			break;
		}
	}
	
	private boolean[] radioButtonsToArray() {
		if(radioButtonR.isSelected())
			return new boolean[] { true, false, false};
		else if(radioButtonP.isSelected())
			return new boolean[] { false, true, false};
		else if(radioButtonM.isSelected())
			return new boolean[] { false, false, true};
		else if(radioButtonInny.isSelected())
			return new boolean[] { false, false, false};
				else
			return null;

	}

	public static void main(String[] args) {
    	
    	EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				new layout("Projekt nr 1");
				
			}
		});
    	
        
    }
	
	private void saveFile(ActionEvent e) {
		try {
			if(lettersSequence == null || matrixSequence == null)
				return;
			
			final JFileChooser fc = new JFileChooser(path);
	        int returnVal = fc.showOpenDialog(layout.this);
	        
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	        	
	            File file = fc.getSelectedFile();
	            
	            String path = file.getAbsolutePath();
	            
	            PrintWriter writer = new PrintWriter(path, "UTF-8");
	            
	            for (int i = 0; i < lettersSequence.size(); i++) {
	            	
	            	StringBuilder sb = new StringBuilder();
	            	
	            	boolean[] currentLetter = lettersSequence.get(i);
	            	if(saveOption == true) {
	            	
		            for (boolean b : currentLetter) {
						sb.append((b ? "1" : "0") + " ");
					}
		            writer.println(sb);
		            }
	            	
	            	boolean[] currentMatrix = matrixSequence.get(i);
		            sb = new StringBuilder();
		            
		            if(saveOption == false) {
		            	for (boolean b : currentMatrix) {
							sb.append((b ? "1" : "0"));
						}
		            	for (boolean b : currentLetter) {
							sb.append((b ? "1" : "0"));
						}
			           }
		            
		            if(saveOption == true) {
		            for (boolean b : currentMatrix) {
						sb.append((b ? "1" : "0") + " ");
						}
		            }
		            writer.println(sb);
				}
	            
	            writer.close();
	        }
		}
		catch(Exception ex) {
			
		}
		
		saveOption = false;
	}
	
	private double[] boolArrayToDouble(boolean[] source) {
		double[] result = new double[source.length];
		for (int i = 0; i < source.length; i++) {
			result[i] = source[i] ? 1 : 0;
		}
		
		return result;
	}
	
	private void loadFile(ActionEvent e) {
		
		try {
			final JFileChooser fc = new JFileChooser(path);
	        int returnVal = fc.showOpenDialog(layout.this);

	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	        	lettersSequence = new ArrayList<boolean[]>();
	        	matrixSequence = new ArrayList<boolean[]>();
	        	
	            File file = fc.getSelectedFile();
	            
	            String path = file.getAbsolutePath();
	            
	            FileInputStream fstream = new FileInputStream(path);
	            DataInputStream in = new DataInputStream(fstream);
	            BufferedReader br = new BufferedReader(new InputStreamReader(in));
	            String strLine;
	            
	            while ((strLine = br.readLine()) != null)   {
				    String[] tokens = strLine.split(" ");
				    
				    if(lettersSequence.size() == matrixSequence.size()){
				    	lettersCount = tokens.length;
				    	
				    	boolean[] letter = new boolean[lettersCount];
				    	
				    	for (int i = 0; i < lettersCount; i++) {
							letter[i] = tokens[i].equals("0") ? false : true;
						}
				    	lettersSequence.add(letter);
				    }
				    else {
				    	pixelCount = tokens.length;
				    	
				    	boolean[] matrix = new boolean[pixelCount];
				    	
				    	for (int i = 0; i < pixelCount; i++) {
							matrix[i] = tokens[i].equals("0") ? false : true;
						}
				    	
				    	matrixSequence.add(matrix);
	            }
	            }
	            br.close();
	        }
		}
		catch(Exception ex) {	
		}
		
		neuronsPerLayer = new int[3];
		
		neuronsPerLayer[0] = pixelCount;
		neuronsPerLayer[1] = 10;
		neuronsPerLayer[2] = lettersCount;
		
		initializeNetwork(neuronsPerLayer);
		
		ArrayList<double[]> lettersSequenceDouble = new ArrayList<double[]>();
		ArrayList<double[]> matrixSequenceDouble = new ArrayList<double[]>();
		for(int i = 0; i< lettersSequence.size(); i++) {
			boolean[] lettersBool = lettersSequence.get(i);
			boolean[] matrixBool = matrixSequence.get(i);
			double[] lettersArray = new double[lettersBool.length];
			double[] matrixArray = new double[matrixBool.length];
			
			for (int j = 0; j < lettersArray.length; j++) {
				lettersArray[j] = lettersBool[j] ? 1.0 : 0.0;
			}
			for (int j = 0; j < matrixArray.length; j++) {
				matrixArray[j] = matrixBool[j] ? 1.0 : 0.0;
			}
			
			
			lettersSequenceDouble.add(lettersArray);
			matrixSequenceDouble.add(matrixArray);
		}	
			
		network.LearnFromSequence(matrixSequenceDouble, lettersSequenceDouble);
		
		label.setText("Sieć nauczona!");
		
	}
}


