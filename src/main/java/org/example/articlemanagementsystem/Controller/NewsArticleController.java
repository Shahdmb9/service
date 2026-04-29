package org.example.articlemanagementsystem.Controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.articlemanagementsystem.ApiResponse.ApiResponse;
import org.example.articlemanagementsystem.Model.NewsArticle;
import org.example.articlemanagementsystem.Service.NewsArticlesService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class NewsArticleController {

    private final NewsArticlesService newsArticlesService;


    @GetMapping("/get-articles")
    public ResponseEntity<?> getNewsArticles() {
        if(newsArticlesService.getNewsArticles().isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("No news articles"));
        }
        return ResponseEntity.status(200).body(newsArticlesService.getNewsArticles());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewsArticle(@RequestBody @Valid NewsArticle newsArticle, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if(newsArticle.isPublished())
            return ResponseEntity.status(400).body(new ApiResponse("News article can not be published on adding"));

        if(newsArticlesService.addNewsArticle(newsArticle)){
            return ResponseEntity.status(200).body(new ApiResponse("News article added successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Article already exists"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateNewsArticle(@PathVariable String id,@RequestBody @Valid NewsArticle newsArticle, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if(newsArticlesService.updateNewsArticle(id,newsArticle))
            return ResponseEntity.status(200).body(new ApiResponse("Article updated successfully"));
        return ResponseEntity.status(400).body(new ApiResponse("Article not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNewsArticle(@PathVariable String id) {

        if(newsArticlesService.deleteNewsArticle(id))
            return ResponseEntity.status(200).body(new ApiResponse("Article deleted"));
        return ResponseEntity.status(400).body(new ApiResponse("Article not found"));
    }

    @PutMapping("/publish-article/{id}")
    public ResponseEntity<?> publishNewsArticle(@PathVariable String id) {
        int response=newsArticlesService.publishNewsArticle(id);
        switch (response){
            case 0:
                return ResponseEntity.status(400).body(new ApiResponse("News article already published"));
            case 1:
                return ResponseEntity.status(200).body(new ApiResponse("News article published successfully"));
            default:
                return ResponseEntity.status(400).body(new ApiResponse("News article can not found"));
        }

    }

    @GetMapping("/get-published-aticles")
    public ResponseEntity<?> getPublishedArticles() {
        if(newsArticlesService.getPublishedNewsArticles().isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("No published news articles "));
        return ResponseEntity.status(200).body(newsArticlesService.getPublishedNewsArticles());
    }

    @GetMapping("/get-by-category/{category}")
    public ResponseEntity<?> getNewsArticlesByCategory(@PathVariable String category) {
        if(!category.matches("(?i)^(politics|sports|technology)"))
            return ResponseEntity.status(400).body(new ApiResponse("Invalid category, must be politics or sports or technology"));
        if(newsArticlesService.getNewsArticlesByCategory(category).isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("No news articles found"));
        return ResponseEntity.status(200).body(newsArticlesService.getNewsArticlesByCategory(category));

    }

}
