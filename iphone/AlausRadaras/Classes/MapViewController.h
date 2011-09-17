//
//  MapViewController.h
//  AlausRadaras
//
//  Created by Laurynas R on 1/25/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <MapKit/MapKit.h>
#import "PubAnnotation.h"
#import "PubAnnotationView.h"
#import "PubDetailViewController.h"
#import "PlaceTableCell.h"
#import "NewPubSubmit.h"
#import "MBProgressHUD.h"

@interface MapViewController : UIViewController<MKMapViewDelegate, MBProgressHUDDelegate> {
	IBOutlet MKMapView *map;
    IBOutlet UIButton *locateMeBtn;
    
    NSMutableArray *pubsOnMap;
	NSMutableArray *pubsAlreadyOnMap;
	
	/* Inforamtion bar */
	UILabel *infoLabel;
	
	/* Map Configuaration */
	BOOL rearrangeWithPub;
	
	/* City Segment */
	UISegmentedControl *citySegmentControl;
	BOOL locationBased;

	// pub list
	UITableView *pubTable;
	IBOutlet PlaceTableCell *placeCell;

    IBOutlet NewPubSubmit *newPubSumbitView;
	NSString *thankYouMsg;

}

@property(nonatomic, retain) IBOutlet MKMapView *map;

@property(nonatomic, retain) NSMutableArray *pubsOnMap;
@property(nonatomic, retain) NSMutableArray *pubsAlreadyOnMap;

@property(nonatomic, retain) IBOutlet UILabel *infoLabel;

@property (nonatomic,retain) IBOutlet UISegmentedControl *citySegmentControl;

@property (nonatomic, retain) IBOutlet 	UITableView *pubTable;

@property (nonatomic, retain) IBOutlet NewPubSubmit *newPubSumbitView;

- (IBAction) gotoPreviousView:(id)sender;
- (void)loadPubAnnotations;
- (IBAction) locateMe:(id)sender;
- (IBAction) cityIndexChanged;

- (IBAction) showPubList:(id)sender;



- (IBAction) openAddNewPubView: (id)sender;

@end
