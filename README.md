# StreamViewer Server
StreamViewer back-end server

###Functionality
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
### LiveChat
### TokenInfo
### Users
### YouTubeVideo
## com.adrianrossino.repository
### ChatMessageRepository
### LiveChatStatusRepository
### UsersRepository
### YouTubeVideoRepository
## com.adrianrossino.service
### UserRegisterService
### UserRegisterServiceImpl
### UserValidationService
### UserValidationServiceImpl
### YouTubeApiService
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