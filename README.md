# StreamViewer Server
StreamViewer back-end server

### Functionality
*The server will receive the login details from a user and validate the token with google, generate a new token (one that is valid for our website), save it and return it.
*If the user has logged-in in the past, the user will send their current token and be validated with out site, if the token has expired but it's a real token a new token will be issued to the user.
*The server will run the YouTube v3 api every 5 minutes to see changes in the user data, fetch new live streams (if available) and save chat messages from the live video.
*We save the most current YouTube video information in our DB for later use.
*The user will make a get call to get the videos we have in our records and open a websocket comunication to get the updates from our server and YouTube api.

## com.adrianrossino
### StreamviewerserverApplication
This class is the boostrap class for spring boot
### WebSocketConfiguration
This class configures the websocket endpoints and registers the broker for the websocket messages between client and server
## com.adrianrossino.auth
### GoogleTokenVerifier
```
verifyToken(String idToken)
```
Receives the token from the client and verifies that indeed belongs to Google.

```
Payload verify(String idTokenString)
```
Returns a Google's Payload object, this object returns the user information we need (family name, picture, given name, email)

### StreamViewerTokenProvider
```
String getNewToken(String userId)
```
This method receives a userId (that in this case we set the same one google sends to us) and creates a new auth token with JWT from auth0

```
String getNewTokenFor(String userId)
```
Calls getNewToken method

## com.adrianrossino.batch
### LiveStatusBatchBean
This class is called every 5 minutes to update the video status in the page, calls YouTubeApiService to receive the chat and videos and sends the message via STOMP to our users.
## com.adrianrossino.common
### InvalidTokenException
An exception class for invalid tokens
## com.adrianrossino.controller
### UsersController
```
Users setUsers(@RequestBody TokenInfo token)
```
This method is called via POST, gets the user "TokenInfo" that has the sender name and the token, if the sender is google we authenticate the token with google first, we then create or update the user and return a new token for our site, if the user is a returning user we just validate the token and return the new token.

### VideosController
```
List<YouTubeVideo> getYoutube()
```
This method is called via GET requests, returns a list of the top nine YouTube videos that we have saved in our DB
## com.adrianrossino.model
### ChatMessage
Model for chat messages:

```
private String id;
private String liveChatId;
private String publishedAt;
private String messageText;
private String displayName;
private Boolean isChatOwner;
private String profileImageUrl;
private String channelId;
```
### LiveChat
Model for Live Chats (includes a list of messages and the current status)

```
private LiveChatStatus status;
private List<ChatMessage> messages;
```
### LiveChatStatus
Includes the id and the token for the next page of chat messages

```
private String chatId;
private String nextPageToken;
```

### TokenInfo
Model for the page token, source is the sender (google or us) and token

```
private String tokenId;
private String source;
```

### Users
Model for site users, includes basic info

```
private String userId;
private String name;
private String token;
private String email;
private String picture;
private String familyName;
```

### YouTubeVideo
Model for YouTube videos

```
private String id;
private String channelId;
private String title;
private String description;
private String publishedAt;
private String thumbnailUrl;
private String liveChatId;
private String status;
private String embedHtml;
```

## com.adrianrossino.repository
### ChatMessageRepository
Interface for CrudRepository, returns **ChatMessage**
### LiveChatStatusRepository
Interface for CrudRepository, returns **LiveChatStatus**
### UsersRepository
Interface for CrudRepository, returns **Users**
### YouTubeVideoRepository
Interface for CrudRepository, returns **YouTubeVideo**
## com.adrianrossino.service
### UserRegisterService
Interface for the registration service
### UserRegisterServiceImpl
Implementation of the **UserRegisterService**
Implements method ```Users saveUser(Payload payload)``` that returns a Users object and receives a Google Payload object to extract information and save it in the DB
### UserValidationService
Interface for the validation service
### UserValidationServiceImpl
Implementation of the **UserValidationService**
Implements method ```Users validateUser(TokenInfo tokenInfo)``` that returns a Users object and receives an TokenInfo object to get the token and validate that we have the user registered and return the user info
### YouTubeApiService
Implementation of the YouTube Api v3 service from Google

```
public static Credential authorize(final NetHttpTransport httpTransport, String clientSecret)
```
Authorizes the client for api use, and returns a Credential, the refresh token is stored in a Datastore called "youtube, so the service only has to be approved once.

```
YouTube getService(String clientSecret)
```
Receives a clientSecret (that is provided by Google api console) and authorizes the use of the api, returns a YouTube object that has the service information and credentials.

```
List<YouTubeVideo> getStatus(YouTube youtubeService)
```
Calls the service and receives the last nine videos and information of them, and saves it in a list of YouTubeVideo objects from our model

```
LiveChat getMessages(String liveChatId, String pageToken, YouTube youtubeService)
```
Calls the service to retrieve the latest messages that we haven't saved in the DB. Returns a LiveChat object that has a list of all messages and the status of the chat, including the next page token.

# application.properties 
There is no included application.properties file, to be able to run the app you need to include the following variables to the file:
* server.port=Server port in which you'll run this app
* spring.datasource.url= Your database URL
* spring.datasource.username= USERNAME
* spring.datasource.password= PASSWORD
* spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
* spring.datasource.hikari.max-lifetime=If you want to refresh your connections after an x amount of time
* client.secret=JSON Object that you can obtain in the api page of your Google cloud console 

# pom.xml
This is a Maven project and all dependencies are added to the pom.xml
