    //
//  BrandsDetailViewController.m
//  AlausRadaras
//
//  Created by Laurynas R on 1/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "BrandsDetailViewController.h"
#import "Brand.h"
#import "SQLiteManager.h"

@implementation BrandsDetailViewController

@synthesize titleLabel, brandList, brandsTable, brandCell;

- (void)dealloc {
	[titleLabel release];
	[brandList release];
	[brandsTable release];
	[brandCell release];
    [super dealloc];
}

 - (void)viewDidLoad {
	 NSLog(@"BrandsDetailViewController viewDidLoad");
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
	 
 }



#pragma mark -
#pragma mark Created methods

- (IBAction) gotoPreviousView:(id)sender {
	[self dismissModalViewControllerAnimated:YES];	
}

- (IBAction) showOnMap:(id)sender {
	NSLog(@"showOnMap");
	NSMutableArray *pubs = [[NSMutableArray alloc]init];

	for(Brand *brand in brandList) {
		[pubs addObjectsFromArray:[[SQLiteManager sharedManager] getPubsByBrandId:brand.brandId]];
	}
	[self showMapWithPubs:pubs WithInfo:titleLabel.text];
	[pubs release];
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
	
	if (cell == nil) {
		[[NSBundle mainBundle] loadNibNamed:@"BrandsTableCell" owner:self options:nil];
        cell = brandCell;		
		self.brandCell = nil;
    }

	cell.labelText.text = [[brandList objectAtIndex:indexPath.row] label];
	cell.brandIcon.image = [UIImage imageNamed:[[brandList objectAtIndex:indexPath.row] icon]];
	if (cell.brandIcon.image == nil) {
		cell.brandIcon.image = [UIImage imageNamed:@"brand_default.png"];
	}
	
    return cell;
}


- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
	NSMutableArray *pubs = [[SQLiteManager sharedManager] getPubsByBrandId:[[brandList objectAtIndex:indexPath.row]brandId]];		 		 
	[self showMapWithPubs:pubs WithInfo:[[brandList objectAtIndex:indexPath.row]label]];
	[pubs release];	
	
	[tableView deselectRowAtIndexPath:indexPath animated:YES];	
}


- (void) showMapWithPubs:(NSMutableArray *)pubs WithInfo:(NSString *) info {
	MapViewController *vietosView = 
		[[MapViewController alloc] initWithNibName:nil bundle:nil];
	
	[vietosView setPubsOnMap:pubs];	
	vietosView.modalTransitionStyle = UIModalTransitionStyleFlipHorizontal;	
	[self presentModalViewController:vietosView animated:YES];
	vietosView.infoLabel.text = 
		[NSString stringWithFormat:@"%@ alus", info];	
	[vietosView release];
}


- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath {
    return NO;
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
 // Override to allow orientations other than the default portrait orientation.
 - (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
 // Return YES for supported orientations.
 return (interfaceOrientation == UIInterfaceOrientationPortrait);
 }
 */

