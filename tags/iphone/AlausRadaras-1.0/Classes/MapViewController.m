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
#import "SQLiteManager.h"

@implementation MapViewController

@synthesize map,pubsOnMap, pubsAlreadyOnMap;
@synthesize infoLabel;
@synthesize citySegmentControl;

- (void)dealloc {
        [citySegmentControl release];
        [infoLabel release];
        [pubsAlreadyOnMap release];
        [pubsOnMap release];
        [map release];
        [super dealloc];
}

 - (void)viewDidLoad {
         NSLog(@"MapViewController viewDidLoad");
         [super viewDidLoad];
         //mapView.mapType = MKMapTypeSatellite;
         map.mapType=MKMapTypeStandard;
         //mapView.mapType=MKMapTypeHybrid;
         map.showsUserLocation = YES;
        // CLLocationCoordinate2D userPosition = mapView.userLocation.location.coordinate;
         CLLocationCoordinate2D centerOfVilnius = {54.689313,25.282631};
         
         MKCoordinateSpan coordSpan = MKCoordinateSpanMake(0.04, 0.05);
         MKCoordinateRegion region = MKCoordinateRegionMake(centerOfVilnius, coordSpan);

         map.region = region;
         pubsAlreadyOnMap = [[NSMutableArray alloc]init];//WithObjects:@"asdf", nil];

         [self loadPubAnnotations];
//       [self dynamicLoadPubAnnotationsForRegion: map];
         
//showAll Radius
         
 }


- (IBAction) gotoPreviousView:(id)sender {
        [self dismissModalViewControllerAnimated:YES];  
}

- (IBAction) locateMe:(id)sender {
        [self locateMe];
}


- (void)loadPubAnnotations {
        for (Pub *pub in pubsOnMap) {
                CLLocationCoordinate2D coord;
                coord.latitude = pub.latitude;
                coord.longitude = pub.longitude;
                
                PubAnnotation *pubAnnotation = [[PubAnnotation alloc] initWithCoordinate:coord];
                [pubAnnotation setPubId:pub.pubId];
                [pubAnnotation setTitle:pub.pubTitle];
                [pubAnnotation setSubtitle:pub.pubAddress];
                
                [map addAnnotation:pubAnnotation];
        }
}


- (void)dynamicLoadPubAnnotationsForRegion:(MKMapView *)mapView {
//NSLog(@"%f", sqrt(17) ); 
        NSLog(@"dynamicLoadPubAnnotationsForRegion");
        double centerLat = mapView.region.center.latitude;
        double centerLong = mapView.region.center.longitude;
        
        double latitudeLength = mapView.region.span.latitudeDelta/2.0;
        double longitudeLength = mapView.region.span.longitudeDelta/2.0;

        double lengthFromCenter = sqrt(latitudeLength * latitudeLength + longitudeLength * longitudeLength);
        
        NSLog(@"map.region.center.latitude: %f", mapView.region.center.latitude);
        NSLog(@"map.region.center.longitude: %f", mapView.region.center.longitude);
        NSLog(@"map.region.span.latitudeDelta/2.0: %f", mapView.region.span.latitudeDelta/2.0);
        NSLog(@"map.region.span.longitudeDelta/2.0: %f", mapView.region.span.longitudeDelta/2.0);
        NSLog(@"lengthFromCenter: %f", lengthFromCenter);

//      [mapView removeAnnotations:mapView.annotations];
        
        for (Pub *pub in pubsOnMap) {
                CLLocationCoordinate2D coord;
                coord.latitude = pub.latitude;
                coord.longitude = pub.longitude;
                
                double pubFromCenter = sqrt((coord.latitude-centerLat)*(coord.latitude-centerLat) + 
                                                                        (coord.longitude-centerLong)*(coord.longitude-centerLong));

                
                if (pubFromCenter <= lengthFromCenter){         

                        if ([self.pubsAlreadyOnMap containsObject:pub.pubId]){
                                continue;
                        }
                        
                        [self.pubsAlreadyOnMap addObject:pub.pubId];

                        PubAnnotation *pubAnnotation = [[PubAnnotation alloc] initWithCoordinate:coord];
                        [pubAnnotation setPubId:pub.pubId];
                        [pubAnnotation setTitle:pub.pubTitle];
                        [pubAnnotation setSubtitle:pub.pubAddress];

                        [map addAnnotation:pubAnnotation];

                }
        }
}

 

