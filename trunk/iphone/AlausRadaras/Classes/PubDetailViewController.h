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
#import "PubBrandSubmit.h"
#import "NewPubBrandSubmit.h"
#import "MBProgressHUD.h"

@interface PubDetailViewController : UIViewController <UITableViewDelegate, UITableViewDataSource, MBProgressHUDDelegate, UIActionSheetDelegate, MKMapViewDelegate>{
	UILabel *pubTitleShortLabel;
	UILabel *pubTitleLabel;
	UILabel *pubAddressLabel;
	UILabel *pubCallLabel;
	UILabel *pubInternetAddessLabel;
	UIButton *directionsButton;
	IBOutlet UIButton *urlButton;
	UIImageView *pubLogoImage;
	
	Pub *currentPub;
	NSString *userCoordinates;
	NSMutableArray *brandList;
	UITableView *brandsTable;
		
	IBOutlet PubBrandSubmit *pubBrandSubmit;
	IBOutlet NewPubBrandSubmit *newBrandSubmit;
	
	NSString *thankYouMsg;
	
	BOOL enableAllFeatures;
	
	// Brand Reporting
	NSString *reportBrandId;
	NSString *reportStatus;
	
	BOOL callAlertBox;
	
	UIView *brandReportView;
    
    IBOutlet MKMapView *map;
}

@property (nonatomic, retain) IBOutlet UILabel *pubTitleShortLabel;
@property (nonatomic, retain) IBOutlet UILabel *pubTitleLabel;
@property (nonatomic, retain) IBOutlet UILabel *pubAddressLabel;
@property (nonatomic, retain) IBOutlet UILabel *pubInternetAddessLabel;
@property (nonatomic, retain) IBOutlet UILabel *pubCallLabel;
@property (nonatomic, retain) IBOutlet UIButton *directionsButton;

@property (nonatomic, retain) IBOutlet 	UITableView *brandsTable;
@property (nonatomic, retain) IBOutlet 	UIImageView *pubLogoImage;

@property (nonatomic, retain) NSMutableArray *brandList;
@property (nonatomic, retain) Pub *currentPub;
@property (nonatomic, retain) NSString *userCoordinates;

@property (nonatomic, retain) IBOutlet UIView *brandReportView;
// Reporting
@property (nonatomic, retain) NSString *reportBrandId;
@property (nonatomic, retain) NSString *reportStatus;

// Map 
@property(nonatomic, retain) IBOutlet MKMapView *map;


- (IBAction) gotoPreviousView:(id)sender;
- (IBAction) dialNumber:(id)sender;
- (IBAction) navigateToPub:(id)sender;
- (IBAction) openWebpage:(id)sender;
- (IBAction) openPubBrandSubmit: (id)sender;
- (IBAction) openAddBrandSubmit: (id)sender;

- (void) postData:(NSString *) params msg:(NSString *)msg;


@end
