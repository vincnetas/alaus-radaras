//
//  NewPubSubmit.m
//  AlausRadaras
//
//  Created by Laurynas R on 9/3/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "NewPubSubmit.h"
#import "DataPublisher.h"
#import "LocationManager.h"

@implementation NewPubSubmit


@synthesize newPubMap, newPubPin, msgText;


- (void)dealloc {
    [newPubPin release];
    [newPubMap release];
    [msgText release];
    [super dealloc];
}



#pragma mark - View lifecycle

- (void)viewDidLoad {
    [super viewDidLoad];
    
    UIColor *background = [[UIColor alloc] initWithPatternImage:[UIImage imageNamed:@"background.png"]];
    self.view.backgroundColor = background;
    [background release];

    
    
    // MAP
    
//    map.hidden = YES;
    newPubMap.showsUserLocation = YES;
    
    CLLocationCoordinate2D userPosition = [[LocationManager sharedManager] getLocationCoordinates];//newPubMap.userLocation.location.coordinate;
//    CLLocationCoordinate2D centerOfVilnius = {54.689313,25.282631};

    /* Pub Location 
    CLLocationCoordinate2D pubCoordinates;
    pubCoordinates.latitude = currentPub.latitude;
    pubCoordinates.longitude = currentPub.longitude;
    */
    MKCoordinateSpan coordSpan = MKCoordinateSpanMake(0.01, 0.02);
    MKCoordinateRegion region = MKCoordinateRegionMake(userPosition, coordSpan);
    newPubMap.region = region;

    UILongPressGestureRecognizer *lpgr = [[UILongPressGestureRecognizer alloc] 
                                          initWithTarget:self action:@selector(handleLongPress:)];
    lpgr.minimumPressDuration = 0.5; //user needs to press for 2 seconds
    [self.newPubMap addGestureRecognizer:lpgr];
    [lpgr release];
}

- (void)viewWillAppear:(BOOL)animated {
	[msgText becomeFirstResponder];
	[super viewDidAppear:animated];
}


- (void)handleLongPress:(UIGestureRecognizer *)gestureRecognizer
{
    if (gestureRecognizer.state != UIGestureRecognizerStateBegan)
        return;
 
    if (newPubPin != nil){
        [self.newPubMap removeAnnotation:newPubPin];
    }
    CGPoint touchPoint = [gestureRecognizer locationInView:self.newPubMap];   
    CLLocationCoordinate2D touchMapCoordinate = 
    [self.newPubMap convertPoint:touchPoint toCoordinateFromView:self.newPubMap];
    
    newPubPin = [[PubAnnotation alloc] initWithCoordinate:touchMapCoordinate];
    [self.newPubMap addAnnotation:newPubPin];
   
}



- (IBAction) gotoPreviousView {
	[self.parentViewController dismissModalViewControllerAnimated:YES];	
}


- (IBAction) sendNewPubSubmit {
	UIAlertView* alertView = 
	[[UIAlertView alloc] initWithTitle:@"Pranešk apie barą"
							   message:nil 
							  delegate:self 
					 cancelButtonTitle:@"Tiek to"
					 otherButtonTitles:@"Esu blaivas!!", nil];
	[alertView show];
	[alertView release];	
}

-(void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
	if(buttonIndex == 1) {
		// check if submited in 12 hours?		
		NSString *uniqueIdentifier = [[UIDevice currentDevice] uniqueIdentifier];
        float lat, lon;
        if (newPubPin != nil) {
            lat = newPubPin.coordinate.latitude;
            lon = newPubPin.coordinate.longitude;
        }
        
        NSLog(@"Data can be published");
        CLLocationCoordinate2D coords = [[LocationManager sharedManager]getLocationCoordinates];
        NSString *post = 
            [NSString stringWithFormat:
             @"type=newPub&message=%@&isNear=%@&location.latitude=%.8f&location.longitude=%.8f",
             [NSString stringWithFormat:@"UID: %@ Message: %@ Lat: %.8f Long: %.8f", uniqueIdentifier, msgText.text, lat, lon], @"isNearTest", coords.latitude, coords.longitude];
        
        [self.parentViewController postData:post msg:@"+1000 taškų už pilietiškumą. Dėkui! :)"];
        
		//msgTextView.text = @"";
		//[submitBtn setEnabled:NO];
        
		[self.parentViewController dismissModalViewControllerAnimated:YES];
	}
}





- (void)viewDidUnload {
    [super viewDidUnload];
}

- (void)didReceiveMemoryWarning {
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
}

@end
