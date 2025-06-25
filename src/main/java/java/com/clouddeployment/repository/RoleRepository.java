package java.com.clouddeployment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.com.clouddeployment.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
}
