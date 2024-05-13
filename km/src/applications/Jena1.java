package applications;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.iterator.ExtendedIterator;

public class Jena1 {
	String filepath;
	String namespace;
	Model model;
	OntModel ontmodel;

	Jena1(String str) {
		this.namespace = "";
		this.filepath = str;
		this.model = ModelFactory.createDefaultModel();
		this.ontmodel = ModelFactory.createOntologyModel();
		this.setModel();
		this.setOntModel();
		if (model != null) {
			this.namespace = model.getNsPrefixURI("");
		} else {
			System.out.println("Error when reading model from ontology");
		}
	}

	public ArrayList<String> readAllPersons() {
		OntClass cl = ontmodel.getOntClass(namespace + "Person");
		Property pname = model.getProperty(namespace + "name");
		ArrayList<String> names = new ArrayList<>();
		for (ExtendedIterator i = cl.listInstances(); i.hasNext();) {
			OntResource c = (OntResource) i.next();
			String name = c.getProperty(pname).getString();
			System.out.println("name: " + name);
			names.add(name);
		}
		return names;
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

	public void setOntModel() {
		InputStream in = FileManager.get().open(this.filepath);
		if (in == null) {
			System.out
					.println("Ontology file: " + this.filepath + " not found");
		}
		ontmodel.read(in, "");
		try {
			in.close();
		} catch (IOException e) {
		}
	}

}
