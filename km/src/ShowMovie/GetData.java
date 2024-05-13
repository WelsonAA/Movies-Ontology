package ShowMovie;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.jena.rdf.model.*;
import tools.JenaEngine;

import static tools.JenaEngine.RDF;

public class GetData {
	private Model model;
	private String namespace;
	private String file;

	public GetData() {
		namespace = "";
		file = "km/data/project.owl";
		setModel();
		if (model != null) {
			namespace = model.getNsPrefixURI("");
		}
	}

	public void setModel() {
		this.model = JenaEngine.readModel("km/data/project.owl");
	}

	/*public ArrayList<Movie> getFilmData(String genre, boolean include) {
		ArrayList<Movie> result = new ArrayList<>();
		Model model = ...; // Assuming you have your Jena model loaded here

		// 1. Retrieve all movie resources
		StmtIterator allMovies = model.listStatements(null, RDF.TYPE, model.getResource(namespace + "Movie"));

		while (allMovies.hasNext()) {
			Statement movieStatement = allMovies.next();
			if (movieStatement.getSubject().isResource()) {
				Resource movieResource = movieStatement.getSubject();

				// 2. Check if movie has the specified genre (or any genre)
				boolean hasGenre = false;
				StmtIterator genreStatements = movieResource.listProperties(model.getProperty(namespace + "isGenreOf"));
				while (genreStatements.hasNext()) {
					Statement genreStatement = genreStatements.next();
					Resource genreResource = genreStatement.getResource();

					// 2.a Include: Check if movie has the specified genre
					if (include && genreResource.getLocalName().equals(genre)) {
						hasGenre = true;
						break; // Exit loop if genre found (for Include)
					}

					// 2.b Exclude: Check if movie has any genre (exclude if true)
					if (!include) {
						hasGenre = true;
						break; // Exit loop if genre found (for Exclude)
					}
				}

				// 3. Add movie based on include/exclude and genre check
				if ((include && hasGenre) || (!include && !hasGenre)) {
					// Optional: Check if movie has a title property before retrieving it
					if (movieResource.getProperty(model.getProperty(namespace + "title")) != null) {
						String movieName = movieResource.getProperty(model.getProperty(namespace + "title")).getString();
						Movie temp = new Movie(movieName);
						// You can add other movie details here based on your requirements
						result.add(temp);
					}
				}
			}
		}

		return result;
	}
	*
	 */


	public ArrayList<Movie> getFilmDataExclude(String name, String property, boolean included) {
		// Initialize an ArrayList to store the result
		ArrayList<Movie> result = new ArrayList<>();

		// List of all possible names
		String[] allNames = {"Crime", "Thriller", "Action", "Comedy"};
		ArrayList<String> possibleNames = new ArrayList<>(Arrays.asList(allNames));

		// Remove the specified name from the list if included is false
		if (!included) {
			possibleNames.remove(name);
		}

		// Iterate over the possible names
		for (String possibleName : possibleNames) {
			// Get the resource corresponding to the possible name
			Resource rs = model.getResource(namespace + possibleName);

			// Get properties for movie details
			Property ptitle = model.getProperty(namespace + "title");
			Property pyear = model.getProperty(namespace + "year");
			Property plang = model.getProperty(namespace + "language");
			Property pnation = model.getProperty(namespace + "country");

			// Check if the resource is not null
			if (rs != null) {
				// Get all statements where the resource has the specified property
				StmtIterator it = rs.listProperties(model.getProperty(namespace + property));

				// Iterate over the statements
				while (it.hasNext()) {
					// Get the next statement
					Statement s = it.next();

					// Get the resource object related to the statement
					Resource re = s.getResource();

					// Get the title property value as a string
					String title = re.getProperty(ptitle).getString();

					// Create a new Movie object with the title
					Movie temp = new Movie(title);

					// Set the language, year, and nation properties for the Movie object
					temp.setLanguage(re.getProperty(plang).getString());
					temp.setYear(re.getProperty(pyear).getString());
					temp.setNation(re.getProperty(pnation).getString());

					// Check if the movie is not already included in the result
					if (!result.contains(temp)) {
						// Add the Movie object to the result ArrayList
						result.add(temp);
					}
				}
			}
		}

		// Return the ArrayList containing the movie data
		return result;
	}


	public ArrayList getFilmDataIncluded(String name, String property){
		Resource rs = model.getResource(namespace + name);
		Property p = model.getProperty(namespace + property);
		Property ptitle = model.getProperty(namespace + "title");
		Property pyear = model.getProperty(namespace + "year");
		Property plang = model.getProperty(namespace + "language");
		Property pnation = model.getProperty(namespace + "country");
		ArrayList result = new ArrayList();

		if ((rs != null) && (p != null)) {
			StmtIterator it = rs.listProperties(p);
			while (it.hasNext()) {
				Statement s = it.next();
				Resource re = s.getResource();
				String title = re.getProperty(ptitle).getString();
				Movie temp = new Movie(title);
				temp.setLanguage(re.getProperty(plang).getString());
				temp.setYear(re.getProperty(pyear).getString());
				temp.setNation(re.getProperty(pnation).getString());
				result.add(temp);
			}
			return result;
		} else {
			return result;
		}
	}


	public ArrayList<Movie> getActorDataExclude(String name, String property, boolean included) {
		// Initialize an ArrayList to store the result
		ArrayList<Movie> result = new ArrayList<>();

		// List of all possible names
		String[] allNames = {"Quentin_Tarantino","Uma_Thurman","Tom_Hanks","John_Travolta"};
		ArrayList<String> possibleNames = new ArrayList<>(Arrays.asList(allNames));

		// Remove the specified name from the list if included is false
		if (!included) {
			possibleNames.remove(name);
		}

		// Iterate over the possible names
		for (String possibleName : possibleNames) {
			// Get the resource corresponding to the possible name
			Resource rs = model.getResource(namespace + possibleName);

			// Get properties for movie details
			Property ptitle = model.getProperty(namespace + "title");
			Property pyear = model.getProperty(namespace + "year");
			Property plang = model.getProperty(namespace + "language");
			Property pnation = model.getProperty(namespace + "country");

			// Check if the resource is not null
			if (rs != null) {
				// Get all statements where the resource has the specified property
				StmtIterator it = rs.listProperties(model.getProperty(namespace + property));

				// Iterate over the statements
				while (it.hasNext()) {
					// Get the next statement
					Statement s = it.next();

					// Get the resource object related to the statement
					Resource re = s.getResource();

					// Get the title property value as a string
					String title = re.getProperty(ptitle).getString();

					// Create a new Movie object with the title
					Movie temp = new Movie(title);

					// Set the language, year, and nation properties for the Movie object
					temp.setLanguage(re.getProperty(plang).getString());
					temp.setYear(re.getProperty(pyear).getString());
					temp.setNation(re.getProperty(pnation).getString());

					// Check if the movie is not already included in the result
					if (!result.contains(temp)) {
						// Add the Movie object to the result ArrayList
						result.add(temp);
					}
				}
			}
		}

		// Return the ArrayList containing the movie data
		return result;
	}
}