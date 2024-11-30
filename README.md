# Introduction

**Cook, your way**

The Refreshments mobile application is an application which allows the user to create specific dietary needs, and proposes recipes without said ingredients. A lot of applications on the Play Store allow for preferences like vegetarian, vegan, and gluten free, but are not so accomodating for the more rare dietary restrictions. I came up with the idea in college but what I designed for the assignment was extremely barebones. No database, a vastly different more basic UI, and an API was connected. You will see below that this was quickly removed after I revisited the app after college.

# The Technologies
Starting out I was working with the Spoonacular API. It did what it needed to do to prove that my prototype was possible. However, it had its limits for the requests. I knew then that ultimately I needed to find another source. I settled with EdamamAPI, which allowed more free requests, and a larger amount of customization. It was perfect for my needs and even allowed me to implement more features that came in very handy near the end of my core completion of the application. EdamamAPI brings in an array of recipes to search for, and allows filters to be applied to these said searches.  

Originally the project was integrating with AWS Amplify. At the time, I had recently gained an AWS certification and hands on experience with it in my workplace. I thought this was a great opportunity to enhance my portfolio and familiarity with AWS by continuing to work with the suite. I implemented what I could from Amplify but ran into compatibilty issues at some point. After reading forums of people moving to Firebase from Amplify and looking at my database architecture, I thought that this design wasn't worth the trouble. My database didn't rely on heavy relationships between the tables, it was not a large scale project either.

Refreshments is a Kotlin based project, which saves users and the data of the users in Firebase. This is with Firebase Realtime Database for the user data, and Firebase Storage for the profile pictures. The user data is minimal and protected from other users. I do not use any data for advertising. For privacy, users can delete most of the data, or all if they request me to. 

# Solution
