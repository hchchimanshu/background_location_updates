# background_location_updates
## Allow the user to get the coordinates of the user's location in the background after a fixed interval[3sec] of time

### buid.gradle[app module] :

__//add location permission1__
```
implementation 'com.google.android.gms:play-services-location:21.0.1'
```
	 
    
    
### Manifest file

__//add the following permissions__
 ```   
 <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
 <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
 <uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION" />
 ```
 
 __add the Location Service class[ as given in the code]
 
 ### Run the application
 1. Add the Location Permission -> Select Presize Location
 2. Add Background Permissio -> Allow All the time
 3. Start Service -> Service will start after 5 seconds
    
    
    
    
    

![Screenshot_2023-05-29-01-56-55-88_f4777176f0357a95fffa73474c93cc7b](https://github.com/hchchimanshu/background_location_updates/assets/52179440/32266237-5ab0-481b-ab9d-92d8bc5caf46)
