//
//  Pub.h
//  AlausRadaras
//
//  Created by Laurynas R on 1/26/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface Pub : NSObject {
	NSString *pubId;
	NSString *pubTitle; 
	NSString *pubAddress;
	NSString *city;
	NSString *phone;
	NSString *webpage;
	double latitude;
	double longitude;
	double distance;

}

@property (nonatomic, retain) NSString *pubId;
@property (nonatomic, retain) NSString *pubTitle;
@property (nonatomic, retain) NSString *pubAddress;
@property (nonatomic, retain) NSString *city;
@property (nonatomic, retain) NSString *phone;
@property (nonatomic, retain) NSString *webpage;
@property (nonatomic, assign) double latitude;
@property (nonatomic, assign) double longitude;
@property (nonatomic, assign) double distance;


-(id) initWithId:(NSString *)pubId Title:(NSString *)pubTitle Address:(NSString *) pubAddress City:(NSString *) pubCity
		   Phone:(NSString *)phone Webpage:(NSString *) webpage Lat:(double) latitude Long:(double) longitude;

@end
