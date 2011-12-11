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
    newPubMap.delegate = nil;
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
    
    newPubMap.showsUserLocation = YES;
    

    UILongPressGestureRecognizer *lpgr = [[UILongPressGestureRecognizer alloc] 
                                          initWithTarget:self action:@selector(handleLongPress:)];
    lpgr.minimumPressDuration = 0.5; //user needs to press for 2 seconds
    [self.newPubMap addGestureRecognizer:lpgr];
    [lpgr release];
    

}

- (void)viewDidUnload {
	// Release any retained subviews of the main view.
	// e.g. self.myOutlet = nil;
	
	self.newPubMap.delegate = nil;
	self.newPubMap = nil;
    [super viewDidUnload];
}


- (void)viewWillAppear:(BOOL)animated {
//	[msgText becomeFirstResponder];
    
    // RESET SCREEN
    CLLocationCoordinate2D userPosition = [[LocationManager sharedManager] getLocationCoordinates];//newPubMap.userLocation.location.
    MKCoordinateSpan coordSpan = MKCoordinateSpanMake(0.005, 0.005);
    MKCoordinateRegion region = MKCoordinateRegionMake(userPosition, coordSpan);
    newPubMap.region = region;

    msgText.text = @"";
    if (newPubPin != nil) {
        [self.newPubMap removeAnnotation:newPubPin];
    }
    [self textFieldShouldReturn: msgText];
    [msgText resignFirstResponder];
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(coordinateChanged_:) name:@"DDAnnotationCoordinateDidChangeNotification" object:nil];
	[super viewDidAppear:animated];
    
    
//    MBProgressHUD *HUD = [[MBProgressHUD alloc] initWithView:self.view];
//	HUD.customView = [[[UIImageView alloc] initWithImage:[UIImage imageNamed:@""]] autorelease];
//	HUD.mode = MBProgressHUDModeCustomView;
//	[self.view addSubview:HUD];
////	HUD.delegate = self;
//	HUD.labelText = @"Pažymėk rastą barą";
//	[HUD showWhileExecuting:@selector(delay) onTarget:self withObject:nil animated:YES];
//	[HUD release];
}

//- (void)delay {
//	sleep(2);
//}


- (void)viewWillDisappear:(BOOL)animated {
	[super viewWillDisappear:animated];	
	// NOTE: This is optional, DDAnnotationCoordinateDidChangeNotification only fired in iPhone OS 3, not in iOS 4.
	[[NSNotificationCenter defaultCenter] removeObserver:self name:@"DDAnnotationCoordinateDidChangeNotification" object:nil];	
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










#pragma mark "-- text editing support --"
// Animate the entire view up or down, to prevent the keyboard from covering the text field.
- (void)moveView:(int)offset
{
    [UIView beginAnimations:nil context:NULL];
    [UIView setAnimationDuration:0.3];
    // Make changes to the view's frame inside the animation block. They will be animated instead
    // of taking place immediately.

    CGRect rect = msgText.frame;
    rect.origin.y -= offset;
    msgText.frame = rect;
    
    NSLog(@"offset:%i",offset);
    
    CGRect mapRect = newPubMap.frame;

    NSLog(@"mapRect.origin.y:%f",mapRect.origin.y);
    NSLog(@"mapRect.size.height:%f",mapRect.size.height);

    mapRect.size.height -= offset;
    newPubMap.frame = mapRect;
    
    [UIView commitAnimations];
}


- (BOOL)textFieldShouldReturn:(UITextField *) sender {
    [sender resignFirstResponder];
    if (msgTextVerticalOffset!=0)
    {
        [self moveView: - msgTextVerticalOffset];
        msgTextVerticalOffset = 0;
    }
//    [self recenterMap];
    return TRUE;
}

- (void)textFieldDidBeginEditing:(UITextField *)theTextField {
    int wantedOffset = theTextField.frame.origin.y-200;
    if ( wantedOffset < 0 ) { 
        wantedOffset = 0;
    } 
    if ( wantedOffset != msgTextVerticalOffset ) {
        [self moveView: wantedOffset - msgTextVerticalOffset];
        msgTextVerticalOffset = wantedOffset;
    }
//    [self recenterMap];
}


- (void) recenterMap {
    // Re-center minimized/maximized map:
     if (newPubPin != nil) {
         newPubMap.centerCoordinate = newPubPin.coordinate;
     } else {
         newPubMap.centerCoordinate = [[LocationManager sharedManager] getLocationCoordinates];
     }
}







- (void)didReceiveMemoryWarning {
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
}

@end
