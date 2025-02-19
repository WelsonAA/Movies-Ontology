package ShowMovie;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends JFrame {

	private JTextArea genre;
	private JTextArea actortext;


	private JRadioButton includeGenreRadio;
	private JRadioButton excludeGenreRadio;
	private JRadioButton includeActorRadio;
	private JRadioButton excludeActorRadio;

	private GetData data;
	private DefaultTableModel filmTableModel;
	private DefaultTableModel actorTableModel;
	private DefaultTableModel directorTableModel;
	private DefaultTableModel writerTableModel;

	private JTable filmTable;
	private JTable actorTable;
	private JTable directorTable;
	private JTable writerTable;

	private Vector filmVectorData;
	private Vector actorVectorData;
	private Vector directorVectorData;
	private Vector writerVectorData;
	private Vector filmVectorColName;


	public MainFrame() {
		super();
		getContentPane().setLayout(null);
		setBounds(100, 100, 800, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		data = new GetData();

		//clear data in the input and table
		final JButton buttonClear = new JButton();
		buttonClear.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				genre.setText(null);
				actortext.setText(null);
				clearData();
			}
		});
		buttonClear.setText("clear");
		buttonClear.setBounds(500, 390, 106, 28);
		getContentPane().add(buttonClear);

		//initial vectors
		filmVectorColName = new Vector();
		filmVectorData = new Vector();
		actorVectorData = new Vector();
		directorVectorData = new Vector();
		writerVectorData = new Vector();

		initTable();

		filmTableModel = new DefaultTableModel();
		filmTableModel.setDataVector(filmVectorData,filmVectorColName);
		filmTable = new JTable(filmTableModel);

		actorTableModel = new DefaultTableModel();
		actorTableModel.setDataVector(actorVectorData,filmVectorColName);
		actorTable = new JTable(actorTableModel);

		directorTableModel = new DefaultTableModel();
		directorTableModel.setDataVector(directorVectorData,filmVectorColName);
		directorTable = new JTable(directorTableModel);

		writerTableModel = new DefaultTableModel();
		writerTableModel.setDataVector(writerVectorData,filmVectorColName);
		writerTable = new JTable(writerTableModel);

		//button search
		final JButton buttonSearch = new JButton();
		buttonSearch.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				clearData();
				ArrayList filmlist;
				ArrayList actfilmlist;
				String str = genre.getText();

				boolean genreSearchType = includeGenreRadio.isSelected() ? true : false;

				if (genreSearchType) {
					filmlist = data.getFilmDataIncluded(str, "isGenreOf");
				}
				else {

					filmlist = data.getFilmDataExclude(str, "isGenreOf",genreSearchType);

				}

				//setData(filmlist, filmVectorData);
				filmTable.invalidate();
				filmTable.updateUI();



				String pname = actortext.getText();
				boolean actorSearchType = includeActorRadio.isSelected() ? true : false;


				if (actorSearchType) {
					actfilmlist = data.getFilmDataIncluded(pname, "isActorOf");
				}
				else {

					actfilmlist = data.getActorDataExclude(pname, "isActorOf",actorSearchType);

				}
				setData(actfilmlist, actorVectorData);
				actorTable.invalidate();
				actorTable.updateUI();

	/*
				ArrayList direfilmlist = data.getFilmData(pname, "isDirectorOf",actorSearchType);
				setData(direfilmlist, directorVectorData);
				directorTable.invalidate();
				directorTable.updateUI();

				ArrayList writerfilmlist = data.getFilmData(pname, "isWriterOf",actorSearchType);
				setData(writerfilmlist, writerVectorData);
				writerTable.invalidate();
				writerTable.updateUI();

				 */
			}
		});

		//add table and text into frame
		buttonSearch.setText("search");
		buttonSearch.setBounds(100, 390, 106, 28);
		getContentPane().add(buttonSearch);

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(50, 46, 100, 30);
		getContentPane().add(scrollPane);

		genre = new JTextArea();
		scrollPane.setViewportView(genre);

		// Include/Exclude Genre radio buttons
		includeGenreRadio = new JRadioButton("Include");
		includeGenreRadio.setBounds(50, 80, 80, 18);
		getContentPane().add(includeGenreRadio);

		excludeGenreRadio = new JRadioButton("Exclude");
		excludeGenreRadio.setBounds(130, 80, 80, 18);
		getContentPane().add(excludeGenreRadio);

		// Group the radio buttons
		ButtonGroup genreButtonGroup = new ButtonGroup();
		genreButtonGroup.add(includeGenreRadio);
		genreButtonGroup.add(excludeGenreRadio);

		final JScrollPane scrollPane_1 = new JScrollPane(filmTable);
		filmTable.setFillsViewportHeight(true);
		scrollPane_1.setBounds(400, 30, 350, 70);
		getContentPane().add(scrollPane_1);


		final JScrollPane scrollPane_input = new JScrollPane();
		scrollPane_input.setBounds(50, 150, 150, 30);
		getContentPane().add(scrollPane_input);

		actortext = new JTextArea();
		scrollPane_input.setViewportView(actortext);

		includeActorRadio = new JRadioButton("Include");
		includeActorRadio.setBounds(50, 184, 80, 18);
		getContentPane().add(includeActorRadio);

		excludeActorRadio = new JRadioButton("Exclude");
		excludeActorRadio.setBounds(130, 184, 80, 18);
		getContentPane().add(excludeActorRadio);

		// Group the radio buttons
		ButtonGroup actorButtonGroup = new ButtonGroup();
		actorButtonGroup.add(includeActorRadio);
		actorButtonGroup.add(excludeActorRadio);


		final JLabel label_inputDirector = new JLabel();
		label_inputDirector.setText("Input Actor name");
		label_inputDirector.setBounds(50, 220, 150, 18);
		getContentPane().add(label_inputDirector);

		final JScrollPane scrollPane_inputDirector = new JScrollPane();
		scrollPane_inputDirector.setBounds(50, 240, 150, 30);
		getContentPane().add(scrollPane_inputDirector);

		final JTextArea directorText = new JTextArea();
		scrollPane_inputDirector.setViewportView(directorText);

		final JRadioButton includeDirectorRadio = new JRadioButton("Include");
		includeDirectorRadio.setBounds(50, 274, 80, 18);
		getContentPane().add(includeDirectorRadio);

		final JRadioButton excludeDirectorRadio = new JRadioButton("Exclude");
		excludeDirectorRadio.setBounds(130, 274, 80, 18);
		getContentPane().add(excludeDirectorRadio);

