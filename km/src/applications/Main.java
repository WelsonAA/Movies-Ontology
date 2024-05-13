/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package applications;

import java.awt.EventQueue;

import ShowMovie.MainFrame;



/**
 * @author DO.ITSUDPARIS
 */
public class Main {
	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		


		//Jena1 jena1 = new Jena1("km/data/project.owl");
		//jena1.readAllPersons();



		//Jena2 jena2 = new Jena2("km/data/project.owl");
		////jena2.ask("km/data/ask.txt");
		////jena2.describe("km/data/describe.txt");
		//jena2.readAllPerson("km/data/query.txt");
		////jena2.construct("km/data/construct.txt");




		//Jena3 jena3 = new Jena3("km/data/project.owl");
		//jena3.readActor();


		//Jena4 jena4 = new Jena4("km/data/project.owl");
		//String title = jena4.getInput();
		//jena4.searchMovie(title);


		Jena5 jena5 = new Jena5("km/data/project.owl");
		jena5.readActorDirector();




		//Jena6 jena6 = new Jena6("km/data/project.owl");
		//jena6.readPersonAge();
		//jena6.readActorMale();
		//jena6.readEnglishMovie();


		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
