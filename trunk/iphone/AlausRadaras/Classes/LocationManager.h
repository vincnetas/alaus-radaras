//
//  LocationManager.h
//  AlausRadaras
//
//  Created by Laurynas R on 2/22/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreLocation/CoreLocation.h>


@interface LocationManager : NSObject <CLLocationManagerDelegate> {
    CLLocationManager *clLocationManager;
	int distance;
	BOOL visibilityControlled;
}

+ (LocationManager*) sharedManager;

+ (id)allocWithZone:(NSZone *)zone;
- (id)copyWithZone:(NSZone *)zone;
- (id)retain;
- (void)release;
- (id)autorelease;
- (void) refresh;
- (void) initializeManager;

- (CLLocationCoordinate2D) getLocationCoordinates;
- (void) setDistance:(int) km;
- (int) getDistance;
- (void) setVisibilityControlled:(BOOL) value;
- (BOOL) getVisibilityControlled;

@end
