//
//  LocationManager.m
//  AlausRadaras
//
//  Created by Laurynas R on 2/22/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "LocationManager.h"

@implementation LocationManager

- (void)dealloc {
	[clLocationManager release];
    [super dealloc];
}

static LocationManager *sharedLocationManager = nil;

+ (LocationManager*) sharedManager {
    if (sharedLocationManager == nil) {
        sharedLocationManager = [[super allocWithZone:NULL] init];
    }
    return sharedLocationManager;
}

+ (id)allocWithZone:(NSZone *)zone {
    return [[self sharedLocationManager] retain];
}

- (id)copyWithZone:(NSZone *)zone {
    return self;
}

- (void) initializeManager {
	clLocationManager = [[CLLocationManager alloc] init];
	clLocationManager.delegate = self;
	clLocationManager.distanceFilter = 1000;  // 1 Km
	clLocationManager.desiredAccuracy = kCLLocationAccuracyKilometer;
	[clLocationManager startUpdatingLocation];
}

- (CLLocationCoordinate2D) getLocationCoordinates{
	return clLocationManager.location.coordinate;
}

- (void)locationManager:(CLLocationManager *)manager 
	didUpdateToLocation:(CLLocation *)newLocation fromLocation:(CLLocation *)oldLocation {
	NSLog(@"didUpdateToLocation: Lat: %.6f, Long: %.6f",newLocation.coordinate.latitude,newLocation.coordinate.longitude);
	NSLog(@"fromLocation: Lat: %.6f, Long: %.6f",oldLocation.coordinate.latitude,oldLocation.coordinate.longitude);
	[clLocationManager stopUpdatingLocation];	
}

- (void) setDistance:(int) km {
	distance = km;
}

- (int) getDistance {
	return distance;
}


- (id)retain {
    return self;
}

- (NSUInteger)retainCount {
    return NSUIntegerMax;  //denotes an object that cannot be released
}

- (void)release {
    //do nothing
}

- (id)autorelease {
    return self;
}

- (void) refresh {
	
}

- (void) setVisibilityControlled:(BOOL) value {
	visibilityControlled = value;
}

- (BOOL) getVisibilityControlled {
	if (![self isUserLocationKnown]) {
		return NO;
	}	
	return visibilityControlled;
}

- (BOOL) isUserLocationKnown {
	int lat = (int) clLocationManager.location.coordinate.latitude;
	int lon = (int)clLocationManager.location.coordinate.longitude;
	NSLog(@"isUserLocationKnown: Lat: %d, Long: %d",lat,lon);
	if ((![CLLocationManager locationServicesEnabled]) ||
		(lat == 0 && lon == 0)) {
			NSLog(@"getVisibilityControlled NO");
			return NO;
	}
	return YES;
}	

- (void)locationManager: (CLLocationManager *)manager
       didFailWithError: (NSError *)error {
	
    NSString *errorString;
    [manager stopUpdatingLocation];
    NSLog(@"Error: %@",[error localizedDescription]);
    switch([error code]) {
        case kCLErrorDenied:
            //Access denied by user
            errorString = @"Slepiesi, kad GPS išjungei?";
            //Do something...
            break;
        case kCLErrorLocationUnknown:
            //Probably temporary...
            errorString = @"Nežinau kur randiesi... hm gal Jūs sacharoje?";
            //Do something else...
            break;
        default:
            errorString = @"Kažkas nelabai gero nutiko :(";
            break;
	}

	UIAlertView *alert = [[UIAlertView alloc] initWithTitle:errorString message:@"" delegate:self cancelButtonTitle:@"Tiek to" otherButtonTitles:nil, nil];
	[alert show];
	[alert release];
}

@end
