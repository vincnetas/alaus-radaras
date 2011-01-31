//
//  TextDatabaseService.m
//  AlausRadaras
//
//  Created by Laurynas R on 1/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "TextDatabaseService.h"


@implementation TextDatabaseService

int pubPhoneIndex = 4;
int pubWebIndex = 5;
int pubLatitudeIndex = 6;
int pubLongitudeIndex = 7;

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
				pub.latitude = [values objectAtIndex:pubLatitudeIndex];
				pub.longitude = [values objectAtIndex:pubLongitudeIndex];

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

- (NSMutableArray *) getPubs {
	NSString* path = [[NSBundle mainBundle] pathForResource:@"pubs" ofType:@"txt"];
	NSString* fileContents = [NSString stringWithContentsOfFile:path usedEncoding:nil error:nil];
	NSArray *lines = [fileContents componentsSeparatedByString:@"\n"];
	NSMutableArray *pubs = [[NSMutableArray alloc]init];
	for (NSString *line in lines) {
		if (![line isEqualToString:@""]) {
			NSArray *values = [line componentsSeparatedByString:@"	"];
			Pub *pub = [[Pub alloc]init];
			
			[pub setPubId:[values objectAtIndex:0]];
			[pub setPubTitle:[values objectAtIndex:1]];
			[pub setPubAddress:[values objectAtIndex:2]];
			[pub setLatitude:[values objectAtIndex:pubLatitudeIndex]];
			[pub setLongitude:[values objectAtIndex:pubLongitudeIndex]];
			
			[pubs addObject:pub];
			[pub release]; 
		}
	}
	return pubs;
}


- (Pub *) getPubWithId:(NSString *)pubId {
	NSString* path = [[NSBundle mainBundle] pathForResource:@"pubs" ofType:@"txt"];
	NSString *fileContents = [NSString stringWithContentsOfFile:path usedEncoding:nil error:nil];
	NSArray *lines = [fileContents componentsSeparatedByString:@"\n"];

	for (NSString *line in lines) {
		if (![line isEqualToString:@""]) {
			NSArray *values = [line componentsSeparatedByString:@"	"];
			if ([pubId isEqualToString:[values objectAtIndex:0]]) {
				
				Pub *pub = [[Pub alloc]init];
				
				[pub setPubId:[values objectAtIndex:0]];
				[pub setPubTitle:[values objectAtIndex:1]];
				[pub setPubAddress:[values objectAtIndex:2]];
				[pub setPhone:[values objectAtIndex:pubPhoneIndex]];
				[pub setWebpage:[values objectAtIndex:pubWebIndex]];
				[pub setLatitude:[values objectAtIndex:pubLatitudeIndex]];
				[pub setLongitude:[values objectAtIndex:pubLongitudeIndex]];
				
				return pub;
			}
		}
	}
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

- (NSString *) getQuote:(int) beersDrank {
	NSLog(@"+getQuote beers:%i", beersDrank);
	NSString* path = [[NSBundle mainBundle] pathForResource:@"qoutes" ofType:@"txt"];
	NSString* fileContents = [NSString stringWithContentsOfFile:path usedEncoding:nil error:nil];
	NSArray *lines = [fileContents componentsSeparatedByString:@"\n"];
	NSMutableArray *quotes = [[NSMutableArray alloc]init];
	for (NSString *line in lines) {
		if (![line isEqualToString:@""]) {
			NSArray *values = [line componentsSeparatedByString:@"	"];
			if (beersDrank == [[values objectAtIndex:0]intValue]) {
				[quotes addObject:[values objectAtIndex:1]];				
			} else {
				if (quotes.count != 0) {
					break;
				}
			}
		}
	}	
	int i = (arc4random()%(quotes.count));
	NSString *quote = [quotes objectAtIndex:i];
	[quotes release];
	
	return quote;
	
}

@end
