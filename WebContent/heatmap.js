function showload()
{
	$("#loading-image").show();
	//$('#sc:b1').prop('disabled', true);
	//$('#sc:b2').prop('disabled', true);
}

function hideload()
{
	$("#loading-image").hide();
	//$('#sc:b1').prop('disabled', false);
	//$('#sc:b2').prop('disabled', false);
}

function getdata()
{
	var x = document.getElementsByClassName("sortable");

	for (var i=0;i<x.length;i++)
	{
		sorttable.makeSortable(x[i]);
	}
	
	var prime_data = document.getElementById("sc:heatdata").value.split("<*>");
	var data = prime_data[0];
	var spl1 = data.split("<|>");
	var spl2 = prime_data[1].split("<|>");
	if(spl1.length>1)
	{
		var colnames = spl1[0].split("<,>");
		var rownames = [];
		
		var heatmapmatrix = [];

		for (var i=1;i<spl1.length;i++)
		{
			var temp = spl1[i].split("<!>");
			
			var secondsplit = temp[0].split("<:>");
			
			rownames.push(secondsplit[0]);
			var heatmapmatrix2 = [];
			heatmapmatrix2.push(temp[1].split("<,>"));
			makeheatmap(colnames, rownames, heatmapmatrix2, 'heatmap_prime_'+secondsplit[1], secondsplit[0], 240);
//			makelineplot(heatmapmatrix2[0], colnames, 'lineplot_prime_'+secondsplit[1], secondsplit[0]);
			document.getElementById("ldata_prime_"+secondsplit[1]).value = spl1[0]+"<|>"+secondsplit[0]+"<:>"+temp[1]+"<!>4";
			heatmapmatrix.push(temp[1].split("<,>"));
			
			var divname = "heatmap_"+secondsplit[1];
				
			var rownamesS = [];
			var heatmapmatrixS = [];
				
			var spl3 = spl2[i-1].split("<!>");
			
			//for multi plot;
			/*var pnames = [];
			var pvalues = [];
			pnames.push(secondsplit[0]);
			pvalues.push(temp[1].split("<,>"));*/
			
			for (var y=0;y<spl3.length;y++)
			{
				var pnam = spl3[y];
				rownamesS.push(pnam);
				y++;
				var da = spl3[y].split("<,>");
					
				//shuts down the plot when comented
//				makedoublelineplot(heatmapmatrix2[0], da, colnames, 'lineplot_'+secondsplit[1]+'_'+pnam, pnam, secondsplit[0], pnam);
				document.getElementById("ldata_"+secondsplit[1]+"_"+pnam).value = pnam+"<:>"+spl3[y]+"<!>2";
				
				/*pnames.push(pnam);
				pvalues.push(da);*/
				
				heatmapmatrixS.push(da);
			}
			
//			makemultilineplot(pvalues, colnames, 'lineplot_prime_'+secondsplit[1], secondsplit[0], pnames);
				
			var nuz = 30*rownamesS.length;
				
			if(nuz<400) nuz = 400;
				
			makeheatmap(colnames, rownamesS, heatmapmatrixS, divname, "", nuz);
			
		}

	}
	
	hideload();
}

function makeheatmap(colnames, rownames, heatmapmatrix, divtouse, plotname, heig)
{
	//alert("g1");
	/*
	for (var y=0;y<heatmapmatrix.length;y++)
	{
		
		for (var v=0;v<heatmapmatrix[y].length;v++)
		{
			//alert(y+","+v+"="+heatmapmatrix[y][v]);
		}
		
	}
	1st heatmap_prime_1422720_at
	2nd - heatmap_1422720_at
	
	
	*/
	var data = [
	    {
	    	x: colnames,
	    	y: rownames,
			z: heatmapmatrix,
			type: 'heatmap',
			zmax: 15,
			zmin: 0
		}
	];
	//alert("g2");
	var layoutDIY = {
		title: plotname,
		height: heig
	};
	//alert("g3:"+divtouse);
	Plotly.newPlot(divtouse, data, layoutDIY, {modeBarButtonsToRemove: ['sendDataToCloud']});
	//alert("g4");
}

function makelineplot(values, pointnames, divtouse, plotname)
{
	
	var trace = {
		x: pointnames,
		y: values,
		mode: 'lines+markers'
	};
	
	var data = [trace];
	
	var layoutDIY = {
		title: plotname,
		yaxis: {
		    autorange: false, 
			range: [0, 15]
		}
		//height: heig
	};
	
	Plotly.newPlot(divtouse, data, layoutDIY, {modeBarButtonsToRemove: ['sendDataToCloud','lasso2d','select2d']});
}

