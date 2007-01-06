/*
 * EbayItemAdapter.java
 *
 * Created on August 3, 2006, 9:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.thornapple.ebay.manager.adapter;

import com.ebay.sdk.ApiAccount;
import com.ebay.sdk.ApiContext;
import com.ebay.sdk.ApiCredential;
import com.ebay.sdk.call.GetItemCall;
import com.ebay.sdk.call.GetMyeBayCall;
import com.ebay.sdk.call.GetSearchResultsCall;
import com.ebay.sdk.eBayAccount;
import com.ebay.sdk.helper.ui.ControlTagItem;
import com.ebay.soap.eBLBaseComponents.AmountType;
import com.ebay.soap.eBLBaseComponents.CategoryArrayType;
import com.ebay.soap.eBLBaseComponents.CategoryType;
import com.ebay.soap.eBLBaseComponents.DetailLevelCodeType;
import com.ebay.soap.eBLBaseComponents.ItemIDType;
import com.ebay.soap.eBLBaseComponents.ItemType;
import com.ebay.soap.eBLBaseComponents.PriceRangeFilterType;
import com.ebay.soap.eBLBaseComponents.ProximitySearchType;
import com.ebay.soap.eBLBaseComponents.RequestCategoriesType;
import com.ebay.soap.eBLBaseComponents.SearchFlagsCodeType;
import com.ebay.soap.eBLBaseComponents.SearchResultItemType;
import com.thornapple.ebay.manager.AuctionItem;
import com.thornapple.ebay.manager.ItemSearchCriteria;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Bill
 */
public class EbayItemAdapter {
    
    private ApiContext apiContext;
    private EbayAdapterConfiguration config;
    private Map categoryMap = new HashMap();
    
    /** Creates a new instance of EbayItemAdapter */
    public EbayItemAdapter() {
        config = EbayAdapterConfiguration.getInstance();
        apiContext = new ApiContext();
        //ApiLogging apiLogging = new ApiLogging();
        //this.apiContext.setApiLogging(apiLogging);
        
        ApiCredential cred = apiContext.getApiCredential();
        ApiAccount ac = cred.getApiAccount();
        eBayAccount ec = cred.geteBayAccount();
        cred.seteBayToken(config.getToken());
        apiContext.setApiServerUrl(config.getApiIURL());
    }
    
    public List getMyEbayItemsWatching() throws Exception{
        List results = new ArrayList();
        
        GetMyeBayCall api = new GetMyeBayCall(this.apiContext);
        
        ControlTagItem ct;
        //ct = (ControlTagItem)this.cbxActiveSort.getSelectedItem();
        //api.setActiveSort((ItemSortTypeCodeType)ct.Tag);
        
        //ct = (ControlTagItem)this.cbxWatchSort.getSelectedItem();
        //api.setWatchSort((ItemSortTypeCodeType)ct.Tag);
        
        api.getMyeBay();
        ItemType[] _temp = api.getReturnedWatchList();
        
        if (_temp == null) return results;
        
        AuctionItem item = null;
        for (int i = 0; i < _temp.length; i++) {
            item = new AuctionItem(_temp[i]);
            item.addLabel("Watching");
            results.add(item);
        }
        
        return results;
    }
    
    public AuctionItem getItemDetails(String id)
    throws Exception {
        GetItemCall gc = new GetItemCall(apiContext);
        DetailLevelCodeType[] detailLevels = new DetailLevelCodeType[] {
            DetailLevelCodeType.ReturnAll,
            DetailLevelCodeType.ItemReturnCategories,
            DetailLevelCodeType.ItemReturnAttributes,
            DetailLevelCodeType.ItemReturnDescription
        };
        gc.setDetailLevel(detailLevels);
        ItemIDType itemID = new ItemIDType(id);
        ItemType item = gc.getItem(itemID);
        
        if (item != null)
            return new AuctionItem(item);
        else
            return null;
        
    }
    
