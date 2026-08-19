package com.manoj.notification.template.repository;

import com.manoj.notification.template.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {
    Optional<Template> findByTemplateCode(String templateCode);
    boolean existsByTemplateCode(String templateCode);
}