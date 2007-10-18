package cashfx;

import javafx.ui.*;
import javafx.ui.canvas.*;
import javax.swing.JComponent;
import java.lang.System;
import java.awt.Point;
import javax.swing.JFrame;
import com.sun.jna.examples.WindowUtils;
import java.lang.System;
import java.lang.Math;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;

class Plottable extends PathElement{
  public operation X():Number;
  public operation Y():Number;
  public attribute selected: Boolean;
}

class Payment extends Plottable {
  public attribute dateFormat: SimpleDateFormat;
  private attribute convertedDate: Number;
  public attribute date: String;
  public attribute amount: Number;
  public attribute description: String;
}

attribute Payment.dateFormat = new SimpleDateFormat("MM-dd-yyyy");
attribute Payment.convertedDate = -1;

operation Payment.X() {
	if (convertedDate < 0) {
        	convertedDate = dateFormat.parse(date).getTime();	
	}
	return convertedDate;
}

operation Payment.Y() {
	return amount;
}

public class Graph extends CompositeNode {
  public attribute alpha: Number;
  public attribute baseline: Number;
  public attribute x: Number;
  public attribute y: Number;
  public attribute sx: Number;
  public attribute sy: Number;
  public attribute off_x: Number;
  public attribute off_y: Number;
  public attribute width: Number;
  public attribute height: Number;
  public attribute data: Plottable*;
  private attribute gdata: Plottable*;
  public operation updateData():AbstractPathElement*;
  public attribute horizontalSpacing:Number;
  public attribute baselineX:Number;
}

attribute Graph.alpha = .01;
attribute Graph.x = 0;
attribute Graph.y = 0;
attribute Graph.sx = 0;
attribute Graph.sy = 0;
attribute Graph.off_x = 20; 
attribute Graph.off_y = 20;
attribute Graph.baseline = 500;

operation sum(a:Plottable*,b:Plottable){
	var sum = 0; 
	//System.out.println("summing {b}");
	//System.out.println("b in a:{select indexof p from p in a where p == b}");
	var i = select first indexof p from p in a where p == b;
	for (x in a where indexof x <= i){
		sum+=((Payment)x).amount;
	}
	System.out.println("sum operation returning {sum}");
	return sum;
}

operation initializeData(graph:Graph,data:Plottable*) {
	var newData = data;
	var min = null;
	var max = null;
	var total = 0;
	for (point in data){
		System.out.println("looking at:{point.X()},{point.Y()}");
		if (min == null){min=point;}
		if (max == null){max=point;}			
		if (point.X()<min.X()){min=point;} 
		if (point.X()>max.X()){max=point;}	 
		total += point.Y();
		var p = select p from p in newData where p.X() == point.X();		
		if (p <> null){
			//do the summing
			//for (newp in p){
				//total += newp.Y();		
			//}	
			System.out.println("total up to {point.X()} is {total}");
			var _newp = Payment{date:((Payment)p).date,amount:total};
			delete newData[_p|_p.X() == point.X()];
			insert _newp as last into newData;
			//System.out.println("newData is {newData}");
		}
	}
	var firstDay = Calendar.getInstance().setTimeInMillis(min.X()).get(Calendar.DAY_OF_YEAR);
	var lastDay = Calendar.getInstance().setTimeInMillis(max.X()).get(Calendar.DAY_OF_YEAR);
	graph.baselineX = min.X();
	System.out.println("baselineX:{graph.baselineX}");
	//var days = lastDay-firstDay;
	var days = max.X()-min.X();
	if (days == 0){days = 1;}
	System.out.println("data range is {days}");
	graph.horizontalSpacing = graph.width/days;
	System.out.println("max:{lastDay}, min:{firstDay}");
	System.out.println("h-spacing is {graph.horizontalSpacing}");
	//System.out.println(newData);
	graph.gdata = newData;
}

operation Graph.updateData(){
	var path:AbstractPathElement* = [];
	System.out.println("updating data...");
	System.out.println("x: {off_x + x} y: {baseline}");
	insert MoveTo {x: bind off_x + x, y: bind baseline} into path;
	var _last = 0;
	var cal = Calendar.getInstance();
	var bX = Calendar.getInstance().setTimeInMillis(baselineX).get(Calendar.DAY_OF_YEAR);
	var days = 0;
        for (point in gdata){	
		System.out.println("adding data for...{point.X()},{point.Y()}");
		//days = cal.setTimeInMillis(point.X()).get(Calendar.DAY_OF_YEAR)-bX;
		//System.out.println("{point.X()} translates to {days}");
		System.out.println("x:{(point.X()-baselineX)*horizontalSpacing} y:{baseline-point.Y()}");
		var py = select p.Y() from p in data where p.X() == point.X();
		insert LineTo{x:bind off_x + x + ((point.X()-baselineX)*horizontalSpacing), y: baseline-point.Y()} into path;
		_last = off_x + ((point.X()-baselineX)*horizontalSpacing);
	}
	insert LineTo{x: _last,y: bind baseline} into path;
	System.out.println("x: {_last} y: {baseline}");
	insert LineTo{x: bind off_x + x,y: bind baseline} into path;
	System.out.println("x: {off_x + x} y: {baseline}");
	return path;
}

