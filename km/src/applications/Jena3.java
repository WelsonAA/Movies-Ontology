package applications;

import tools.JenaEngine;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;


public class Jena3 {
	private Model model;
	private String file;
	private String namespace;
	 
	Jena3(String path){
		this.namespace = "";
		this.file = path;
		this.model = JenaEngine.readModel(path);
		if(model != null ){
			namespace = model.getNsPrefixURI("");
		}
		
	}
	
	public void readActor(){
		Model owlmodel = JenaEngine.readInferencedModelFromRuleFile(model, "km/data/owlrules.txt");
		Model ourmodel = JenaEngine.readInferencedModelFromRuleFile(owlmodel, "km/data/jena3.txt");
		Property pname = model.getProperty(namespace + "name");
		ResIterator iter =ourmodel.listResourcesWithProperty(pname);
		for (; iter.hasNext();) {
			Resource i = iter.next();
			JenaEngine.readRsDataType(ourmodel, namespace, i, "name");
		}

	}
}
