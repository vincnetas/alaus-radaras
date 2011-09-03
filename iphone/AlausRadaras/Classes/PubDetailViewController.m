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
#import "MBProgressHUD.h"
#import "DataPublisher.h"
#import <QuartzCore/QuartzCore.h>

@implementation PubDetailViewController

@synthesize directionsButton, pubLogoImage,pubInternetAddessLabel, pubTitleShortLabel, pubTitleLabel, pubAddressLabel, pubCallLabel;
@synthesize brandList, currentPub;
@synthesize brandsTable;
@synthesize userCoordinates;
@synthesize reportBrandId, reportStatus, brandReportView;

- (void)dealloc {
	[reportBrandId release];
	[reportStatus release];
	
	[brandReportView release];
	[urlButton release];
	[newBrandSubmit release];
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
	 /* iOS3.1 Support */	 
	 //brandsTable.backgroundView = nil; 
     NSLog(@"PubDetailViewController start viewDidLoad");	 

	 pubTitleShortLabel.text = currentPub.pubTitle;
	 pubTitleLabel.text = currentPub.pubTitle;
 	 pubAddressLabel.text = [NSString stringWithFormat:@"%@, %@", currentPub.pubAddress, currentPub.city];
	 pubInternetAddessLabel.text = currentPub.webpage;
	 pubCallLabel.text = currentPub.phone;
	 pubLogoImage.image = [UIImage imageNamed:[NSString stringWithFormat:@"pub_%@.png", currentPub.pubId]];
	 
	 if ([@"" isEqualToString:pubInternetAddessLabel.text]) {
		 urlButton.hidden = YES;
	 }
	 
	 //AlausRadarasAppDelegate *appDelegate = (AlausRadarasAppDelegate *)[[UIApplication sharedApplication] delegate];
	 brandList = [[SQLiteManager sharedManager] getBeersByPubId:currentPub.pubId];
	 
	 /* Disable if I view didn't get users coordinates */
	 if (userCoordinates == nil) {
		 if ([[LocationManager sharedManager]isUserLocationKnown]) {
			 CLLocationCoordinate2D userLoc = [[LocationManager sharedManager]getLocationCoordinates];
			 userCoordinates = [[NSString stringWithFormat:@"%.8f,%.8f",userLoc.latitude,userLoc.longitude]copy];			 
		 } else {
			 directionsButton.enabled = NO;	
		 }
	 }
	 
	 
	 NSUserDefaults *standardUserDefaults = [NSUserDefaults standardUserDefaults];
	 enableAllFeatures = [standardUserDefaults boolForKey:@"EnableAllFeatures"];
	 
	 NSLog(@"PubDetailViewController viewDidLoad");	 
}


#pragma mark -
#pragma mark Action methods

- (IBAction) gotoPreviousView:(id)sender {
	[self dismissModalViewControllerAnimated:YES];	
}

- (IBAction) dialNumber:(id)sender {
	callAlertBox = TRUE;
	UIAlertView* alertView = 
	[[UIAlertView alloc] initWithTitle:@"Tai skambinam į barą?"
							   message:nil 
							  delegate:self 
					 cancelButtonTitle:@"NE!"
					 otherButtonTitles:@"Skambinam!", nil];
	[alertView show];
	[alertView release];
}

//-(void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
//
//}

- (IBAction) navigateToPub:(id)sender {	
	NSLog(@"navigateToPub: from %@", userCoordinates);
	[[UIApplication sharedApplication] openURL:
		[NSURL URLWithString:[NSString stringWithFormat:@"http://maps.google.com/?saddr=%@&daddr=%.8lf,%.8lf", 
							  userCoordinates, currentPub.latitude, currentPub.longitude]]];
}


