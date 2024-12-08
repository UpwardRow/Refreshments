# Introduction

**Refresh your recipes!**

The Refreshments mobile application is an application which allows the user to create specific dietary needs and proposes recipes without said ingredients. Many applications on the Play Store allow for vegetarian, vegan, and gluten-free preferences, but are not so accommodating for the rare dietary restrictions. Or even picky eaters like children. I came up with the idea in college but what I designed for the assignment was extremely barebones. No database, a vastly different more basic UI, and an API was connected. You will see below that this was quickly removed after I revisited the app after college.

# Application

## Development Tools

Originally I was working with the Spoonacular API. It did what it needed to do to prove that my prototype was possible. However, it had its limits for the requests. I knew then that ultimately I needed to find another source. I settled with EdamamAPI, which allowed more free requests, and a larger variety of customization. It was perfect for my needs and even allowed me to implement more features that came in very handy near the end of my core completion of the application. EdamamAPI brings in an array of recipes to search for, allowing filters to be applied to these searches.  

Originally the project was integrating with AWS Amplify. At the time, I had recently gained an AWS certification and hands-on experience with it in my workplace. I thought this was a great opportunity to enhance my portfolio and familiarity with AWS by continuing to work with the suite. I implemented what I could from Amplify but ran into compatibilty issues at some point. After reading forums of people moving to Firebase from Amplify and looking at my database architecture, I thought that this design wasn't worth the trouble. My database didn't rely on heavy relationships between the tables, it was not a large-scale project either.

Refreshments is a Kotlin-based project that saves users and their data in Firebase. This is with Firebase Realtime Database for the user data and Firebase Storage for the profile pictures. The user data is minimal and protected from other users. I do not use any data for advertising. For privacy, users can delete most of the data or all if they request me to. 

## User Manual

Refreshments is entirely reliant on user data, as such you must create an account. 

![Sign In](https://github.com/UpwardRow/Refreshments/blob/main/Sign%20In.png)

Once signed in, you can add your custom or predefined quick filters. 

![Home Screen](https://github.com/UpwardRow/Refreshments/blob/main/Home%20Screen.png)

Quick filters do the same thing as the custom filters, they are just faster to use if you have pre-existing conditions you would like to account for.

![Quick Filters](https://github.com/UpwardRow/Refreshments/blob/main/Quick%20Filters.png)

Your account can be edited and added to as you would like. 

![Account](https://github.com/UpwardRow/Refreshments/blob/main/Account.png)

Now, search for recipes! All of the chosen filters will be applied to the search.

![Search Results](https://github.com/UpwardRow/Refreshments/blob/main/Search%20Results.png)

# Conclusion

I gained a huge amount of experience and enjoyment out the design of Refreshnents, such as:

* Reshaped the entire database when was neccessary, from Amplify to Firebase
* Maintained a git feature branch workflow
* Discovered asyncronous programming within Android
* Made use of many objects and object orientated programming instruments (Object classes for dark mode, enums for filters, interfaces, plus many Android best practices)
* Implemented a fully functional dark mode with responsive elements which change depending on mode

I hope to use the knowledge from this project to benefit myself in my future web development timeline. 

P.S. For those curious, as with all of my current Android projects, there is colour scheme that I borrowed from. This one is the Mirrors album from DJ Seinfeld.
