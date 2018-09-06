//******************************************************************************
//                                       RadiometricTargetDAOSesame.java
// SILEX-PHIS
// Copyright © INRA 2018
// Creation date: 4 sept. 2018
// Contact: morgane.vidal@inra.fr, anne.tireau@inra.fr, pascal.neveu@inra.fr
//******************************************************************************
package phis2ws.service.dao.sesame;

import java.util.ArrayList;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.MalformedQueryException;
import org.eclipse.rdf4j.query.QueryEvaluationException;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import phis2ws.service.configuration.URINamespaces;
import phis2ws.service.dao.manager.DAOSesame;
import phis2ws.service.utils.sparql.SPARQLQueryBuilder;
import phis2ws.service.view.model.phis.RadiometricTarget;

/**
 * Allows CRUD methods of radiometric target in the triplestore.
 * @author Morgane Vidal <morgane.vidal@inra.fr>
 */
public class RadiometricTargetDAOSesame extends DAOSesame<RadiometricTarget> {
    final static Logger LOGGER = LoggerFactory.getLogger(RadiometricTargetDAOSesame.class);
    
    //The following params are used to search in the triplestore
    public String rdfType;
    
    //Triplestore relations
    private final static URINamespaces NAMESPACES = new URINamespaces();
    
    final static String TRIPLESTORE_CONCEPT_RADIOMETRIC_TARGET = NAMESPACES.getObjectsProperty("cRadiometricTarget");
    final static String TRIPLESTORE_RELATION_LABEL = NAMESPACES.getRelationsProperty("label");
    final static String TRIPLESTORE_RELATION_TYPE = NAMESPACES.getRelationsProperty("type");
    
    /**
     * Generates the query to get the uri and the label of the radiometric targets
     * @example
     * SELECT DISTINCT  ?uri ?label WHERE {
     *      ?uri  rdf:type  <http://www.phenome-fppn.fr/vocabulary/2017#RadiometricTarget> . 
     *      ?uri  rdfs:label  ?label  .
     * }
     * @return the query
     */
    @Override
    protected SPARQLQueryBuilder prepareSearchQuery() {
        SPARQLQueryBuilder query = new SPARQLQueryBuilder();
        query.appendDistinct(Boolean.TRUE);
        
        query.appendSelect("?" + URI + " ?" + LABEL);
        query.appendTriplet("?" + URI, TRIPLESTORE_RELATION_TYPE, TRIPLESTORE_CONCEPT_RADIOMETRIC_TARGET, null);
        query.appendTriplet("?" + URI, TRIPLESTORE_RELATION_LABEL, "?" + LABEL, null);
        
        LOGGER.debug(SPARQL_SELECT_QUERY + query.toString());
        return query;
    }
    
    /**
     * Get a radiometric target from a given binding set.
     * Assume that the following attributes exist : uri, label.
     * @param bindingSet a binding set, result from a search query
     * @return a radiometric target with data extracted from the given binding set
     */
    private RadiometricTarget getFromBindingSet(BindingSet bindingSet) {
        RadiometricTarget radiometricTarget = new RadiometricTarget();
        
        radiometricTarget.setUri(bindingSet.getValue(URI).stringValue());
        radiometricTarget.setLabel(bindingSet.getValue(LABEL).stringValue());
        
        return radiometricTarget;
    }
    
    /**
     * Get the radiometric targets (uri, label) of the triplestore.
     * @return the list of the radiometric target founded
     */
    public ArrayList<RadiometricTarget> getRadiometricTargets() {
        SPARQLQueryBuilder query = prepareSearchQuery();
        TupleQuery tupleQuery = getConnection().prepareTupleQuery(QueryLanguage.SPARQL, query.toString());
        ArrayList<RadiometricTarget> radiometricTargets = new ArrayList<>();

        try (TupleQueryResult result = tupleQuery.evaluate()) {
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                RadiometricTarget radiometricTarget = getFromBindingSet(bindingSet);
                radiometricTargets.add(radiometricTarget);
            }
        }
        return radiometricTargets;
    }

    @Override
    public Integer count() throws RepositoryException, MalformedQueryException, QueryEvaluationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
