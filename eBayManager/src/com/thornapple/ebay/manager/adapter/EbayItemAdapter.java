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
import com.ebay.sdk.call.GetSearchResultsCall;
import com.ebay.sdk.eBayAccount;
import com.ebay.soap.eBLBaseComponents.AmountType;
import com.ebay.soap.eBLBaseComponents.DetailLevelCodeType;
import com.ebay.soap.eBLBaseComponents.ItemIDType;
import com.ebay.soap.eBLBaseComponents.ItemType;
import com.ebay.soap.eBLBaseComponents.PriceRangeFilterType;
import com.ebay.soap.eBLBaseComponents.SearchResultItemType;
import com.thornapple.ebay.manager.AuctionItem;
import com.thornapple.ebay.manager.ItemSearchCriteria;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bill
 */
public class EbayItemAdapter {
    
    private ApiContext apiContext;
    private EbayAdapterConfiguration config;
    
    /** Creates a new instance of EbayItemAdapter */
    public EbayItemAdapter() {
        config = EbayAdapterConfiguration.getInstance();
        apiContext = new ApiContext();
        ApiCredential cred = apiContext.getApiCredential();
        ApiAccount ac = cred.getApiAccount();
        eBayAccount ec = cred.geteBayAccount();
        cred.seteBayToken(config.getToken());
        apiContext.setApiServerUrl(config.getApiIURL());
    }
    
    public AuctionItem getItemDetails(String id)
    throws Exception {
        GetItemCall gc = new GetItemCall(apiContext);
        DetailLevelCodeType[] detailLevels = new DetailLevelCodeType[] {
            DetailLevelCodeType.ReturnAll,
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
        
        if (criteria.getMaximumPrice() > 0){
            PriceRangeFilterType priceRange = new PriceRangeFilterType();
            priceRange.setMinPrice(new AmountType(criteria.getMinimumPrice()));
            priceRange.setMaxPrice(new AmountType(criteria.getMaximumPrice()));
            api.setPriceRangeFilter(priceRange);
            filterTotalCost = criteria.isUseTotalPrice();
        }
        
        SearchResultItemType[] _temp = api.getSearchResults();
        
        AuctionItem item = null;
        for (int i = 0; i < _temp.length; i++) {
            item = new AuctionItem(_temp[i].getItem());
            System.out.println(item);
            if (filterTotalCost) {
                if (item.getTotalCost() <= criteria.getMaximumPrice())
                    results.add(item);
            } else
                results.add(item);
        }
        
        return results;
        
    }
    
    
    
}
