package org.springframework.samples.petclinic.owner;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component("ownerRepositoryImpl") //http://zetcode.com/springboot/component/
public class OwnerRepositoryCustomImpl implements OwnerRepository {

	@PersistenceContext
    private EntityManager entityManager;
        
	@Override
	public Collection<Owner> findByLastName(String lastName) {
		// Unsafe injection	
		String sqlQuery = "SELECT DISTINCT owner FROM Owner owner left join fetch owner.pets WHERE owner.lastName = '" + lastName +"'";
	    
		// Fix injection with parameterization 1/2
		//String sqlQuery = "SELECT DISTINCT owner FROM Owner owner left join fetch owner.pets WHERE owner.lastName = :lName";
			
		TypedQuery<Owner> query = this.entityManager.createQuery(sqlQuery, Owner.class);
		// Fix injection with parameterization 2/2
		//query.setParameter("lName", lastName);
	    	
		return query.getResultList();
	}
