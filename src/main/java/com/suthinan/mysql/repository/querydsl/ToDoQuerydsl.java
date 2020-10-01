package com.suthinan.mysql.repository.querydsl;

import com.querydsl.jpa.impl.JPAQuery;
import com.suthinan.mysql.entity.QToDoEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ToDoQuerydsl {
    @PersistenceContext
    private EntityManager entityManager;

    public String getDetailByTitle(String title) {
        JPAQuery query = new JPAQuery(entityManager);
        QToDoEntity qToDoEntity = QToDoEntity.toDoEntity;
        query.select(qToDoEntity.detail).from(qToDoEntity).where(qToDoEntity.title.contains(title));
        return (String) query.fetchOne();
    }

}
