package br.newton.AV2_AAW.repository;

import br.newton.AV2_AAW.model.ProductEntity;
import br.newton.AV2_AAW.model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<ProductEntity, String> {
}