//
//  AlausRadarasViewController.m
//  AlausRadaras
//
//  Created by Laurynas R on 1/21/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "AlausRadarasViewController.h"
#import "Pub.h"
#import "SQLiteManager.h";
#import "LocationManager.h";
//vibrate
#import <AudioToolbox/AudioServices.h>
#import <QuartzCore/QuartzCore.h>

#import "JSON/JSON.h"


@implementation AlausRadarasViewController

@synthesize pintCountLabel, infoView;

- (void)dealloc {
	[infoView release];
	[settingsController release];
	[luckyController release];
	[beerCounterController release];
	[pintCountLabel release];
    [super dealloc];
}

// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
	/* Application setup */
    [super viewDidLoad];
	UIColor *background = [[UIColor alloc] initWithPatternImage:[UIImage imageNamed:@"background.png"]];
	self.view.backgroundColor = background;
	[background release];
	
	
//	NSString *params = 
//		[NSString stringWithFormat:
//		 @"lastUpdate=2011-04-01"];
//	NSData *postData = [params dataUsingEncoding:NSASCIIStringEncoding allowLossyConversion:YES];
//	NSString *postLength = [NSString stringWithFormat:@"%d", [params length]];
//	

	responseData = [[NSMutableData data] retain];

	
	NSMutableURLRequest *request = [[[NSMutableURLRequest alloc] init] autorelease];
	[request setURL:[NSURL URLWithString:@"http://www.alausradaras.lt/json?lastUpdate=2011-04-01"]];
	[request setHTTPMethod:@"GET"];
//	[request setValue:postLength forHTTPHeaderField:@"Content-Length"];
	[request setValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"Content-Type"];
//	[request setHTTPBody:postData];
	
	[[NSURLConnection alloc] initWithRequest:request delegate:self];
	
}

- (void)connection:(NSURLConnection *)connection didFailWithError:(NSError *)error {
    // release the connection, and the data object
    [connection release];
	
    NSLog(@"Connection failed! Error - %@ %@",
          [error localizedDescription],
          [[error userInfo] objectForKey:NSURLErrorFailingURLStringErrorKey]);
}

- (void)connectionDidFinishLoading:(NSURLConnection *)connection {
    NSLog(@"JSON connectionDidFinishLoading");
	[connection release];
	
	NSString *responseString = [[NSString alloc] initWithData:responseData encoding:NSUTF8StringEncoding];
//	[responseData release];
	
	NSDictionary *results = [responseString JSONValue];
	NSArray *brands = [[results objectForKey:@"update"] objectForKey:@"brands"];

	NSLog(@"VISO: %i", [brands count]);
	
	for (NSDictionary *brand in brands){		
		NSLog(@"%@\n", [brand objectForKey:@"title"]);
	}
	
	//NSLog(@"%@",responseString);
}

- (void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)data {
   NSLog(@"JSON didReceiveData: %i", 	[data length]);	
	[responseData appendData:data];
}

- (void)connection:(NSURLConnection *)connection didReceiveResponse:(NSURLResponse *)response {
	NSLog(@"JSON didReceiveResponse");	
	[responseData setLength:0];

}


- (void)viewDidAppear:(BOOL)animated {
	NSLog(@"AlausRadarasViewController viewDidAppear");
	/* Get Total Beers from user defaults */
	NSUserDefaults *standardUserDefaults = [NSUserDefaults standardUserDefaults];
	beerCount = 0;
	if (standardUserDefaults) {
		beerCount = [standardUserDefaults integerForKey:@"TotalBeers"];
		int currentBeerCount = [standardUserDefaults integerForKey:@"CurrentBeers"];
		beerCount += currentBeerCount;
	}
	pintCountLabel.text = [NSString stringWithFormat:@"%i", beerCount];
		
	enableAllFeatures = [standardUserDefaults boolForKey:@"EnableAllFeatures"];

	[self becomeFirstResponder];
	[super viewDidAppear:animated];
}

- (void)viewWillDisappear:(BOOL)animated {
    [self resignFirstResponder];
	[infoView removeFromSuperview];
    [super viewWillDisappear:animated];
}

-(IBAction) clickPint:(id) sender {
	beerCounterController.modalTransitionStyle = UIModalTransitionStyleCrossDissolve;	
	[self presentModalViewController:beerCounterController animated:YES];
}

-(IBAction) clickBeers:(id) sender {
	BrandsViewController *brandsView = 
		[[BrandsViewController alloc] initWithNibName:nil bundle:nil];
	brandsView.modalTransitionStyle = UIModalTransitionStyleCoverVertical;	
	[self presentModalViewController:brandsView animated:YES];
	[brandsView release];
}

-(IBAction) clickPlaces:(id) sender {
	MapViewController *vietosView = 
			[[MapViewController alloc] initWithNibName:nil bundle:nil];
	[vietosView setPubsOnMap:[[[SQLiteManager sharedManager]getPubs]autorelease]];
	vietosView.modalTransitionStyle = UIModalTransitionStyleFlipHorizontal;	
	[self presentModalViewController:vietosView animated:YES];
	[vietosView release];
}

-(IBAction) clickLucky:(id) sender {
	luckyController.modalTransitionStyle = UIModalTransitionStyleCoverVertical;	
	[self presentModalViewController:luckyController animated:YES];
}


-(IBAction) clickSettings:(id) sender {
	NSLog(@"clickSettings");
	
	if (enableAllFeatures) {
		settingsController.modalTransitionStyle = UIModalTransitionStylePartialCurl;	
		[self presentModalViewController:settingsController animated:YES];
	} else {
		//	UIViewController * myViewController = [[UIViewController alloc] initWithNibName:@"TestView" bundle:nil];
		//	settingsController = [[SettingsController alloc] initWithNibName:nil bundle:nil];
		settingsController.view.alpha = 1.0;
		
		[self.view addSubview: settingsController.view];
		CATransition *animation = [CATransition animation];
		[animation setDelegate:self];
		//kCATransitionMoveIn, kCATransitionPush, kCATransitionReveal, kCATransitionFade
		//kCATransitionFromLeft, kCATransitionFromRight, kCATransitionFromTop, kCATransitionFromBottom
		[animation setType:kCATransitionMoveIn];
		[animation setSubtype:kCATransitionFade];
		[animation setDuration:0.75];
		[animation setTimingFunction:[CAMediaTimingFunction functionWithName:kCAMediaTimingFunctionEaseInEaseOut]];
		[[settingsController.view layer] addAnimation:animation forKey:kCATransition];				
	}
}

-(IBAction) clickInfo:(id) sender {
	[self.view addSubview:infoView];
}
-(IBAction) removeInfo:(id) sender {
	[infoView removeFromSuperview];
}

- (BOOL)canBecomeFirstResponder {
	return YES;
}

- (void)motionEnded:(UIEventSubtype)motion withEvent:(UIEvent *)event {
	if (event.type == UIEventSubtypeMotionShake) {
		NSLog(@"Shake detected - opening taxi view");
		AudioServicesPlaySystemSound(kSystemSoundID_Vibrate);
		TaxiViewController *taxiView = 
			[[TaxiViewController alloc] initWithNibName:nil bundle:nil];
		taxiView.modalTransitionStyle = UIModalTransitionStyleCoverVertical;	
		[self presentModalViewController:taxiView animated:YES];
		[taxiView release];
	}
	[super motionEnded: motion withEvent: event];
}




- (void)didReceiveMemoryWarning {
	NSLog(@"AlausRadarasViewController: Recieved a Memory Warning... Oooops..");
    [super didReceiveMemoryWarning];
}



@end



