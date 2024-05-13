package applications;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

import tools.FileTool;

public class Jena2 {
	String filepath;
	String namespace;
	Model model;

	Jena2(String str) {
		this.namespace = "";
		this.filepath = str;
		this.model = ModelFactory.createDefaultModel();
		this.setModel();
		if (model != null) {
			this.namespace = model.getNsPrefixURI("");
		} else {
			System.out.println("Error when reading model from ontology");
		}
	}
	public ArrayList readAllPerson(String file){
		String str_query = getQuery(file);
		Query query = QueryFactory.create(str_query);

		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		ArrayList<String> personNames = new ArrayList<>();

		while (results.hasNext()) {
			String personName = results.nextSolution().getLiteral("name").getString();
			personNames.add(personName);
			System.out.println(personName);
		}

		return personNames;
	}
	
	public void construct(String file){
		String str_query = getQuery(file);
		Query query = QueryFactory.create(str_query);
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		
		System.out.println(qe.execConstruct());
	}
	
	public void ask(String file){
		String str_query = getQuery(file);
		Query query = QueryFactory.create(str_query);
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		
		System.out.println(qe.execAsk());
	}
	
	public void describe(String file){
		String str_query = getQuery(file);
		Query query = QueryFactory.create(str_query);
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		
		System.out.println(qe.execDescribe());
	}
	public String getQuery(String file){
		File queryFile = new File(file);
		// use the FileManager to find the input file
		InputStream in = FileManager.get().open(file);
		if (in == null) {
			System.out.println("Query file: " + file + " not found");
			return null;
		} else {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return null;
			}
		}
		String queryString = FileTool.getContents(queryFile);
		return queryString;
	}
	
	public void setModel() {
		// use the FileManager to find the input file
		InputStream in = FileManager.get().open(this.filepath);
		if (in == null) {
			System.out
					.println("Ontology file: " + this.filepath + " not found");
		}

		// read the RDF/XML file
		model.read(in, "");
		try {
			in.close();
		} catch (IOException e) {
		}
		
	}
	
	

}
