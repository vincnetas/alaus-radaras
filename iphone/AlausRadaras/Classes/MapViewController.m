//
//  MapViewController.m
//  AlausRadaras
//
//  Created by Laurynas R on 1/25/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "MapViewController.h"
#import <MapKit/MapKit.h>
#import "PubAnnotationView.h"
#import "Pub.h"

@implementation MapViewController

@synthesize mapView,pubsOnMap;

- (void)dealloc {
	[pubsOnMap release];
	[mapView release];
	[super dealloc];
}

 - (void)viewDidLoad {
	 NSLog(@"MapViewController viewDidLoad");
	 [super viewDidLoad];
	 //mapView.mapType = MKMapTypeSatellite;
	 mapView.mapType=MKMapTypeStandard;
	 //mapView.mapType=MKMapTypeHybrid;
	 mapView.showsUserLocation = YES;
	 
	 CLLocationCoordinate2D sweLoc = {54.689313,25.282631};
	 MKCoordinateSpan sweSpan = MKCoordinateSpanMake(0.073226, 0.119476);
	 MKCoordinateRegion sweRegion = MKCoordinateRegionMake(sweLoc, sweSpan);
	 
	 mapView.region = sweRegion;
	 
	 [self loadPubAnnotations];

 }


- (IBAction) gotoPreviousView:(id)sender {
	[self dismissModalViewControllerAnimated:YES];	
}


- (void)loadPubAnnotations {
	
	for (Pub *pub in pubsOnMap) {
	//	if (![line isEqualToString:@""]) {
			CLLocationCoordinate2D coord;
			coord.latitude = [pub.latitude doubleValue];
			coord.longitude = [pub.longitude doubleValue];
			
			PubAnnotation *pubAnnotation = [[PubAnnotation alloc] initWithCoordinate:coord];
			[pubAnnotation setPubId:pub.pubId];
			[pubAnnotation setTitle:pub.pubTitle];
			[pubAnnotation setSubtitle:pub.pubAddress];
			
			[mapView addAnnotation:pubAnnotation];
			//[pubAnnotation release]; /* realising cause navigation problems */
	//	}
	}
	
	/* Version 1:
	
	NSString* path = [[NSBundle mainBundle] pathForResource:@"pubs" ofType:@"txt"];
	NSString* fileContents = [NSString stringWithContentsOfFile:path usedEncoding:nil error:nil];
	
	NSArray *lines = [fileContents componentsSeparatedByString:@"\n"];
	
	for (NSString *line in lines) {
		if (![line isEqualToString:@""]) {
			NSArray *values = [line componentsSeparatedByString:@"	"];
			
			CLLocationCoordinate2D coord;
			coord.latitude = [[values objectAtIndex:5]doubleValue];
			coord.longitude = [[values objectAtIndex:6]doubleValue];
			
			PubAnnotation *pubAnnotation = [[PubAnnotation alloc] initWithCoordinate:coord];
			[pubAnnotation setPubId:[values objectAtIndex:0]];
			[pubAnnotation setTitle:[values objectAtIndex:1]];
			[pubAnnotation setSubtitle:[values objectAtIndex:2]];
			
			[mapView addAnnotation:pubAnnotation];
			//[pubAnnotation release]; // realising cause navigation problems 
		}
	}
	*/
}
 
/*
- (MKAnnotationView *)mapView:(MKMapView *)mapView viewForAnnotation:(id <MKAnnotation>)annotation {
	NSLog(@"viewForAnnotation");
	if ([annotation isKindOfClass:[MKUserLocation class]]) {
		//Don't trample the user location annotation (pulsing blue dot).
		return nil;
	}
	
	MKAnnotationView *customAnnotationView=[[[MKAnnotationView alloc] initWithAnnotation:annotation reuseIdentifier:nil] autorelease];
	UIImage *pinImage = [UIImage imageNamed:@"pin.png"];
	[customAnnotationView setImage:pinImage];
	customAnnotationView.leftCalloutAccessoryView = leftIconView;

    customAnnotationView.canShowCallout = YES;
    return customAnnotationView;

	/*
	PubAnnotationView *annotationView = nil;
	PubAnnotationView* myAnnotation = (PubAnnotationView *)annotation;
	NSString* identifier = @"Pin";
	PubAnnotationView *newAnnotationView = 
		(PubAnnotationView *)[self.mapView dequeueReusableAnnotationViewWithIdentifier:identifier];
	
	if (nil == newAnnotationView) {
		newAnnotationView = 
			[[[PubAnnotationView alloc] initWithAnnotation:myAnnotation reuseIdentifier:identifier] autorelease];
	}
	
	annotationView = newAnnotationView;
		
	[annotationView setEnabled:YES];
	[annotationView setCanShowCallout:YES];
	
	return annotationView;
}*/

