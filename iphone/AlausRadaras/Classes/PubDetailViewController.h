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
#import "PubBrandSubmit.h";
@interface PubDetailViewController : UIViewController <UITableViewDelegate, UITableViewDataSource>{
	UILabel *pubTitleShortLabel;
	UILabel *pubTitleLabel;
	UILabel *pubAddressLabel;
	UILabel *pubCallLabel;
	UILabel *pubInternetAddessLabel;
	UIButton *directionsButton;
	UIImageView *pubLogoImage;
	
	Pub *currentPub;
	NSString *userCoordinates;
	NSMutableArray *brandList;
	UITableView *brandsTable;
		
	IBOutlet PubBrandSubmit *pubBrandSubmit;
}

@property (nonatomic, retain) IBOutlet UILabel *pubTitleShortLabel;
@property (nonatomic, retain) IBOutlet UILabel *pubTitleLabel;
@property (nonatomic, retain) IBOutlet UILabel *pubAddressLabel;
@property (nonatomic, retain) IBOutlet UILabel *pubInternetAddessLabel;
@property (nonatomic, retain) IBOutlet UILabel *pubCallLabel;
@property (nonatomic, retain) IBOutlet UIButton *directionsButton;

@property (nonatomic, retain) IBOutlet 	UITableView *brandsTable;
@property (nonatomic, retain) IBOutlet 	UIImageView *pubLogoImage;

@property(nonatomic, retain) NSMutableArray *brandList;
@property (nonatomic, retain) Pub *currentPub;
@property (nonatomic, retain) NSString *userCoordinates;

@property (retain) IBOutlet UIView *reportPubInfoView;


- (IBAction) gotoPreviousView:(id)sender;
- (IBAction) dialNumber:(id)sender;
- (IBAction) navigateToPub:(id)sender;
- (IBAction) openWebpage:(id)sender;
- (IBAction) reportPubInfo:(id)sender;
- (IBAction) closeReportPubInfo:(id)sender;
- (IBAction) openPubBrandSubmit: (id)sender;

@end
