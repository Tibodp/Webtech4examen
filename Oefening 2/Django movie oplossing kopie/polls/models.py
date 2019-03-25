from django.db import models

# Create your models here.
class Movie(models.Model):
    movie_name = models.CharField(max_length=50)
    movie_genre = models.CharField(max_length=50)

    def __str__(self):
        return self.movie_name

    def search_movie(self, word):
        return word in self.movie_name
        

class Actor(models.Model):
    actor_name = models.CharField(max_length=50)
    actor_movie = models.ForeignKey(Movie, on_delete=models.CASCADE)

    def __str__(self):
        return self.actor_name
    
    