function makedoublelineplot(values1, values2, pointnames, divtouse, plotname, name1, name2)
{
	var trace1 = {
		x: pointnames,
		y: values1,
		mode: 'lines+markers',
		name: name1
	};
	
	var trace2 = {
		x: pointnames,
		y: values2,
		mode: 'lines+markers',
		name: name2
	};
	
	var data = [trace1, trace2];
	
	var layoutDIY = {
		title: plotname,
		yaxis: {
		    autorange: false, 
			range: [0, 15]
		}
		//height: heig
	};
	
	Plotly.newPlot(divtouse, data, layoutDIY, {modeBarButtonsToRemove: ['sendDataToCloud','lasso2d','select2d']});
}

function openPopUp(plot1, plot2)
{
	var plot = document.getElementById(plot1).value+"<|>"+document.getElementById(plot2).value;
	document.getElementById("topopup").value = plot;
	window.open("popupplot.jsf", "_blank","location=1,status=1,scrollbars=1");
	
}

function openPopUpPrime(mplot)
{
//	alert("1: ldata_prime_"+mplot);
	var plot = document.getElementById("ldata_prime_"+mplot).value;
	var checked = $('.c_'+mplot+':checked');
	for(var y=0;y<checked.length;y++)
	{
		var sec = checked[y].id.split("<:>")[1];
//		alert("2 ldata_"+mplot+"_"+sec);
		plot = plot+"<|>"+document.getElementById("ldata_"+mplot+"_"+sec).value;
	}
//	alert(plot);
	document.getElementById("topopup").value = plot;
	window.open("popupplot.jsf", "_blank","location=1,status=1,scrollbars=1");
	
	/*alert(mplot);
	var checked = $('.c_'+mplot+':checked');
	alert(checked.length);
	for(var y=0;y<checked.length;y++)
	{
		alert("id"+y+":"+checked[y].id);
	}*/
}

function openPopUpMulti()
{
	var ids = $('.idis');
	var plot = "";
	
	for(var i=ids.length-1;i>=0;i--)
	{
//		alert(ids[i].value);
		var mplot = ids[i].value;
		var checked = $('.c_'+mplot+':checked');
		
		if(checked.length>0)
		{
			if(plot=="") plot = document.getElementById("ldata_prime_"+mplot).value;
			else plot = plot+"<|>"+document.getElementById("ldata_prime_"+mplot).value.split("<|>")[1];
		}
	}
	
	for(var i=ids.length-1;i>=0;i--)
	{
//		alert(ids[i].value);
		var mplot = ids[i].value;
		var checked = $('.c_'+mplot+':checked');
		
		for(var y=0;y<checked.length;y++)
		{
			var sec = checked[y].id.split("<:>")[1];
			plot = plot+"<|>"+document.getElementById("ldata_"+mplot+"_"+sec).value;
		}
		
	}
	
//	alert(plot);
	
	if(plot!="")
	{
		document.getElementById("topopup").value = plot;
		window.open("popupplot.jsf", "_blank","location=1,status=1,scrollbars=1");
	}
	
	
	/*var plot = document.getElementById("ldata_prime_"+mplot).value;
	var checked = $('.c_'+mplot+':checked');
	for(var y=0;y<checked.length;y++)
	{
		var sec = checked[y].id.split("<:>")[1];
		plot = plot+"<|>"+document.getElementById("ldata_"+mplot+"_"+sec).value;
	}
	document.getElementById("topopup").value = plot;
	window.open("popupplot.jsf", "_blank","location=1,status=1,scrollbars=1");*/
}

function openedPopUp()
{
//	alert("GLLLLLLLLLLLLLLLL");
//	alert(opener.document.getElementById("topopup").value);

	//<!>
	
	var plot = opener.document.getElementById("topopup").value;
	var mainsplit = plot.split("<|>");
	var timepoints = mainsplit[0].split("<,>");
	var names = [];
	var values = [];
	var sizs = [];
	
//	for (var i=1;i<mainsplit.length;i++)
	for(var i=mainsplit.length-1;i>=1;i--)
	{
		var secsplit = mainsplit[i].split("<:>");
		var thirdplit = secsplit[1].split("<!>");
		names.push(secsplit[0]);
		values.push(thirdplit[0].split("<,>"));
		sizs.push(thirdplit[1]);
	}
	
//	var temp = "";
//	
//	for(var i=0;i<values[0].length;i++)
//	{
//		temp = temp+"::"+values[0][i];
//	}
//	
//	alert(temp);
	
	makemultilineplot(values, timepoints, "plotdiv", "", names, sizs);
}

function makemultilineplot(values, pointnames, divtouse, plotname, names, sizs)
{
	
	var data = [];
	
	for(var i=names.length-1;i>=0;i--)
	{
		var trace = {
			x: pointnames,
			y: values[i],
			mode: 'lines+markers',
			name: names[i],
			line: {
				width: sizs[i]
			}
		};
		
		data.push(trace);
	}
	
	var vals = [];
	
	for(var i=0;i<pointnames.length;i++)
	{
		vals[i] = 5;
	}
	
	var sep = {
		x: pointnames,
		y: vals,
		mode: 'lines',
		name: 'Cutoff',
		line: {
		    dash: 'dot',
		    color: 'red',
			width: 2
		}
	};
	
	data.push(sep);
	
	var layoutDIY = {
		title: plotname,
		autosize: false,
		width: 1000,
		yaxis: {
		    autorange: false, 
			range: [0, 15]
		}
		//height: heig
	};
	
	Plotly.newPlot(divtouse, data, layoutDIY, {modeBarButtonsToRemove: ['sendDataToCloud','lasso2d','select2d']});
}

