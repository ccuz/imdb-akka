var mongoose = require('mongoose');

var MovieSchema = new mongoose.Schema({
  MovieID: {type: String, index: {unique: true}},
  /*
  SeriesId: String,
  SeriesType: String,
  SeriesEndYear: String,
  ReleaseYear: String,
  AltTitles: Array,
  Business: { "Budget" : String, "BudgetCurrency" : String, "HighGBO" : String, "HighGBOCountry" : String, "HighGBOCurrency" : String },
  Parental: { "Certificates" : Array },
  Technical : { "Colors" : Array, "Sounds" : Array },
  Countries : Array,
  Genres : Array,
  Keywords : Array,
  Languages: Array,
  Locations: Array,
  Rating : { "RatingDist" : String, "Rating" : String, "RatingVotes" : String },
  RunningTime : String*/
}, { collection: 'Movie' });

MovieSchema.on('index', function(err) {
    if (err) {
        console.error('User index error: %s', err);
    } else {
        console.info('User indexing complete');
    }
});

module.exports = mongoose.model('Movie', MovieSchema);
