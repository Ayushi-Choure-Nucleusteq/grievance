package com.grievance.repository;

import com.grievance.entity.Member;
import com.grievance.enums.MemberRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * MemberRepo Repository Interface.
 * <p>This interface serves as the (DAO) for the Member entity.</p>
 * <p>It extends JpaRepository which provides JPA-specific methods</p>
 */
@Repository
public interface MemberRepo extends JpaRepository<Member, Integer> {

    /**
     * Finds a member by its email address.
     *
     * @param email the email address of the member.
     * @return the member entity with the given email or null if not found.
     */
    Member findByEmail(String email);

    /**
     * Checks the existence of a member.
     *
     * @param email Email of the member.
     * @param password Password of the member.
     * @param memberRole Role of the member.
     * @return true if a member exists with given credentials.
     */
    boolean existsByEmailAndPasswordAndRole(
            String email,
    		String password,
    		MemberRole memberRole);
}
