//
//  BrandsViewController.m
//  AlausRadaras
//
//  Created by Laurynas R on 1/24/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "BrandsViewController.h"
#import "BrandsTableCell.h"

@implementation BrandsViewController

@synthesize brandList, brandsTable, brandCell;

- (void)dealloc {
	[brandList release];
	[brandsTable release];
	[brandCell release];
    [super dealloc];
}

- (IBAction) gotoPreviousView:(id)sender {
	[self dismissModalViewControllerAnimated:YES];	
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
	 
	/* Separator color */ 
	brandsTable.separatorColor = [UIColor grayColor];

	//[NSString stringWithContentsOfFile:@"brands.txt"];
	 NSStringEncoding *encoding;
	 NSError* error;
	 NSString* path = [[NSBundle mainBundle] pathForResource:@"brands" ofType:@"txt"];
	 NSString* fileContents = [NSString stringWithContentsOfFile:path usedEncoding:&encoding error:&error];

	 NSArray *lines = [fileContents componentsSeparatedByString:@"\n"];

	 brandList = [[NSMutableArray alloc]initWithCapacity:[lines count]];
	 for (NSString *line in lines) {
		 if (![line isEqualToString:@""]) {
			NSArray *values = [line componentsSeparatedByString:@"	"];

			Brand *brand = [[Brand alloc]init];
			brand.icon = [NSString stringWithFormat:@"brand_%@.png", [values objectAtIndex:0]];
			brand.label =	[values objectAtIndex:1];

			[brandList addObject:brand];
			[brand release];
		 }
	 }
	 
 }



#pragma mark -
#pragma mark Table view methods

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
	return [brandList count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {	
	static NSString *CellIdentifier = @"Cell";
	BrandsTableCell *cell = (BrandsTableCell *)[tableView dequeueReusableCellWithIdentifier:CellIdentifier];
	//UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
	
	if (cell == nil) {
		[[NSBundle mainBundle] loadNibNamed:@"BrandsTableCell" owner:self options:nil];

        cell = brandCell;		
		self.brandCell = nil;
//		cell = [[[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier] autorelease];
    }
//	cell.textLabel.text = [[brandList objectAtIndex:indexPath.row] label]; 
//		NSLog(@"%@", [brand label]);

	cell.labelText.text = [[brandList objectAtIndex:indexPath.row] label];
	cell.brandIcon.image = [UIImage imageNamed:[[brandList objectAtIndex:indexPath.row] icon]];
		
    return cell;
}


- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
	[tableView deselectRowAtIndexPath:indexPath animated:YES];
}

- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath {
    return NO;
}






#pragma mark -
#pragma mark Other methods


- (void)didReceiveMemoryWarning {
   	NSLog(@"BrandsViewController: Recieved a Memory Warning... Oooops..");
	
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
 // Override to allow orientations other than the default portrait orientation.
 - (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
 // Return YES for supported orientations.
 return (interfaceOrientation == UIInterfaceOrientationPortrait);
 }
 */

