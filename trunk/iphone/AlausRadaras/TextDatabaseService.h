//
//  TextDatabaseService.h
//  AlausRadaras
//
//  Created by Laurynas R on 1/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface TextDatabaseService : NSObject {

}

- (NSMutableArray *) findPubsHavingBrand:(NSString *)brand;

- (NSMutableArray *) getBrandsByTag:(NSString *)tagId;
- (NSMutableArray *) getBrandsByCountry:(NSString *)countryId;

- (NSMutableArray *) getTags;
- (NSMutableArray *) getCountries;

@end
