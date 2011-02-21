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

@interface MapViewController : UIViewController<MKMapViewDelegate> {
	IBOutlet MKMapView *map;
	NSMutableArray *pubsOnMap;
	NSMutableArray *pubsAlreadyOnMap;
	
	/* Inforamtion bar */
	UILabel *infoLabel;
	
	/* Map Configuaration */
	BOOL rearrangeWithPub;
	
	/* City Segment */
	UISegmentedControl *citySegmentControl;

}

@property(nonatomic, retain) IBOutlet MKMapView *map;
@property(nonatomic, retain) NSMutableArray *pubsOnMap;
@property(nonatomic, retain) NSMutableArray *pubsAlreadyOnMap;

@property(nonatomic, retain) IBOutlet UILabel *infoLabel;

@property (nonatomic,retain) IBOutlet UISegmentedControl *citySegmentControl;

- (IBAction) gotoPreviousView:(id)sender;
- (void)loadPubAnnotations;
- (IBAction) locateMe:(id)sender;
- (IBAction) cityIndexChanged;

@end
