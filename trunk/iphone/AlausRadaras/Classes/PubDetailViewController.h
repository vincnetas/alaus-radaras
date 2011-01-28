//
//  PubDetailViewController.h
//  AlausRadaras
//
//  Created by Laurynas R on 1/26/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Pub.h"
#import "MapViewController.h"

@interface PubDetailViewController : UIViewController <UITableViewDelegate, UITableViewDataSource>{
	UILabel *pubTitleShortLabel;
	UILabel *pubTitleLabel;
	UILabel *pubAddressLabel;
	UILabel *pubCallLabel;

	Pub *currentPub;
	NSString *userCoordinates;
	NSMutableArray *brandList;
	UITableView *brandsTable;
}

@property (nonatomic, retain) IBOutlet UILabel *pubTitleShortLabel;
@property (nonatomic, retain) IBOutlet UILabel *pubTitleLabel;
@property (nonatomic, retain) IBOutlet UILabel *pubAddressLabel;
@property (nonatomic, retain) IBOutlet UILabel *pubCallLabel;
@property (nonatomic, retain) IBOutlet 	UITableView *brandsTable;

@property(nonatomic, retain) NSMutableArray *brandList;
@property (nonatomic, retain) Pub *currentPub;
@property (nonatomic, retain) NSString *userCoordinates;


- (IBAction) gotoPreviousView:(id)sender;
- (IBAction) showOnMap:(id)sender;
- (IBAction) dialNumber:(id)sender;
- (IBAction) navigateToPub:(id)sender;

@end
