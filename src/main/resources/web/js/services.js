movieApp
.factory('MovieService', ['$http', function ($http) {

    var urlBase = 'http://localhost:3456';
    var MovieService = {};

    MovieService.listMovies = function () {
        return $http.get(urlBase+'/movie');
    };


    MovieService.getMovieDetails = function (firstval) {
        return $http.get(urlBase+'/movie', {params: {movieId: firstval} });
    };

    return CalculatorService;
}]);