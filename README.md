# WebCrawlerSample
 
This repository is a simple web carwler for CNN and Twitter websites. It extracts latest tweets from a specific ID, and latest CNN articles, and then shows them in a single web page. 

  - Viewing latest tweets and CNN articles in a single page
  - Not using any data storage (it can lead to performance issues though)
  - Searching for a keyword in CNN articles and limiting the results

Extracting data from CNN web pages is a heavy task, because the size of each web page is more than 1.5 MB, and we are not using any data storages or even caching. Therefore, this web crawler only uses the sitemap XML files provided on the CNN website.  

### New Features!

  - Configuring the web crawler so as to fetch any desired data 

There is a form in the top of the web page, which can be used to specify the keyword you are interested in, on CNN website (the default value is 'trump'). Also we can get the latest tweets of anyone in Tweeter.

### Preview

In order to see the features of this repository instantly, it is published on Openshift: http://crawler-m-ghaemifard.b542.starter-us-east-2a.openshiftapps.com/WebCrawler/

There is no need to fill the form, just click 'Retrieve'.

### Installation

This web crawler depends on the Gradle Build Tool, so the first thing to do is to install it from [here](https://gradle.org/install/).

Then open a terminal in the working directory and run the following commands:

``` 
/WebCrawlerSample$ gradle build
/WebCrawlerSample$ gradle appRun
```

After that, you'll be able to see the web crawler from here: http://localhost:8080/WebCrawlerSample

 
