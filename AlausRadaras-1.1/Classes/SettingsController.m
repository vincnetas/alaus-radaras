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
	UIColor *background = [[UIColor alloc] initWithPatternImage:[UIImage imageNamed:@"black-background.png"]];
	self.view.backgroundColor = background;
	[background release];
	
	NSUserDefaults *standardUserDefaults = [NSUserDefaults standardUserDefaults];
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
	[self.parentViewController dismissModalViewControllerAnimated:YES];	
}

- (void) viewDidDisappear:(BOOL)animated {
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
