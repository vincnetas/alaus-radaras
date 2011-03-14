//
//  Pub.m
//  AlausRadaras
//
//  Created by Laurynas R on 1/26/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Pub.h"


@implementation Pub

@synthesize pubId, pubTitle, pubAddress, city, phone, webpage, latitude, longitude;
@synthesize distance;

-(id) initWithId:(NSString *) pid Title:(NSString *)title Address:(NSString *) address City:(NSString *) pubCity
		   Phone:(NSString *) phoneNumber Webpage:(NSString *) web 
			 Lat:(double) lat Long:(double) lon {
	[super init];
	pubId = pid;
	pubTitle = title;
	pubAddress = address;
	city = pubCity;
	phone = phoneNumber;
	webpage = web;
	latitude = lat;
	longitude = lon;
	return self;
}


@end
