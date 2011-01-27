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
	NSString *phone;
	NSString *webpage;
	NSString *latitude;
	NSString *longitude;
}

@property (nonatomic, retain) NSString *pubId;
@property (nonatomic, retain) NSString *pubTitle;
@property (nonatomic, retain) NSString *pubAddress;
@property (nonatomic, retain) NSString *phone;
@property (nonatomic, retain) NSString *webpage;
@property (nonatomic, retain) NSString *latitude;
@property (nonatomic, retain) NSString *longitude;

@end
