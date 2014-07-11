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
  var options = {
    "sPaginationType": "full_numbers",
    "bDestroy": true,
    "bProcessing": false,
    "oLanguage": {"sUrl": $("#datatables_idioma").val() },
    "bServerSide" : true,
    "sServerMethod" : "GET",
    "sAjaxSource" : $("#url").val()
	};

	options.aoColumns = cargaColumnas();
	options.fnServerParams = cargaParametrosExtra;

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
  return columns;
}
