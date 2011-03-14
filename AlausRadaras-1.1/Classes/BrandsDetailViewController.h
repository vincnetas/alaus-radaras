//
//  BrandsDetailViewController.h
//  AlausRadaras
//
//  Created by Laurynas R on 1/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Brand.h"
#import "MapViewController.h"
#import "BrandsTableCell.h"

@interface BrandsDetailViewController : UIViewController <UITableViewDelegate, UITableViewDataSource> {
	UILabel *titleLabel;
	
	NSMutableArray *brandList;	
	UITableView *brandsTable;
	BrandsTableCell *brandCell;
	
}

@property (nonatomic, retain) IBOutlet UILabel *titleLabel;

@property(nonatomic, retain) NSMutableArray *brandList;
@property (nonatomic, retain) IBOutlet BrandsTableCell *brandCell;
@property (nonatomic, retain) IBOutlet 	UITableView *brandsTable;

- (IBAction) gotoPreviousView:(id)sender;
- (IBAction) showOnMap:(id)sender;

@end
