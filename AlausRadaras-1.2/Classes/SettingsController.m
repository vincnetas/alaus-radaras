    //
//  SettingsController.m
//  AlausRadaras
//
//  Created by Laurynas R on 2/21/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "SettingsController.h"
#import "LocationManager.h"

@implementation SettingsController

@synthesize visibilityControl, visibleDistanceSlider, distanceLabel;

- (void)dealloc {
	[visibilityControl release];
	[visibleDistanceSlider release];
	[distanceLabel release];
    [super dealloc];
}

- (void)viewDidLoad {
	/* Application setup */
    [super viewDidLoad];

	
	[[LocationManager sharedManager]initializeManager];

	
	NSUserDefaults *standardUserDefaults = [NSUserDefaults standardUserDefaults];
	BOOL enableAllFeatures = [standardUserDefaults boolForKey:@"EnableAllFeatures"];
	UIColor *background;
	/* iOS3.1 Support */
	if (enableAllFeatures) {
		background = [[UIColor alloc] initWithPatternImage:[UIImage imageNamed:@"black-background.png"]];
	} else {
		background = [[UIColor alloc] initWithPatternImage:[UIImage imageNamed:@"black-background-small.png"]];
		visibilityControl.segmentedControlStyle = UISegmentedControlStyleBar;	
		
		CGRect rect = CGRectMake(0, 0, 320, 260);
		UIButton *button=[[UIButton alloc] initWithFrame:rect];
		[button setFrame:rect];
		[button setBackgroundColor:[UIColor clearColor]];
		//	[button setBackgroundImage:buttonImageNormal forState:UIControlStateNormal];
		[button setContentMode:UIViewContentModeCenter];
		[button addTarget:self action:@selector(hideSettings:) forControlEvents:UIControlEventTouchUpInside];
		[self.view addSubview:button];
		[button release];
	}
	self.view.backgroundColor = background;
	[background release];
	
	//self.view.backgroundColor = [UIColor clearColor];
	self.view.opaque = NO;
	
	if (standardUserDefaults) {
		BOOL visibilityControlled = [standardUserDefaults boolForKey:@"VisibilityControlled"];
		visibilityDistance = [standardUserDefaults integerForKey:@"VisibilityDistance"];
		[[LocationManager sharedManager]setVisibilityControlled:visibilityControlled];
		
		if (visibilityControlled) {
			visibilityControl.selectedSegmentIndex = 1;
		} else {
			visibilityControl.selectedSegmentIndex = 0;
		}
		visibleDistanceSlider.value = visibilityDistance;
	}
	if (visibilityDistance == 0) {
		visibilityDistance = 16;
	}
	
	[[LocationManager sharedManager]setDistance:visibilityDistance];
	[self visibilityControlIndexChanged];
	
	NSLog(@"SettingsController viewDidLoad");
}

-(IBAction) hideSettings:(id) sender {
	NSLog(@"hideSettings");
	
	//	CATransition *animation = [CATransition animation];
	//	[animation setDelegate:self];
	//	//kCATransitionMoveIn, kCATransitionPush, kCATransitionReveal, kCATransitionFade
	//	//kCATransitionFromLeft, kCATransitionFromRight, kCATransitionFromTop, kCATransitionFromBottom
	//	[animation setType:kCATransitionMoveIn];
	//	[animation setSubtype:kCATransitionFade];
	//	[animation setDuration:0.75];
	//	[animation setTimingFunction:[CAMediaTimingFunction functionWithName:kCAMediaTimingFunctionEaseOut]];
	//	[[settingsController.view layer] addAnimation:animation forKey:kCATransition];
	
	[self.view removeFromSuperview];
	self.view.alpha = 0.0;
	[self viewDidDisappear:NO];
	/* Fade-out 
	 [UIView animateWithDuration:0.2
	 animations:^{settingsController.view.alpha = 0.0;}
	 completion:^(BOOL finished){[settingsController.view removeFromSuperview];}];
	 */
}

-(IBAction) visibilityControlIndexChanged {
	switch (self.visibilityControl.selectedSegmentIndex) {
		case 0:
			NSLog(@"Setting Infinity selected.");
			visibleDistanceSlider.enabled = NO;
			visibleDistanceSlider.value = 30;
			distanceLabel.text = @"∞";
			break;
		case 1:
			NSLog(@"Setting Radius selected.");

			//TODO: check if location services are enabled

			visibleDistanceSlider.enabled = YES;
			visibleDistanceSlider.value = visibilityDistance;
			distanceLabel.text =  [[NSString alloc]initWithFormat:@"%i", visibilityDistance];
			break;
			
		default:
			break;
	}
}


- (IBAction) visibilityDistanceChanged:(id)sender {
	UISlider *visibilitySlider = (UISlider *) sender;
	visibilityDistance = (int) visibilitySlider.value;
	distanceLabel.text =  [[NSString alloc]initWithFormat:@"%i", visibilityDistance];
}

- (IBAction) gotoPreviousView {
	NSLog(@"gotoPreviousView");
	[self.parentViewController dismissModalViewControllerAnimated:YES];	
}

- (void) viewDidDisappear:(BOOL)animated {
	NSLog(@"SettingsController viewDidDisappear");
	
	NSUserDefaults *standardUserDefaults = [NSUserDefaults standardUserDefaults];
	if (standardUserDefaults) {
		[standardUserDefaults setBool:visibleDistanceSlider.enabled forKey:@"VisibilityControlled"];
		[standardUserDefaults setInteger:visibilityDistance forKey:@"VisibilityDistance"];
		[standardUserDefaults synchronize];
	}
	[[LocationManager sharedManager]setDistance:visibilityDistance];
	[[LocationManager sharedManager]setVisibilityControlled:visibleDistanceSlider.enabled ];
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
 // Implement loadView to create a view hierarchy programmatically, without using a nib.
 - (void)loadView {
 }
 */

/*
 // Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
 - (void)viewDidLoad {
 [super viewDidLoad];
 }
 */

/*
 // Override to allow orientations other than the default portrait orientation.
 - (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
 // Return YES for supported orientations.
 return (interfaceOrientation == UIInterfaceOrientationPortrait);
 }
 */
