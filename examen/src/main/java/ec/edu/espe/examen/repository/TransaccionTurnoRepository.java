package ec.edu.espe.examen.repository;

import ec.edu.espe.examen.model.TransaccionTurno;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransaccionTurnoRepository extends MongoRepository<TransaccionTurno, String> {
    List<TransaccionTurno> findByCodigoTurno(String codigoTurno);
}