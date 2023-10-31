package com.grievance.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommentTest {

    private Comment comment;

    @BeforeEach
    public void setUp() {
        comment = new Comment();
    }
    

    @Test
    public void testCommentId() {
        comment.setCommentId(1);
        assertEquals(1, comment.getCommentId());
    }

    @Test
    public void testContent() {
        comment.setContent("Test content");
        assertEquals("Test content", comment.getContent());
    }

    @Test
    public void testMemberEmail() {
        comment.setMemberEmail("ayushi@nucleusteq.com");
        assertEquals("ayushi@nucleusteq.com", comment.getMemberEmail());
    }

    @Test
    public void testCreationTime() {
        comment.setCreationTime("2023-09-22 12:00:00");
        assertEquals("2023-09-22 12:00:00", comment.getCreationTime());
    }

    @Test
    public void testTicket() {
        Ticket ticket = new Ticket();
        comment.setTicket(ticket);
        assertNotNull(comment.getTicket());
    }

    @Test
    public void testToString() {
        comment.setCommentId(1);
        comment.setContent("Test content");
        comment.setMemberEmail("ayushi@nucleusteq.com");
        comment.setCreationTime("2023-09-22 12:00:00");
        Ticket ticket = new Ticket();
        comment.setTicket(ticket);

        String expectedString = "Comment [commentId=1, content=Test content, creationTime=2023-09-22 12:00:00, memberEmail=ayushi@nucleusteq.com, ticket=" + ticket.toString() + "]";
        assertEquals(expectedString, comment.toString());
    }
    
    

    @Test
    public void testEqualsAndHashCode() {
        Comment comment1 = new Comment();
        comment1.setCommentId(1);
        comment1.setContent("First comment");
        comment1.setCreationTime("2023-09-22 12:00:00");
        comment1.setMemberEmail("ayushi@nucleusteq.com");

        Comment comment2 = new Comment();
        comment2.setCommentId(1);
        comment2.setContent("First comment");
        comment2.setCreationTime("2023-09-22 12:00:00");
        comment2.setMemberEmail("ayushi@nucleusteq.com");

        Comment comment3 = new Comment();
        comment3.setCommentId(2);
        comment3.setContent("Second comment");
        comment3.setCreationTime("2023-09-22 12:00:12");
        comment3.setMemberEmail("example2@email.com");

        assertEquals(comment1, comment2);
        assertNotEquals(comment1, comment3);

        assertEquals(comment1.hashCode(), comment2.hashCode());
        assertNotEquals(comment1.hashCode(), comment3.hashCode());
    }
}
