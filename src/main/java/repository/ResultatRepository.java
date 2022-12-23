package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bdd.Resultat;

@Repository
public interface ResultatRepository extends JpaRepository<Resultat, Integer> {
}
