//
//  SQLiteManager.h
//  AlausRadaras
//
//  Created by Laurynas R on 2/6/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "FMDatabase.h"
#import "FMDatabaseAdditions.h"
#import "Brand.h"
#import "CodeValue.h"
#import "FeelingLucky.h"
#import "Pub.h"
#import "Taxi.h"

@interface SQLiteManager : NSObject {
	NSString *databaseName;
	NSString *databasePath;
	FMDatabase *db;
}

+ (SQLiteManager*) updateManager;


+ (SQLiteManager*) sharedManager;
+ (id)allocWithZone:(NSZone *)zone;
- (id)copyWithZone:(NSZone *)zone;
- (id)retain;
- (NSUInteger)retainCount;
- (void)release;

- (id)autorelease;

- (void) refresh;
- (void) initializeDatabase;
- (void) copyDatabaseIfNotExists;
- (void) recreateDatabase;
- (void) createNewDatabase;

- (NSMutableArray *) getBrands;
- (NSMutableArray *) getBrandsLocationBased;
- (NSString *) getBrandsLabelById:(NSString *)brandId;

- (NSMutableArray *) getBrandsByPubId:(NSString *)pubId;

- (NSMutableArray *) getBrandsByCountry:(NSString *)country;

- (NSMutableArray *) getBrandsByTag:(NSString *)tag;

- (NSMutableArray *) getPubs;
- (Pub *) getPubById:(NSString *) pubId;
- (NSMutableArray *) getPubsByBrandId:(NSString *) brandId;

- (NSMutableArray *) getCountries;
- (NSMutableArray *) getCountriesLocationBased;

- (NSMutableArray *) getTags;
- (NSMutableArray *) getTagsLocationBased;

- (FeelingLucky *) feelingLucky;

- (NSString *) getQuote:(int) amount;

- (NSMutableArray *) getTaxies;

 - (void) insertBrands;
 - (void) insertPubs;
 - (void) insertTags;
 - (void) insertCountries;
 - (void) insertQuotes;
 - (void) insertAssociations;
 
- (void) updateBeer: (NSDictionary *) beer;
- (void) updateBrand: (NSDictionary *) brand;
- (void) updatePub: (NSDictionary *) pub;

@end
