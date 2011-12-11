//
//  SQLiteManager.m
//  AlausRadaras
//
//  Created by Laurynas R on 2/6/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "SQLiteManager.h"
#import "LocationManager.h"

@implementation SQLiteManager

- (void)dealloc {
	[db release];
    [super dealloc];
}

static SQLiteManager *sharedSQLiteManager = nil;
static SQLiteManager *updateSQLiteManager = nil;

+ (SQLiteManager*) sharedManager {
    if (sharedSQLiteManager == nil) {
        sharedSQLiteManager = [[super allocWithZone:NULL] init];
    }
    return sharedSQLiteManager;
}

+ (SQLiteManager*) updateManager {
    if (updateSQLiteManager == nil) {
        updateSQLiteManager = [[super allocWithZone:NULL] init];
    }
    return updateSQLiteManager;
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
	databaseName = @"beer-radar-schema.sqlite";
	NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory , NSUserDomainMask, YES);
	databasePath = [[paths objectAtIndex:0] stringByAppendingPathComponent:databaseName];
	
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
	
    
	NSLog(@"Using Database at: %@",databasePath);
	db = [FMDatabase databaseWithPath:databasePath];
	
	// Dont autorelease the database, retain it instead.
	// Might cause memory problems - if so try refactoring
	[db retain];
	[db setLogsErrors:TRUE];
	[db setTraceExecution:FALSE];
	
    if (![db open]) {
        NSLog(@"Could not open db.");
		return;
    } else {
		NSLog(@"Database opened");
	}
	
	
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
    NSString* path;
    NSString* fileContents;
    NSArray *lines;
    
    /*
     [db beginTransaction];
     path = [[NSBundle mainBundle] pathForResource:@"brands" ofType:@"txt"];
     fileContents = [NSString stringWithContentsOfFile:path usedEncoding:nil error:nil];
     lines = [fileContents componentsSeparatedByString:@"\n"];
     
     for (NSString *line in lines) {
     if (![line isEqualToString:@""]) {
     NSArray *values = [line componentsSeparatedByString:@"\t"];
     NSLog(@"Inserting brand: %@", [values objectAtIndex:0]);
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
     NSLog(@"Inserting pub: %@", [values objectAtIndex:0]);
     
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
     */
	
	[db beginTransaction];
	path = [[NSBundle mainBundle] pathForResource:@"tags" ofType:@"txt"];
	fileContents = [NSString stringWithContentsOfFile:path usedEncoding:nil error:nil];
	lines = [fileContents componentsSeparatedByString:@"\n"];
	for (NSString *line in lines) {
		if (![line isEqualToString:@""]) {
			NSArray *values = [line componentsSeparatedByString:@"\t"];
			NSLog(@"Inserting tag: %@", [values objectAtIndex:0]);
            
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
			NSLog(@"Inserting country: %@", [values objectAtIndex:0]);
            
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
			NSLog(@"Inserting quote: %@", [values objectAtIndex:0]);
            
			[db executeUpdate:@"insert into quotes (amount, text) values (?, ?)",
			 [NSNumber numberWithInt:[[values objectAtIndex:0]intValue]],
			 [values objectAtIndex:1]];
		}
	}
	[db commit];
	
	/*
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
     
     NSString *allTags = [[values objectAtIndex:4] substringToIndex:[[values objectAtIndex:4] length] - 1];
     NSArray *tags = [allTags componentsSeparatedByString:@","];
     for (NSString *tag in tags) {
     [db executeUpdate:@"insert into brands_tags (brand_id, tag) values (?, ?)",
     [values objectAtIndex:0],
     [tag stringByReplacingOccurrencesOfString:@" " withString:@""]];                       
     }
     }
     }
     [db commit];
     */
	
	[db beginTransaction];
	path = [[NSBundle mainBundle] pathForResource:@"taxi" ofType:@"txt"];
	fileContents = [NSString stringWithContentsOfFile:path usedEncoding:nil error:nil];
	lines = [fileContents componentsSeparatedByString:@"\n"];
	for (NSString *line in lines) {
		if (![line isEqualToString:@""]) {
			NSArray *values = [line componentsSeparatedByString:@"\t"];
			NSLog(@"Inserting taxi: %@", [values objectAtIndex:0]);
			
			[db executeUpdate:@"insert into taxi (title, phone, city, latitude, longitude) values (?, ?, ?, ?, ?)",
			 [values objectAtIndex:0],
			 [values objectAtIndex:1],
			 [values objectAtIndex:2],
			 [NSNumber numberWithDouble:[[values objectAtIndex:3]doubleValue]],
			 [NSNumber numberWithDouble:[[values objectAtIndex:4]doubleValue]]];
		}
	}
	[db commit]; 
	
	NSLog(@"Data inserted");
}



