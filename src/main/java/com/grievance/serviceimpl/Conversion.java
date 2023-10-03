package com.grievance.serviceimpl;

import java.util.List;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.grievance.outdto.DepartmentOutDto;
import com.grievance.outdto.MemberOutDto;
import com.grievance.outdto.TicketOutDto;
import com.grievance.entity.Comment;
import com.grievance.entity.Department;
import com.grievance.entity.Member;
import com.grievance.entity.Ticket;
import com.grievance.indto.DepartmentDto;
import com.grievance.indto.MemberDto;
import com.grievance.indto.TicketDto;

/**
 * Conversion class.
 */
@Component
public class Conversion {

    /**
     * Convert from DepartmentDto to Department entity.
     *
     * @param dto the DepartmentDto to convert.
     * @return the corresponding Department entity.
     */
    final Department departmentDtoToEntity(
            final DepartmentDto dto) {
        if (Objects.isNull(dto)) {
            return null;
        }

        Department department = new Department();
        department.setDeptName(dto.getDeptName());

        return department;
    }

    /**
     * Convert from Department entity to DepartmentOutDto.
     *
     * @param department the Department entity to convert.
     * @return the corresponding DepartmentOutDto.
     */
    final DepartmentOutDto departmentToOutDto(
    		final Department department) {
        if (Objects.isNull(department)) {
            return null;
        }

        DepartmentOutDto dto = new DepartmentOutDto();
        dto.setDeptId(department.getDeptId());
        dto.setDeptName(department.getDeptName());

        return dto;
    }

    /**
     * Converts the provided MemberDto into a Member entity.
     *
     * @param dto the MemberDto to convert.
     * @return the resulting Member entity.
     */
    final Member memberdtoToEntity(final MemberDto dto) {
        if (Objects.isNull(dto)) {
            return null;
        }

        Member member = new Member();
        member.setName(dto.getName());
        member.setEmail(dto.getEmail());
        member.setPassword(dto.getPassword());
        member.setRole(dto.getRole());

        Department department = departmentDtoToEntity(dto.getDepartment());
        member.setDepartment(department);

        return member;
    }

    /**
     * Converts the provided Member entity into a MemberOutDto.
     *
     * @param entity the Member entity to convert.
     * @return the resulting MemberOutDto.
     */
    final MemberOutDto memberToOutDto(final Member entity) {
        if (Objects.isNull(entity)) {
            return null;
        }

        MemberOutDto dto = new MemberOutDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setIsLoggedIn(entity.getIsLoggedIn());
        dto.setRole(entity.getRole());

        if (Objects.nonNull(entity.getDepartment())) {
            dto.setDeptName(entity.getDepartment().getDeptName());
        }
        return dto;
    }

    /**
     * Converts the provided TicketDto into a Ticket entity.
     *
     * @param dto the TicketDto to convert.
     * @return the resulting Ticket entity.
     */
    final Ticket ticketdtoToTicket(final TicketDto dto) {
        if (Objects.isNull(dto)) {
            return null;
        }

        Ticket entity = new Ticket();
        entity.setTicketName(dto.getTicketName());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        entity.setCreationDate(dto.getCreationDate());
        entity.setLastUpdateDate(dto.getLastUpdateDate());
        entity.setTicketType(dto.getTicketType());

        if (Objects.nonNull(dto.getComments())) {
            List<Comment> comments = entity.getComments();
            Comment newComment = new Comment();
            newComment.setContent(dto.getComments());
            newComment.setTicket(entity);
            newComment.setMemberEmail(dto.getMember().getEmail());
            comments.add(newComment);
            entity.setComments(comments);
        }

        if (Objects.nonNull(dto.getDepartment())) {
            Department department = new Department();
            department.setDeptName(dto.getDepartment().getDeptName());
            entity.setDepartment(department);
        }

        if (Objects.nonNull(dto.getMember())) {
            Member member = new Member();
            entity.setMember(member);
        }
        return entity;
    }

    /**
     * Converts a given Ticket entity into a TicketOutDto.
     *
     * @param entity the Ticket entity to convert.
     * @return the resulting TicketOutDto.
     */
    final TicketOutDto ticketToOutDto(final Ticket entity) {
        if (Objects.isNull(entity)) {
            return null;
        }

        TicketOutDto outDto = new TicketOutDto();
        outDto.setTicketId(entity.getTicketId());
        outDto.setTicketName(entity.getTicketName());
        outDto.setDescription(entity.getDescription());
        outDto.setStatus(entity.getStatus());
        outDto.setCreationDate(entity.getCreationDate());
        outDto.setLastUpdateDate(entity.getLastUpdateDate());
        outDto.setTicketType(entity.getTicketType());
        outDto.setComments(entity.getComments());

        if (Objects.nonNull(entity.getMember())) {
            outDto.setMember(entity.getMember().getName());
        }

        if (Objects.nonNull(entity.getDepartment())) {
            outDto.setDepartment(entity.getDepartment().getDeptName());
        }

        return outDto;
    }
}
