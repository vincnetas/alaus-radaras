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
	return visibilityControlled;
}


@end
