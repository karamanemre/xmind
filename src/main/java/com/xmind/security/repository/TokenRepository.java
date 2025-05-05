package com.xmind.security.repository;

import com.xmind.security.entity.TokenEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Integer> {

    @Query("""
                select t from tokens t
                join t.user u
                where u.id = :id and (t.expired = false or t.revoked = false)
            """)
    List<TokenEntity> findAllValidTokenByUserId(@Param("id") Long id);

    Optional<TokenEntity> findByToken(@Param("token") String token);
}
