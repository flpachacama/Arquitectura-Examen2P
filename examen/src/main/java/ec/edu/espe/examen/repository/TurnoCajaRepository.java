package ec.edu.espe.examen.repository;

import ec.edu.espe.examen.model.TurnoCaja;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TurnoCajaRepository extends MongoRepository<TurnoCaja, String> {
    Optional<TurnoCaja> findByCodigoTurno(String codigoTurno);
    Optional<TurnoCaja> findByCodigoCajaAndCodigoCajeroAndEstado(String codigoCaja, String codigoCajero, String estado);
}
