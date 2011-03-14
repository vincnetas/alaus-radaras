//
//  LocationController.m
//  AlausRadaras
//
//  Created by Laurynas R on 2/22/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "LocationController.h"

@implementation LocationController

static LocationController *sharedInstance = nil;

+ (LocationController *) sharedManager {
    @synchronized(self) {
        if (sharedInstance = nil) {
            sharedInstance=[[LocationController alloc] init];       
		}
    }
    return sharedInstance;
}

+(id)alloc {
	return [[self sharedInstance] retain];
/*
    @synchronized(self) {
        NSAssert(sharedInstance == nil, @"Attempted to allocate a second instance of a singleton LocationController.");
        sharedInstance = [super alloc];
    }
    return sharedInstance;*/
}

//-(id) init {
//	locationManager = [[CLLocationManager alloc] init];
//    locationManager.delegate = self;
//    [self start];
//    return self;
//}

- (void) initializeLocationService {
	NSLog(@"Initailizing LocationController");
	locationManager = [[CLLocationManager alloc] init];
	locationManager.delegate = self;
	locationManager.distanceFilter = 1000;  // 1 Km
	locationManager.desiredAccuracy = kCLLocationAccuracyKilometer;
	[locationManager startUpdatingLocation];
}

-(void) getLocation {
	NSLog(@"%f", locationManager.location.coordinate.longitude);
	//return locationManager.location;
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

@end