var graph = Graph{};
graph.width = 650;
graph.height = 650;
graph.data = [
Payment{amount:20,date:"10-1-2007" ,description:"Chipotle"},
Payment{amount:40,date:"10-2-2007",description:"Chipotle"},
Payment{amount:80,date:"10-3-2007",description:"Chipotle"},
Payment{amount:30,date:"10-4-2007",description:"Chipotle"},
Payment{amount:90,date:"10-4-2007",description:"Chipotle"},
Payment{amount:-20,date:"10-4-2007",description:"Chipotle"},
Payment{amount:50,date:"10-5-2007",description:"Chipotle"},
Payment{amount:120,date:"10-6-2007",description:"Chipotle"},
Payment{amount:20,date:"10-7-2007",description:"Chipotle"}
];
initializeData(graph,graph.data);

var frame = Frame{
title: "Test"
width: bind graph.width + 20
height: 650
};

function Graph.composeNode() = Group {
	content: [
		Line {
            	x1: bind off_x -2
            	y1: bind off_y
            	x2: bind off_x - 2
            	y2: bind baseline + 2
            	stroke: black
            	strokeWidth: 2
        	},
		Line {
            	x1: bind off_x - 2
            	y1: bind baseline + 2
            	x2: bind off_x + width - 2
            	y2: bind baseline + 2
            	stroke: black
            	strokeWidth: 2
        	},
		Path{
		d: bind updateData()
		opacity: bind alpha
        	fill: yellow
        	strokeWidth: 2
		onMousePressed:operation(e:CanvasMouseEvent){
			//select closest point to e.x - use a box for area (ppoint in box)
			var p = select point from point in data where (point.X()-baselineX) == e.x+off_x;
			if (p <> null){
			//do something, then
			System.out.println("{p.X()}");
			p.selected = true;
			return;
			} 
			var gap = 10000000;
			var c = 0;
			for (point in data) {
				c = (point.X()-baselineX)*horizontalSpacing - e.x + off_x;
				//point.X()-baselineX-e.x+off_x
				if (Math.abs(c)<=gap){
					p = point;
					gap = Math.abs(c);
				}
			}	
			System.out.println("cliked {e.x}, gap to point is {p.X()}({gap})");
			p.selected = true;
			//Math.min(1,2);
		}
		},
		Line {//selected date
            	x1: bind select first ((point.X()-baselineX)*horizontalSpacing)+off_x from point in data where point.selected
            	y1: bind off_y
            	x2: bind select first ((point.X()-baselineX)*horizontalSpacing)+off_x from point in data where point.selected
            	y2: bind baseline + 2
            	stroke: red
            	strokeWidth: 2
		cursor: HAND
		onMouseDragged: operation(e:CanvasMouseEvent) {
                	sx += e.localDragTranslation.x;
            	}
        	}
	]
	//catch these and set the select value in the paymentTable
	onMousePressed:{}
	onMouseMoved:{}
	onMouseReleased:{}
};


var paymentTable = Table{
	columns:
                [TableColumn {
                    text: "Date"
                },
		TableColumn {
		    text: "Description"
		},
		TableColumn {
		    text: "Amount"
		},
		TableColumn {
		    text: "Balance"
		}]
	cells: bind foreach (payment in graph.data)
		[TableCell {
                        text:bind "{((Payment)payment).date}"
                        selected: bind payment.selected
                 },
		 TableCell {
                        text:bind ((Payment)payment).description  
                 },
		 TableCell {
                        text:bind "${((Payment)payment).amount}"
                 },
		 TableCell {
                        text:bind "${sum(graph.data,payment)}"
                 }]
	visible: true
};

/*
TODO: provide way to enter new Payments
as an aside- also delete directly from the table
also, should make the table cells editable
*/
var editView = View {
	transform: translate(50, 70)
	content:TextField{columns:30,text: "Descritpion"}
};

frame.content = Canvas{
	content:Group{
		content:[graph,editView,
			View{transform: translate(150, 70),content:paymentTable}]
	}
};
trigger on (Graph.visible = value) {
	if (value){
		alpha = [0.00,0.01 .. 1.00] dur 2000 fps 90;
	}
}
frame.undecorated = true;
frame.visible = true;
WindowUtils.setWindowTransparent(frame.getWindow(), true);

