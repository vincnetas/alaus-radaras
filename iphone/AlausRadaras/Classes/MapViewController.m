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
#import "LocationManager.h"
#import "PubsViewController.h"
#import "PlaceTableCell.h"

@implementation MapViewController

@synthesize map, pubsOnMap, pubsAlreadyOnMap;
@synthesize infoLabel;
@synthesize citySegmentControl;
@synthesize pubTable;
@synthesize newPubSumbitView;

- (void)dealloc {
    [newPubSumbitView release];
    
	[pubTable release];
	[citySegmentControl release];
	[infoLabel release];
	[pubsAlreadyOnMap release];
	[pubsOnMap release];
	[map release];
	[super dealloc];
}

 - (void)viewDidLoad {
	 [super viewDidLoad];
	 
	 locationBased = [[LocationManager sharedManager]getVisibilityControlled];
	 
	 //mapView.mapType = MKMapTypeSatellite;
	 map.mapType=MKMapTypeStandard;
	 //mapView.mapType=MKMapTypeHybrid;
	 
	 map.showsUserLocation = YES;
	 CLLocationCoordinate2D userPosition = map.userLocation.location.coordinate;
	 CLLocationCoordinate2D centerOfVilnius = {54.689313,25.282631};
	 CLLocationCoordinate2D centerMap = centerOfVilnius;

	 
	 pubsAlreadyOnMap = [[NSMutableArray alloc]init];
	 
	 [self loadPubAnnotations];
	 
	 CLLocationCoordinate2D db = [[LocationManager sharedManager]getLocationCoordinates];
	 NSLog(@"\nUI:%.8f, %.8f\nDB:%.8f, %.8f",userPosition.latitude, userPosition.longitude, db.latitude, db.longitude);
	 
	 if (locationBased) {
		 double di = [[LocationManager sharedManager]getDistance];
		 infoLabel.text = [NSString stringWithFormat:@"%@ • (%.0f Km atstumu)", infoLabel.text, di] ;
		 centerMap = [[LocationManager sharedManager]getLocationCoordinates];
	 }
	
	 MKCoordinateSpan coordSpan = MKCoordinateSpanMake(0.04, 0.05);
	 MKCoordinateRegion region = MKCoordinateRegionMake(centerMap, coordSpan);
	 map.region = region;
	 
	[pubTable setHidden:YES];
	 UIColor *background = [[UIColor alloc] initWithPatternImage:[UIImage imageNamed:@"background.png"]];
	 self.view.backgroundColor = background;
	 [background release];
	 
	 /* Transparent Background */
	 pubTable.backgroundColor = [UIColor clearColor];
	 pubTable.opaque = NO;
	 
	 /* Separator color */ 
	 pubTable.separatorColor = [UIColor grayColor];
	 NSLog(@"MapViewController viewDidLoad");
}

- (void)mapView:(MKMapView *)mapView didUpdateUserLocation:(MKUserLocation *)userLocation {
	NSLog(@"didUpdateUserLocation");
	
}


- (IBAction) gotoPreviousView:(id)sender {
	[self dismissModalViewControllerAnimated:YES];	
}

- (IBAction) locateMe:(id)sender {
	[self locateMe];
}

- (IBAction) showPubList:(id)sender {
	PubsViewController *pubsView = 
		[[PubsViewController alloc] initWithNibName:nil bundle:nil];
	
	pubsView.pubList = pubsOnMap;
	pubsView.modalTransitionStyle = UIModalTransitionStyleCoverVertical;	
	[self presentModalViewController:pubsView animated:YES];
	
	[pubsView release];
}



- (void)loadPubAnnotations {
	for (Pub *pub in pubsOnMap) {
		CLLocationCoordinate2D coord;
		coord.latitude = pub.latitude;
		coord.longitude = pub.longitude;
		
		PubAnnotation *pubAnnotation = [[PubAnnotation alloc] initWithCoordinate:coord];
		[pubAnnotation setPubId:pub.pubId];
		[pubAnnotation setTitle:pub.pubTitle];
		
		if (pub.distance != 0 && locationBased){
			[pubAnnotation setSubtitle:[NSString stringWithFormat:@"%@ • (~%.2f Km)",pub.pubAddress,pub.distance]];
		} else {
			[pubAnnotation setSubtitle:[NSString stringWithFormat:@"%@",pub.pubAddress]];			
		}
		[map addAnnotation:pubAnnotation];
	}
}

- (MKAnnotationView *) mapView:(MKMapView *) mapView viewForAnnotation:(id ) annotation {
//	NSLog(@"viewForAnnotation");

	if ([annotation isKindOfClass:[MKUserLocation class]]) {
		//Don't trample the user location annotation (pulsing blue dot).
		return nil;
	}
	
	MKAnnotationView *customAnnotationView=[[[MKAnnotationView alloc] initWithAnnotation:annotation reuseIdentifier:nil] autorelease];
	UIImage *pinImage = [UIImage imageNamed:@"pin.png"];
	[customAnnotationView setImage:pinImage];
    customAnnotationView.canShowCallout = YES;
	
//	UIImageView *leftIconView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"pub_obuolys_icon.png"]];
//	customAnnotationView.leftCalloutAccessoryView = leftIconView;
	
	UIButton *rightButton = [UIButton buttonWithType:UIButtonTypeDetailDisclosure];
//	[rightButton addTarget:self action:@selector(annotationViewClick:) forControlEvents:UIControlEventTouchUpInside];
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

	[pubDetailView release];
}

