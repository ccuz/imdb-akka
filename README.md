# imdb-akka
Akka HTTP &amp; MongoDB with Akka

Run following to retrieve the content from the db (OK, you first have to populate a mongodb with data...
see https://www.packtpub.com/books/content/mongo-goes-to-the-movies):
curl "localhost:3456/movie/%22The%20Good%20Wife%22%20(2009)"

For the AngluarJS, just fix it in 'src/main/resources/web' directory. Then open the browser at http://localhost:3456/