// Group the director radio buttons
		ButtonGroup directorButtonGroup = new ButtonGroup();
		directorButtonGroup.add(includeDirectorRadio);
		directorButtonGroup.add(excludeDirectorRadio);


		final JScrollPane scrollPane_output1 = new JScrollPane(actorTable);
		actorTable.setFillsViewportHeight(true);
		scrollPane_output1.setBounds(400, 115, 350, 70);
		getContentPane().add(scrollPane_output1);

//
		final JScrollPane scrollPane_output2 = new JScrollPane(directorTable);
		directorTable.setFillsViewportHeight(true);
		scrollPane_output2.setBounds(400, 205, 350, 70);
		getContentPane().add(scrollPane_output2);


		final JScrollPane scrollPane_output3 = new JScrollPane(writerTable);
		writerTable.setFillsViewportHeight(true);
		scrollPane_output3.setBounds(400, 295, 350, 70);
		getContentPane().add(scrollPane_output3);


		final JLabel label = new JLabel();
		label.setText("Input genre");
		label.setBounds(50, 22, 80, 18);
		getContentPane().add(label);

		final JLabel label_1 = new JLabel();
		label_1.setText("Filmlist");
		label_1.setBounds(400, 13, 66, 18);
		getContentPane().add(label_1);

		final JLabel labeinput = new JLabel();
		labeinput.setText("Input person name");
		labeinput.setBounds(50, 130, 150, 18);
		getContentPane().add(labeinput);

		final JLabel labeoutput1 = new JLabel();
		labeoutput1.setText("As actor");
		labeoutput1.setBounds(400, 100, 150, 10);
		getContentPane().add(labeoutput1);

		final JLabel labeoutput2 = new JLabel();
		labeoutput2.setText("As director");
		labeoutput2.setBounds(400, 190, 150, 18);
		getContentPane().add(labeoutput2);

		final JLabel labeoutput3 = new JLabel();
		labeoutput3.setText("As writer");
		labeoutput3.setBounds(400, 278, 150, 18);
		getContentPane().add(labeoutput3);
	}


	public void initTable(){
		this.filmVectorColName.addElement("Title");
		this.filmVectorColName.addElement("Year");
		this.filmVectorColName.addElement("Language");
		this.filmVectorColName.addElement("Nationality");
	}

	//add data into table
	public void setData(ArrayList list, Vector data){
		for(int i = 0; i < list.size() ; i++){
			Vector vector = new Vector();
			Movie movie = (Movie) list.get(i);
			vector.addElement(movie.getTitle());
			vector.addElement(movie.getYear());
			vector.addElement(movie.getLanguage());
			vector.addElement(movie.getNation());
			data.addElement(vector);
		}
	}

	public void clearData(){
		this.filmVectorData.clear();
		this.directorVectorData.clear();
		this.actorVectorData.clear();
		this.writerVectorData.clear();
	}
}