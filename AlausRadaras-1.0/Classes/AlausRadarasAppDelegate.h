//
//  AlausRadarasAppDelegate.h
//  AlausRadaras
//
//  Created by Laurynas R on 1/21/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "FMDatabase.h"
#import "FMDatabaseAdditions.h"
#import "AlausRadarasViewController.h"
#import "Brand.h"
#import "CodeValue.h"
#import "FeelingLucky.h"
#import "Pub.h"


@class AlausRadarasViewController;

@interface AlausRadarasAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    AlausRadarasViewController *viewController;
	
	NSString *databaseName;
	NSString *databasePath;
	FMDatabase *db;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet AlausRadarasViewController *viewController;

- (void) copyDatabaseIfNeeded;

- (void) updateDatabase;

- (NSMutableArray *) getBrands;
- (NSMutableArray *) getBrandsByPubId:(NSString *)pubId;
- (NSMutableArray *) getBrandsByCountry:(NSString *)country;
- (NSMutableArray *) getBrandsByTag:(NSString *)tag;

- (NSMutableArray *) getPubs;
- (Pub *) getPubById:(NSString *) pubId;
- (NSMutableArray *) getPubsByBrandId:(NSString *) brandId;

- (NSMutableArray *) getCountries;

- (NSMutableArray *) getTags;

- (FeelingLucky *) feelingLucky;

- (NSString *) getQuote:(int) amount;
/*
- (void) insertBrands;
- (void) insertPubs;
- (void) insertTags;
- (void) insertCountries;
- (void) insertQuotes;
- (void) insertAssociations;
*/

@end

