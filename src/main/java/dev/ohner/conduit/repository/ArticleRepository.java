package dev.ohner.conduit.repository;

import dev.ohner.conduit.repository.entity.ArticleEntity;
import dev.ohner.conduit.service.model.Slug;
import org.springframework.data.repository.ListCrudRepository;


public interface ArticleRepository extends ListCrudRepository<ArticleEntity, Slug> { }

