//
//  AlausRadarasAppDelegate.m
//  AlausRadaras
//
//  Created by Laurynas R on 1/21/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "AlausRadarasAppDelegate.h"

@implementation AlausRadarasAppDelegate

@synthesize window;
@synthesize viewController;

- (void)dealloc {
	[db release];
    [viewController release];
    [window release];
    [super dealloc];
}


#pragma mark -
#pragma mark Application lifecycle

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {

	databaseName = @"beer-radar-db.sqlite";
	NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory , NSUserDomainMask, YES);
	databasePath = [[paths objectAtIndex:0] stringByAppendingPathComponent:databaseName];

	NSFileManager *fileManager = [NSFileManager defaultManager];
	[fileManager removeItemAtPath:databasePath error:nil];

	[self copyDatabaseIfNeeded];

	NSLog(@"Database: %@",databasePath);
	db = [FMDatabase databaseWithPath:databasePath];
	
	// Dont autorelease the database, retain it instead.
	// Might cause memory problems - if so try refactoring
	[db retain];
	[db setLogsErrors:TRUE];
	[db setTraceExecution:TRUE];
    //[db setShouldCacheStatements:YES];	// kind of experimentalish.
	
    if (![db open]) {
        NSLog(@"Could not open db.");
		return;
    } else {
		NSLog(@"Database opened");
	}
	
	//TODO check if database update is needed
	/* UPDATE DATABASE */
	//[self updateDatabase];

	if(FALSE){
		[db beginTransaction];
		NSString* path = [[NSBundle mainBundle] pathForResource:@"brands" ofType:@"txt"];
		NSString* fileContents = [NSString stringWithContentsOfFile:path usedEncoding:nil error:nil];
		NSArray *lines = [fileContents componentsSeparatedByString:@"\n"];
		for (NSString *line in lines) {
			if (![line isEqualToString:@""]) {
				NSArray *values = [line componentsSeparatedByString:@"\t"];
				[db executeUpdate:@"insert into brands (brandId, label, icon) values (?, ?, ?)",
				 [values objectAtIndex:0],
				 [values objectAtIndex:1],
				 [NSString stringWithFormat:@"brand_%@.png", [values objectAtIndex:0]]];
			}
		}
		[db commit];
	
	
		[db beginTransaction];
		path = [[NSBundle mainBundle] pathForResource:@"pubs" ofType:@"txt"];
		fileContents = [NSString stringWithContentsOfFile:path usedEncoding:nil error:nil];
		lines = [fileContents componentsSeparatedByString:@"\n"];
		for (NSString *line in lines) {
			if (![line isEqualToString:@""]) {
				NSArray *values = [line componentsSeparatedByString:@"\t"];
				[db executeUpdate:@"insert into pubs (pubId, pubTitle, pubAddress, city, phone, webpage, latitude, longitude) values (?, ?, ?, ?, ?, ?, ?, ?)",
				 [values objectAtIndex:0],
				 [values objectAtIndex:1],
				 [values objectAtIndex:2],
				 [values objectAtIndex:3],
				 [values objectAtIndex:4],
				 [values objectAtIndex:5],
				 [NSNumber numberWithDouble:[[values objectAtIndex:6]doubleValue]],
				 [NSNumber numberWithDouble:[[values objectAtIndex:7]doubleValue]]];
			}
		}
		[db commit];	
	
	
		[db beginTransaction];
		 path = [[NSBundle mainBundle] pathForResource:@"tags" ofType:@"txt"];
		fileContents = [NSString stringWithContentsOfFile:path usedEncoding:nil error:nil];
		lines = [fileContents componentsSeparatedByString:@"\n"];
		for (NSString *line in lines) {
			if (![line isEqualToString:@""]) {
				NSArray *values = [line componentsSeparatedByString:@"\t"];
				[db executeUpdate:@"insert into tags (code, title) values (?, ?)",
				 [values objectAtIndex:0],
				 [values objectAtIndex:1]];
			}
		}
		[db commit];
	
	
		[db beginTransaction];
		path = [[NSBundle mainBundle] pathForResource:@"countries" ofType:@"txt"];
		fileContents = [NSString stringWithContentsOfFile:path usedEncoding:nil error:nil];
		lines = [fileContents componentsSeparatedByString:@"\n"];
		for (NSString *line in lines) {
			if (![line isEqualToString:@""]) {
				NSArray *values = [line componentsSeparatedByString:@"\t"];
				[db executeUpdate:@"insert into countries (code, name) values (?, ?)",
				 [values objectAtIndex:0],
				 [values objectAtIndex:1]];
			}
		}
		[db commit];
	
	
		[db beginTransaction];
		path = [[NSBundle mainBundle] pathForResource:@"qoutes" ofType:@"txt"];
		fileContents = [NSString stringWithContentsOfFile:path usedEncoding:nil error:nil];
		lines = [fileContents componentsSeparatedByString:@"\n"];
		for (NSString *line in lines) {
			if (![line isEqualToString:@""]) {
				NSArray *values = [line componentsSeparatedByString:@"\t"];
				[db executeUpdate:@"insert into quotes (amount, text) values (?, ?)",
				 [NSNumber numberWithInt:[[values objectAtIndex:0]intValue]],
				 [values objectAtIndex:1]];
			}
		}
		[db commit];
	
	
		[db beginTransaction];
		path = [[NSBundle mainBundle] pathForResource:@"brands" ofType:@"txt"];
		fileContents = [NSString stringWithContentsOfFile:path usedEncoding:nil error:nil];
		lines = [fileContents componentsSeparatedByString:@"\n"];
		for (NSString *line in lines) {
			if (![line isEqualToString:@""]) {
				NSArray *values = [line componentsSeparatedByString:@"\t"];
				
				NSLog(@"Inserting brand<->pub association: %@", [values objectAtIndex:0]);
				NSArray *pubIds = [[values objectAtIndex:2] componentsSeparatedByString:@","];
				for (NSString *pubId in pubIds) {
					[db executeUpdate:@"insert into pubs_brands (brand_id, pub_id) values (?, ?)",
					 [values objectAtIndex:0],
					 [pubId stringByReplacingOccurrencesOfString:@" " withString:@""]];			
				}
				
				NSLog(@"Inserting brand<->country association: %@", [values objectAtIndex:0]);
				NSArray *countries = [[values objectAtIndex:3] componentsSeparatedByString:@","];
				for (NSString *country in countries) {
					[db executeUpdate:@"insert into brands_countries (brand_id, country) values (?, ?)",
					 [values objectAtIndex:0],
					 [country stringByReplacingOccurrencesOfString:@" " withString:@""]];			
				}
				
				NSLog(@"Inserting brand<->tag association: %@", [values objectAtIndex:0]);
				NSArray *tags = [[values objectAtIndex:4] componentsSeparatedByString:@","];
				for (NSString *tag in tags) {
					[db executeUpdate:@"insert into brands_tags (brand_id, tag) values (?, ?)",
					 [values objectAtIndex:0],
					 [tag stringByReplacingOccurrencesOfString:@" " withString:@""]];			
				}
			}
		}
		[db commit];
	}
	
	
	
	// Override point for customization after application launch.
	[application setStatusBarStyle:UIStatusBarStyleBlackOpaque];
    
	// Add the view controller's view to the window and display.
    [self.window addSubview:viewController.view];
    [self.window makeKeyAndVisible];

    return YES;
}


