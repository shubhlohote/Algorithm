package auctionserver;

/**
 * @author YOUR NAME SHOULD GO HERE
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AuctionServer {

    /**
     * Singleton: the following code makes the server a Singleton. You should
     * not edit the code in the following noted section.
     *
     * For test purposes, we made the constructor protected.
     */

    /* Singleton: Begin code that you SHOULD NOT CHANGE! */
    protected AuctionServer() {
    }

    private static AuctionServer instance = new AuctionServer();

    public static AuctionServer getInstance() {
        return instance;
    }

    /* Singleton: End code that you SHOULD NOT CHANGE! */
 /* Statistic variables and server constants: Begin code you should likely leave alone. */
    /**
     * Server statistic variables and access methods:
     */
    private int soldItemsCount = 0;
    private int revenue = 0;
    private int unsoldItems = 0;
    private int totalItems = 0;
    
    public int soldItemsCount() {
        return this.soldItemsCount;
    }

    public int revenue() {
        return this.revenue;
    }    

    public int getUnsoldItems() {
        return unsoldItems;
    }

    public int getTotalItems() {
        return totalItems;
    }
    
    
    
    

    /**
     * Server restriction constants:
     */
    public static final int maxBidCount = 10; // The maximum number of bids at any given time for a buyer.
    public static final int maxSellerItems = 20; // The maximum number of items that a seller can submit at any given time.
    public static final int serverCapacity = 80; // The maximum number of active items at a given time.


    /* Statistic variables and server constants: End code you should likely leave alone. */
    /**
     * Some variables we think will be of potential use as you implement the
     * server...
     */
    // List of items currently up for bidding (will eventually remove things that have expired).
    private List<Item> itemsUpForBidding = new ArrayList<Item>();

    // The last value used as a listing ID.  We'll assume the first thing added gets a listing ID of 0.
    private int lastListingID = -1;

    // List of item IDs and actual items.  This is a running list with everything ever added to the auction.
    private HashMap<Integer, Item> itemsAndIDs = new HashMap<Integer, Item>();

    // List of itemIDs and the highest bid for each item.  This is a running list with everything ever added to the auction.
    private HashMap<Integer, Integer> highestBids = new HashMap<Integer, Integer>();

    // List of itemIDs and the person who made the highest bid for each item.   This is a running list with everything ever bid upon.
    private HashMap<Integer, String> highestBidders = new HashMap<Integer, String>();

    // List of sellers and how many items they have currently up for bidding.
    private HashMap<String, Integer> itemsPerSeller = new HashMap<String, Integer>();

    // List of buyers and how many items on which they are currently bidding.
    private HashMap<String, Integer> itemsPerBuyer = new HashMap<String, Integer>();
    
    // List of sellers and how many consecutive items they have less than $75 bidding.
    private HashMap<String, Integer> sellerLowValue = new HashMap<String, Integer>();
    
    // List of sellers and how many consecutive items they have currently up for bidding.
    private HashMap<String, Integer> sellerUnsoldItems = new HashMap<String, Integer>();
    
    private List<String> disqualifiedSellers = new ArrayList<String>();
        
    public List<String> getDisqualifiedSellers() {
        return disqualifiedSellers;
    }
    // Object used for instance synchronization if you need to do it at some point 
    // since as a good practice we don't use synchronized (this) if we are doing internal
    // synchronization.
    //
    private Object instanceSellerLock = new Object();
    private Object instanceBuyerLock = new Object();
    private Object itemBiddingLock = new Object();

    /*
	 *  The code from this point forward can and should be changed to correctly and safely 
	 *  implement the methods as needed to create a working multi-threaded server for the 
	 *  system.  If you need to add Object instances here to use for locking, place a comment
	 *  with them saying what they represent.  Note that if they just represent one structure
	 *  then you should probably be using that structure's intrinsic lock.
     */
    /**
     * Attempt to submit an <code>Item</code> to the auction
     *
     * @param sellerName Name of the <code>Seller</code>
     * @param itemName Name of the <code>Item</code>
     * @param lowestBiddingPrice Opening price
     * @param biddingDurationMs Bidding duration in milliseconds
     * @return A positive, unique listing ID if the <code>Item</code> listed
     * successfully, otherwise -1
     */
    public int submitItem(String sellerName, String itemName, int lowestBiddingPrice, int biddingDurationMs) {
        // TODO: IMPLEMENT CODE HERE
        // Some reminders:
        //   Make sure there's room in the auction site.
        //   If the seller is a new one, add them to the list of sellers.
        //   If the seller has too many items up for bidding, don't let them add this one.
        //   Don't forget to increment the number of things the seller has currently listed.

        synchronized (itemBiddingLock) {
            synchronized (instanceSellerLock) {
                if (itemsUpForBidding.size() < serverCapacity 
                        && (!itemsPerSeller.containsKey(sellerName) || itemsPerSeller.get(sellerName) < maxSellerItems)
                        && !disqualifiedSellers.contains(sellerName)) {
                    lastListingID++;
                    totalItems++;
                    Item item = new Item(sellerName, itemName, lastListingID, lowestBiddingPrice, biddingDurationMs);
                    itemsUpForBidding.add(item);
                    itemsAndIDs.put(lastListingID, item);
                    if (!itemsPerSeller.containsKey(sellerName)) {
                        itemsPerSeller.put(sellerName, 1);
                    } else {
                        itemsPerSeller.put(sellerName, itemsPerSeller.get(sellerName) + 1);
                    }
                    if(lowestBiddingPrice < 75){
                        if(sellerLowValue.containsKey(sellerName)){
                            sellerLowValue.put(sellerName, sellerLowValue.get(sellerName)+1);
                            if(sellerLowValue.get(sellerName) >= 3){
                                boolean higherPrice = false;
                                for(Item i: itemsUpForBidding){
                                    if(i.lowestBiddingPrice() >= 75){
                                        higherPrice = true;
                                    }
                                }
                                
                                if(higherPrice){
                                    disqualifiedSellers.add(sellerName);
                                }
                            }
                        }else{
                            sellerLowValue.put(sellerName, 1);
                        }
                                                
                    }else{
                        if(sellerLowValue.containsKey(sellerName)){
                            sellerLowValue.put(sellerName, 0);
                        }
                    }
                    
                    return lastListingID;

                }
            }
        }
        return -1;
    }

    /**
     * Get all <code>Items</code> active in the auction
     *
     * @return A copy of the <code>List</code> of <code>Items</code>
     */
    public List<Item> getItems() {
        // TODO: IMPLEMENT CODE HERE
        // Some reminders:
        //    Don't forget that whatever you return is now outside of your control.
        List<Item> tempList = new ArrayList<Item>();
        List<Item> removeList = new ArrayList<Item>();
        synchronized (itemBiddingLock) {
            for (Item i : itemsUpForBidding) {                
                if (i.biddingOpen()) {
                    Item tempItem = new Item(i.seller(), i.name(), i.listingID(), i.lowestBiddingPrice(), i.biddingDurationMs());
                    tempList.add(tempItem);
                }else{                    
                    if(itemUnbid(i.listingID())){
                        if(sellerUnsoldItems.containsKey(i.seller())){
                            sellerUnsoldItems.put(i.seller(), sellerUnsoldItems.get(i.seller())+1);
                            if(sellerUnsoldItems.get(i.seller())>=5 && !disqualifiedSellers.contains(i.seller())){
                                disqualifiedSellers.add(i.seller());
                            }
                        }else{
                            sellerUnsoldItems.put(i.seller(), 1);
                        }
                        
                        removeList.add(i);
                        unsoldItems++;
                    }
                }
            }
            for(Item item: removeList){
                itemsUpForBidding.remove(item);                
            }
        }

        return tempList;
    }

    /**
     * Attempt to submit a bid for an <code>Item</code>
     *
     * @param bidderName Name of the <code>Bidder</code>
     * @param listingID Unique ID of the <code>Item</code>
     * @param biddingAmount Total amount to bid
     * @return True if successfully bid, false otherwise
     */
    public boolean submitBid(String bidderName, int listingID, int biddingAmount) {
        // TODO: IMPLEMENT CODE HERE
        // Some reminders:
        //   See if the item exists.
        //   See if it can be bid upon.
        //   See if this bidder has too many items in their bidding list.
        //   Get current bidding info.
        //   See if they already hold the highest bid.
        //   See if the new bid isn't better than the existing/opening bid floor.
        //   Decrement the former winning bidder's count
        //   Put your bid in place
        synchronized (instanceBuyerLock) {
            if(itemsAndIDs.containsKey(listingID)){                
                if(itemsAndIDs.get(listingID).biddingOpen() && 
                        ((itemsPerBuyer.containsKey(bidderName) && itemsPerBuyer.get(bidderName) < maxBidCount) || !itemsPerBuyer.containsKey(bidderName))
                        && (highestBidders.containsKey(listingID) && !highestBidders.get(listingID).equalsIgnoreCase(bidderName) || !highestBidders.containsKey(listingID)) 
                        && ((highestBids.containsKey(listingID) && highestBids.get(listingID) < biddingAmount) || (!highestBids.containsKey(listingID) && biddingAmount > itemsAndIDs.get(listingID).lowestBiddingPrice()))){
                    
                    if(highestBidders.containsKey(listingID)){                        
                        String oldBidder = highestBidders.get(listingID);
                        itemsPerBuyer.put(oldBidder, itemsPerBuyer.get(oldBidder)-1);
                    }
                    highestBidders.put(listingID, bidderName);
                    highestBids.put(listingID, biddingAmount);
                    
                    if(itemsPerBuyer.containsKey(bidderName)){                        
                        itemsPerBuyer.put(bidderName, itemsPerBuyer.get(bidderName) +1);                        
                    }else{
                        itemsPerBuyer.put(bidderName, 1);
                    }
                    return true;
                }
            }                  
        }
        return false;
    }

    /**
     * Check the status of a <code>Bidder</code>'s bid on an <code>Item</code>
     *
     * @param bidderName Name of <code>Bidder</code>
     * @param listingID Unique ID of the <code>Item</code>
     * @return 1 (success) if bid is over and this <code>Bidder</code> has
     * won<br>
     * 2 (open) if this <code>Item</code> is still up for auction<br>
     * 3 (failed) If this <code>Bidder</code> did not win or the
     * <code>Item</code> does not exist
     */
    public int checkBidStatus(String bidderName, int listingID) {
        // TODO: IMPLEMENT CODE HERE
        // Some reminders:
        //   If the bidding is closed, clean up for that item.
        //     Remove item from the list of things up for bidding.
        //     Decrease the count of items being bid on by the winning bidder if there was any...
        //     Update the number of open bids for this seller

        if (itemsAndIDs.containsKey(listingID)) {
            Item i = itemsAndIDs.get(listingID);
            if (i.biddingOpen()) {
                return 2;
            } else {
                synchronized (itemBiddingLock) {
                    if (itemsUpForBidding.contains(i)) {
                        synchronized (instanceSellerLock) {
                            synchronized (instanceBuyerLock) {
                                itemsUpForBidding.remove(i);
                                if (highestBids.containsKey(listingID)) {
                                    soldItemsCount++;
                                    revenue += highestBids.get(listingID);
                                    int itemcount = itemsPerSeller.get(i.seller()) - 1;
                                    itemsPerSeller.put(i.seller(), itemcount);

                                    int bidderItems = itemsPerBuyer.get(highestBidders.get(listingID)) - 1;
                                    itemsPerBuyer.put(highestBidders.get(listingID), bidderItems);
                                }
                            }
                        }
                    }
                }

                if (highestBidders.get(listingID).equalsIgnoreCase(bidderName)) {
                    return 1;
                }
            }
        }

        return 3;
    }

    /**
     * Check the current bid for an <code>Item</code>
     *
     * @param listingID Unique ID of the <code>Item</code>
     * @return The highest bid so far or the opening price if no bid has been
     * made, -1 if no <code>Item</code> exists
     */
    public int itemPrice(int listingID) {

        if (highestBids.containsKey(listingID)) {
            return highestBids.get(listingID);
        } else if (itemsAndIDs.containsKey(listingID)) {
            return itemsAndIDs.get(listingID).lowestBiddingPrice();
        }
        return -1;
    }

    /**
     * Check whether an <code>Item</code> has been bid upon yet
     *
     * @param listingID Unique ID of the <code>Item</code>
     * @return True if there is no bid or the <code>Item</code> does not exist,
     * false otherwise
     */
    public Boolean itemUnbid(int listingID) {

        // TODO: IMPLEMENT CODE HERE
        if (highestBids.containsKey(listingID)) {
            return false;
        }
        return true;
    }

}
