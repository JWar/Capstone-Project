# Capstone-Project
Capstone Project for Nanodegree. Simple messaging app using Firebase, with SMS fallback option.
Using Intent Service, Loaders (would prefer RxJava but spec requires those two and I can do everything I need
with those two so seems wasteful to add RxJava), Content Provider.   
Uses Firebase Push Notifications to alert app to the presence of a new message, Intent Service is then
run using Retrofit to get new message. 

Plenty of room for extending app - 
RxJava everything.
Replace Content Provider with Room.
Use Glide/Picasso for Image handling.
Headers in Msgs list, with grouped together dates.
Notifications can handle multiple messages/are more sophisticated
Name rather than tel in Msg list.
Contact list, displaying list of contacts with edit/delete/create

Note: treating backend as a black box that just does what its supposed to.