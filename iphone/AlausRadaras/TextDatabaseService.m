//
//  TextDatabaseService.m
//  AlausRadaras
//
//  Created by Laurynas R on 1/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "TextDatabaseService.h"
#import "Pub.h"
#import "Brand.h"
#import "CodeValue.h"

@implementation TextDatabaseService


// Rename
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

/*
 Returs NSMutableArray of type Brands by Tag Id
 */
- (NSMutableArray *) getBrandsByTag:(NSString *)tagId {
	NSLog(@"+getBrandsByTag: %@", tagId);
	
	NSString* path = [[NSBundle mainBundle] pathForResource:@"brands" ofType:@"txt"];
	NSString* fileContents = [NSString stringWithContentsOfFile:path usedEncoding:nil error:nil];
	NSArray *lines = [fileContents componentsSeparatedByString:@"\n"];
	NSMutableArray *brands = [[NSMutableArray alloc]init];
	for (NSString *line in lines) {
		if (![line isEqualToString:@""]) {
			NSArray *values = [line componentsSeparatedByString:@"	"];
			
			if ([[values objectAtIndex:4] rangeOfString:tagId].location != NSNotFound){
				Brand *brand = [[Brand alloc]init];
				[brand setIcon:[NSString stringWithFormat:@"brand_%@.png", [values objectAtIndex:0]]];
				[brand setLabel:[values objectAtIndex:1]];
				[brand setPubsString:[values objectAtIndex:2]];
				
				[brands addObject:brand];
				[brand release];
			}
		}
	}
	
	return brands;
}

/*
 Returs NSMutableArray of type Brands by Tag Id
 */
- (NSMutableArray *) getBrandsByCountry:(NSString *)countryId {
	NSLog(@"+getBrandsByCountry: %@", countryId);
	
	NSString* path = [[NSBundle mainBundle] pathForResource:@"brands" ofType:@"txt"];
	NSString* fileContents = [NSString stringWithContentsOfFile:path usedEncoding:nil error:nil];
	NSArray *lines = [fileContents componentsSeparatedByString:@"\n"];
	NSMutableArray *brands = [[NSMutableArray alloc]init];
	for (NSString *line in lines) {
		if (![line isEqualToString:@""]) {
			NSArray *values = [line componentsSeparatedByString:@"	"];
			
			if ([[values objectAtIndex:3] rangeOfString:countryId].location != NSNotFound){
				Brand *brand = [[Brand alloc]init];
				
				[brand setIcon:[NSString stringWithFormat:@"brand_%@.png", [values objectAtIndex:0]]];
				[brand setLabel:[values objectAtIndex:1]];
				[brand setPubsString:[values objectAtIndex:2]];
				
				[brands addObject:brand];
				[brand release];
			}
		}
	}
	return brands;
}



/*
 */
- (NSMutableArray *) getTags {
	NSLog(@"+getTags");
	
	NSString* path = [[NSBundle mainBundle] pathForResource:@"tags" ofType:@"txt"];
	NSString* fileContents = [NSString stringWithContentsOfFile:path usedEncoding:nil error:nil];
	NSArray *lines = [fileContents componentsSeparatedByString:@"\n"];
	NSMutableArray *tags = [[NSMutableArray alloc]init];
	for (NSString *line in lines) {
		if (![line isEqualToString:@""]) {
			NSArray *values = [line componentsSeparatedByString:@"	"];
			
		
			CodeValue *item = [[CodeValue alloc]init];
			[item setCode:[values objectAtIndex:0]];
			[item setDisplayValue:[values objectAtIndex:1]];
	
			[tags addObject:item];
			[item release];
			
		}
	}
	return tags;
}

/*
 */
- (NSMutableArray *) getCountries {
	NSLog(@"+getCountries");
	
	NSString* path = [[NSBundle mainBundle] pathForResource:@"countries" ofType:@"txt"];
	NSString* fileContents = [NSString stringWithContentsOfFile:path usedEncoding:nil error:nil];
	NSArray *lines = [fileContents componentsSeparatedByString:@"\n"];
	NSMutableArray *tags = [[NSMutableArray alloc]init];
	for (NSString *line in lines) {
		if (![line isEqualToString:@""]) {
			NSArray *values = [line componentsSeparatedByString:@"	"];
			
			CodeValue *item = [[CodeValue alloc]init];
			[item setCode:[values objectAtIndex:0]];
			[item setDisplayValue:[values objectAtIndex:1]];
			
			[tags addObject:item];
			[item release];
			
		}
	}
	return tags;
}

@end
