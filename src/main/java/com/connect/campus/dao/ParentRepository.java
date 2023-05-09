package com.connect.campus.dao;

import com.connect.campus.entities.ParentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends JpaRepository<ParentEntity, Integer> {

    public ParentEntity findByParentId(int parentId);
    @Query(value = "SELECT * FROM parent_table WHERE parent_id=:parentId AND password=:password",nativeQuery = true)
    public ParentEntity findParent(@Param("parentId") int id, @Param("password") String password);
}