    public List findItems(ItemSearchCriteria criteria)
    throws Exception {
        
        List results = new ArrayList(0);
        
        boolean filterTotalCost = false;
        
        GetSearchResultsCall api = new GetSearchResultsCall(this.apiContext);
        api.setQuery(criteria.getQuery());
        
        
        DetailLevelCodeType[] detailLevels = new DetailLevelCodeType[] {
            DetailLevelCodeType.ReturnAll,
            DetailLevelCodeType.ItemReturnCategories,
            DetailLevelCodeType.ItemReturnDescription
        };
        api.setDetailLevel(detailLevels);
        
        //returns the totals per category - still need category of individual item
        RequestCategoriesType categories = new RequestCategoriesType();
        categories.setLevels(4);
        categories.setMaxCategories(5);
        categories.setMaxSubcategories(5);
        api.setCategories(categories);
        
        // Search flags
        ArrayList al = new ArrayList();
        if( criteria.isUseDescription() )
            al.add(SearchFlagsCodeType.SearchInDescription);
        
        SearchFlagsCodeType flags[] = new SearchFlagsCodeType[al.size()];
        for(int i = 0; i < al.size(); i++ )
            flags[i] = (SearchFlagsCodeType)al.get(i);
        
        if( flags.length > 0 )
            api.setSearchFlags(flags);
        
        if (criteria.getMaximumDistance() > 0 && criteria.getZipCode() != null){
            ProximitySearchType pst = new ProximitySearchType();
            pst.setPostalCode(criteria.getZipCode());
            pst.setMaxDistance(criteria.getMaximumDistance());
            api.setProximitySearch(pst);
        }
        if (criteria.getMaximumPrice() > 0 || criteria.getMinimumPrice() > 0){
            PriceRangeFilterType priceRange = new PriceRangeFilterType();
            priceRange.setMinPrice(new AmountType(criteria.getMinimumPrice()));
            priceRange.setMaxPrice(new AmountType(criteria.getMaximumPrice()));
            api.setPriceRangeFilter(priceRange);
            filterTotalCost = criteria.isUseTotalPrice();
        }
        
        if (criteria.getMaximumDistance() > 0){
            ProximitySearchType pst = new ProximitySearchType();
            pst.setPostalCode(criteria.getZipCode());
            pst.setMaxDistance(criteria.getMaximumDistance());
            api.setProximitySearch(pst);
        }
        
        SearchResultItemType[] _temp = api.getSearchResults();
        CategoryArrayType c = api.getReturnedCategoryArray();
        CategoryType[] cats = c.getCategory();
        for (int i = 0; i < cats.length; i++) {
            if (!categoryMap.containsKey(cats[i].getCategoryID()))
                categoryMap.put(cats[i].getCategoryID(),cats[i].getCategoryName());
        }
        //System.out.println(cats.getCategory(0).getCategoryName() + ":"+cats.getCategory(0).getNumOfItems());
        
        AuctionItem item = null;
        for (int i = 0; i < _temp.length; i++) {
            item = new AuctionItem(_temp[i].getItem());
            item.getCategories().add( getCategoryName(_temp[i].getItem().getPrimaryCategory().getCategoryID()) );
            if (filterTotalCost) {
                if (item.getTotalCost() <= criteria.getMaximumPrice())
                    results.add(item);
            } else
                results.add(item);
        }
        
        return results;
        
    }
    
    public String getCategoryName(String categoryID){
        return (String) categoryMap.get(categoryID);
    }
    
    public static void main (String[] args) throws Exception {
        EbayItemAdapter ebay = new EbayItemAdapter();
        ItemSearchCriteria criteria = new ItemSearchCriteria();
        criteria.setQuery("guild acoustic guitar");
        List<AuctionItem> items = ebay.findItems(criteria);
        ItemType item = items.get(0).getItem();
        System.out.println(item.getPrimaryCategory().getCategoryID());
        //AttributeType[] atts = item.getAttributeArray().getAttribute();
        //for (int i=0;i<atts.length;i++){
            //System.out.println(atts[i].getAttributeLabel());
        //}
        //for (AuctionItem item : items) {
            //System.out.println(item.getID() + " " + item.getItem().getPrimaryCategory());
        //}
    }
    
}
