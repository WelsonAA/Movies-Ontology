package applications;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.apache.jena.util.iterator.ExtendedIterator;

class Jena1Test {
    public static boolean checkConsistency(String ontologyFilePath) {
        try {
            // Load OWL ontology using Apache Jena
            OntModel model = ModelFactory.createOntologyModel();
            model.read(ontologyFilePath);

            // Configure reasoner (Pellet)
            Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
            InfModel infModel = ModelFactory.createInfModel(reasoner, model);

            // Perform consistency checking
            return infModel.validate().isValid();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean checkInferenceActorandPerson() {
        // Load ontology
        OntModel model = ModelFactory.createOntologyModel();
        model.read("km/data/projectttttt.owl");
        //OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        InfModel infModel = ModelFactory.createInfModel(reasoner, model);

        // Define expected inferences (example)
        String superClassLocalName = "Person";
        String subClassLocalName = "Actor";

        // Get resources representing the classes
        Resource superClass = model.getResource(model.getNsPrefixURI("") + superClassLocalName);
        Resource subClass = model.getResource(model.getNsPrefixURI("") + subClassLocalName);

        // Check if SubClass is a subclass of SuperClass
        StmtIterator stmtIterator = infModel.listStatements(subClass, infModel.getProperty("http://www.w3.org/2000/01/rdf-schema#subClassOf"), superClass);

        // Return true only if there's at least one statement indicating SubClass is a subclass of SuperClass
        return stmtIterator.hasNext();
    }
    public boolean checkInferenceDirectorandPerson() {
        // Load ontology
        OntModel model = ModelFactory.createOntologyModel();
        model.read("km/data/projectttttt.owl");

        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        InfModel infModel = ModelFactory.createInfModel(reasoner, model);

        // Define expected inferences (example)
        String superClassLocalName = "Person";
        String subClassLocalName = "Director";

        // Get resources representing the classes
        Resource superClass = model.getResource(model.getNsPrefixURI("") + superClassLocalName);
        Resource subClass = model.getResource(model.getNsPrefixURI("") + subClassLocalName);

        // Check if SubClass is a subclass of SuperClass
        StmtIterator stmtIterator = infModel.listStatements(subClass, infModel.getProperty("http://www.w3.org/2000/01/rdf-schema#subClassOf"), superClass);

        // Return true only if there's at least one statement indicating SubClass is a subclass of SuperClass
        return stmtIterator.hasNext();
    }
    public boolean checkInferenceMovieandActor() {
        // Load ontology
        OntModel model = ModelFactory.createOntologyModel();
        model.read("km/data/projectttttt.owl");

        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        InfModel infModel = ModelFactory.createInfModel(reasoner, model);

        // Define expected inferences (example)
        String movieClassLocalName = "Movie";
        String PersonClassLocalName = "Person";
        String propertyName = "hasActor";

        // Get resources representing the classes
        Resource movieClass = model.getResource(model.getNsPrefixURI("") + movieClassLocalName);
        Resource personClass = model.getResource(model.getNsPrefixURI("") + PersonClassLocalName);
        Property property = model.getProperty(model.getNsPrefixURI("") + propertyName);

        // Get the URI of the property
        if (property != null) {
            ExtendedIterator<Statement> stmtIterator = infModel.listStatements(movieClass, property, personClass);
            while (stmtIterator.hasNext()) {
                Statement stmt = stmtIterator.next();
                if (stmt.getPredicate().getLocalName().equals(propertyName)) {
                    stmtIterator.close();
                    return true;
                }
            stmtIterator.close();
            return false;
            }
        } else {
            return false;
        }

        //stmtIterator.close();
        return false;

        // Check if SubClass is a subclass of SuperClass
        //StmtIterator stmtIterator = infModel.listStatements(movieClass, infModel.getProperty("http://www.w3.org/2002/07/owlhasActor"), personClass);

        // Return true only if there's at least one statement indicating SubClass is a subclass of SuperClass
        //return stmtIterator.hasNext();
    }
    @org.junit.jupiter.api.Test
    public void testInference() {
        // Load ontology and perform reasoning
        boolean isInferenceActorandPerson = this.checkInferenceActorandPerson();
        boolean isInferenceDirectorandPerson = this.checkInferenceDirectorandPerson();
        //boolean isInferenceMovieandActor = this.checkInferenceMovieandActor();
        // Assert
        assertTrue(isInferenceActorandPerson);
        assertTrue(isInferenceDirectorandPerson);
        //assertTrue(isInferenceMovieandActor);
    }
    @org.junit.jupiter.api.Test
    public void testConsistencyCheck() {
        // Arrange
        String ontologyFilePath = "km/data/projectttttt.owl";

        // Act
        //boolean isConsistent = this.checkConsistency(ontologyFilePath);

        // Assert
        //assertTrue(isConsistent);
    }

    @org.junit.jupiter.api.Test
    void readAllPersonsTest() {
        Jena1 jena1 = new Jena1("km/data/project.owl");
        ArrayList<String>names = jena1.readAllPersons();
        // Assertions to verify the returned list
        assertNotNull(names);
        assertFalse(names.isEmpty()); // Check if there are any persons

        // You can add further assertions based on your ontology data
        // For example, if you know there should be 3 people:
        assertEquals(3, names.size());

        // Or check if a specific name exists in the list:
        assertTrue(names.contains("John Travolta")); // Replace with an expected name
        assertTrue(names.contains("Quentin Tarantino")); // Replace with an expected name
        assertTrue(names.contains("Uma Thurman")); // Replace with an expected name

    }
    @org.junit.jupiter.api.Test
    void readAllPersonTest() {
        Jena2 jena2 = new Jena2("km/data/project.owl");
        ArrayList<String>names = jena2.readAllPerson("km/data/query.txt");
        // Assertions to verify the returned list
        assertNotNull(names);
        assertFalse(names.isEmpty()); // Check if there are any persons

        // You can add further assertions based on your ontology data
        // For example, if you know there should be 3 people:
        assertEquals(3, names.size());

        // Or check if a specific name exists in the list:
        assertTrue(names.contains("John Travolta")); // Replace with an expected name
        assertTrue(names.contains("Quentin Tarantino")); // Replace with an expected name
        assertTrue(names.contains("Uma Thurman")); // Replace with an expected name

    }
    @org.junit.jupiter.api.Test
    void searchMovieTest() {
        Jena4 jena4 = new Jena4("km/data/project.owl");
        ArrayList<String> returns = jena4.searchMovie("Kill_Bill");



        assertEquals("2003", returns.get(0));
        assertEquals("USA", returns.get(1));

    }




}