#pragma mark -
#pragma mark Brand

- (NSMutableArray *) getBeers {
	NSMutableArray *result = [[NSMutableArray alloc]init];
	FMResultSet *rs = 
	[db executeQuery:@"select * from beers b ORDER by b.title asc"];
	while ([rs next]) {
		Brand *brand = [[Brand alloc] init];
		brand.brandId = [[rs stringForColumn:@"id"]copy];
		brand.icon = [[rs stringForColumn:@"icon"]copy];
		brand.label = [[rs stringForColumn:@"title"]copy];
		[result addObject:brand];
		[brand release];
	}
	[rs close];
	return result;
}

- (NSMutableArray *) getBeersLocationBased {
    
	NSMutableArray *result = [[NSMutableArray alloc]init];
    
	CLLocationCoordinate2D coordinates = [[LocationManager sharedManager]getLocationCoordinates];
	NSLog(@"Im here: %.2f, %.2f",  coordinates.latitude, coordinates.longitude);
	
	NSString *query = [[NSString alloc] initWithString:[NSString stringWithFormat:@"SELECT b.id, b.icon, b.title FROM beers b "]];
	if ([[LocationManager sharedManager]getVisibilityControlled]) {
		query = [NSString stringWithFormat:@"%@ INNER JOIN pubs_beers pb ON b.id = pb.beer_id INNER JOIN pubs AS p ON p.id=pb.pub_id AND distance(p.latitude, p.longitude, %f,%f) < %i GROUP BY b.id",
				 query, coordinates.latitude, coordinates.longitude, [[LocationManager sharedManager]getDistance]];
	}
	query = [NSString stringWithFormat:@"%@ ORDER by b.title asc", query];
    
	FMResultSet *rs = 
    [db executeQuery:query];
    
	
	while ([rs next]) {
		Brand *brand = [[Brand alloc] init];
		brand.brandId = [[rs stringForColumn:@"id"]copy];
        
        
        NSMutableArray *tags = [self getTagsByBeer:brand.brandId];
        NSString *tagString = [[tags objectAtIndex:0]displayValue];
        
        for (int i = 1; i < [tags count]; i++) {
            tagString = [NSString stringWithFormat:@"%@, %@", tagString, [[tags objectAtIndex:i]displayValue]];
        }
        
        brand.tagsAsString = tagString;
        
        
        
		brand.icon = [[rs stringForColumn:@"icon"]copy];
		brand.label = [[rs stringForColumn:@"title"]copy];
		[result addObject:brand];
		[brand release];
	}
	
	[rs close];
	return result;
}

