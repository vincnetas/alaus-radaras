//
//  Taxi.m
//  AlausRadaras
//
//  Created by Laurynas R on 3/14/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Taxi.h"


@implementation Taxi

@synthesize title, city, phone, latitude, longitude;

-(id) initWithTitle:(NSString *)aTitle City:(NSString *) aCity
			  Phone:(NSString *)aPhone Lat:(double) lat Long:(double) lon {
	[super init];
	title = aTitle;
	city = aCity;
	phone = aPhone;
	latitude = lat;
	longitude = lon;
	return self;
}
@end