#pragma mark -
#pragma mark Database stuff


- (void) copyDatabaseIfNeeded {
	//Using NSFileManager we can perform many file system operations.
	NSFileManager *fileManager = [NSFileManager defaultManager];
	NSError *error;
	BOOL success = [fileManager fileExistsAtPath:databasePath]; 
	
	if(!success) {
		NSLog(@"Copying database");
		NSString *defaultDBPath = [[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent:@"beer-radar-db.sqlite"];
		success = [fileManager copyItemAtPath:defaultDBPath toPath:databasePath error:&error];
		
		if (!success) 
			NSAssert1(0, @"Failed to create writable database file with message '%@'.", [error localizedDescription]);
	} else {
		NSLog(@"Database exists");	
	}
}


- (void) updateDatabase {
	/* delete the old db. */
	NSFileManager *fileManager = [NSFileManager defaultManager];
	[fileManager removeItemAtPath:databasePath error:nil];
	 
	/* Copy database if needed */
	[self copyDatabaseIfNeeded];

	NSLog(@"Creating initial database");
	
	NSLog(@"Finished creating database.");
	
	NSLog(@"Inserting initial data");
	[self insertBrands];
	[self insertPubs];
	[self insertTags];
	[self insertCountries];
	[self insertQuotes];
	[self insertAssociations];
	NSLog(@"Finished inserting initial data");
}



#pragma mark -
#pragma mark Brand

- (NSMutableArray *) getBrands {
	NSMutableArray *result = [[NSMutableArray alloc]init];
	FMResultSet *rs = 
		[db executeQuery:@"select * from brands"];
	while ([rs next]) {
		Brand *brand = [[Brand alloc] init];
		brand.brandId = [rs stringForColumn:@"brandId"];
		brand.icon = [rs stringForColumn:@"icon"];
		brand.label = [rs stringForColumn:@"label"];
		[result addObject:brand];
		[brand release];
	}
	return result;
}

- (NSMutableArray *) getBrandsByPubId:(NSString *)pubId {
	NSMutableArray *result = [[NSMutableArray alloc]init];
	FMResultSet *rs = 
		[db executeQuery:@"SELECT * FROM brands b INNER JOIN pubs_brands pb ON b.brandId = pb.brand_id AND pb.pub_id = ?", 
			pubId];
	while ([rs next]) {
		Brand *brand = [[Brand alloc] init];
		brand.brandId = [rs stringForColumn:@"brandId"];
		brand.icon = [rs stringForColumn:@"icon"];
		brand.label = [rs stringForColumn:@"label"];
		[result addObject:brand];
		[brand release];
	}
	return result;
}

- (NSMutableArray *) getBrandsByCountry:(NSString *)country {
	NSMutableArray *result = [[NSMutableArray alloc]init];
	FMResultSet *rs = 
			[db executeQuery:@"SELECT * FROM brands b INNER JOIN brands_countries bc ON b.brandId = bc.brand_id AND bc.country = ?", 
				country];
	while ([rs next]) {
		Brand *brand = [[Brand alloc] init];
		brand.brandId = [rs stringForColumn:@"brandId"];
		brand.icon = [rs stringForColumn:@"icon"];
		brand.label = [rs stringForColumn:@"label"];
		[result addObject:brand];
		[brand release];
	}
	return result;
}

- (NSMutableArray *) getBrandsByTag:(NSString *)tag {
	NSMutableArray *result = [[NSMutableArray alloc]init];
	FMResultSet *rs = 
		[db executeQuery:@"SELECT * FROM brands b INNER JOIN brands_tags bt ON b.brandId = bt.brand_id AND bt.tag = ?",
			tag];
	while ([rs next]) {
		Brand *brand = [[Brand alloc] init];
		brand.brandId = [rs stringForColumn:@"brandId"];
		brand.icon = [rs stringForColumn:@"icon"];
		brand.label = [rs stringForColumn:@"label"];
		[result addObject:brand];
		[brand release];
	}
	return result;
}


#pragma mark -
#pragma mark Pub

- (NSMutableArray *) getPubs {
	NSMutableArray *result = [[NSMutableArray alloc]init];
	FMResultSet *rs = 
	[db executeQuery:@"SELECT * FROM pubs"];
    while ([rs next]) {
        Pub* pub = [[Pub alloc] initWithId:[rs stringForColumn:@"pubId"]
									 Title:[rs stringForColumn:@"pubTitle"]
								   Address:[rs stringForColumn:@"pubAddress"] 
									  City: [rs stringForColumn:@"city"]
									 Phone:[rs stringForColumn:@"phone"]
								   Webpage:[rs stringForColumn:@"webpage"]
									   Lat:[rs doubleForColumn:@"latitude"]
									  Long:[rs doubleForColumn:@"longitude"]];
		[result addObject:pub];
		[pub release];
    }
	
	return result;
}

- (Pub *) getPubById:(NSString *) pubId {
	FMResultSet *rs = 
		[db executeQuery:@"SELECT * FROM pubs WHERE pubId = ?", pubId];
    while ([rs next]) {
        Pub* pub = [[Pub alloc] initWithId:[rs stringForColumn:@"pubId"]
									 Title:[rs stringForColumn:@"pubTitle"]
								   Address:[rs stringForColumn:@"pubAddress"] 
									  City: [rs stringForColumn:@"city"]
									 Phone:[rs stringForColumn:@"phone"]
								   Webpage:[rs stringForColumn:@"webpage"]
									   Lat:[rs doubleForColumn:@"latitude"]
									  Long:[rs doubleForColumn:@"longitude"]];
		return pub;		
    }
	return nil;
}

- (NSMutableArray *) getPubsByBrandId:(NSString *) brandId {
	NSMutableArray *result = [[NSMutableArray alloc]init];

	FMResultSet *rs = 
		[db executeQuery:@"SELECT * FROM pubs p INNER JOIN pubs_brands pb ON p.pubId = pb.pub_id AND pb.brand_id = ?",
						brandId];
    while ([rs next]) {
        Pub* pub = [[Pub alloc] initWithId:[rs stringForColumn:@"pubId"]
								Title:[rs stringForColumn:@"pubTitle"]
								Address:[rs stringForColumn:@"pubAddress"] 
								City: [rs stringForColumn:@"city"]
								Phone:[rs stringForColumn:@"phone"]
								Webpage:[rs stringForColumn:@"webpage"]
								Lat:[rs doubleForColumn:@"latitude"]
								Long:[rs doubleForColumn:@"longitude"]];
		[result addObject:pub];
		[pub release];
    }
	
	return result;
}


#pragma mark -
#pragma mark Tag

- (NSMutableArray *) getTags {
	NSMutableArray *result = [[NSMutableArray alloc]init];
	FMResultSet *rs = 
	[db executeQuery:@"select * from tags ORDER BY title asc"];
	while ([rs next]) {
		CodeValue *item = [[CodeValue alloc] init];
		item.code = [rs stringForColumn:@"code"];
		item.displayValue = [rs stringForColumn:@"title"];
		[result addObject:item];
		[item release];
	}
	return result;
}

#pragma mark -
#pragma mark Country

- (NSMutableArray *) getCountries {
	NSMutableArray *result = [[NSMutableArray alloc]init];
	FMResultSet *rs = 
	[db executeQuery:@"select * from countries ORDER BY name asc"];
	while ([rs next]) {
		CodeValue *item = [[CodeValue alloc] init];
		item.code = [rs stringForColumn:@"code"];
		item.displayValue = [rs stringForColumn:@"name"];
		[result addObject:item];
		[item release];
	}
	return result;
}

#pragma mark -
#pragma mark Feeling Lucky

- (FeelingLucky *) feelingLucky {
	FMResultSet *rs = 
		[db executeQuery:@"SELECT * FROM pubs p INNER JOIN pubs_brands pb ON p.pubId = pb.pub_id ORDER BY RANDOM() LIMIT 1"];

	FeelingLucky *lucky = [[FeelingLucky alloc]init];
	while ([rs next]) {
		Pub* pub = [[Pub alloc] initWithId:[rs stringForColumn:@"pubId"]
									 Title:[rs stringForColumn:@"pubTitle"]
								   Address:[rs stringForColumn:@"pubAddress"] 
									  City: [rs stringForColumn:@"city"]
									 Phone:[rs stringForColumn:@"phone"]
								   Webpage:[rs stringForColumn:@"webpage"]
									   Lat:[rs doubleForColumn:@"latitude"]
									  Long:[rs doubleForColumn:@"longitude"]];
		NSMutableArray *brands = [self getBrandsByPubId:pub.pubId];
		int i = (arc4random()%(brands.count));
		Brand *brand = [brands objectAtIndex:i];
		lucky.pub = pub;
		lucky.brand = brand;
		return lucky;
	}
}

#pragma mark -
#pragma mark Quote

- (NSString *) getQuote:(int) amount {
	FMResultSet *rs = 
		[db executeQuery:@"SELECT text FROM quotes q WHERE q.amount = ? ORDER BY RANDOM() LIMIT 1",
		 [NSNumber numberWithInt:amount]];
	while ([rs next]) {
		return [rs stringForColumn:@"text"];
	}
	return nil;
}





#pragma mark -
#pragma mark Initial data inserts

- (void) insertBrands {
    [db beginTransaction];
	NSString* path = [[NSBundle mainBundle] pathForResource:@"brands" ofType:@"txt"];
	NSString* fileContents = [NSString stringWithContentsOfFile:path usedEncoding:nil error:nil];
	NSArray *lines = [fileContents componentsSeparatedByString:@"\n"];
	for (NSString *line in lines) {
		if (![line isEqualToString:@""]) {
			NSArray *values = [line componentsSeparatedByString:@"\t"];
			[db executeUpdate:@"insert into brands (brandId, label, icon) values (?, ?, ?)",
				[values objectAtIndex:0],
				[values objectAtIndex:1],
				[NSString stringWithFormat:@"brand_%@.png", [values objectAtIndex:0]]];
		}
	}
    [db commit];
}

- (void) insertPubs {
    [db beginTransaction];
	NSString* path = [[NSBundle mainBundle] pathForResource:@"pubs" ofType:@"txt"];
	NSString* fileContents = [NSString stringWithContentsOfFile:path usedEncoding:nil error:nil];
	NSArray *lines = [fileContents componentsSeparatedByString:@"\n"];
	for (NSString *line in lines) {
		if (![line isEqualToString:@""]) {
			NSArray *values = [line componentsSeparatedByString:@"\t"];
			[db executeUpdate:@"insert into pubs (pubId, pubTitle, pubAddress, city, phone, webpage, latitude, longitude) values (?, ?, ?, ?, ?, ?, ?, ?)",
			 [values objectAtIndex:0],
			 [values objectAtIndex:1],
			 [values objectAtIndex:2],
			 [values objectAtIndex:3],
			 [values objectAtIndex:4],
			 [values objectAtIndex:5],
			 [NSNumber numberWithDouble:[[values objectAtIndex:6]doubleValue]],
			 [NSNumber numberWithDouble:[[values objectAtIndex:7]doubleValue]]];
		}
	}
    [db commit];	
}

- (void) insertTags {
    [db beginTransaction];
	NSString* path = [[NSBundle mainBundle] pathForResource:@"tags" ofType:@"txt"];
	NSString* fileContents = [NSString stringWithContentsOfFile:path usedEncoding:nil error:nil];
	NSArray *lines = [fileContents componentsSeparatedByString:@"\n"];
	for (NSString *line in lines) {
		if (![line isEqualToString:@""]) {
			NSArray *values = [line componentsSeparatedByString:@"\t"];
			[db executeUpdate:@"insert into tags (code, title) values (?, ?)",
			 [values objectAtIndex:0],
			 [values objectAtIndex:1]];
		}
	}
    [db commit];
}

- (void) insertCountries {
    [db beginTransaction];
	NSString* path = [[NSBundle mainBundle] pathForResource:@"countries" ofType:@"txt"];
	NSString* fileContents = [NSString stringWithContentsOfFile:path usedEncoding:nil error:nil];
	NSArray *lines = [fileContents componentsSeparatedByString:@"\n"];
	for (NSString *line in lines) {
		if (![line isEqualToString:@""]) {
			NSArray *values = [line componentsSeparatedByString:@"\t"];
			[db executeUpdate:@"insert into countries (code, name) values (?, ?)",
			 [values objectAtIndex:0],
			 [values objectAtIndex:1]];
		}
	}
    [db commit];
}

- (void) insertQuotes {
    [db beginTransaction];
	NSString* path = [[NSBundle mainBundle] pathForResource:@"qoutes" ofType:@"txt"];
	NSString* fileContents = [NSString stringWithContentsOfFile:path usedEncoding:nil error:nil];
	NSArray *lines = [fileContents componentsSeparatedByString:@"\n"];
	for (NSString *line in lines) {
		if (![line isEqualToString:@""]) {
			NSArray *values = [line componentsSeparatedByString:@"\t"];
			[db executeUpdate:@"insert into quotes (amount, text) values (?, ?)",
			 [NSNumber numberWithInt:[[values objectAtIndex:0]intValue]],
			 [values objectAtIndex:1]];
		}
	}
    [db commit];
}

- (void) insertAssociations {
	[db beginTransaction];
	NSString* path = [[NSBundle mainBundle] pathForResource:@"brands" ofType:@"txt"];
	NSString* fileContents = [NSString stringWithContentsOfFile:path usedEncoding:nil error:nil];
	NSArray *lines = [fileContents componentsSeparatedByString:@"\n"];
	for (NSString *line in lines) {
		if (![line isEqualToString:@""]) {
			NSArray *values = [line componentsSeparatedByString:@"\t"];
	
			NSLog(@"Inserting brand<->pub association: %@", [values objectAtIndex:0]);
			NSArray *pubIds = [[values objectAtIndex:2] componentsSeparatedByString:@","];
			for (NSString *pubId in pubIds) {
				[db executeUpdate:@"insert into pubs_brands (brand_id, pub_id) values (?, ?)",
				 [values objectAtIndex:0],
				 [pubId stringByReplacingOccurrencesOfString:@" " withString:@""]];			
			}
			
			NSLog(@"Inserting brand<->country association: %@", [values objectAtIndex:0]);
			NSArray *countries = [[values objectAtIndex:3] componentsSeparatedByString:@","];
			for (NSString *country in countries) {
				[db executeUpdate:@"insert into brands_countries (brand_id, country) values (?, ?)",
				 [values objectAtIndex:0],
				 [country stringByReplacingOccurrencesOfString:@" " withString:@""]];			
			}
			
			NSLog(@"Inserting brand<->tag association: %@", [values objectAtIndex:0]);
			NSArray *tags = [[values objectAtIndex:4] componentsSeparatedByString:@","];
			for (NSString *tag in tags) {
				[db executeUpdate:@"insert into brands_tags (brand_id, tag) values (?, ?)",
				 [values objectAtIndex:0],
				 [tag stringByReplacingOccurrencesOfString:@" " withString:@""]];			
			}
		}
	}
    [db commit];
}



#pragma mark -
#pragma mark Memory management

- (void)applicationDidReceiveMemoryWarning:(UIApplication *)application {
	/*
     Free up as much memory as possible by purging cached data objects that can be recreated (or reloaded from disk) later.
     */
	NSLog(@"AlausRadarasAppDelegate: Recieved a Memory Warning... Oooops..");
	
}


#pragma mark -
#pragma mark Other methods



- (void)applicationWillResignActive:(UIApplication *)application {
    /*
     Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
     Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
     */
}


- (void)applicationDidEnterBackground:(UIApplication *)application {
    /*
     Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
     If your application supports background execution, called instead of applicationWillTerminate: when the user quits.
     */
}


- (void)applicationWillEnterForeground:(UIApplication *)application {
    /*
     Called as part of  transition from the background to the inactive state: here you can undo many of the changes made on entering the background.
     */
}


- (void)applicationDidBecomeActive:(UIApplication *)application {
    /*
     Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
     */
}


- (void)applicationWillTerminate:(UIApplication *)application {
    /*
     Called when the application is about to terminate.
     See also applicationDidEnterBackground:.
     */
}






@end

