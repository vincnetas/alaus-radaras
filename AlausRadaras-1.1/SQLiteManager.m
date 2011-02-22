//
//  SQLiteManager.m
//  AlausRadaras
//
//  Created by Laurynas R on 2/6/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "SQLiteManager.h"


@implementation SQLiteManager

- (void)dealloc {
	[db release];
    [super dealloc];
}

static SQLiteManager *sharedSQLiteManager = nil;

+ (SQLiteManager*) sharedManager {
    if (sharedSQLiteManager == nil) {
        sharedSQLiteManager = [[super allocWithZone:NULL] init];
    }
    return sharedSQLiteManager;
}

+ (id)allocWithZone:(NSZone *)zone {
    return [[self sharedSQLiteManager] retain];
}

- (id)copyWithZone:(NSZone *)zone {
    return self;
}

- (id)retain {
    return self;
}

- (NSUInteger)retainCount {
    return NSUIntegerMax;  //denotes an object that cannot be released
}

- (void)release {
    //do nothing
}

- (id)autorelease {
    return self;
}

- (void) refresh {
	[db close];
	[db open];
}

- (void) initializeDatabase {
	databaseName = @"beer-radar-db.sqlite";
	NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory , NSUserDomainMask, YES);
	databasePath = [[paths objectAtIndex:0] stringByAppendingPathComponent:databaseName];
	
	NSLog(@"Using Database at: %@",databasePath);
	db = [FMDatabase databaseWithPath:databasePath];
	
	// Dont autorelease the database, retain it instead.
	// Might cause memory problems - if so try refactoring
	[db retain];
	[db setLogsErrors:TRUE];
	[db setTraceExecution:FALSE];
    //[db setShouldCacheStatements:YES];	// kind of experimentalish.
	
    if (![db open]) {
        NSLog(@"Could not open db.");
		return;
    } else {
		NSLog(@"Database opened");
	}

	/*
	//	Inserts go here
	NSLog(@"Inserting data");
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
				tag = [tag stringByReplacingOccurrencesOfString:@" " withString:@""];
				tag = [tag stringByReplacingOccurrencesOfString:@"\n" withString:@""];
				[db executeUpdate:@"insert into brands_tags (brand_id, tag) values (?, ?)",
				 [values objectAtIndex:0],
				 tag];                       
			}
		}
	}
	[db commit];
	NSLog(@"Data inserted");
	*/
}


#pragma mark -
#pragma mark Database stuff

/*
 * Database is copied from application bundle to documents directory
 * With all the data inserted
 */