function checekAllByType(mplot)
{
	if($("#m_"+mplot).is(':checked')) $('.c_'+mplot).attr('checked', 'checked');
	else $('.c_'+mplot).removeAttr('checked');
}

function drawSinglePlot()
{
	hideload();
	var vals = document.getElementById("plot").value;
//	alert(vals);
	
	var s1 = vals.split("<|>");
	
	var pointnames = s1[0].split("<,>");
	
	var s3 = s1[1].split("<:>");
	var s2 = s1[2].split("<:>");
	
	var names = [s2[0], s3[0]];
	var sizs = [2, 2];
	var values = [s2[1].split("<,>"), s3[1].split("<,>")];
	
	
	makemultilineplot(values, pointnames, "plotdiv", "", names, sizs);
}

function filterTables(iskendal)
{
//	var cut= 0.51;
	var cut;
	
	
	if(iskendal) cut = document.getElementById("sc:tabv:searchText2").value;
	else cut = document.getElementById("sc:tabv:searchText3").value;
	
	var downd = document.getElementById("sc:downdata").value;

	var tats = downd.split("<.>");
	
	var newtext = "";
	
	for(var i=0;i<tats.length;i++)
	{
		if(i>0) newtext = newtext+"<.>";
		var itex = tats[i].split("<|>");
		newtext = newtext+itex[0]+"<|>"+itex[1];
		
		/*
		if(newtext=="") newtext = itex[0]+"<|>"+itex[1];
		else newtext = newtext+"<|>"+itex[0]+"<|>"+itex[1];
		*/
		
		for(var r=2;r<itex.length;r++)
		{
			var iitex = itex[r].split("<;>");
			var val1;
			if(iskendal) val1 = iitex[5]; //Kendall
			else val1 = iitex[6]; //Kendall dev
			if(val1<0) val1 = 0-val1;
			if(val1>cut)  newtext = newtext+"<|>"+itex[r];
		}
		
		tats[i].split(": ");
		var tid = 'tab_'+tats[i].split(": ")[1].split("<;>")[0];
		
		var table = document.getElementById(tid);
		
		var removeFromTab = [];
		
		for(var z=1;z<table.rows.length;z++)
		{
			var val1;
			if(iskendal) val1 = table.rows[z].cells[1].innerHTML;
			else val1 = table.rows[z].cells[2].innerHTML;
			if(val1<0) val1 = 0-val1;
			if(val1<cut) removeFromTab.push(z);
			
		}
		
		var sub = 0;
		for(var z=0;z<removeFromTab.length;z++)
		{
			table.deleteRow(removeFromTab[z]-sub);
			sub = sub+1;
		}
	}
	
//	alert(newtext);
	document.getElementById("sc:downdata").value = newtext;
	
}

function filterTables_old()
{
//	var cut= 0.51;
	var cut= document.getElementById("sc:tabv:searchText2").value;
	
	var downd = document.getElementById("sc:downdata").value;

	var tats = downd.split("<.>");
	
	var newtext = "";
	
	for(var i=0;i<tats.length;i++)
	{
		if(i>0) newtext = newtext+"<.>";
		var itex = tats[i].split("<|>");
		newtext = newtext+itex[0]+"<|>"+itex[1];
		
		/*
		if(newtext=="") newtext = itex[0]+"<|>"+itex[1];
		else newtext = newtext+"<|>"+itex[0]+"<|>"+itex[1];
		*/
		
		for(var r=2;r<itex.length;r++)
		{
			var iitex = itex[r].split("<;>");
			var val1 = iitex[5];
			if(val1<0) val1 = 0-val1;
			var val2 = iitex[6];
			if(val2<0) val2 = 0-val2;
			if(val1>cut || val2>cut)  newtext = newtext+"<|>"+itex[r];
		}
		
		tats[i].split(": ");
		var tid = 'tab_'+tats[i].split(": ")[1].split("<;>")[0];
		
		var table = document.getElementById(tid);
		
		var removeFromTab = [];
		
		for(var z=1;z<table.rows.length;z++)
		{
			var val1 = table.rows[z].cells[1].innerHTML;
			if(val1<0) val1 = 0-val1;
			var val2 = table.rows[z].cells[2].innerHTML;
			if(val2<0) val2 = 0-val2;
			if(val1<cut && val2<cut) removeFromTab.push(z);
			
		}
		
		var sub = 0;
		for(var z=0;z<removeFromTab.length;z++)
		{
			table.deleteRow(removeFromTab[z]-sub);
			sub = sub+1;
		}
	}
	
//	alert(newtext);
	document.getElementById("sc:downdata").value = newtext;
	
}