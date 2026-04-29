package org.example.articlemanagementsystem.Service;


import jakarta.validation.Valid;
import org.example.articlemanagementsystem.ApiResponse.ApiResponse;
import org.example.articlemanagementsystem.Model.NewsArticle;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@Service
public class NewsArticlesService {

    ArrayList<NewsArticle> newsArticles=new ArrayList<>();


    public ArrayList<NewsArticle> getNewsArticles() {
        return newsArticles;
    }


    public boolean addNewsArticle( NewsArticle newsArticle) {
        for(NewsArticle article:newsArticles){
            if(article.getId().equals(newsArticle.getId())){
                return false;
            }
        }
        newsArticles.add(newsArticle);
        return true;
    }


    public boolean updateNewsArticle( String id, NewsArticle newsArticle) {

        for (int i = 0; i < newsArticles.size(); i++) {
            if(newsArticles.get(i).getId().equals(id)){
                newsArticles.set(i,newsArticle);
                return true;
            }

        }
        return false;
    }



    public boolean deleteNewsArticle( String id) {
        for (NewsArticle newsArticle : newsArticles) {
            if (newsArticle.getId().equals(id)) {
                newsArticles.remove(newsArticle);
                return true;
            }
        }
        return false;
    }

    public int publishNewsArticle( String id) {
        for (NewsArticle newsArticle : newsArticles) {
            if (newsArticle.getId().equals(id)) {
                if(newsArticle.isPublished())
                    return 0;
                newsArticle.setPublished(true);
                newsArticle.setPublishDate(LocalDate.now());
                return 1;
            }
        }
        return 2;
    }

    public ArrayList<NewsArticle> getPublishedNewsArticles() {
        ArrayList<NewsArticle> publishedNewsArticles=new ArrayList<>();

        for (NewsArticle newsArticle : newsArticles) {
            if (newsArticle.isPublished()) {
                publishedNewsArticles.add(newsArticle);
            }
        }

        return publishedNewsArticles;

    }

    public ArrayList<NewsArticle> getNewsArticlesByCategory(String category) {
        ArrayList<NewsArticle> newsArticlesByCategory=new ArrayList<>();

        for (NewsArticle newsArticle : newsArticles) {
            if (newsArticle.getCategory().equals(category)) {
                newsArticlesByCategory.add(newsArticle);
            }
        }
        return newsArticlesByCategory;
    }

}
