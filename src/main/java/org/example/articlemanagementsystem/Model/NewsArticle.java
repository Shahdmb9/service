package org.example.articlemanagementsystem.Model;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NewsArticle {

    @NotEmpty(message = "id can not be empty")
    private String id;

    @NotEmpty(message = "title can not be empty")
    @Size(max = 100)
    private String title;

    @NotEmpty(message = "author can not be empty")
    @Size(min=5,max = 20)
    private String author;

    @NotEmpty(message = "Content can not be empty")
    @Size(min=200)
    private String Content;

    @NotEmpty(message = "Category can not be empty")
    @Pattern(regexp = "(?i)^(politics|sports|technology)$",
            message = "category must be politics or sports or technology")
    private String category;

    @NotEmpty(message = "imageUrl can not be empty")
    private String imageUrl;

    private boolean isPublished=false;
    private LocalDate publishDate=LocalDate.now();


}
