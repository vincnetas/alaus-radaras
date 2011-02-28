//
//  PubDetailViewController.m
//  AlausRadaras
//
//  Created by Laurynas R on 1/26/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "PubDetailViewController.h"
#import "Brand.h"
#import "SQLiteManager.h"
#import "LocationManager.h"

@implementation PubDetailViewController

@synthesize directionsButton, pubLogoImage,pubInternetAddessLabel, pubTitleShortLabel, pubTitleLabel, pubAddressLabel, pubCallLabel;
@synthesize brandList, brandsTable, currentPub;
@synthesize userCoordinates;
@synthesize reportPubInfoView;

- (void)dealloc {
	[urlButton release];
	[addBrandSubmit release];
	[pubBrandSubmit release];
	[directionsButton release];
	[pubLogoImage release];
	[pubInternetAddessLabel release];
	[userCoordinates release];
	[currentPub release];
	[pubTitleShortLabel release];
	[pubTitleLabel release];
	[pubAddressLabel release];
	[pubCallLabel release];
	[brandsTable release];
	[brandList release];
    [super dealloc];
}


 - (void)viewDidLoad {
	 [super viewDidLoad];
	 UIColor *background = [[UIColor alloc] initWithPatternImage:[UIImage imageNamed:@"background.png"]];
	 self.view.backgroundColor = background;
	 [background release];

	 /* Transparent Background */
	 brandsTable.backgroundColor = [UIColor clearColor];
	 brandsTable.opaque = NO;
	 brandsTable.backgroundView = nil; 
	 
	 pubTitleShortLabel.text = currentPub.pubTitle;
	 pubTitleLabel.text = currentPub.pubTitle;
 	 pubAddressLabel.text = currentPub.pubAddress;
	 pubInternetAddessLabel.text = currentPub.webpage;
	 pubCallLabel.text = currentPub.phone;
	 pubLogoImage.image = [UIImage imageNamed:[NSString stringWithFormat:@"pub_%@.png", currentPub.pubId]];
	 
	 if ([@"" isEqualToString:pubInternetAddessLabel.text]) {
		 urlButton.hidden = YES;
	 }
	 
	 //AlausRadarasAppDelegate *appDelegate = (AlausRadarasAppDelegate *)[[UIApplication sharedApplication] delegate];
	 brandList = [[SQLiteManager sharedManager] getBrandsByPubId:currentPub.pubId];
	 
	 /* Disable if I view didn't get users coordinates */
	 if (userCoordinates == nil) {
		 if ([[LocationManager sharedManager]isUserLocationKnown]) {
			 CLLocationCoordinate2D userLoc = [[LocationManager sharedManager]getLocationCoordinates];
			 userCoordinates = [[NSString stringWithFormat:@"%.8f,%.8f",userLoc.latitude,userLoc.longitude]copy];			 
		 } else {
			 directionsButton.enabled = NO;	
		 }
	 }
	 NSLog(@"PubDetailViewController viewDidLoad");
	 
	 self.reportPubInfoView.frame = CGRectMake(0.0, 700.0, self.reportPubInfoView.frame.size.width, self.reportPubInfoView.frame.size.height);
	 [self.view addSubview:self.reportPubInfoView];
 }

#pragma mark -
#pragma mark Action methods

- (IBAction) gotoPreviousView:(id)sender {
	[self dismissModalViewControllerAnimated:YES];	
}

- (IBAction) dialNumber:(id)sender {
	UIAlertView* alertView = 
	[[UIAlertView alloc] initWithTitle:@"Tai skambinam į barą?"
							   message:nil 
							  delegate:self 
					 cancelButtonTitle:@"NE!"
					 otherButtonTitles:@"Skambinam!", nil];
	[alertView show];
	[alertView release];
}

-(void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
	if(buttonIndex == 1) {
		NSString *phonenumber = [NSString stringWithFormat:@"tel://%@", currentPub.phone];
		phonenumber = [phonenumber stringByReplacingOccurrencesOfString:@" " withString:@""];
		NSLog(@"Calling pub: %@", phonenumber);
		[[UIApplication sharedApplication] openURL:[NSURL URLWithString:phonenumber]];
	}
}

- (IBAction) navigateToPub:(id)sender {	
	NSLog(@"navigateToPub: from %@", userCoordinates);
	[[UIApplication sharedApplication] openURL:
		[NSURL URLWithString:[NSString stringWithFormat:@"http://maps.google.com/?saddr=%@&daddr=%.8lf,%.8lf", 
							  userCoordinates, currentPub.latitude, currentPub.longitude]]];
}


- (IBAction) openWebpage:(id)sender {	
	[[UIApplication sharedApplication] openURL:[NSURL URLWithString:currentPub.webpage]];
}

- (IBAction) reportPubInfo:(id)sender {
	[UIView beginAnimations: @"moveCNGCallout" context: nil];
	[UIView setAnimationDelegate: self];
	[UIView setAnimationDuration: 0.5];
	[UIView setAnimationCurve: UIViewAnimationCurveEaseInOut];
	self.reportPubInfoView.frame = CGRectMake(0.0, 50.0, self.reportPubInfoView.frame.size.width, self.reportPubInfoView.frame.size.height);
	[UIView commitAnimations];	
	//[self.view addSubview:self.reportPubInfoView];
}

