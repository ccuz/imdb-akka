movieApp.
controller('MovieController', function($scope, MovieService) {

   $scope.listMovies = function () {
      MovieService.listMovies()
      .success(function (result) {
          $scope.movies = result;
      })
      .error(function (error) {
          $scope.status = 'Backend error: ' + error.message;
      });
   }

   $scope.movieDetails = function () {
      MovieService.multiplyValues($scope.firstVal)
      .success(function (result) {
          $scope.movie = result;
      })
      .error(function (error) {
          $scope.status = 'Backend error: ' + error.message;
      });
   }
});