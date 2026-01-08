package com.naveen.easycar.repository;

import com.naveen.easycar.entity.MaintenanceWindow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceWindowRepository extends JpaRepository<MaintenanceWindow, Long> {

    List<MaintenanceWindow> findByCarId(Long carId);
}

