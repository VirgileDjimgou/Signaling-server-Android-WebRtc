# Bee Driver Tracker -android
Bee Driver Tracking Client is an Android GPS tracking application. 
It can work with Firebase Cloud Env amd Traccar open source server software.


<img src="media/bee_black.png" align="left" height="128px" />
<img align="left" width="0" height="128px" hspace="10" />

<img src="media/Ioengine.jpg" align="left" height="128px" />
<img align="right" width="0" height="128px" hspace="10" />

<div style="display:block; height: 168px;">
* <i>The location Picker for Android supported by Firebase and Google Cloud</i> *


Component library  / Module for Android that uses Google Maps and returns a latitude, longitude and an address based on the location picked with the Activity provided.
</div>

<br/>
<p align="center">
<b><a href="#features">Features</a></b>
|
<b><a href="#localization">Localization</a></b>
|
<b><a href="#customization">Customization</a></b>
|
<b><a href="#tracking">Tracking</a></b>
|
<b><a href="#extra">Extra</a></b>
|
<b><a href="#who-made-this">Who Made This</a></b>
|
<b><a href="#bugs-and-feedback">Bugs and Feedback</a></b>
|
<b><a href="#license">License</a></b>
</p>
<br/>

### Features

<img align="right" width="0" height="368px" hspace="20"/>
<img src="media/app.gif" height="368px" align="right" />

* Search by text
* Geo Location by GPS, network
* Google Places (optional)
* Customization (Theme and layout)
* Events Tracking
* Multi-language support (English and French supported by default)
* RTL (Right-To-Left) layout support
<br><br><br>


### Prerequisites

minSdkVersion >= 15<br/>
Google Play Services = 11.8.0<br/>
Support Library = 27.0.2


#### Geocoding API Fallback

In few cases, the geocoding service from Android fails due to an issue with the NetworkLocator. The only way of fixing this is rebooting the device.

In order to cover these cases, you can instruct BeeTech to use the Geocoding API. To enable it, just use the method '''withGeolocApiKey''' when invoking the LocationPicker.

You should provide your Server Key as parameter. Keep in mind that the free tier only allows 2,500 requests per day. You can track how many times is it used in the Developer Console from Google. 


##### Important

Searching using the "SearchView" (geocoder) will be restricted to a zone if you are with a Locale from: DE, France, Cameroon. If not, the search will return results from all the world.


Sample usage
------------

We provide a sample project which provides runnable code samples that demonstrate their use in Android applications.
Note that you need to include your Google Play services key in the sample to be able to test it.


Who made this
--------------

| <a href="https://github.com/IoEngine"><img src="https://avatars0.githubusercontent.com/u/8148309?s=400&u=a293f76a0d32dcda91fcccfbea34b9c61c6ac792&v=4" alt="IoEngine" align="left" height="100" width="100" /></a>
|---
| [IoEngine](https://github.com/IoEngine)



Bugs and Feedback
-----------------

For bugs, questions and discussions please use the [Github Issues](https://github.com/chichikolon/Cartographe_Moonlight/issues).


License
-------

Copyright 2018 Beetech Gmbh S.L.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
