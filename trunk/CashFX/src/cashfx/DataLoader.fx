/*
 * DataLoader.fx
 *
 * Created on Oct 22, 2007, 10:53:19 PM
 */

package cashfx;

/**
 * @author TWXS025
 */

class DataLoader {
    private attribute em: EntityManager*;
    public operation loadData(startDate:String,endDate:String):AbstractPathElement*;
}

operation DataLoader.loadData(startDate:String, endDate:String){
    var payments:Payment* = [];
    
    return payments;
    
}