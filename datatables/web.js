var table;

$(document).ready(function(){
	lanzarBusqueda();
});


function lanzarBusqueda(){
	if(table==undefined){
		initDatatable();
	}else{
		table.fnDraw();
	}
}

function initDatatable(){
	//parametros iniciales
	var options = {
		"sPaginationType": "full_numbers",
		"bDestroy": true,
		"bProcessing": false,
		"oLanguage": {"sUrl": $("#datatables_idioma").val() }, //sacamos de campo url de jsp multiidoma
		"bServerSide" : true,
		"sServerMethod" : "GET",
		"sAjaxSource" : $("#url").val(),
		"drawCallback": function( settings ) {
			console.log("drawCallback...");
		}//, //ojo! array en ie8 si termina en coma falla
		//"bFilter": false, //desactiva campo busqueda
		//"orderMulti": false, //evita busquedas multiples pulsando shift
		//"pageLength": $("#registrosPorPaginaDatatables").val() //registros por página sale de campo
	};

	options.aoColumns = cargaColumnas();
	//opc1 enviar parametros extras - solo usar 1
	options.fnServerParams = cargaParametrosExtra;
	//opc2 enviar parametros extras - solo usar 1
	options.data = function (d) {
	                d.fechadesde = $("#fechadesde").val();
	                d.fechahasta = $("#fechahasta").val();
				}

	options.fnDrawCallback = function( oSettings,json ) {
		terminadaCarga();
		customCallback(oSettings, json);
	}
	
	//sobreescribimos llamada ajax para controlar errores
	options.fnServerData = function ( sSource, aoData, fnCallback, oSettings ) {
		oSettings.jqXHR = $.ajax( {
			"dataType": 'json',
			"type": "GET",
			"url": sSource,
			"data": aoData,
			"success": fnCallback,
			"cache": false,
			"error": function(jqXHR, textStatus, errorThrown) {    
	    	    gestionDeError(jqXHR.responseText);            
      		}
    	});
	};
	
	//quitar loading manualmente de tabla 
	jQuery.fn.dataTableExt.oApi.fnProcessingIndicator = function ( oSettings, onoff ) {
	    if ( typeof( onoff ) == 'undefined' ) {
	        onoff = true;
	    }
	    this.oApi._fnProcessingDisplay( oSettings, onoff );
	};
	
	
	table = $("#tablaDatos").dataTable(options);
}

cargaParametrosExtra = function ( aoData ) {
	aoData.push( { "name": "nombreCampoEstatico", "value": "1" } );
	aoData.push( { "name": "nombreCampoDinamico", "value": $("#campoPais").val() } );
}


function terminadaCarga(){
  
}

function cargaColumnas(){
	var columns = [];
	//uso los th para indicar parametros especificos por filas
	var columnName = $("#tickets").find('th');
	$(columnName).each(function(){
		var propiedades = {};
		propiedades['bSortable']=true;
		if(($(this).attr('mRender')!="")){
			propiedades['mRender']=eval($(this).attr('mRender'));
		}
		propiedades['data']=$(this).attr('data');
		propiedades['title']=$(this).attr('title');
		propiedades['className']=$(this).attr('className');
		columns.push(propiedades);
	});
	return columns;
}


function columnaNormalizado(data, type, full){
	if(type=="display"){
		//aqui formateamos el contenido de la celda
		return '<div title="'+data+'">'+recortarString(data,25)+'</div>';
	}else{
		//esto se devolverá en el resto de casos como por ejemplo cuando ordenamos
		return data;
	}
}