- (MKAnnotationView *) mapView:(MKMapView *) mapView viewForAnnotation:(id ) annotation {
	if ([annotation isKindOfClass:[MKUserLocation class]]) {
		//Don't trample the user location annotation (pulsing blue dot).
		return nil;
	}
	
	MKAnnotationView *customAnnotationView=[[[MKAnnotationView alloc] initWithAnnotation:annotation reuseIdentifier:nil] autorelease];
	UIImage *pinImage = [UIImage imageNamed:@"pin.png"];
	[customAnnotationView setImage:pinImage];
    customAnnotationView.canShowCallout = YES;
	//UIImageView *leftIconView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"LeftIconImage.png"]];
	//customAnnotationView.leftCalloutAccessoryView = leftIconView;
	UIButton *rightButton = [UIButton buttonWithType:UIButtonTypeDetailDisclosure];
//	[rightButton addTarget:self action:@selector(annotationViewClick:) forControlEvents:UIControlEventTouchUpInside];
	customAnnotationView.rightCalloutAccessoryView = rightButton;
    return customAnnotationView;
}

//- (IBAction) annotationViewClick:(id) sender {
//    NSLog(@"clicked");
//}

- (void)mapView:(MKMapView *)mapView annotationView:(MKAnnotationView *)view calloutAccessoryControlTapped:(UIControl *)control {

	PubAnnotation *pubAnnotation = view.annotation;

	NSString* path = [[NSBundle mainBundle] pathForResource:@"pubs" ofType:@"txt"];
	NSString *fileContents = [NSString stringWithContentsOfFile:path usedEncoding:nil error:nil];
	NSArray *lines = [fileContents componentsSeparatedByString:@"\n"];
	
	PubDetailViewController *pubDetailView = 
			[[PubDetailViewController alloc] initWithNibName:nil bundle:nil];

	for (NSString *line in lines) {
		if (![line isEqualToString:@""]) {
			NSArray *values = [line componentsSeparatedByString:@"	"];
			if ([pubAnnotation.pubId isEqualToString:[values objectAtIndex:0]]) {
			
				Pub *tempPub = [[Pub alloc]init];
				tempPub.pubId = [values objectAtIndex:0];
				tempPub.pubTitle = [values objectAtIndex:1];
				tempPub.pubAddress = [values objectAtIndex:2];
				tempPub.phone = [values objectAtIndex:3];
				tempPub.webpage = [values objectAtIndex:4];
				tempPub.latitude = [values objectAtIndex:5];
				tempPub.longitude = [values objectAtIndex:6];

				pubDetailView.currentPub = tempPub;
				
				[tempPub release];
		
			}
		}
	}
	pubDetailView.modalTransitionStyle = UIModalTransitionStyleFlipHorizontal;	
	[self presentModalViewController:pubDetailView animated:YES];
	
	

	[pubAnnotation release];
	[pubDetailView release];

}





- (void)didReceiveMemoryWarning {
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
	NSLog(@"MapViewController: Recieved a Memory Warning... Oooops..");

    // Release any cached data, images, etc. that aren't in use.
}

- (void)viewDidUnload {
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}




@end







// The designated initializer.  Override if you create the controller programmatically and want to perform customization that is not appropriate for viewDidLoad.
/*
 - (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
 self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
 if (self) {
 // Custom initialization.
 }
 return self;
 }
 */


/*
 // Override to allow orientations other than the default portrait orientation.
 - (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
 // Return YES for supported orientations.
 return (interfaceOrientation == UIInterfaceOrientationPortrait);
 }
 */

