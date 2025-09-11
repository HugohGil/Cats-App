Project made for a code challenge.

Strategies decided during development:

I used Retrofit for fetching the data from the Cat API and Paging 3 to implement the pagination of the data.
I decided to use these two libraries because I did a similar project before where I had to fetch data from an API and I went with a more manual route with HttpURLConnection and InputStreamReader, and for the pagination I detected the scroll position and loaded the next
page when it was needed.
This time i wanted to learn more modern and common libraries so I decided to go with Retrofit and Paging 3.

Since I decided to implement Room for the offline functionality I chose to have different view models instead of sharing the same one, and just loading the data from the database when I went into a new screen.
Since the screens for the breed list and the favourite breeds were so similar I decided to reuse the composables but still kept separate view models to have a more organized structure.

I'm not experienced with tests so I did some simple unit tests for the BreedListViewModel and didn't go further with integration and E2e tests as I wouldn't have time to learn them properly.
