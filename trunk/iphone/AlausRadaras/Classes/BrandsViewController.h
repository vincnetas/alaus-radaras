//
//  BrandsViewController.h
//  AlausRadaras
//
//  Created by Laurynas R on 1/24/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Brand.h"

@class BrandsTableCell;

@interface BrandsViewController : UIViewController <UITableViewDelegate, UITableViewDataSource>{
	NSMutableArray *brandList;
	
	UITableView *brandsTable;
	BrandsTableCell *brandCell;
}

@property(nonatomic, retain) NSMutableArray *brandList;
@property (nonatomic, retain) IBOutlet BrandsTableCell *brandCell;
@property (nonatomic, retain) IBOutlet 	UITableView *brandsTable;

- (IBAction) gotoPreviousView:(id)sender;
@end
