<%-- 
    Document   : Factura
    Created on : 13-julio-2012, 9:26:01
    Author     : Gilberth
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri= "/WEB-INF/c.tld" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html:html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Factura</title>
        <link type="text/css" href="/Inventario/css/ui.all.css" rel="stylesheet" />
        <link type="text/css" href="/Inventario/css/comun.css" rel="stylesheet" />
        <link rel="stylesheet" type="text/css" media="screen" href="/Inventario/css/ui.jqgrid.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="/Inventario/css/themes/ui-lightness/jquery.ui.datepicker.css"/>
        <script src="/Inventario/Js/jquery-1.7.2.min.js" type="text/javascript"></script>
        <script src="/Inventario/Js/i18n/grid.locale-es.js" type="text/javascript"></script>
        <script src="/Inventario/Js/jquery.jqGrid.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="/Inventario/Js/jquery.validate.js"></script>
        <script src="/Inventario/Js/i18n/messages_es.js" type="text/javascript"></script>
        <script src="/Inventario/Js/jquery.ui.datepicker.js" type="text/javascript"></script>
        <script src="/Inventario/Js/jquery.ui.core.js" type="text/javascript"></script>
        <script src="/Inventario/Js/i18n/jquery.ui.datepicker-es.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" media="all" href="/Inventario/niceforms_files/niceforms-default.css">
        <%
            String usuario = "";
            HttpSession sesionOk = request.getSession();
            if (sesionOk.getAttribute("usuario") == null) {
        %>
        <jsp:forward page="/index.jsp">
            <jsp:param name="mensaje" value="Es obligatorio identificarse"/>
        </jsp:forward>
        <%    } else {
                usuario = String.valueOf(sesionOk.getAttribute("usuario"));
            }
        %>


        <script type="text/javascript">
            $(function(){ 
                $("#fecha").datepicker({
                    showOtherMonths: true,
                    selectOtherMonths: true,
                    changeYear: true,
                    changeMonth: true
                });
                $('#submit').click(function(e) {
                    e.preventDefault();
                    if(document.forms[0].idFactura.value==""){
                        document.forms[0].op.value="nuevo";
                    }
                    else {
                        document.forms[0].op.value="modificar";
                    }
                    $("#forma").submit(); 
                }); 
                $("#forma").validate({
                    event: "blur",
                    rules : {
                        nombre : {
                            required : true,
                            minlength : 3,
                            maxlength : 45
                        }
                    },
                    debug: false,
                    errorElement: "label",
                    submitHandler: function(form){
                        form.submit();
                        //alert('El formulario ha sido validado correctamente!');
                    }
                });
                jQuery("#list4").jqGrid({
                    url:'Jsp/PlantillaDispositivo/getGriddahico.jsp?op=bus',
                    datatype: "json",
                    height:100,
                    colNames:['IdCaracteristicaPlantilla', 'IdPlantillaDispositivo', 'Nombre', 'Habilitar', 'Obligatorio'],
                    colModel:[
                        {name:'idCaracteristicaPlantilla',index:'idCaracteristicaPlantilla', width:1},
                        {name:'idPlantillaDispositivo',index:'idPlantillaDispositivo', width:1},
                        {name:'nombre',index:'nombre', width:360, editable:true, editrules:{required:true}},
                        {name:'habilitar',index:'habilitar', width:80, editable:true, align: "center",edittype:"checkbox", formatter:'checkbox', editoptions: { value: "true:false" }},
                        {name:'obligatorio',index:'obligatorio', width:80, editable:true, align: "center",edittype:"checkbox", formatter:'checkbox', editoptions: { value:"true:false" }}		
                    ],
                    pager: '#prowed1',
                    editurl: "Jsp/PlantillaDispositivo/getGriddahico.jsp",
                    caption: "Lista de Caracteristicas"
                }); 
                jQuery("#list4").jqGrid('navGrid',"#prowed1",{edit:false,add:false,del:true});
                jQuery("#list4").jqGrid('inlineNav',"#prowed1");
                //Ocultar Columna idTipoDocumento
                jQuery("#list4").setGridParam().hideCol(["idCaracteristicaPlantilla", "idPlantillaDispositivo"]).trigger("reloadGrid");

                //Grilla Plantillas Dispositivos Disponibles
                jQuery("#list5").jqGrid({
                    url:'Jsp/PlantillaDispositivo/getGriddahicoDisponible.jsp?op=bus',
                    datatype: "json",
                    height:100,
                    colNames:['IdPlantillaDispositivo', 'Nombre'],
                    colModel:[
                        {name:'idPlantillaDispositivo',index:'idPlantillaDispositivo', width:1},
                        {name:'nombre',index:'nombre', width:360, editable:true}
                    ],
                    pager: '#prowed2',
                    editurl: "Jsp/PlantillaDispositivo/getGriddahicoDisponible.jsp",
                    caption: "Lista de Plantillas Disponibles"
                }); 
                jQuery("#list5").jqGrid('navGrid',"#prowed2",{edit:false,add:false,del:false});
                //Ocultar Columna idTipoDocumento
                jQuery("#list5").setGridParam().hideCol("idPlantillaDispositivo").trigger("reloadGrid");

                //Grilla Plantillas Dispositivos Hijas
                jQuery("#list6").jqGrid({
                    url:'Jsp/PlantillaDispositivo/getGriddahicoHija.jsp?op=bus',
                    datatype: "json",
                    height:100,
                    colNames:['IdPlantillaDispositivo', 'IdPlantillaDispositivoHija', 'Nombre'],
                    colModel:[
                        {name:'idPlantillaDispositivo',index:'idPlantillaDispositivo', width:1},
                        {name:'idPlantillaDispositivoHija',index:'idPlantillaDispositivoHija', width:1},
                        {name:'nombre',index:'nombre', width:360, editable:true}
                    ],
                    pager: '#prowed3',
                    editurl: "Jsp/PlantillaDispositivo/getGriddahicoHija.jsp",
                    caption: "Lista de Plantillas Hijas"
                }); 
                jQuery("#list6").jqGrid('navGrid',"#prowed3",{edit:false,add:false,del:false});
                //Ocultar Columna idTipoDocumento
                jQuery("#list6").setGridParam().hideCol(["idPlantillaDispositivo", "idPlantillaDispositivoHija"]).trigger("reloadGrid");
                jQuery("#a2").click( function(){ 
                    var idd = jQuery("#list5").jqGrid('getGridParam','selrow'); 
                    if (idd) {
                        $.post("Jsp/PlantillaDispositivo/getGriddahicoDisponible.jsp?op=del",{ id:idd },function(data){jQuery("#list5").trigger("reloadGrid");jQuery("#list6").trigger("reloadGrid")})
                    }else{
                        alert("Por favor, selecciones una Plantilla Disponible");
                    }
                });
                jQuery("#a3").click( function(){ 
                    var idd = jQuery("#list6").jqGrid('getGridParam','selrow'); 
                    if (idd) {
                        $.post("Jsp/PlantillaDispositivo/getGriddahicoHija.jsp?op=del",{ id:idd },function(data){jQuery("#list6").trigger("reloadGrid");jQuery("#list5").trigger("reloadGrid")})
                    }else{
                        alert("Por favor, selecciones una Plantilla Disponible");
                    }
                });
            }); 

            function nuevo(){
                document.forms[0].op.value="";
                document.forms[0].idFactura.value="";
                document.forms[0].idEntidad.value="";
                document.forms[0].fecha.value="";
                document.forms[0].total.value="";
            }
            
            function eliminar(){
                document.forms[0].op.value="eliminar";
                document.forms[0].submit();
            }
            
            function atras(){
                document.forms[0].op.value="atras";
                document.forms[0].submit();
            }
            
        </script>

        <style type="text/css">
            .error-message, label.error {
                color: #ff0000;
                margin:0;
                display: inline;
                font-size: 1em !important;
                font-weight:lighter;
            }
        </style>
    </head>
    <body>
        <div >
            <html:form action="/Factura" method="post" styleId="forma">

                <input type="hidden" name="op" value=""> 
                <input type="hidden" name="idFactura" value='<%= String.valueOf(request.getAttribute("getIdFactura"))%>'> 

                <fieldset>
                    <legend>Ingreso de Factura</legend>

                    <table>
                        <tr>
                            <td class="text">Entidad</td>
                            <% if (request.getAttribute("getIdFactura") != "") {%> 
                            <td><html:select property="idEntidad" styleId="idEntidad" size="1" style="width:240px;" disabled="true" value='<%= String.valueOf(request.getAttribute("getIdEntidad"))%>'>
                                    <c:forEach items="${CMB_ENTIDAD}" var="cat">
                                        <html:option value="${cat.idEntidad}"><c:out value='${cat.nombre}'/></html:option>
                                    </c:forEach>
                                </html:select></td>
                            <% } else {%> 
                            <td><html:select property="idEntidad" styleId="idEntidad" size="1" style="width:240px;" value='<%= String.valueOf(request.getAttribute("getIdEntidad"))%>'>
                                    <c:forEach items="${CMB_ENTIDAD}" var="cat">
                                        <html:option value="${cat.idEntidad}"><c:out value='${cat.nombre}'/></html:option>
                                    </c:forEach>
                                </html:select></td>
                            <% }%> 
                        </tr>
                        <tr>
                            <td class="text">No. Factura</td>
                            <% if (request.getAttribute("getIdFactura") != "") {%> 
                            <td><html:text property="numFactura" readonly="true" styleId="numFactura" value='<%= String.valueOf(request.getAttribute("getNumFactura"))%>'></html:text></td>
                            <% } else {%> 
                            <td><html:text property="numFactura" styleId="numFactura" value='<%= String.valueOf(request.getAttribute("getNumFactura"))%>'></html:text></td>
                            <% }%> 
                        </tr>
                        <tr>
                            <td class="text">Fecha</td>
                            <% if (request.getAttribute("getIdFactura") != "") {%> 
                            <td><html:text property="fecha" readonly="true" styleId="fecha" value='<%= String.valueOf(request.getAttribute("getFecha"))%>'></html:text></td>
                            <% } else {%> 
                            <td><html:text property="fecha" styleId="fecha" value='<%= String.valueOf(request.getAttribute("getFecha"))%>'></html:text></td>
                            <% }%> 
                        </tr>
                        <tr>
                            <td class="text">Total</td>
                            <td><html:text property="total" styleId="total" value='<%= String.valueOf(request.getAttribute("getTotal"))%>'></html:text></td>
                        </tr>
                        <%
                            if (request.getAttribute("getIdPlantillaDispositivo") != "") {
                        %>
                        <tr><td colspan="9">&nbsp;</td></tr>
                        <tr>
                            <td colspan="9" class="text">Por favor establesca cuales serian las caracteristicas para esta plantilla.</td>
                        </tr>
                        <tr><td colspan="9">&nbsp;</td></tr>
                        <tr>
                            <td colspan="9"><table id="list4"></table></td>
                            <td><div id="prowed1"></div></td>
                        </tr>
                        <tr><td colspan="9">&nbsp;</td></tr>
                        <tr>
                            <td colspan="9" class="text">En esta seccion puede asignar las plantillas hijas.</td>
                        </tr>
                        <tr><td colspan="9">&nbsp;</td></tr>
                        <tr>
                            <td colspan="3"><table id="list5"></table></td>
                            <td><div id="prowed2"></div></td>
                            <td><div align="center"><a class="boton" id="a2" href="#" title="Agregar Plantilla Disponible">></a></div><div align="center">&nbsp;</div><div align="center"><a class="boton" id="a3" href="#" title="Quitar Plantilla Hija"><</a></div></td>
                            <td></td>
                            <td><table id="list6"></table></td>
                            <td><div id="prowed3"></div></td>
                        </tr>
                        <%  }
                        %>
                        <tr><td colspan="9">&nbsp;</td></tr>
                        <tr>
                            <td colspan="9"><a class="boton" href="javascript:nuevo();">Nuevo</a> <a class="boton" id="submit" href="javascript:guardar();">Guardar</a> <% if (request.getAttribute("getIdPlantillaDispositivo") != "") {%> <a class="boton" href="javascript:eliminar();">Eliminar</a> <% }%> <a class="boton" href="javascript:atras();">Volver</a></td>
                        </tr>
                        <%
                            if (request.getAttribute("respuesta") != "") {
                        %>
                        <tr><td colspan="9">&nbsp;</td></tr>
                        <tr>
                            <td colspan="9" class="text"><%= String.valueOf(request.getAttribute("respuesta"))%></td>
                        </tr>
                        <%  }
                        %>
                    </table>
                </fieldset>
            </html:form>
        </div>
    </body>
</html:html>
