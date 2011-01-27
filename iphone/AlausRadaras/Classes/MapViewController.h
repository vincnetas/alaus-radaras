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
	IBOutlet MKMapView *mapView;
	NSMutableArray *pubsOnMap;
}

@property(nonatomic, retain) IBOutlet MKMapView *mapView;
@property(nonatomic, retain) NSMutableArray *pubsOnMap;

- (IBAction) gotoPreviousView:(id)sender;
- (void)loadPubAnnotations;

@end