- (NSMutableArray *) getBeersWithTagsLocationBased {
    
	NSMutableArray *result = [[NSMutableArray alloc]init];
    
	CLLocationCoordinate2D coordinates = [[LocationManager sharedManager]getLocationCoordinates];
	NSLog(@"Im here: %.2f, %.2f",  coordinates.latitude, coordinates.longitude);
	
	NSString *query = [[NSString alloc] initWithString:[NSString stringWithFormat:@"SELECT b.id, b.icon, b.title FROM beers b INNER JOIN tags t INNER JOIN beers_tags AS bt ON bt.tag = t.code WHERE bt.beer_id = b.id"]];
    
	if ([[LocationManager sharedManager]getVisibilityControlled]) {
		query = [NSString stringWithFormat:@"%@ INNER JOIN pubs_beers pb ON b.id = pb.beer_id INNER JOIN pubs AS p ON p.id=pb.pub_id AND distance(p.latitude, p.longitude, %f,%f) < %i GROUP BY b.id",
				 query, coordinates.latitude, coordinates.longitude, [[LocationManager sharedManager]getDistance]];
	}
	query = [NSString stringWithFormat:@"%@ ORDER by b.title asc", query];
    
	FMResultSet *rs = 
    [db executeQuery:query];
    
	
	while ([rs next]) {
		Brand *brand = [[Brand alloc] init];
		brand.brandId = [[rs stringForColumn:@"id"]copy];
        
        
        
        
        NSMutableArray *tags = [self getTagsByBeer:brand.brandId];
        NSString *tagString = [[tags objectAtIndex:0]displayValue];
        
        for (int i = 1; i < [tags count]; i++) {
            tagString = [NSString stringWithFormat:@"%@, %@", tagString, [[tags objectAtIndex:i]displayValue]];
        }
        
        brand.tagsAsString = tagString;
        
        
        /*
         NSMutableArray *result = [[NSMutableArray alloc]init];
         NSString *query = [[NSString alloc] initWithString:[NSString stringWithFormat:@"select * from tags t INNER JOIN beers_tags AS bt ON bt.tag = t.code WHERE bt.beer_id = %@", beerId]];
         
         FMResultSet *rs = [db executeQuery:query];
         while ([rs next]) {
         CodeValue *item = [[CodeValue alloc] init];
         item.code = [rs stringForColumn:@"code"];
         item.displayValue = [rs stringForColumn:@"title"];
         [result addObject:item];
         [item release];
         }
         return result;
         */
        
        
		brand.icon = [[rs stringForColumn:@"icon"]copy];
		brand.label = [[rs stringForColumn:@"title"]copy];
		[result addObject:brand];
		[brand release];
	}
	
	[rs close];
	return result;
}

- (NSString *) getBeersLabelById:(NSString *)beerId {	
	FMResultSet *rs = 
    [db executeQuery:@"SELECT * FROM beers b WHERE b.id = ?", beerId];
	NSString *result;
	while ([rs next]) {
		result = [[rs stringForColumn:@"title"]copy];
		break;
	}
	[rs close];
	return result;
}


- (NSMutableArray *) getBeersByPubId:(NSString *)pubId {
	NSMutableArray *result = [[NSMutableArray alloc]init];
    
	FMResultSet *rs = 
    [db executeQuery:@"SELECT * FROM beers b INNER JOIN pubs_beers pb ON b.id = pb.beer_id AND pb.pub_id = ? ORDER BY b.title asc", pubId];
	while ([rs next]) {
		Brand *brand = [[Brand alloc] init];
		brand.brandId = [[rs stringForColumn:@"id"]copy];
		brand.icon = [[rs stringForColumn:@"icon"]copy];
		brand.label = [[rs stringForColumn:@"title"]copy];
		[result addObject:brand];
		[brand release];
	}
	[rs close];
	return result;
}

- (NSMutableArray *) getBeersByCountry:(NSString *)country {
	NSMutableArray *result = [[NSMutableArray alloc]init];
    
	CLLocationCoordinate2D coordinates = [[LocationManager sharedManager]getLocationCoordinates];
	NSString *query = [[NSString alloc] initWithString:
                       [NSString stringWithFormat:@"SELECT b.id, b.icon, b.title FROM beers b INNER JOIN brands AS br ON b.brand_id=br.id AND br.country = '%@'", country]];
	if ([[LocationManager sharedManager]getVisibilityControlled]) {
		query = [NSString stringWithFormat:@"%@ INNER JOIN pubs_beers AS pb ON pb.beer_id = b.id INNER JOIN pubs AS p ON p.id = pb.pub_id AND distance(p.latitude, p.longitude, %f,%f) < %i GROUP BY b.id", query, coordinates.latitude, coordinates.longitude, [[LocationManager sharedManager]getDistance]];
	}
	query = [NSString stringWithFormat:@"%@ ORDER by b.title asc", query];
	FMResultSet *rs = [db executeQuery:query];
	
	while ([rs next]) {
		Brand *brand = [[Brand alloc] init];
		brand.brandId = [[rs stringForColumn:@"id"]copy];
		brand.icon = [[rs stringForColumn:@"icon"]copy];
		brand.label = [[rs stringForColumn:@"title"]copy];
		[result addObject:brand];
		[brand release];
	}
	[rs close];
	return result;
}

