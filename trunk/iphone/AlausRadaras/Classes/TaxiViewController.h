//
//  TaxiViewController.h
//  AlausRadaras
//
//  Created by Laurynas R on 3/14/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "BrandsTableCell.h"


@interface TaxiViewController : UIViewController {
	UITableView *taxiTable;
	BrandsTableCell *brandCell;

	NSMutableArray *taxiList;
}


@property (nonatomic, retain) IBOutlet UITableView *taxiTable;
@property (nonatomic, retain) BrandsTableCell *brandCell;

@property (nonatomic, retain) NSMutableArray *taxiList;

- (IBAction) back:(id)sender;

@end