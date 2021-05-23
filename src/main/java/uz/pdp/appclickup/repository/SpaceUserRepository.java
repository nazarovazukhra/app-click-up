package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import uz.pdp.appclickup.entity.SpaceUser;

import java.util.UUID;

public interface SpaceUserRepository extends JpaRepository<SpaceUser, UUID> {


}
