//
//  PubDetailViewController.m
//  AlausRadaras
//
//  Created by Laurynas R on 1/26/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "PubDetailViewController.h"
#import "Brand.h"
#import "AlausRadarasAppDelegate.h"

@implementation PubDetailViewController

@synthesize brandList, brandsTable,currentPub, pubTitleShortLabel, pubTitleLabel,pubAddressLabel,pubCallLabel;

- (void)dealloc {
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
	 NSLog(@"PubDetailViewController viewDidLoad");
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
	 pubCallLabel.text = currentPub.phone;

	 
	 /* TODO: REDO */
	 NSString* path = [[NSBundle mainBundle] pathForResource:@"brands" ofType:@"txt"];
	 NSString* fileContents = [NSString stringWithContentsOfFile:path usedEncoding:nil error:nil];
	 NSArray *lines = [fileContents componentsSeparatedByString:@"\n"];
	 
	 brandList = [[NSMutableArray alloc]initWithCapacity:[lines count]];
	 for (NSString *line in lines) {
		 if (![line isEqualToString:@""]) {
			 
			 NSArray *values = [line componentsSeparatedByString:@"	"];
			 
			 if ([[values objectAtIndex:2] rangeOfString: currentPub.pubId].location != NSNotFound){
				 Brand *brand = [[Brand alloc]init];
				 brand.icon = [NSString stringWithFormat:@"brand_%@.png", [values objectAtIndex:0]];
				 brand.label =	[values objectAtIndex:1];
				 
				 [brandList addObject:brand];
				 [brand release];
			 }
		 }
	 }
	 
	 
	 
 }

#pragma mark -
#pragma mark Action methods

- (IBAction) gotoPreviousView:(id)sender {
	[self dismissModalViewControllerAnimated:YES];	
}

- (IBAction) showOnMap:(id)sender {
	NSLog(@"showOnMap");
	
	// TODO: Implement
	
	MapViewController *vietosView = 
		[[MapViewController alloc] initWithNibName:nil bundle:nil];
	
	NSMutableArray *pubs = [[NSMutableArray alloc]init];

	[pubs addObject:currentPub];
	[vietosView setPubsOnMap:pubs];
	
	vietosView.modalTransitionStyle = UIModalTransitionStyleCrossDissolve;	
	[self presentModalViewController:vietosView animated:YES];
	
	[vietosView release];
	[pubs release];
}

- (IBAction) dialNumber:(id)sender {
	NSString *phonenumber = [NSString stringWithFormat:@"tel://%@", currentPub.phone];
	phonenumber = [phonenumber stringByReplacingOccurrencesOfString:@" " withString:@""];

	NSLog(@"dialNumber: %@", phonenumber);

	// TODO: Maybe show alert box before dialing?
	
	[[UIApplication sharedApplication] openURL:[NSURL URLWithString:phonenumber]];
}

- (IBAction) navigateToPub:(id)sender {
	NSLog(@"navigateToPub");
	
	// TODO: implement
	
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
	
//	int section = indexPath.section;
//	NSMutableArray *sectionItems = [sections objectAtIndex:section];
	/**/
	int numOfBrands = [brandList count];
	int rowNum = 0;
	for(int brandNum = 0; brandNum<numOfBrands/3;brandNum++){
		
	   	for (rowNum = 0; rowNum < numOfBrands; rowNum++) {

			int colNum = 0;	
			for (colNum =0; colNum<4; colNum++) {
					
					Brand *item = [brandList objectAtIndex:brandNum];

					CGRect rect = CGRectMake(70*colNum, 0, 64, 64);
					UIButton *button=[[UIButton alloc] initWithFrame:rect];
					[button setFrame:rect];
					UIImage *buttonImageNormal=[UIImage imageNamed:item.icon];
	//				[button setBackgroundImage:buttonImageNormal forState:UIControlStateNormal];
					[button setBackgroundColor:[UIColor greenColor]];
					[button setContentMode:UIViewContentModeCenter];
					
					NSString *tagValue = [NSString stringWithFormat:@"%d%d", indexPath.section+0, 0];
					button.tag = [tagValue intValue];
					//	[button addTarget:self action:@selector(buttonPressed:) forControlEvents:UIControlEventTouchUpInside];
					[hlcell.contentView addSubview:button];
					[button release];
					
					UILabel *label = [[[UILabel alloc] initWithFrame:CGRectMake(31+(100*colNum)-15, 66, 95, 12)] autorelease];
					
					label.text = item.label;
					label.textColor = [UIColor lightGrayColor];
					label.backgroundColor = [UIColor grayColor];
					label.textAlignment = UITextAlignmentCenter;
					label.font = [UIFont fontWithName:@"ArialMT" size:12]; 
					[hlcell.contentView addSubview:label];
			}
		}
	}
	
	/*
	int n = [brandList count];
	int i=0,i1=0; 
	while(i<n){
		int yy = 4 +i1*74;
		int j=0;
		for(j=0; j<4;j++){
			
			if (i>=n) break;
			Brand *item = [brandList objectAtIndex:i];
			
			CGRect rect = CGRectMake(18+80*j, yy, 40, 40);
			UIButton *button=[[UIButton alloc] initWithFrame:rect];
			[button setFrame:rect];
			UIImage *buttonImageNormal=[UIImage imageNamed:item.icon];
			[button setBackgroundImage:buttonImageNormal	forState:UIControlStateNormal];
			[button setContentMode:UIViewContentModeCenter];
			
			NSString *tagValue = [NSString stringWithFormat:@"%d%d", indexPath.section+1, i];
			button.tag = [tagValue intValue];
			//NSLog(@"....tag....%d", button.tag);
			
		//	[button addTarget:self action:@selector(buttonPressed:) forControlEvents:UIControlEventTouchUpInside];
			[hlcell.contentView addSubview:button];
			[button release];
			
			UILabel *label = [[[UILabel alloc] initWithFrame:CGRectMake((80*j)-4, yy+44, 80, 12)] autorelease];
			label.text = item.label;
			label.textColor = [UIColor lightGrayColor];
			label.backgroundColor = [UIColor clearColor];
			label.textAlignment = UITextAlignmentCenter;
			label.font = [UIFont fontWithName:@"ArialMT" size:12]; 
			[hlcell.contentView addSubview:label];
			
			i++;
		}
		i1++;
	}*/
	 
	return hlcell;
}


- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
	return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
	return 1;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {  
	//NSMutableArray *sectionItems = [sections objectAtIndex:indexPath.section];
	int numRows = [brandList count]/4;
	return numRows * 80.0;
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

