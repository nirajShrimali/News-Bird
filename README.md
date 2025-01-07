News Bird App
News Bird is an Android application designed to fetch and display the latest news articles from Newsapi.org. With its clean interface and real-time updates, users can browse news across categories, view detailed articles, and stay informed.

Features
Real-Time News Updates: Fetches live news from Newsapi.org.
Category-Based Browsing: Navigate through Business, Technology, Sports, and other news categories.
Detailed View: Provides more in-depth content for any selected article, with a link to the original source.
Seamless Performance: Ensures smooth scrolling and efficient image loading using RecyclerView and Picasso.
Backend Workflow
Here's what happens in the backend to make the app functional:

API Integration with Retrofit:

The app uses the Retrofit library to connect with the Newsapi.org REST API.
Upon launching, a GET request is sent to the API endpoint with parameters like the API key, selected category, and country code.
Example API call:
plaintext
Copy code
https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=YOUR_API_KEY
The server responds with JSON data containing articles (title, description, image URL, source, etc.).
Data Parsing:

The JSON response is parsed using Retrofit’s GSON converter. The response is mapped into a Java model class (e.g., NewsArticle or NewsResponse).
Example JSON response:
json
Copy code
{
  "status": "ok",
  "totalResults": 100,
  "articles": [
    {
      "source": { "id": "bbc-news", "name": "BBC News" },
      "author": "John Doe",
      "title": "Breaking News Title",
      "description": "This is a short summary of the news.",
      "url": "https://example.com/article",
      "urlToImage": "https://example.com/image.jpg",
      "publishedAt": "2025-01-07T10:00:00Z"
    }
  ]
}
Data Binding to RecyclerView:

The parsed list of articles is passed to the RecyclerView.Adapter, which dynamically creates and binds ViewHolder objects for each news item.
The RecyclerView efficiently handles large datasets, displaying only visible items and recycling off-screen views.
Image Loading with Picasso: ![Third](https://github.com/user-attachments/assets/1c2ab0af-f482-42ce-911c-1c3244797d3e)


For each news item, the app fetches and loads the article's thumbnail image from the URL provided in the JSON response.
Picasso handles caching and ensures smooth scrolling without lag, even with multiple images being loaded.
Detail View Navigation: ![Second](https://github.com/user-attachments/assets/2700f8be-a912-43e4-8d08-11905ef87696)

When a user clicks on a news item, the app passes the article details (e.g., title, description, image URL) to a new activity or fragment.
This detail view shows the full article and a button to open the original news source in a WebView.
Category Filtering:

The app dynamically updates the API request URL when the user selects a different category. The backend fetches news for the selected category and refreshes the RecyclerView with the updated data.
How the App Works (Frontend and Backend)
Home Screen: ![First](https://github.com/user-attachments/assets/c1bc4a01-6401-434d-9f54-7bf6e3f910b9)


When the app starts, a default API request fetches top news articles for the "General" category.
Backend processes the request and delivers JSON data, which is displayed in the RecyclerView.
Detailed View:

Clicking an article sends its data to a new activity or fragment. The detailed article includes:
Title
Content
Image
A button to view the full article in the browser.
No additional API call is required at this step.
Category Selection:

When a user selects a category (e.g., Technology), the app modifies the API endpoint by adding the selected category as a parameter.
Backend fetches new JSON data, and the frontend updates the RecyclerView with articles from the chosen category.
Technologies Used
Android Studio: Development environment.
Java: Programming language for app logic.
Retrofit: To handle API requests and responses.
RecyclerView: For dynamic and efficient display of news articles.
Picasso: For image loading and caching.
Newsapi.org: The source for live news data.
