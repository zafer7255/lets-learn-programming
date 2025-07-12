package com.MyClassroom.ProductionLevelApplication.RepoDAO;

import com.MyClassroom.ProductionLevelApplication.Models.ClassRecording;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRecordingRepo extends JpaRepository<ClassRecording,Integer> {

}