- (void) locateMe {
	// quick hack to check if system has user location
	if (map.userLocation.coordinate.latitude != -180) {
		
		CLLocationCoordinate2D userCoordinate = map.userLocation.location.coordinate;
		NSLog(@"%@", [NSString stringWithFormat:@"%f,%f",userCoordinate.latitude, userCoordinate.longitude]);
		
		MKCoordinateSpan span = MKCoordinateSpanMake(0.03, 0.04);
		MKCoordinateRegion region = MKCoordinateRegionMake(map.userLocation.coordinate, span);
		[map setRegion:region animated:YES];
		[map regionThatFits:region];
	} else {
		UIAlertView* alertView = 
		[[UIAlertView alloc] initWithTitle:@"Nežinau kur randiesi... hm gal Jūs sacharoje?"
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
			[pubTable setHidden:YES];         
			[map setHidden:NO];
            [locateMeBtn setHidden:NO];
			break;
		case 1:
			[map setHidden:YES];
            [locateMeBtn setHidden:YES];
			[pubTable setHidden:NO];
			break;
		case 2:
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




















#pragma mark -
#pragma mark Table view methods

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
	return [pubsOnMap count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    static NSString *MyIdentifier = @"PlaceCell";
    
    PlaceTableCell *cell = (PlaceTableCell *)[tableView dequeueReusableCellWithIdentifier:MyIdentifier];
    if(cell == nil) {
        [[NSBundle mainBundle] loadNibNamed:@"PlaceTableCell" owner:self options:nil];
        cell = placeCell;
    }
    
	Pub *pub = [pubsOnMap objectAtIndex:indexPath.row];
	cell.titleText.text = [[pub pubTitle]copy];
    cell.addressText.text = [NSString stringWithFormat:@"%@, %@",[[pub pubAddress]copy],[[pub city]copy]];	
    
	if (pub.distance != 0 && locationBased){
		cell.distanceText.text = [NSString stringWithFormat:@"~%.2f km", [pub distance]];
	}
    
	cell.icon.image = [UIImage imageNamed:@"beer_02.png"];
	
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
	[tableView deselectRowAtIndexPath:indexPath animated:YES];	
	
	PubDetailViewController *pubDetailView = 
		[[PubDetailViewController alloc] initWithNibName:nil bundle:nil];
	
	pubDetailView.currentPub = [[SQLiteManager sharedManager] getPubById:[[pubsOnMap objectAtIndex:indexPath.row]pubId]];
	
    CLLocationCoordinate2D userCoordinate = map.userLocation.location.coordinate;
	pubDetailView.userCoordinates = [NSString stringWithFormat:@"%f,%f",userCoordinate.latitude,userCoordinate.longitude];
	pubDetailView.modalTransitionStyle = UIModalTransitionStyleCoverVertical;	
	[self presentModalViewController:pubDetailView animated:YES];
	
	[pubDetailView release];
}

- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath {
    return NO;
}








- (IBAction) openAddNewPubView: (id)sender {
//    newPubSubmit.pub = currentPub;
    
	newPubSumbitView.modalTransitionStyle = UIModalTransitionStyleCoverVertical;//UIModalTransitionStyleCoverVertical;	
	[self presentModalViewController:newPubSumbitView animated:YES];	
}






#pragma mark -
#pragma mark NSURLConnection methods



- (void) postData:(NSString *) params msg:(NSString *)msg {
	NSLog(@"MapViewController postData");
	thankYouMsg = msg;
	NSData *postData = [params dataUsingEncoding:NSASCIIStringEncoding allowLossyConversion:YES];
	NSString *postLength = [NSString stringWithFormat:@"%d", [params length]];
	
	NSMutableURLRequest *request = [[[NSMutableURLRequest alloc] init] autorelease];
	[request setURL:[NSURL URLWithString:@"http://alausradaras.lt/android/submit.php"]];
	[request setHTTPMethod:@"POST"];
	[request setValue:postLength forHTTPHeaderField:@"Content-Length"];
	[request setValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"Content-Type"];
	[request setHTTPBody:postData];
	
	[[NSURLConnection alloc] initWithRequest:request delegate:self];
}


- (void)connection:(NSURLConnection *)connection didFailWithError:(NSError *)error {
    // release the connection, and the data object
    [connection release];
    
    NSLog(@"Connection failed! Error - %@ %@",
          [error localizedDescription],
          [[error userInfo] objectForKey:NSURLErrorFailingURLStringErrorKey]);
	
	//Connection error occured
	UIAlertView* alertView = 
    [[UIAlertView alloc] initWithTitle:@"Nepavyko nusiųsti... gal dar alaus?"
                               message:nil 
                              delegate:self 
                     cancelButtonTitle:@"Meginsiu vėliau"
                     otherButtonTitles:nil];
	[alertView show];
	[alertView release];
}

- (void)connectionDidFinishLoading:(NSURLConnection *)connection {
    NSLog(@"New Pub Successfully Sent");

	MBProgressHUD *HUD = [[MBProgressHUD alloc] initWithView:self.view];
	HUD.customView = [[[UIImageView alloc] initWithImage:[UIImage imageNamed:@""]] autorelease];
	HUD.mode = MBProgressHUDModeCustomView;
	[self.view addSubview:HUD];
	HUD.delegate = self;
	HUD.labelText = thankYouMsg;//@"Tik alus išgelbės mus!";
	[HUD showWhileExecuting:@selector(delay) onTarget:self withObject:nil animated:YES];
	[HUD release];
}

- (void)delay {
	sleep(2);
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

