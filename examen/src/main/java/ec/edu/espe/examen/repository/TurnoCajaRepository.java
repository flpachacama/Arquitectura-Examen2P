package ec.edu.espe.examen.repository;

import ec.edu.espe.examen.model.TurnoCaja;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TurnoCajaRepository extends MongoRepository<TurnoCaja, String> {
    Optional<TurnoCaja> findByCodigoTurno(String codigoTurno);
}
