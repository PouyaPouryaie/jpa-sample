package ir.bigz.spring.jpasample.basic;

import jakarta.persistence.EntityManager;
import org.hibernate.engine.spi.EntityEntry;
import org.hibernate.engine.spi.EntityKey;
import org.hibernate.engine.spi.PersistenceContext;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ProductService {

    private final ProductDao productDao;

    private final EntityManager entityManager;


    public ProductService(ProductDao productDao, EntityManager entityManager) {
        this.productDao = productDao;
        this.entityManager = entityManager;
    }

    public List<Product> getAllProduct(){
        List<Product> all = productDao.findAll();
        //how use sharedSession for manage entity and monitor in hibernate as provider of jpa
        SharedSessionContractImplementor sharedSession = entityManager.unwrap(SharedSessionContractImplementor.class);
        PersistenceContext persistenceContext = sharedSession.getPersistenceContext();
        System.out.println("number of association in managed state: " + persistenceContext.getCollectionEntriesSize());
        System.out.println("number of entity in managed state: " + persistenceContext.getNumberOfManagedEntities());
        Map<EntityKey, Object> entitiesByKey = persistenceContext.getEntitiesByKey();
        Set<EntityKey> entityKeys = entitiesByKey.keySet();
        for(EntityKey entityKey: entityKeys){
            Object o = entitiesByKey.get(entityKey);
            EntityEntry entry = persistenceContext.getEntry(o);
            List<Object> objects = Arrays.asList(entry.getLoadedState());
            List<String> collect = objects.stream().map(o1 -> o1.toString()).collect(Collectors.toList());
            System.out.println(entry.getEntityName());
            System.out.println(entry.getStatus());
            System.out.println(String.join(",", collect));
        }
        return all;
    }

    public Product save(Product product) {
        Product newProduct = productDao.save(product);
        return newProduct;
    }
}
