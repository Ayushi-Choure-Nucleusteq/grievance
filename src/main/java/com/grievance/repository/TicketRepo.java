package com.grievance.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.grievance.entity.Department;
import com.grievance.entity.Member;
import com.grievance.entity.Ticket;
import com.grievance.enums.TicketStatus;

/**
 * Repository interface for Ticket-related operations.
 */
@Repository
public interface TicketRepo extends JpaRepository<Ticket, Integer> {

     /**
	 * Fetches a ticket based on its ID.
	 *
	 * @param ticketId The ID of the ticket to fetch.
	 * @return The ticket associated with the specified ID.
	 */
	Ticket findByticketId(Integer ticketId);

	/**
	 * Fetches a list of tickets related to a specific member.
	 *
	 * @param member      The member to filter by.
	 * @param of
	 * @return Page of tickets related to the specified member.
	 */
//	@Query("SELECT t FROM Ticket t WHERE t.status = ?3")
	Page<Ticket> findByMember(Member member, Pageable of);

	/**
	 * Fetches a list of tickets related to a department.
	 *
	 * @param department      The department to filter by.
	 * @param of
	 * @return Page of tickets related to the specified member.
	 */
//	@Query("SELECT t FROM Ticket t WHERE t.status = ?3")
	Page<Ticket> findByDepartment(Department department, Pageable of);

	/**
	 * @param memberId
	 * @param status
	 * @param of
	 * @return page
	 */
	@Query("SELECT t FROM Ticket t "
	 + "WHERE t.member.id = :memberId "
			+ "AND t.status = :status")
	Page<Ticket> findByMemberAndStatus(@Param("memberId") Integer memberId,
            @Param("status") Optional<TicketStatus> status, Pageable of);

	/**
	 * @param departmentId
	 * @param status
	 * @param of
	 * @return page
	 */
	@Query("SELECT t FROM Ticket t "
	 + "WHERE t.department.deptId = :departmentId "
			   + "AND t.status = :status")
	Page<Ticket> findByDepartmentAndStatus(@Param("departmentId")
			Integer departmentId, @Param("status")
			  Optional<TicketStatus> status, Pageable of);

	/**
	 * @param of
	 * @param status
	 * @return page
	 */
	@Query("SELECT t FROM Ticket t "
				+ "WHERE t.status = :status")
	Page<Ticket> findAllAndStatus(Pageable of,
			Optional<TicketStatus> status);
}
