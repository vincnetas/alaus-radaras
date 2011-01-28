//
//  BrandsViewController.m
//  AlausRadaras
//
//  Created by Laurynas R on 1/24/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "BrandsViewController.h"


@implementation BrandsViewController

@synthesize brandList, brandsTable, brandCell;
@synthesize beerCatagoriesControl,brandsDetails;
@synthesize searchBar, isSearchOn;

- (void)dealloc {
	[searchBar release];
	[isSearchOn release];	
	[brandsDetails release];
	[beerCatagoriesControl release];
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


	 NSString* path = [[NSBundle mainBundle] pathForResource:@"brands" ofType:@"txt"];
	 NSString* fileContents = [NSString stringWithContentsOfFile:path usedEncoding:nil error:nil];

	 NSArray *lines = [fileContents componentsSeparatedByString:@"\n"];

	 brandList = [[NSMutableArray alloc]initWithCapacity:[lines count]];
	 for (NSString *line in lines) {
		 if (![line isEqualToString:@""]) {
			NSArray *values = [line componentsSeparatedByString:@"	"];

			Brand *brand = [[Brand alloc]init];
			brand.icon = [NSString stringWithFormat:@"brand_%@.png", [values objectAtIndex:0]];
			brand.label =	[values objectAtIndex:1];
			brand.pubsString =	[values objectAtIndex:2];

			[brandList addObject:brand];
			[brand release];
		 }
	 }
	 
	 category = 0;

	 TextDatabaseService *service = [[TextDatabaseService alloc]init];
	 tagsList = [service getTags];
	 countryList = [service getCountries]; 
	 
	 [service release];
	 
	 /* Search initialization */	 
	 searchBar = [[UISearchBar alloc] init];
	 searchBar.placeholder = @"Type a search term";
	 searchBar.tintColor = [UIColor blackColor];
	 searchBar.delegate = self;
	 [searchBar sizeToFit];
	 self.isSearchOn = NO;
	 [searchBar setAutocapitalizationType:UITextAutocapitalizationTypeNone];
	 [searchBar sizeToFit];
	 
	 
 }



#pragma mark -
#pragma mark Segment Controller methods


-(IBAction) beerCategoryControlIndexChanged {
	switch (self.beerCatagoriesControl.selectedSegmentIndex) {
		case 0:
			NSLog(@"Segment Alus selected.");
			category = 0;
			break;
		case 1:
			NSLog(@"Segment Salys selected.");
			category = 1;
			break;
		case 2:
			NSLog(@"Segment Tipai selected.");
			category = 2;
			break;
			
		default:
			break;
	}
	[brandsTable reloadData];
}


	

#pragma mark -
#pragma mark Table view methods

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
	switch (category) {
		case 0:
			return [brandList count];
		case 1:
			return [countryList count];
		case 2:
			return [tagsList count];
		default:
			break;
	}
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
	switch (category) {
		case 0:
			cell.labelText.text = [[brandList objectAtIndex:indexPath.row] label];
			cell.brandIcon.image = [UIImage imageNamed:[[brandList objectAtIndex:indexPath.row] icon]];
			break;
		case 1:
			cell.labelText.text = [[countryList objectAtIndex:indexPath.row] displayValue];
			cell.brandIcon.image = [UIImage imageNamed:@"map_02.png"];
			break;
		case 2:
			cell.labelText.text = [[tagsList objectAtIndex:indexPath.row] displayValue];
			cell.brandIcon.image = [UIImage imageNamed:@"tag_02.png"];
			break;
		default:
			break;
	}

    return cell;
}


- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
		
	TextDatabaseService *service = [[TextDatabaseService alloc]init];
	
	switch (category) {
		case 0:{
			NSMutableArray *pubs = [service findPubsHavingBrand:[[brandList objectAtIndex:indexPath.row]pubsString]];		 		 
			[self showMapWithPubs:pubs];
			[pubs release];	
			break;
		} case 1: {	
			NSMutableArray *brandsByCountry = [service getBrandsByCountry:[[countryList objectAtIndex:indexPath.row]code]];
			[self showBrandDetails:brandsByCountry title:[[countryList objectAtIndex:indexPath.row]displayValue]];
			[brandsByCountry release];
			break;
		} case 2: {
			NSMutableArray *brandsByTag = [service getBrandsByTag:[[tagsList objectAtIndex:indexPath.row]code]];
			[self showBrandDetails:brandsByTag title:[[tagsList objectAtIndex:indexPath.row]displayValue]];
			[brandsByTag release];
			break;
		}
		default:
			break;
	}

	[tableView deselectRowAtIndexPath:indexPath animated:YES];	
	[service release];
}

- (void) showMapWithPubs:(NSMutableArray *)pubs {
	MapViewController *vietosView = 
		[[MapViewController alloc] initWithNibName:nil bundle:nil];
	
	[vietosView setPubsOnMap:pubs];	 
	vietosView.modalTransitionStyle = UIModalTransitionStyleFlipHorizontal;	
	[self presentModalViewController:vietosView animated:YES];
	
	[vietosView release];
}

- (void) showBrandDetails:(NSMutableArray *)brands title:(NSString *) titleText{
	[brandsDetails setBrandList:brands];
	brandsDetails.titleLabel.text = titleText;
	[[brandsDetails brandsTable]reloadData];
	brandsDetails.modalTransitionStyle = UIModalTransitionStyleCrossDissolve;	
	[self presentModalViewController:brandsDetails animated:YES];
}


- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath {
    return NO;
}


#pragma mark -
#pragma mark UISearchBar methods


- (IBAction) toggleSearch: (id) object {
    isSearchOn = ! isSearchOn;
    
    if (isSearchOn) {
		brandsTable.tableHeaderView = searchBar; // show the search bar on top of table
    } else {
        brandsTable.tableHeaderView = nil;
        [searchBar resignFirstResponder ]; 
    }
    [brandsTable scrollRectToVisible:[[brandsTable tableHeaderView] bounds] animated:NO]; // scroll to top so we see the search bar
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

