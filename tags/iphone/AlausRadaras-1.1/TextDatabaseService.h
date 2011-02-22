//
//  TextDatabaseService.h
//  AlausRadaras
//
//  Created by Laurynas R on 1/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Pub.h"
#import "Brand.h"
#import "CodeValue.h"
#import "FeelingLucky.h"

@interface TextDatabaseService : NSObject {

}

- (NSMutableArray *) findPubsHavingBrand:(NSString *)brand;

- (NSMutableArray *) getBrandsByTag:(NSString *)tagId;
- (NSMutableArray *) getBrandsByCountry:(NSString *)countryId;
/* Returns all the brands */
- (NSMutableArray *) getBrands;
- (NSMutableArray *) getPubs;
- (Pub *) getPubWithId:(NSString *)pubId;
- (NSMutableArray *) getTags;
- (NSMutableArray *) getCountries;

- (NSString *) getQuote:(int) beersDrank;

- (FeelingLucky *) feelingLucky;

@end
