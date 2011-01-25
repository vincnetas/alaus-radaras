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

@implementation MapViewController

@synthesize mapView;
NSString * const GMAP_ANNOTATION_SELECTED = @"gmapselected";

- (void)dealloc {
	[mapView release];
	[super dealloc];
}

 - (void)viewDidLoad {
	 NSLog(@"viewDidLoad");
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
	NSStringEncoding *encoding;
	NSError* error;
	NSString* path = [[NSBundle mainBundle] pathForResource:@"pubs" ofType:@"txt"];
	NSString* fileContents = [NSString stringWithContentsOfFile:path usedEncoding:&encoding error:&error];
	
	NSArray *lines = [fileContents componentsSeparatedByString:@"\n"];
	
	for (NSString *line in lines) {
		if (![line isEqualToString:@""]) {
			NSArray *values = [line componentsSeparatedByString:@"	"];
			
			CLLocationCoordinate2D coord;
			//	workingCoordinate.latitude = 54.689313;
			//	workingCoordinate.longitude = 25.282631;
			coord.latitude = [[values objectAtIndex:5]doubleValue];//[NSString stringWithFormat:@"brand_%@.png", [values objectAtIndex:0]];
			coord.longitude = [[values objectAtIndex:6]doubleValue];
			
			PubAnnotation *pubAnnotation = [[PubAnnotation alloc] initWithCoordinate:coord];
			[pubAnnotation setTitle:[values objectAtIndex:1]];
			[pubAnnotation setSubtitle:[values objectAtIndex:2]];
			
			[mapView addAnnotation:pubAnnotation];
			[pubAnnotation release];
		}
	}
}
 
- (MKAnnotationView *)mapView:(MKMapView *)mapView viewForAnnotation:(id <MKAnnotation>)annotation {
	NSLog(@"viewForAnnotation");
	if ([annotation isKindOfClass:[MKUserLocation class]]) {
		//Don't trample the user location annotation (pulsing blue dot).
		return nil;
	}
		
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
}


- (void)mapView:(MKMapView *)mapView annotationView:(MKAnnotationView *)view calloutAccessoryControlTapped:(UIControl *)control {
	NSLog(@"calloutAccessoryControlTapped");
}

- (void) annotationClicked: (id <MKAnnotation>) annotation {
	PubAnnotation * ann = (PubAnnotation*) annotation;
	NSLog(@"Annotation clicked: %@", ann.title);
	
//	UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"CustomCalloutMapView" message:[NSString stringWithFormat:@"You clicked at annotation: %@",ann.name] delegate:self cancelButtonTitle:nil otherButtonTitles:@"OK", nil];
//	[alert show];
//	[alert release];
}

- (void)observeValueForKeyPath:(NSString *)keyPath
                      ofObject:(id)object
                        change:(NSDictionary *)change
                       context:(void *)context {
	
/*
    NSString *action = (NSString*)context;
	
    if([action isEqualToString:GMAP_ANNOTATION_SELECTED]){
		BOOL annotationAppeared = [[change valueForKey:@"new"] boolValue];
		if (annotationAppeared) {
			NSLog(@"annotation selected %@", ((MyAnnotationView*) object).annotation.title);
			[self showAnnotation:((MyAnnotationView*) object).annotation];
			((MyAnnotationView*) object).image = [UIImage imageNamed:@"icon-sel.png"];
		}
		else {
			NSLog(@"annotation deselected %@", ((MyAnnotationView*) object).annotation.title);
			[self hideAnnotation];
			((MyAnnotationView*) object).image = [UIImage imageNamed:@"icon.png"];
		}
	}*/
}

- (void)showAnnotation:(PubAnnotation*)annotation {
	NSLog(@"showAnnotation");
//	self.moreInfoView.text.text = annotation.title;
//	[UIView beginAnimations: @"moveCNGCallout" context: nil];
//	[UIView setAnimationDelegate: self];
//	[UIView setAnimationDuration: 0.5];
//	[UIView setAnimationCurve: UIViewAnimationCurveEaseInOut];
//	self.moreInfoView.frame = CGRectMake(10.0, 250.0, self.moreInfoView.frame.size.width, self.moreInfoView.frame.size.height);
//	[UIView commitAnimations];	
	
}

- (void)hideAnnotation {
	NSLog(@"hideAnnotation");
//	self.moreInfoView.text.text = nil;
//	[UIView beginAnimations: @"moveCNGCalloutOff" context: nil];
//	[UIView setAnimationDelegate: self];
//	[UIView setAnimationDuration: 0.5];
//	[UIView setAnimationCurve: UIViewAnimationCurveEaseInOut];
//	self.moreInfoView.frame = CGRectMake(10.0, 250.0 + 300, self.moreInfoView.frame.size.width, self.moreInfoView.frame.size.height);
//	[UIView commitAnimations];
}

- (void) stopFollowLocation {
	NSLog(@"stopFollowLocation called. Good place to put stop follow location annotation code.");
	
	PubAnnotation * annotation;
	for (annotation in mapView.selectedAnnotations) {
		[mapView deselectAnnotation:annotation animated:NO];
	}
	
	[self hideAnnotation];
}










- (void)didReceiveMemoryWarning {
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
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

