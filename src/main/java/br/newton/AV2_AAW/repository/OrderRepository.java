package br.newton.AV2_AAW.repository;

import br.newton.AV2_AAW.model.OrderEntity;
import br.newton.AV2_AAW.model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<OrderEntity, String> {
}