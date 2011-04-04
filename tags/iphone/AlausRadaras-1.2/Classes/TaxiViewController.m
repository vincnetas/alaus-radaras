//
//  TaxiViewController.m
//  AlausRadaras
//
//  Created by Laurynas R on 3/14/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "TaxiViewController.h"
#import "SQLiteManager.h";
#import "Taxi.h"

@implementation TaxiViewController

@synthesize taxiTable, brandCell;
@synthesize taxiList;

- (void)dealloc {
	[brandCell release];
	[taxiList release];
	[taxiTable release];
    [super dealloc];
}

- (void)viewDidLoad {
	/* Application setup */
    [super viewDidLoad];
	UIColor *background = [[UIColor alloc] initWithPatternImage:[UIImage imageNamed:@"background.png"]];
	self.view.backgroundColor = background;
	[background release];
	
	taxiList = [[SQLiteManager sharedManager]getTaxies];

	/* Transparent Background */
	taxiTable.backgroundColor = [UIColor clearColor];
	taxiTable.opaque = NO;
	
	/* Separator color */ 
	taxiTable.separatorColor = [UIColor grayColor];
}

- (IBAction) back:(id)sender {
	[self dismissModalViewControllerAnimated:YES];	
}

#pragma mark -
#pragma mark Table view methods

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
	return [taxiList count];
}

//- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {	
//	static NSString *CellIdentifier = @"TaxiCell";
//	
//	UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
//	if (cell == nil) {
//		cell = [[[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier] autorelease];
//    }
//	cell.textColor = [UIColor whiteColor];
//	cell.textLabel.text = [[taxiList objectAtIndex:indexPath.row] title];
//	
//    return cell;
//}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {	
	static NSString *CellIdentifier = @"TaxiCell";
	BrandsTableCell *cell = (BrandsTableCell *)[tableView dequeueReusableCellWithIdentifier:CellIdentifier];
	
	if (cell == nil) {
		[[NSBundle mainBundle] loadNibNamed:@"BrandsTableCell" owner:self options:nil];
		cell = brandCell;		
		self.brandCell = nil;
	}
	Taxi *taxi = [taxiList objectAtIndex:indexPath.row];
	cell.labelText.text = [taxi title];
	cell.label2Text.text = [NSString stringWithFormat:@"%@, %@",[taxi phone],[taxi city]];
	//[taxi release];
	cell.brandIcon.image = [UIImage imageNamed:@"taxi2.png"];
	return cell;
	
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
	[tableView deselectRowAtIndexPath:indexPath animated:YES];
	
	NSString *phonenumber = [NSString stringWithFormat:@"tel://%@", [[taxiList objectAtIndex:indexPath.row] phone]];
	phonenumber = [phonenumber stringByReplacingOccurrencesOfString:@" " withString:@""];
	NSLog(@"Calling taxi: %@", phonenumber);
	[[UIApplication sharedApplication] openURL:[NSURL URLWithString:phonenumber]];
}

- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath {
    return NO;
}




#pragma mark -
#pragma mark Other methods


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
