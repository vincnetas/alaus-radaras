//
//  TextDatabaseService.m
//  AlausRadaras
//
//  Created by Laurynas R on 1/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "TextDatabaseService.h"
#import "Pub.h"

@implementation TextDatabaseService

- (NSMutableArray *) findPubsHavingBrand:(NSString *)pubsString {
	NSLog(@"+findPubsHavingBrand: %@", pubsString);
//	NSArray *pubIds = [pubsString componentsSeparatedByString:@", "];
	
	NSString* path = [[NSBundle mainBundle] pathForResource:@"pubs" ofType:@"txt"];
	NSString* fileContents = [NSString stringWithContentsOfFile:path usedEncoding:nil error:nil];
	NSArray *lines = [fileContents componentsSeparatedByString:@"\n"];
	NSMutableArray *pubs = [[NSMutableArray alloc]init];
	for (NSString *line in lines) {
		if (![line isEqualToString:@""]) {
			NSArray *values = [line componentsSeparatedByString:@"	"];
			
			if ([pubsString rangeOfString:[values objectAtIndex:0]].location != NSNotFound){
				Pub *pub = [[Pub alloc]init];
				
				[pub setPubId:[values objectAtIndex:0]];
				[pub setPubTitle:[values objectAtIndex:1]];
				[pub setPubAddress:[values objectAtIndex:2]];
				pub.latitude = [values objectAtIndex:5];
				pub.longitude = [values objectAtIndex:6];

				[pubs addObject:pub];
				[pub release]; // realising cause navigation problems 
			}
		}
	}
	
	return pubs;
}


@end