- (IBAction) closeReportPubInfo:(id)sender {
	[UIView beginAnimations: @"moveCNGCallout" context: nil];
	[UIView setAnimationDelegate: self];
	[UIView setAnimationDuration: 0.5];
	[UIView setAnimationCurve: UIViewAnimationCurveEaseInOut];
	self.reportPubInfoView.frame = CGRectMake(0.0, 700.0, self.reportPubInfoView.frame.size.width, self.reportPubInfoView.frame.size.height);
	[UIView commitAnimations];	
	//[self.view addSubview:self.reportPubInfoView];
}

- (IBAction) keyboardDoneAction: (id)sender {
	[sender resignFirstResponder];
}



#pragma mark -
#pragma mark UITableViewDelegate methods

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
	
	static NSString *hlCellID = @"PubsBrands";
	
	UITableViewCell *hlcell = [tableView dequeueReusableCellWithIdentifier:hlCellID];
	if(hlcell == nil) {
		hlcell =  [[[UITableViewCell alloc] 
					initWithStyle:UITableViewCellStyleDefault reuseIdentifier:hlCellID] autorelease];
		hlcell.accessoryType = UITableViewCellAccessoryNone;
		hlcell.selectionStyle = UITableViewCellSelectionStyleNone;
	}
		
	int n = [brandList count];
	int i=0,i1=0; 
	int j=0;
	int yy = 4 +i1*74;
	while(i<n){
		yy = 4 +i1*74;
		for(j=0; j<4; j++){
			
			if (i>=n) break;
			
			Brand *item = [brandList objectAtIndex:i];
			
			CGRect rect = CGRectMake(18+80*j, yy, 40, 40);
			UIButton *button=[[UIButton alloc] initWithFrame:rect];
			[button setFrame:rect];
			UIImage *buttonImageNormal=[UIImage imageNamed:item.icon];
			if (buttonImageNormal == nil) {
				buttonImageNormal = [UIImage imageNamed:@"brand_default.png"];
			}
		
			[button setBackgroundImage:buttonImageNormal	forState:UIControlStateNormal];
			[button setContentMode:UIViewContentModeCenter];
			
			NSString *tagValue = [NSString stringWithFormat:@"%d", i];
			button.tag = [tagValue intValue];

			[button addTarget:self action:@selector(openPubBrandSubmit:) forControlEvents:UIControlEventTouchUpInside];
			[hlcell.contentView addSubview:button];
			[button release];
			
			UILabel *label = [[[UILabel alloc] initWithFrame:CGRectMake((80*j)-4, yy+44, 80, 12)] autorelease];
			label.text = item.label;
			label.textColor = [UIColor lightGrayColor];
			label.backgroundColor = [UIColor clearColor];
			label.textAlignment = UITextAlignmentCenter;
			label.font = [UIFont fontWithName:@"ArialMT" size:10]; 
			[hlcell.contentView addSubview:label];
			
			i++;
		}
		i1++;
	}
	if (j==4) {
		j = 0;
		yy = 4+i1*74;
	}
	NSLog(@"j:%d yy:%d", j, yy);
	CGRect rect = CGRectMake(18+80*j, yy, 40, 40);
	UIButton *button=[[UIButton alloc] initWithFrame:rect];
	[button setFrame:rect];
	UIImage *buttonImageNormal=[UIImage imageNamed:@"new_brand.png"];
	
	[button setBackgroundImage:buttonImageNormal	forState:UIControlStateNormal];
	[button setContentMode:UIViewContentModeCenter];
	
	[button addTarget:self action:@selector(openAddBrandSubmit:) forControlEvents:UIControlEventTouchUpInside];
	[hlcell.contentView addSubview:button];
	[button release];
	
	UILabel *label = [[[UILabel alloc] initWithFrame:CGRectMake((80*j)-4, yy+44, 80, 12)] autorelease];
	label.text = @"Pridėk alų";
	label.textColor = [UIColor lightGrayColor];
	label.backgroundColor = [UIColor clearColor];
	label.textAlignment = UITextAlignmentCenter;
	label.font = [UIFont fontWithName:@"ArialMT" size:10]; 
	[hlcell.contentView addSubview:label];
	
	return hlcell;
}

- (IBAction) openPubBrandSubmit: (id)sender {
	Brand *item = [brandList objectAtIndex:[sender tag]];
	pubBrandSubmit.brand = item;
	pubBrandSubmit.pubId = currentPub.pubId;
	pubBrandSubmit.modalTransitionStyle = UIModalTransitionStylePartialCurl;	
	[self presentModalViewController:pubBrandSubmit animated:YES];	
}

- (IBAction) openAddBrandSubmit: (id)sender {
	addBrandSubmit.modalTransitionStyle = UIModalTransitionStylePartialCurl;//UIModalTransitionStyleCoverVertical;	
	[self presentModalViewController:addBrandSubmit animated:YES];	
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
	return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
	return 1;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {  
	//NSMutableArray *sectionItems = [sections objectAtIndex:indexPath.section];
	int numRows = ([brandList count]/4) + 1;
	NSLog(@"Rows %d",numRows);
	return numRows * 80;//95.0;
} 

#pragma mark -
#pragma mark Other methods

- (void)didReceiveMemoryWarning {
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
	NSLog(@"PubDetailViewController: Recieved a Memory Warning... Oooops..");

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

