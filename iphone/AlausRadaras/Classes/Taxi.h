//
//  Taxi.h
//  AlausRadaras
//
//  Created by Laurynas R on 3/14/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface Taxi : NSObject {
	NSString *title;
	NSString *phone; 
	NSString *city;
	double latitude;
	double longitude;
}

@property (nonatomic, retain) NSString *title;
@property (nonatomic, retain) NSString *city;
@property (nonatomic, retain) NSString *phone;
@property (nonatomic, assign) double latitude;
@property (nonatomic, assign) double longitude;

-(id) initWithTitle:(NSString *)aTitle City:(NSString *) aCity
		   Phone:(NSString *)aPhone Lat:(double) lat Long:(double) lon;

@end