- (IBAction) openWebpage:(id)sender {	
	[[UIApplication sharedApplication] openURL:[NSURL URLWithString:currentPub.webpage]];
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
				buttonImageNormal = [UIImage imageNamed:@"beer_default.png"];
			}
		
			[button setBackgroundImage:buttonImageNormal	forState:UIControlStateNormal];
			[button setContentMode:UIViewContentModeCenter];
			
			NSString *tagValue = [NSString stringWithFormat:@"%d", i];
			button.tag = [tagValue intValue];

			[button addTarget:self action:@selector(openPubBrandSubmit:) forControlEvents:UIControlEventTouchUpInside];
			[hlcell.contentView addSubview:button];
			[button release];
			
			UILabel *label = [[[UILabel alloc] initWithFrame:CGRectMake((80*j-2), yy+42, 80, 12)] autorelease];
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
	reportBrandId = [[[brandList objectAtIndex:[sender tag]] brandId ]copy];
	
	// open a dialog with two custom buttons
	UIActionSheet *actionSheet = [[UIActionSheet alloc] initWithTitle:[NSString stringWithFormat:@"%@", [[brandList objectAtIndex:[sender tag]]label]]
															 delegate:self cancelButtonTitle:nil destructiveButtonTitle:nil
													otherButtonTitles:@"Vis dar yra", @"Dingo", @"Išgėrė", @"Tiek to", nil];
	actionSheet.actionSheetStyle = UIActionSheetStyleBlackTranslucent;
	actionSheet.cancelButtonIndex = 3;
	[actionSheet showInView:self.view]; 
	[actionSheet release];
}

#pragma mark -
#pragma mark - UIActionSheetDelegate

- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex {
	switch (buttonIndex) {
		case 0:
			reportStatus = @"EXISTS";
			[self sendPubBrandSubmit];
			break;
			
		case 1:
			reportStatus = @"DISCONTINUED";
			[self sendPubBrandSubmit];
			break;
			
		case 2:
			reportStatus = @"TEMPORARY_SOLD_OUT";
			[self sendPubBrandSubmit];
			break;
			
		default:
			break;
	}
}
- (void) sendPubBrandSubmit {
	UIAlertView* alertView = 
	[[UIAlertView alloc] initWithTitle:@"Pranešk apie alų"
							   message:nil 
							  delegate:self 
					 cancelButtonTitle:@"Tiek to"
					 otherButtonTitles:@"Esu blaivas!!", nil];
	[alertView show];
	[alertView release];	
}

-(void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
	
	if(buttonIndex == 1){
		if (callAlertBox) {
			
			// CALL ALERT BOX

			NSString *phonenumber = [NSString stringWithFormat:@"tel://%@", currentPub.phone];
			phonenumber = [phonenumber stringByReplacingOccurrencesOfString:@" " withString:@""];
			NSLog(@"Calling pub: %@", phonenumber);
			[[UIApplication sharedApplication] openURL:[NSURL URLWithString:phonenumber]];
		} else {
			
			// REPORT ALERT

			NSString *uniqueIdentifier = [[UIDevice currentDevice] uniqueIdentifier];
			NSString *message = [NSString stringWithFormat:@"UID: %@ Pub: %@", uniqueIdentifier, currentPub.pubTitle];
			NSLog(@"%@", message);
			
			BOOL success = 
				[[DataPublisher sharedManager] submitPubBrand:reportBrandId pub:currentPub.pubId status:reportStatus message:uniqueIdentifier validate:YES];
			if (success) {
				NSLog(@"Data can be published");
				CLLocationCoordinate2D coords= [[LocationManager sharedManager]getLocationCoordinates];
				NSString *post = 
				[NSString stringWithFormat:
					@"type=pubBrandInfo&status=%@&brandId=%@&pubId=%@&message=%@&location.latitude=%.8f&location.longitude=%.8f",
				 reportStatus, reportBrandId, currentPub.pubId, message, coords.latitude, coords.longitude];
				
				[self postData:post msg:@"Tik alus išgelbės mus!"];
			}
			
		}
	}
	callAlertBox = FALSE;
}




- (IBAction) openAddBrandSubmit: (id)sender {
    newBrandSubmit.pub = currentPub;

	newBrandSubmit.modalTransitionStyle = UIModalTransitionStyleCoverVertical;//UIModalTransitionStyleCoverVertical;	
	[self presentModalViewController:newBrandSubmit animated:YES];	
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
#pragma mark NSURLConnection methods



- (void) postData:(NSString *) params msg:(NSString *)msg {
	NSLog(@"PubDetailViewController postData");
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
    NSLog(@"Pub Brands successfully sent");
	
	[[DataPublisher sharedManager]addSubmittedBrand];
	
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