- (void) copyDatabaseIfNotExists {
	/* Copy database if needed */
	NSError *error;
	NSFileManager *fileManager = [NSFileManager defaultManager];
	BOOL success = [fileManager fileExistsAtPath:databasePath]; 
	
	if(!success) {
		NSLog(@"Database not found, copying the NEW database");
		NSString *defaultDBPath = [[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent:databaseName];
		success = [fileManager copyItemAtPath:defaultDBPath toPath:databasePath error:&error];
		if (!success) 
			NSAssert1(0, @"Failed to create writable database file with message '%@'.", [error localizedDescription]);
	} else {
		NSLog(@"Database exists");	
	}
}

- (void) recreateDatabase {
	/* delete the old db. */
	NSLog(@"Removing the OLD database");
	NSFileManager *fileManager = [NSFileManager defaultManager];
	[fileManager removeItemAtPath:databasePath error:nil];
	
	/* Copy database if needed */
	NSError *error;
	BOOL success = [fileManager fileExistsAtPath:databasePath]; 
	
	if(!success) {
		NSLog(@"Copying the NEW database");
		NSString *defaultDBPath = [[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent:databaseName];
		success = [fileManager copyItemAtPath:defaultDBPath toPath:databasePath error:&error];
		if (!success) 
			NSAssert1(0, @"Failed to create writable database file with message '%@'.", [error localizedDescription]);
	} else {
		NSLog(@"Database exists");	
	}
}

/*
 * Database is created from scratch
 * Scheme & data are created/inserted
 */
- (void) createNewDatabase {
	/*
	NSLog(@"Creating new database");
	NSLog(@"Droping tables");
	[db executeUpdate:@"DROP TABLE IF EXISTS pubs_brands;"];
	[db executeUpdate:@"DROP TABLE IF EXISTS brands_countries;"];	
	[db executeUpdate:@"DROP TABLE IF EXISTS brands_tags;"];	
	[db executeUpdate:@"DROP TABLE IF EXISTS quotes;"];	
	[db executeUpdate:@"DROP TABLE IF EXISTS pubs;"];
	[db executeUpdate:@"DROP TABLE IF EXISTS brands;"];
	[db executeUpdate:@"DROP TABLE IF EXISTS tags;"];
	[db executeUpdate:@"DROP TABLE IF EXISTS countries;"];
	NSLog(@"Tables droped");
	NSLog(@"Creating tables");
	[db executeUpdate:@"CREATE TABLE pubs("
									   "pubId 		TEXT PRIMARY KEY, "
									   "pubTitle 		TEXT NOT NULL, "
									   "longitude DOUBLE NOT NULL, "
									   "latitude 	DOUBLE NOT NULL, "
									   "pubAddress 	TEXT, "
									   "city	 	TEXT, "
									   "phone	 	TEXT, "
									   "webpage	 	TEXT);"];

	[db executeUpdate:@"CREATE TABLE brands("
										 "brandId 			TEXT PRIMARY KEY, "
										 "label 			TEXT NOT NULL, "
										 "icon			TEXT);"];
	

	[db executeUpdate:@"CREATE TABLE pubs_brands("
										 "pub_id			TEXT NOT NULL, "
										 "brand_id 		TEXT NOT NULL);"];
	
	[db executeUpdate:@"CREATE TABLE countries("
										 "code			TEXT NOT NULL," 
										 "name			TEXT NOT NULL);"];
	

	[db executeUpdate:@"CREATE TABLE brands_countries("
										 "brand_id			TEXT NOT NULL,"
										 "country			TEXT NOT NULL);"];
	
	
	[db executeUpdate:@"CREATE TABLE tags("
										 "code			TEXT NOT NULL,"
										 "title			TEXT NOT NULL);"];
	
	[db executeUpdate:@"CREATE TABLE brands_tags("
										 "brand_id			TEXT NOT NULL,"
										 "tag				TEXT NOT NULL);"];

	
	[db executeUpdate:@"CREATE TABLE quotes("
										 "amount			INTEGER NOT NULL,"
										 "text			TEXT NOT NULL);"];
	*/
	NSLog(@"Tables created");
	NSLog(@"Inserting data");
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
	NSLog(@"Data inserted");
}



#pragma mark -
#pragma mark Brand

- (NSMutableArray *) getBrands {
	NSMutableArray *result = [[NSMutableArray alloc]init];
	FMResultSet *rs = 
	[db executeQuery:@"select * from brands"];
	while ([rs next]) {
		Brand *brand = [[Brand alloc] init];
		brand.brandId = [[rs stringForColumn:@"brandId"]copy];
		brand.icon = [[rs stringForColumn:@"icon"]copy];
		brand.label = [[rs stringForColumn:@"label"]copy];
		[result addObject:brand];
		[brand release];
	}
	[rs close];
	return result;
}

- (NSMutableArray *) getBrandsByPubId:(NSString *)pubId {
	NSMutableArray *result = [[NSMutableArray alloc]init];
	FMResultSet *rs = 
	[db executeQuery:@"SELECT * FROM brands b INNER JOIN pubs_brands pb ON b.brandId = pb.brand_id AND pb.pub_id = ?", 
	 pubId];
	while ([rs next]) {
		Brand *brand = [[Brand alloc] init];
		brand.brandId = [[rs stringForColumn:@"brandId"]copy];
		brand.icon = [[rs stringForColumn:@"icon"]copy];
		brand.label = [[rs stringForColumn:@"label"]copy];
		[result addObject:brand];
		[brand release];
	}
	[rs close];
	return result;
}

- (NSMutableArray *) getBrandsByCountry:(NSString *)country {
	NSMutableArray *result = [[NSMutableArray alloc]init];
	FMResultSet *rs = 
	[db executeQuery:@"SELECT * FROM brands b INNER JOIN brands_countries bc ON b.brandId = bc.brand_id AND bc.country = ?", 
	 country];
	while ([rs next]) {
		Brand *brand = [[Brand alloc] init];
		brand.brandId = [[rs stringForColumn:@"brandId"]copy];
		brand.icon = [[rs stringForColumn:@"icon"]copy];
		brand.label = [[rs stringForColumn:@"label"]copy];
		[result addObject:brand];
		[brand release];
	}
	[rs close];
	return result;
}

- (NSMutableArray *) getBrandsByTag:(NSString *)tag {
	NSMutableArray *result = [[NSMutableArray alloc]init];
	FMResultSet *rs = 
	[db executeQuery:@"SELECT * FROM brands b INNER JOIN brands_tags bt ON b.brandId = bt.brand_id AND bt.tag = ?",
	 tag];
	while ([rs next]) {
		Brand *brand = [[Brand alloc] init];
		brand.brandId = [[rs stringForColumn:@"brandId"]copy];
		brand.icon = [[rs stringForColumn:@"icon"]copy];
		brand.label = [[rs stringForColumn:@"label"]copy];
		[result addObject:brand];
		[brand release];
	}
	[rs close];
	return result;
}


#pragma mark -
#pragma mark Pub

- (NSMutableArray *) getPubs {
	NSMutableArray *result = [[NSMutableArray alloc]init];
	FMResultSet *rs = 
	[db executeQuery:@"SELECT * FROM pubs"];
    while ([rs next]) {
        Pub* pub = [[Pub alloc] initWithId:[[rs stringForColumn:@"pubId"]copy]
									 Title:[[rs stringForColumn:@"pubTitle"]copy]
								   Address:[[rs stringForColumn:@"pubAddress"]copy] 
									  City: [[rs stringForColumn:@"city"]copy]
									 Phone:[[rs stringForColumn:@"phone"]copy]
								   Webpage:[[rs stringForColumn:@"webpage"]copy]
									   Lat:[rs doubleForColumn:@"latitude"]
									  Long:[rs doubleForColumn:@"longitude"]];
		[result addObject:pub];
		[pub release];
    }
	[rs close];
	return result;
}

- (Pub *) getPubById:(NSString *) pubId {
	FMResultSet *rs = 
		[db executeQuery:@"SELECT * FROM pubs WHERE pubId = ?", pubId];
    while ([rs next]) {
        Pub* pub = [[Pub alloc] initWithId:[[rs stringForColumn:@"pubId"]copy]
									 Title:[[rs stringForColumn:@"pubTitle"]copy]
								   Address:[[rs stringForColumn:@"pubAddress"] copy]
									  City: [[rs stringForColumn:@"city"]copy]
									 Phone:[[rs stringForColumn:@"phone"]copy]
								   Webpage:[[rs stringForColumn:@"webpage"]copy]
									   Lat:[rs doubleForColumn:@"latitude"]
									  Long:[rs doubleForColumn:@"longitude"]];
		[rs close];
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
		item.code = [[rs stringForColumn:@"code"]copy];
		item.displayValue = [[rs stringForColumn:@"name"]copy];
		[result addObject:item];
		[item release];
	}
	[rs close];
	return result;
}

#pragma mark -
#pragma mark Feeling Lucky

- (FeelingLucky *) feelingLucky {
	FMResultSet *rs = 
	[db executeQuery:@"SELECT * FROM pubs p INNER JOIN pubs_brands pb ON p.pubId = pb.pub_id ORDER BY RANDOM() LIMIT 1"];
	
	FeelingLucky *lucky = [[FeelingLucky alloc]init];
	while ([rs next]) {
		Pub* pub = [[Pub alloc] initWithId:[[rs stringForColumn:@"pubId"]copy]
									 Title:[[rs stringForColumn:@"pubTitle"]copy]
								   Address:[[rs stringForColumn:@"pubAddress"]copy] 
									  City: [[rs stringForColumn:@"city"]copy]
									 Phone:[[rs stringForColumn:@"phone"]copy]
								   Webpage:[[rs stringForColumn:@"webpage"]copy]
									   Lat:[rs doubleForColumn:@"latitude"]
									  Long:[rs doubleForColumn:@"longitude"]];
		NSMutableArray *brands = [self getBrandsByPubId:pub.pubId];
		int i = (arc4random()%(brands.count));
		Brand *brand = [brands objectAtIndex:i];
		lucky.pub = pub;
		lucky.brand = brand;
		[rs close];
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


@end