- (MKAnnotationView *) mapView:(MKMapView *) mapView viewForAnnotation:(id ) annotation {
//      NSLog(@"viewForAnnotation");

        if ([annotation isKindOfClass:[MKUserLocation class]]) {
                //Don't trample the user location annotation (pulsing blue dot).
                return nil;
        }
        
        MKAnnotationView *customAnnotationView=[[[MKAnnotationView alloc] initWithAnnotation:annotation reuseIdentifier:nil] autorelease];
        UIImage *pinImage = [UIImage imageNamed:@"pin.png"];
        [customAnnotationView setImage:pinImage];
    customAnnotationView.canShowCallout = YES;
        
//      UIImageView *leftIconView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"pub_obuolys_icon.png"]];
//      customAnnotationView.leftCalloutAccessoryView = leftIconView;
        
        UIButton *rightButton = [UIButton buttonWithType:UIButtonTypeDetailDisclosure];
//      [rightButton addTarget:self action:@selector(annotationViewClick:) forControlEvents:UIControlEventTouchUpInside];
        customAnnotationView.rightCalloutAccessoryView = rightButton;
    return customAnnotationView;
}

/* Animating droping pins */
- (void)mapView:(MKMapView *)mapView didAddAnnotationViews:(NSArray *)views { 
        MKAnnotationView *annotationView; 

        for (annotationView in views) {
                if (annotationView.annotation == mapView.userLocation) {
                        [self locateMe];
        }
                
                CGRect endFrame = annotationView.frame;
                annotationView.frame = CGRectMake(annotationView.frame.origin.x, annotationView.frame.origin.y - 200.0, annotationView.frame.size.width, annotationView.frame.size.height);
                
                [UIView beginAnimations:nil context:NULL];
                [UIView setAnimationDuration:0.1];
                [UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];
                [annotationView setFrame:endFrame];
                [UIView commitAnimations];
        }
}

- (void)mapView:(MKMapView *)mapView annotationView:(MKAnnotationView *)view calloutAccessoryControlTapped:(UIControl *)control {
        PubAnnotation *pubAnnotation = view.annotation;

        PubDetailViewController *pubDetailView = 
                [[PubDetailViewController alloc] initWithNibName:nil bundle:nil];

        pubDetailView.currentPub = [[SQLiteManager sharedManager] getPubById:pubAnnotation.pubId];
        
    CLLocationCoordinate2D userCoordinate = map.userLocation.location.coordinate;
        pubDetailView.userCoordinates = [NSString stringWithFormat:@"%f,%f",userCoordinate.latitude,userCoordinate.longitude];
        pubDetailView.modalTransitionStyle = UIModalTransitionStyleCoverVertical;       
        [self presentModalViewController:pubDetailView animated:YES];

//      [pubAnnotation release];
        [pubDetailView release];
}

- (void) locateMe {
        // quick hack to check if system has user location
        if (map.userLocation.coordinate.latitude != -180) {
                MKCoordinateSpan span = MKCoordinateSpanMake(0.03, 0.04);
                MKCoordinateRegion region = MKCoordinateRegionMake(map.userLocation.coordinate, span);
                [map setRegion:region animated:YES];
                [map regionThatFits:region];
        } else {
                UIAlertView* alertView = 
                [[UIAlertView alloc] initWithTitle:@"Negaliu nustatyti tavo buvimo vietos"
                                                                   message:nil 
                                                                  delegate:self 
                                                 cancelButtonTitle:@"Tiek to"
                                                 otherButtonTitles:nil];
                [alertView show];
                [alertView release];
        }
}

/*
- (void)mapView:(MKMapView *)mapView regionWillChangeAnimated:(BOOL)animated {
        NSLog(@"regionWillChangeAnimated");
}

- (void)mapView:(MKMapView *)mapView regionDidChangeAnimated:(BOOL)animated {
        NSLog(@"regionDidChangeAnimated");
        [self dynamicLoadPubAnnotationsForRegion: mapView];
}

- (void)mapViewWillStartLoadingMap:(MKMapView *)mapView {
        NSLog(@"mapViewWillStartLoadingMap");   
}
*/


-(IBAction) cityIndexChanged {
        switch (self.citySegmentControl.selectedSegmentIndex) {
                case 0:
                        [self showRegionWithLatitude:54.689313 Longitude:25.282631];
                        break;
                case 1:
                        [self showRegionWithLatitude:54.896872 Longitude:23.892426];
                        break;
                case 2:
                        [self showRegionWithLatitude:55.698541 Longitude:21.147317];
                        break;
                        
                default:
                        break;
        }
}

- (void) showRegionWithLatitude:(double)lat Longitude:(double)lon {
        CLLocationCoordinate2D regionCenter = {lat,lon};
        MKCoordinateSpan span = MKCoordinateSpanMake(0.03, 0.04);
        MKCoordinateRegion region = MKCoordinateRegionMake(regionCenter, span);
        [map setRegion:region animated:YES];
        [map regionThatFits:region];    
        
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
