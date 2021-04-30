# ListAndDetailDemo

## Introduction

The purpose of this exercise is to determine the initial level of the competencies regarding Android application development. The assignment is to deliver the source code of a small application conforming the specifications and requirements as described in this document.

## Goal

The goal of the assignment is to deliver source code for an application. The application contains a list of category with products of a catalog. Data is fetched dynamically from a web server.

## Requirements

The tehcnologies, methodologies, principles, etc. should meet the following requirements:

- Code should have good test coverage
- The source code should be without any errors and needs to compile on Android Studio without any further coding or configuration effort,
- The source code should be targeted for the most recent version of the Android platform
- The definition of the user interface is should take in account Android specific best practices and common patterns;
- Any frameworks and libraries can be used.
-  Use the JSON-based data source available at http://mobcategories.s3-website-eu-west-1.amazonaws.com/
- Note that need to use this external resource, just copying the content and storing it in a local file is not enough.
- Images can be fetched using the same URL with image name: http://mobcategories.s3-website-eu-west-1.amazonaws.com/Bread.jpg
- Note that image might be missing.

The application should consist of two pages:
**List page:**
* The list page should contain a list with products categorized per category.
* Each item in the list should display full name and thumbnail of the image (if
present).
* Clicking an item will cause the application to open the detail view.

**Detail page:**
The detail page should display the following information of the selected product:
* Name of the product.
* Image with bigger view.
* Price of the product.

## Solution
This is sample android project contains MVVM architecture pattern and android architecture components such as LiveData, ViewModel, Repository, Room, etc.

I have followed below diagram as reference which shows basic form of MVVM architecture.

mvvm-architecture

You can find all MVVM architecture codes for Activity, Fragment, ViewModel, ViewModelFactory, LiveData, Repository, Dao, Entity, Room database, Retrofit and Glide library, etc.

Code coverage report can be found in **/reports** directory. 

After downloading **reports/CodeCoverage.zip** folder, *index.html* can be opened in browser for better visualizing the details.

Click [here](https://github.com/ercanduman/ListAndDetailDemo/raw/master/reports/CodeCoverage.zip/ "CodeCoverage.zip") to download coverage zip folder.

Code coverage report's screenshot is as follow
![](https://raw.githubusercontent.com/ercanduman/ListAndDetailDemo/master/reports/Screen%20Shot%20Code%20Coverage_2021-03-28%20at%2019.16.21.png)


Notice: If you got an error such as: "Can not perform this action after onSaveInstanceState", Make sure the device you are running the tests on is unlocked and has active network. If the screen is off or at the lock screen you will get a stack trace that looks roughly like this.
