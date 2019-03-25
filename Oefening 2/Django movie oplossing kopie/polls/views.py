from django.shortcuts import render

# Create your views here.
from django.http import HttpResponse
from .models import Movie
from .models import Actor
import string


def index(request):
    #return HttpResponse("Hello, here will be a list with movies")
    movie_name = [m.movie_name for m in Movie.objects.all()]

    return render(request, 'polls/index.html', {'movie_name': movie_name})

def detail(request, movie_name):
    movie =  Movie.objects.filter(movie_name=movie_name).first()
    actor_name = movie.actor_set.all()

    return render(request, 'polls/detail.html', {'actor_name': actor_name, 'movie_name': movie_name})

def search_form(request):
    return render(request, 'polls/index.html', {})

def search_movie(request):
    if request.method == 'POST':
        word = request.POST['search_term']
        movie_list = Movie.objects.all()
        result_movie = [a.movie_name for a in movie_list if a.search_movie(word)]
        return detail(request,next(iter(result_movie), None))

def add_movie(request):
        if request.method == 'POST':
                movie_name = request.POST['movie_name']
                movie_genre = request.POST['movie_genre']
                Movie.objects.create(movie_name=movie_name, movie_genre=movie_genre)
                return index(request)
        else:        
                return render(request, 'polls/add_movie.html', {})
        
def add_actor(request):
        if request.method == 'POST':
                movie_name = request.POST['movie_name']
                actor_name = request.POST['actor_name']
                movie =  Movie.objects.filter(movie_name=movie_name).first()
                Actor.objects.create(actor_name=actor_name, actor_movie=movie)
                return index(request)
