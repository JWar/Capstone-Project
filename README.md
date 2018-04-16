# Capstone-Project
Capstone Project for Nanodegree. Simple messaging app using Firebase, with SMS fallback option.
Using Intent Service, Loaders (would prefer RxJava but spec requires those two and I can do everything I need
with those two so seems wasteful to add RxJava), Content Provider.   
Uses Firebase Push Notifications to alert app to the presence of a new message, Intent Service is then
run using Retrofit to get new message. 

Plenty of room for extending app - RxJava everything. Use Glide/Picasso for Image handling.

Note: treating backend as a black box that just does what its supposed to.