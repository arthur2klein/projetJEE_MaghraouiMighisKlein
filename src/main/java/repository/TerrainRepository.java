package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bdd.Terrain;

@Repository
public interface TerrainRepository extends JpaRepository<Terrain, Integer> {
	Terrain findByNom(String string);

}
