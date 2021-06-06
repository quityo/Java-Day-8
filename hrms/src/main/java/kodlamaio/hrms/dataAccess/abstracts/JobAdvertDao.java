package kodlamaio.hrms.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kodlamaio.hrms.entities.concretes.JobAdvert;
import kodlamaio.hrms.entities.dtos.JobAdvertListDto;

public interface JobAdvertDao extends JpaRepository<JobAdvert, Integer> {

	@Query("SELECT new kodlamaio.hrms.entities.dtos.JobAdvertListDto(e.companyName,p.title,j.numberOfOpenPositions,j.createdAt,j.applicationDeadline) FROM JobAdvert j JOIN j.employer e JOIN j.jobPosition p WHERE j.isActive=:isActive AND e.companyName=:companyName")
	List<JobAdvertListDto> findAllByIsActiveAndEmployer_CompanyNameForList(@Param("isActive") boolean isActive,
			@Param("companyName") String companyName);
	
	@Query("SELECT new kodlamaio.hrms.entities.dtos.JobAdvertListDto(e.companyName,p.title,j.numberOfOpenPositions,j.createdAt,j.applicationDeadline) FROM JobAdvert j JOIN j.employer e JOIN j.jobPosition p WHERE j.isActive=:isActive")
	List<JobAdvertListDto> findAllByIsActiveForList(@Param("isActive") boolean isActive);

	@Query("SELECT new kodlamaio.hrms.entities.dtos.JobAdvertListDto(e.companyName,p.title,j.numberOfOpenPositions,j.createdAt,j.applicationDeadline) FROM JobAdvert j JOIN j.employer e JOIN j.jobPosition p WHERE j.isActive=:isActive ORDER BY j.createdAt DESC")
	List<JobAdvertListDto> findAllByIsActiveOrderByCreatedAtDescForList(@Param("isActive") boolean isActive);

	@Query("SELECT new kodlamaio.hrms.entities.dtos.JobAdvertListDto(e.companyName,p.title,j.numberOfOpenPositions,j.createdAt,j.applicationDeadline) FROM JobAdvert j JOIN j.employer e JOIN j.jobPosition p WHERE j.isActive=:isActive ORDER BY j.createdAt")
	List<JobAdvertListDto> findAllByIsActiveOrderByCreatedAtForList(@Param("isActive") boolean isActive);
}