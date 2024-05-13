package applications;

import tools.JenaEngine;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;

public class Jena5 {
	private Model model;
	private String file;
	private String namespace;
	 
	Jena5(String path){
		this.namespace = "";
		this.file = path;
		this.model = JenaEngine.readModel(path);
		if(model != null ){
			namespace = model.getNsPrefixURI("");
		}
	}
	
	public String readActorDirector(){
		this.model = JenaEngine.readInferencedModelFromRuleFile(model, "km/data/owlrules.txt");
		this.model = JenaEngine.readInferencedModelFromRuleFile(model, "km/data/jena5.txt");
		
		String query = "PREFIX ns: <http://www.owl-ontologies.com/unnamed.owl#>"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
				+ "SELECT ?person  "
				+ "WHERE{"
				+ "?person rdf:type ns:ActorDirector. " + "}";
		double startTime = System.nanoTime();
		String answer = JenaEngine.executeQuery(model, query);
		double endTime = System.nanoTime();
		double duration = (endTime - startTime);  // Duration in nanoseconds
		System.out.println(JenaEngine.executeQuery(model, query));
		System.out.print(duration / 1_000_000);
		System.out.println(" milliseconds");
		return answer;
	}

}
