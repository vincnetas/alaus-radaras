//
//  LocationController.h
//  AlausRadaras
//
//  Created by Laurynas R on 2/22/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <CoreLocation/CoreLocation.h>
#import <Foundation/Foundation.h>

@interface  LocationController : NSObject <CLLocationManagerDelegate> {
    CLLocationManager *locationManager;
}

+ (LocationController*) sharedManager;

-(void) initializeLocationService;
-(void) getLocation;

@end