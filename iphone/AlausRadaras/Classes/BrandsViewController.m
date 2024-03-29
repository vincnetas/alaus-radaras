//
//  BrandsViewController.m
//  AlausRadaras
//
//  Created by Laurynas R on 1/24/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "BrandsViewController.h"
#import "SQLiteManager.h"
#import "LocationManager.h"

@implementation BrandsViewController

@synthesize brandList, brandsTable, brandCell;
@synthesize beerCatagoriesControl,brandsDetails;
@synthesize searchBar, isSearchOn;

- (void)dealloc {
	[searchBar release];
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
	
	/* iOS3.1 Support */
	//brandsTable.backgroundView = nil; 
	 
	/* Separator color */ 
	brandsTable.separatorColor = [UIColor grayColor];

	category = 0;
	 
	 brandList =  [[SQLiteManager sharedManager] getBeersLocationBased];

	 countryList = [[SQLiteManager sharedManager] getCountriesLocationBased];	 
	 
	 tagsList = [[SQLiteManager sharedManager] getTagsLocationBased];

	 	 
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
            
            
            cell.label2Text.text = [[brandList objectAtIndex:indexPath.row] tagsAsString];

			cell.brandIcon.image = [UIImage imageNamed:[[brandList objectAtIndex:indexPath.row] icon]];
			if (cell.brandIcon.image == nil) {
				cell.brandIcon.image = [UIImage imageNamed:@"beer_default.png"];
			}
			break;
		case 1:
			cell.labelText.text = [[countryList objectAtIndex:indexPath.row] displayValue];
			cell.brandIcon.image = [UIImage imageNamed:@"map_02.png"];
			break;
		case 2:
			cell.labelText.text = [[tagsList objectAtIndex:indexPath.row] displayValue];
			cell.brandIcon.image = [UIImage imageNamed:@"tag_021.png"];
			break;
		default:
			break;
	}

    return cell;
}


- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
		

	switch (category) {
		case 0:{
			NSMutableArray *pubs = [[SQLiteManager sharedManager]getPubsByBeerId:[[brandList objectAtIndex:indexPath.row]brandId]];
			[self showMapWithPubs:pubs title:[[brandList objectAtIndex:indexPath.row]label]];
			[pubs release];	
			break;
		} case 1: {	
			NSMutableArray *brandsByCountry = [[SQLiteManager sharedManager] getBeersByCountry:[[countryList objectAtIndex:indexPath.row]code]];
			[self showBrandDetails:brandsByCountry title:[[countryList objectAtIndex:indexPath.row]displayValue]];
			[brandsByCountry release];
			break;
		} case 2: {
			NSMutableArray *brandsByTag = [[SQLiteManager sharedManager] getBeersByTag:[[tagsList objectAtIndex:indexPath.row]code]];
			[self showBrandDetails:brandsByTag title:[[tagsList objectAtIndex:indexPath.row]displayValue]];
			[brandsByTag release];
			break;
		}
		default:
			break;
	}
	[tableView deselectRowAtIndexPath:indexPath animated:YES];	
}

- (void) showMapWithPubs:(NSMutableArray *)pubs title:(NSString *) titleText {
	MapViewController *vietosView = 
		[[MapViewController alloc] initWithNibName:nil bundle:nil];
	
	[vietosView setPubsOnMap:pubs];
	vietosView.modalTransitionStyle = UIModalTransitionStyleFlipHorizontal;	
	[self presentModalViewController:vietosView animated:YES];
	
	NSString *info = [NSString stringWithFormat:@"%@ alus",titleText];
	
	if ([[LocationManager sharedManager]getVisibilityControlled]) {
		double di = [[LocationManager sharedManager]getDistance];
		info = [NSString stringWithFormat:@"%@ • (%.0f Km atstumu)", info, di] ;
	}
	
	vietosView.infoLabel.text = info;
	[vietosView release];
//	[info release];
}

- (void) showBrandDetails:(NSMutableArray *)brands title:(NSString *) titleText {
	[brandsDetails setBrandList:brands];
	brandsDetails.titleLabel.text = titleText;
	[[brandsDetails brandsTable]reloadData];
	brandsDetails.modalTransitionStyle = UIModalTransitionStyleCoverVertical;	
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
        [searchBar resignFirstResponder]; 
    }
	[brandsTable scrollRectToVisible:[[brandsTable tableHeaderView] bounds] animated:YES]; // scroll to top so we see the search bar
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