- (NSMutableArray *) getBeersByTag:(NSString *)tag {
	NSMutableArray *result = [[NSMutableArray alloc]init];
	
	CLLocationCoordinate2D coordinates = [[LocationManager sharedManager]getLocationCoordinates];
	
	NSString *query = [[NSString alloc] initWithString:[NSString stringWithFormat:@"SELECT b.id, b.icon, b.title FROM beers b INNER JOIN beers_tags bt ON b.id = bt.beer_id AND bt.tag = '%@'", tag]];
    
	if ([[LocationManager sharedManager]getVisibilityControlled]) {
		query = [NSString stringWithFormat:@"%@ INNER JOIN pubs_beers AS pb ON pb.beer_id = b.id INNER JOIN pubs AS p ON p.id = pb.pub_id AND distance(p.latitude, p.longitude, %f,%f) < %i GROUP BY b.id", query, coordinates.latitude, coordinates.longitude, [[LocationManager sharedManager]getDistance]];
	}
	
	query = [NSString stringWithFormat:@"%@ ORDER by b.title asc", query];
	
	FMResultSet *rs = 
    [db executeQuery:query];
	
	while ([rs next]) {
		Brand *brand = [[Brand alloc] init];
		brand.brandId = [[rs stringForColumn:@"id"]copy];
		brand.icon = [[rs stringForColumn:@"icon"]copy];
		brand.label = [[rs stringForColumn:@"title"]copy];
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
	
	CLLocationCoordinate2D coordinates = [[LocationManager sharedManager]getLocationCoordinates];
	NSString *query = [[NSString alloc] initWithString: [NSString stringWithFormat:@"SELECT id, title, address, city, phone, url, latitude, longitude , distance(latitude, longitude, %f,%f) as distance FROM pubs", coordinates.latitude, coordinates.longitude]];
	if ([[LocationManager sharedManager]getVisibilityControlled]) {
		query = [NSString stringWithFormat:@"%@ WHERE distance < %i ORDER BY distance asc", query, [[LocationManager sharedManager]getDistance]];
	} else {
		query = [NSString stringWithFormat:@"%@ ORDER BY title asc", query];
	}
	FMResultSet *rs = [db executeQuery:query];
    
    while ([rs next]) {
        Pub* pub = [[Pub alloc] initWithId:[[rs stringForColumn:@"id"]copy]
									 Title:[[rs stringForColumn:@"title"]copy]
								   Address:[[rs stringForColumn:@"address"]copy] 
									  City: [[rs stringForColumn:@"city"]copy]
									 Phone:[[rs stringForColumn:@"phone"]copy]
								   Webpage:[[rs stringForColumn:@"url"]copy]
									   Lat:[rs doubleForColumn:@"latitude"]
									  Long:[rs doubleForColumn:@"longitude"]];
		pub.distance = [rs doubleForColumn:@"distance"];
		[result addObject:pub];
		[pub release];
    }
	[rs close];
	return result;
}

- (Pub *) getPubById:(NSString *) pubId {
	FMResultSet *rs = 
    [db executeQuery:@"SELECT * FROM pubs WHERE id = ?", pubId];
    while ([rs next]) {
        Pub* pub = [[Pub alloc] initWithId:[[rs stringForColumn:@"id"]copy]
									 Title:[[rs stringForColumn:@"title"]copy]
								   Address:[[rs stringForColumn:@"address"] copy]
									  City: [[rs stringForColumn:@"city"]copy]
									 Phone:[[rs stringForColumn:@"phone"]copy]
								   Webpage:[[rs stringForColumn:@"url"]copy]
									   Lat:[rs doubleForColumn:@"latitude"]
									  Long:[rs doubleForColumn:@"longitude"]];
		[rs close];
		return pub;		
    }
	return nil;
}

- (NSMutableArray *) getPubsByBeerId:(NSString *) brandId {
	NSMutableArray *result = [[NSMutableArray alloc]init];
	
	CLLocationCoordinate2D coordinates = [[LocationManager sharedManager]getLocationCoordinates];
	NSString *query = [[NSString alloc] initWithString:[NSString stringWithFormat:@"SELECT id, title, address, city, phone, url, latitude, longitude , distance(latitude, longitude, %f,%f) as distance FROM pubs p INNER JOIN pubs_beers pb ON p.id = pb.pub_id AND pb.beer_id = '%@'",  coordinates.latitude, coordinates.longitude, brandId]];
	if ([[LocationManager sharedManager]getVisibilityControlled]) {
		query = [NSString stringWithFormat:@"%@ AND distance < %i", query, [[LocationManager sharedManager]getDistance]];
	}
	FMResultSet *rs = [db executeQuery:query];
	
    while ([rs next]) {
        Pub* pub = [[Pub alloc] initWithId:[[rs stringForColumn:@"id"]copy]
									 Title:[[rs stringForColumn:@"title"]copy]
								   Address:[[rs stringForColumn:@"address"] copy]
									  City: [[rs stringForColumn:@"city"]copy]
									 Phone:[[rs stringForColumn:@"phone"]copy]
								   Webpage:[[rs stringForColumn:@"url"]copy]
									   Lat:[rs doubleForColumn:@"latitude"]
									  Long:[rs doubleForColumn:@"longitude"]];
		pub.distance = [rs doubleForColumn:@"distance"];
		[result addObject:pub];
        //	[pub release];
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


- (NSMutableArray *) getTagsByBeer: (NSString *) beerId {
	NSMutableArray *result = [[NSMutableArray alloc]init];
    NSString *query = [[NSString alloc] initWithString:[NSString stringWithFormat:@"select * from tags t INNER JOIN beers_tags AS bt ON bt.tag = t.code WHERE bt.beer_id = %@", beerId]];

	FMResultSet *rs = [db executeQuery:query];
	while ([rs next]) {
		CodeValue *item = [[CodeValue alloc] init];
		item.code = [rs stringForColumn:@"code"];
		item.displayValue = [rs stringForColumn:@"title"];
		[result addObject:item];
		[item release];
	}
	return result;
}

- (NSMutableArray *) getTagsLocationBased {
	NSMutableArray *result = [[NSMutableArray alloc]init];
	
	CLLocationCoordinate2D coordinates = [[LocationManager sharedManager]getLocationCoordinates];
	
	NSString *query = [[NSString alloc] initWithString:[NSString stringWithFormat:@"select t.code, t.title from tags t"]];
	
	if ([[LocationManager sharedManager]getVisibilityControlled]) {
		query = [NSString stringWithFormat:@"%@ INNER JOIN beers_tags AS bt ON bt.tag = t.code INNER JOIN pubs_beers AS pb ON pb.beer_id = bt.beer_id INNER JOIN pubs AS p on p.id = pb.pub_id AND distance(p.latitude, p.longitude, %f,%f) < %i GROUP BY t.title",
				 query, coordinates.latitude, coordinates.longitude, [[LocationManager sharedManager]getDistance]];
	}
	
	query = [NSString stringWithFormat:@"%@ ORDER BY t.title asc", query];
	
	FMResultSet *rs = 
    [db executeQuery:query];
	
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

- (NSMutableArray *) getCountriesLocationBased {
	NSMutableArray *result = [[NSMutableArray alloc]init];
	
	CLLocationCoordinate2D coordinates = [[LocationManager sharedManager]getLocationCoordinates];
	
	NSString *query = [[NSString alloc] initWithString:[NSString stringWithFormat:@"SELECT c.code, c.name FROM countries c"]];
	
	if ([[LocationManager sharedManager]getVisibilityControlled]) {
        query = [NSString stringWithFormat:@"%@ INNER JOIN brands AS br ON br.country = c.code INNER JOIN beers AS b ON b.brand_id = br.id INNER JOIN pubs_beers AS pb ON pb.beer_id = b.id INNER JOIN pubs AS p ON p.id = pb.pub_id AND distance(p.latitude, p.longitude, %f,%f) < %i GROUP BY c.code",
				 query, coordinates.latitude, coordinates.longitude, [[LocationManager sharedManager]getDistance]];
	}
	
	query = [NSString stringWithFormat:@"%@ ORDER BY c.name asc", query];
	
	FMResultSet *rs = 
    [db executeQuery:query];
    
	
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
	
	CLLocationCoordinate2D coordinates = [[LocationManager sharedManager]getLocationCoordinates];
	NSString *query = [[NSString alloc] initWithString:[NSString stringWithFormat:@"SELECT * FROM pubs p INNER JOIN pubs_beers pb ON p.id = pb.pub_id"]];
	if ([[LocationManager sharedManager]getVisibilityControlled]) {
		query = [NSString stringWithFormat:@"%@ AND distance(p.latitude, p.longitude, %f,%f) < %i", 
                 query, coordinates.latitude, coordinates.longitude, [[LocationManager sharedManager]getDistance]];
	}
	query = [NSString stringWithFormat:@"%@ ORDER BY RANDOM() LIMIT 1", query];
    
	FMResultSet *rs = [db executeQuery:query];
	
	FeelingLucky *lucky = [[FeelingLucky alloc]init];
	while ([rs next]) {
		Pub* pub = [[Pub alloc] initWithId:[[rs stringForColumn:@"id"]copy]
									 Title:[[rs stringForColumn:@"title"]copy]
								   Address:[[rs stringForColumn:@"address"]copy] 
									  City: [[rs stringForColumn:@"city"]copy]
									 Phone:[[rs stringForColumn:@"phone"]copy]
								   Webpage:[[rs stringForColumn:@"url"]copy]
									   Lat:[rs doubleForColumn:@"latitude"]
									  Long:[rs doubleForColumn:@"longitude"]];
        
        NSLog([NSString stringWithFormat:@"FEeL lucky : %@", pub.pubId]);
        
		NSMutableArray *brands = [self getBeersByPubId:pub.pubId];
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
#pragma mark Taxi

- (NSMutableArray *) getTaxies {
	NSMutableArray *result = [[NSMutableArray alloc]init];
	CLLocationCoordinate2D coordinates = [[LocationManager sharedManager]getLocationCoordinates];
	NSString *query = [[NSString alloc] initWithString:[NSString stringWithFormat:@"SELECT title, phone, city, latitude, longitude, distance(latitude, longitude, %f,%f) as distance FROM taxi t",  coordinates.latitude, coordinates.longitude]];
	if ([[LocationManager sharedManager]getVisibilityControlled]) {
		query = [NSString stringWithFormat:@"%@ WHERE distance < %i", query, 20];
	}
	FMResultSet *rs = [db executeQuery:query];
	
    while ([rs next]) {
        Taxi* taxi = [[Taxi alloc] initWithTitle:[[rs stringForColumn:@"title"]copy]
                                            City:[[rs stringForColumn:@"city"]copy] 
                                           Phone:[[rs stringForColumn:@"phone"]copy]
                                             Lat:[rs doubleForColumn:@"latitude"]
                                            Long:[rs doubleForColumn:@"longitude"]];
		[result addObject:taxi];
		[taxi release];
    }	
	return result;
}



#pragma mark -
#pragma mark Update data


// UPDATE services used for SYNC
// 1. Check if exist in DB
// 2.1. If yes -> Update data
// 2.2. If not -> Save data

- (void) updateBrand: (NSDictionary *) brand {
    FMResultSet *rs = 
    [db executeQuery:[NSString stringWithFormat:@"SELECT * FROM brands b WHERE b.id = '%@'", [brand objectForKey:@"id"]]];
    
	if ([rs next]) {
        [db executeUpdate:@"UPDATE brands SET title = ?, icon = ?, homePage = ?, country = ?, hometown = ?, description = ? WHERE id = ?",
         [brand objectForKey:@"title"],
         [brand objectForKey:@"icon"],
         [brand objectForKey:@"homePage"],
         [brand objectForKey:@"country"],
         [brand objectForKey:@"hometown"],
         [brand objectForKey:@"description"],
         [brand objectForKey:@"id"]];
	} else {
        [db executeUpdate:@"INSERT INTO brands (id, title, icon, homePage, country, hometown, description) values (?, ?, ?, ?, ?, ?, ?)",
         [brand objectForKey:@"id"],
         [brand objectForKey:@"title"],
         [brand objectForKey:@"icon"],
         [brand objectForKey:@"homePage"],
         [brand objectForKey:@"country"],
         [brand objectForKey:@"hometown"],
         [brand objectForKey:@"description"]];
    }
    [rs close];
}

- (void) updateBeer: (NSDictionary *) beer {
    FMResultSet *rs = 
    [db executeQuery:[NSString stringWithFormat:@"SELECT * FROM beers b WHERE b.id = '%@'", [beer objectForKey:@"id"]]];
    
    // UPDATE/INSERT BEER
	if ([rs next]) {
        [db executeUpdate:@"UPDATE beers SET title = ?, icon = ? WHERE id = ?",
         [beer objectForKey:@"title"],
         [NSString stringWithFormat:@"beer_%@.png",[beer objectForKey:@"icon"]],
         [beer objectForKey:@"id"]];
	} else {
        [db executeUpdate:@"INSERT INTO beers (id, title, icon) values (?, ?, ?)",
         [beer objectForKey:@"id"],
         [beer objectForKey:@"title"],
         [NSString stringWithFormat:@"beer_%@.png",[beer objectForKey:@"icon"]]];
    }
    
    // UPDATE BEER-BRAND RELATIONSHIP
    FMResultSet *rs_brand = 
    [db executeQuery:[NSString stringWithFormat:@"SELECT * FROM brands b WHERE b.id = '%@'", [beer objectForKey:@"brandId"]]];
    if ([rs_brand next]) {
        [db executeUpdate:@"UPDATE beers SET brand_id = ? WHERE id = ?",
         [beer objectForKey:@"brandId"],
         [beer objectForKey:@"id"]];
    }
    
    // UPDATE BEER-TAG RELATIONSHIP
    // Remove all tags
    [db executeUpdate:[NSString stringWithFormat:@"DELETE FROM beers_tags WHERE beer_id = '%@'", [beer objectForKey:@"id"]]];
    // Insert new tags
    NSArray *beerTags = [beer objectForKey:@"tags"];
    
    FMResultSet *rs_tag;
    for (NSString *tag in beerTags) {
        // Check if tag exists
        rs_tag = 
        [db executeQuery:[NSString stringWithFormat:@"SELECT * FROM tags t WHERE t.code = '%@'", tag]];
        if ([rs_tag next]) {
            [db executeUpdate:@"INSERT INTO beers_tags (beer_id, tag) values (?, ?)",
                [beer objectForKey:@"id"], tag];
        }
    }

    [rs close];
    [rs_brand close];
    [rs_tag close];
}

- (void) updatePub: (NSDictionary *) pub {
    FMResultSet *rs = 
    [db executeQuery:[NSString stringWithFormat:@"SELECT * FROM pubs p WHERE p.id = '%@'", [pub objectForKey:@"id"]]];
    
	if ([rs next]) {
		// update pub data
        [db executeUpdate:@"UPDATE pubs SET title = ?, address = ?, phone = ?, url = ?, latitude = ?, longitude = ?, city = ? WHERE id = ?",
             [pub objectForKey:@"title"],
             [pub objectForKey:@"address"],
             [pub objectForKey:@"phone"],
             [pub objectForKey:@"homepage"],
             [pub objectForKey:@"latitude"],
             [pub objectForKey:@"longitude"],
             [pub objectForKey:@"city"],
             [pub objectForKey:@"id"]];
	} else {
		// insert pub
        [db executeUpdate:@"INSERT INTO pubs (id, title, address, phone, url, latitude, longitude, city) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
             [pub objectForKey:@"id"],
             [pub objectForKey:@"title"],
             [pub objectForKey:@"address"],
             [pub objectForKey:@"phone"],
             [pub objectForKey:@"homepage"],
             [pub objectForKey:@"latitude"],
             [pub objectForKey:@"longitude"],
             [pub objectForKey:@"city"]];
	}
	
    
    // UPDATE PUBS-BEER RELATIONSHIP
    // Remove all beers from pub
    [db executeUpdate:[NSString stringWithFormat:@"DELETE FROM pubs_beers WHERE pub_id = '%@'", [pub objectForKey:@"id"]]];
    // Insert new beers
	NSArray *beerIds = [pub objectForKey:@"beerIds"];
    
    for (NSString *beerId in beerIds) {
        rs = [db executeQuery:[NSString stringWithFormat:@"SELECT * FROM beers b WHERE b.id = '%@'", beerId]];
        if ([rs next]) {
            [db executeUpdate:@"INSERT INTO pubs_beers (pub_id, beer_id) VALUES (?, ?);",
                [pub objectForKey:@"id"], beerId];
        }
    }

	[rs close];
}


@end
