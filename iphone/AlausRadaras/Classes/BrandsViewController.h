//
//  BrandsViewController.h
//  AlausRadaras
//
//  Created by Laurynas R on 1/24/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Brand.h"
#import "MapViewController.h"
#import "BrandsTableCell.h"
#import "TextDatabaseService.h"
#import "BrandsDetailViewController.h"

@class BrandsTableCell;

@interface BrandsViewController : UIViewController <UITableViewDelegate, UITableViewDataSource>{
	NSMutableArray *brandList;
	NSMutableArray *tagsList;
	NSMutableArray *countryList;
	
	UITableView *brandsTable;
	BrandsTableCell *brandCell;
	
	UISegmentedControl *beerCatagoriesControl;
	
	BrandsDetailViewController *brandsDetails;
	
	int category;
}

@property (nonatomic, retain) NSMutableArray *brandList;
@property (nonatomic, retain) IBOutlet BrandsTableCell *brandCell;
@property (nonatomic, retain) IBOutlet 	UITableView *brandsTable;

@property (nonatomic,retain) IBOutlet UISegmentedControl *beerCatagoriesControl;

@property(nonatomic, retain) IBOutlet BrandsDetailViewController *brandsDetails;


- (IBAction) gotoPreviousView:(id)sender;
- (IBAction) beerCategoryControlIndexChanged;

@end
