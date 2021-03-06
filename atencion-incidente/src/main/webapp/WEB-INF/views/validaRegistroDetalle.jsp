<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!doctype html>
<html>
	<head>
 		<title>Detalle de Valores Claves</title>
 		<!-- Latest compiled and minified CSS -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/bootstrap/css/bootstrap.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/bootstrap/css/bootstrap-datepicker.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/vendors/datatables/css/jquery.dataTables.css">
		<!-- Optional theme -->
		
 		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css" />
 		<script type="text/javascript" src="${pageContext.request.contextPath}/vendors/js/jquery.min.js"></script>
 		<script src="${pageContext.request.contextPath}/vendors/bootstrap/js/bootstrap.min.js"></script>
 		<script src="${pageContext.request.contextPath}/vendors/bootstrap/js/bootstrap-datepicker.js"></script>

 	</head>

 	<body>
 		<header>
 			<div class="bg-main">
 				<div id="header-nav" class="wrap">
 					<div class="pull-left">
 						<img src="${pageContext.request.contextPath}/img/logo.png" alt="Logo" /> 
 					</div>
 					<div class="pull-right" id="picture">
 						<img src="${pageContext.request.contextPath}/img/usuario.png" alt="Logo" /> 
 					</div>
 				</div>
 			</div>
 			<div class="wrap">
 				<p class="txt-oge text-right" >Usuario SI2921 - Javier Huaman</p>
 			</div>
 		</header>

 		<div class="wrap">
  			<div class="pull-left" id="menu">
    			<ul class="nav nav-pills nav-stacked">
  					<li><a href="#" class="menu"><span class="glyphicon glyphicon-folder-close"></span> Administraci&oacute;n</a></li>
            		<li >
            			<i class="glyphicon glyphicon-triangle-top arrow"></i>
            			<a href="#" class="menu">
            				<span class="glyphicon glyphicon-folder-close"></span>  Sol. de Atenci&oacute;n de Incidente 
            			</a>
            		</li>
            		<li>
            			<a href="${pageContext.request.contextPath}/solicitud/consulta" class="menu">
            				Consulta de Sol. CTI
            			</a>
            		</li>
            		<li ><a href="#" class="menu"><span class="glyphicon glyphicon-folder-close"></span> Sol. de Servicio</a></li>
            		<li ><a href="#" class="menu"> <span class="glyphicon glyphicon-folder-close"></span> Sol. de Atenci&oacute;n de Req.</a></li>
            		<li class="active"><a href="${pageContext.request.contextPath}/incidente/actualizarbc" style="padding-left: 30px" > <span class="glyphicon glyphicon-folder-close"></span> Actualizar Incidentes BC.</a></li>
				</ul>
  			</div>
  			<div id="content">
    			<div class="container-fluid">
    				<div class="panel panel-default">
  						<div class="panel-heading">
    						<h3 class="panel-title">Detalle Valores Clave</h3>
  						</div>
  						<div class="panel-body">

    						<form>
    							<div class="row">
    								<div class="col-md-4">
    									<div class="form-horizontal">
  											<div class="form-group">
    											<label for="inputtext3" class="col-sm-5 control-label">Tipo Solicitud:</label>
    											<div class="col-sm-7">
      												<form:select path="tiposSolicitud" id="tipoSolicitud" cssClass="form-control" required="true" disabled="true" >
				    									<c:forEach items="${tiposSolicitud}" var="tipo">
				            							  <option 
				            							       <c:if test="${tipo.key eq validaRegistro.idTipoSolicitud}">
				            							         selected="selected"
				            							       </c:if>
				            							  value="${tipo.key}">${tipo.value} </option>
				        								</c:forEach>
													</form:select>
    											</div>
  											</div>
  											<div class="form-group">
    											<label for="inputtext3" class="col-sm-5 control-label">Sistema:</label>
												<div class="col-sm-7">
      												<form:select path="sistemas" id="sistema" cssClass="form-control" required="true" disabled="true" >
				    									<c:forEach items="${sistemas}" var="tipo">
				            							  <option 
				            							       <c:if test="${tipo.key eq validaRegistro.idSistema}">
				            							         selected="selected"
				            							       </c:if>
				            							  value="${tipo.key}">${tipo.value} </option>
				        								</c:forEach>
													</form:select>
    											</div>
  											</div>
  										</div>
									</div>
									<div class="col-md-4">
    									<div class="form-horizontal">
  											<div class="form-group">
    											<label for="inputtext3" class="col-sm-5 control-label">Proceso:</label>
    											<div class="col-sm-7">
      												<form:select path="procesos" id="procesos" cssClass="form-control" required="true" disabled="true" >
				    									<c:forEach items="${procesos}" var="tipo">
				            							  <option 
				            							       <c:if test="${tipo.key eq validaRegistro.idProceso}">
				            							         selected="selected"
				            							       </c:if>
				            							  value="${tipo.key}">${tipo.value} </option>
				        								</c:forEach>
													</form:select>
    											</div>
  											</div>
  											<div class="form-group">
    											<label for="inputtext3" class="col-sm-5 control-label">Sub Proceso:</label>
 												<div class="col-sm-7">
      												<form:select path="subProcesos" id="subProcesos" cssClass="form-control" required="true" disabled="true" >
				    									<c:forEach items="${subProcesos}" var="tipo">
				            							  <option 
				            							       <c:if test="${tipo.key eq validaRegistro.idSubProceso}">
				            							         selected="selected"
				            							       </c:if>
				            							  value="${tipo.key}">${tipo.value} </option>
				        								</c:forEach>
													</form:select>
    											</div>
  											</div>
  										</div>
									</div>
								</div><!-- fin row-->
							</form><!-- fin form-->
							
							
    						<form:form modelAttribute="validaRegistro"  style="margin-top: -49px;" >
    							<div class="row">
    								<div class="col-md-2" style="text-align: right;" >
    									<div class="form-horizontal">
  											<div class="form-group">
    											<div class="col-sm-10">
    											<label for="secuencia" >Secuencia:</label>
      												<form:input path="numSecuencia" id="txtSecuencia" disabled="true" class="form-control" style="" />
    											</div>
  											</div>
  										</div>
									</div>
									<div class="col-md-9" style="margin-left: -10px;" >
    									<div class="form-horizontal">
  											<div class="form-group">
    											<div class="col-sm-16">
    											<label for="txtDescripcion" >Descripción:</label>
      												<form:textarea path="txtDescripcion" id="txtDescripcion" disabled="true" class="form-control" style="resize: none;" />
    											</div>
  											</div>
  										</div>
									</div>
								</div><!-- fin row-->
    						</form:form><!-- fin form-->


  						</div>
					</div>
					
				<div class="panel panel-default">
  						<div class="panel-body" style="padding-left: 40px;" >

						<form:form modelAttribute="validaRegistroDet" >
    							<div class="row">
    								<div class="col-md-2">
    									<div class="form-horizontal">
  											<div class="form-group">
    											<div class="col-sm-8">
    												<label >Numero:</label>
      												<form:input path="numClave" id="txtNumClave" disabled="true" class="form-control" style="" />
    											</div>
  											</div>
  										</div>
									</div>
									<div class="col-md-5">
    									<div class="form-horizontal">
  											<div class="form-group">
    											<div class="col-sm-12">
	    											<label >Tipo Clave:</label>   											
    														<select class="form-control"  id="tipoClave"   >
				            							  <option   value="TXT">Texto</option>
				            							   <option   value="FNC">Funcion </option>
													</select>
    											
    											
    											
    											
    											</div>
  											</div>
  										</div>
									</div>
									<div class="col-md-5">
    									<div class="form-horizontal">
  						
  											<div class="form-group">
    											<div class="col-sm-12">
	    											<label>Valor:</label>
      												<form:textarea path="txtValorClave" id="txtValorClave" disabled="false" class="form-control" style="resize: none;" />
    											</div>
  											</div>
  										</div>
									</div>
								</div><!-- fin row-->
								
								<div class="row">
    								<div class="col-md-2">
    									<div class="form-horizontal">
  											<div class="form-group">
    											<div class="col-sm-8">
    												<label for="lblnumLogitudParam">Longitud:</label>
      												<form:input path="numLogitudParam" id="numLogitudParam" disabled="false" class="form-control" style="" />
    											</div>
  											</div>
  										</div>
									</div>
									<div class="col-md-5">
    									<div class="form-horizontal">
  											<div class="form-group">
    											<div class="col-sm-12">
	    											<label for="txtFormatoParam">Formato:</label>
      												<form:textarea path="txtFormatoParam" id="txtFormatoParam" disabled="false" class="form-control" style="resize: none;" />
    											</div>
  											</div>
  										</div>
									</div>
									<div class="col-md-5">
    									<div class="form-horizontal">
  						
  											<div class="form-group">
    											<div class="col-sm-12">
	    										</div>
  											</div>
  										</div>
									</div>
								</div><!-- fin row-->
								<div class="row">
								  <div class="pull-right" style="margin-right:10px;">
								   <a class="btn btn-primary" href="${pageContext.request.contextPath}/validaRegistro" >Volver</a>
								  	<button id="btn_limpiarDet" type="button" class="btn btn-primary">Limpiar</button>
								    <button id="btn_guardarDet" type="button" class="btn btn-primary">Guardar</button>
								  </div>
								</div>
    						</form:form><!-- fin form-->

  						</div>
					</div>


	
			<div>
	    				<table id="validaRegistroDetList" class="table table-striped table-bordered" cellspacing="0" width="100%">
					        <thead>
					            <tr>
					                <th style="width: 50px;" >Secuencia</th>
					                <th style="width: 80px;">Tipo Clave</th>
					                <th style="width: 250px;">Valor</th>
					                <th style="width: 100px;">Longitud</th>
					                <th style="width: 250px;">Formato</th>
					                <th>Opci&oacute;n</th>
					            </tr>
					        </thead>
					        <tfoot>
					            <tr>
					                <th style="width: 50px;" >Secuencia</th>
					                <th style="width: 80px;">Tipo Clave</th>
					                <th style="width: 250px;">Valor</th>
					                <th style="width: 100px;">Longitud</th>
					                <th style="width: 250px;">Formato</th>
					                <th>Opci&oacute;n</th>
					            </tr>
					        </tfoot>
					        <tbody>
					        </tbody>
					    </table>
    				</div>




    			</div>
  			</div>
		</div>

		<script type="text/javascript" src="${pageContext.request.contextPath}/vendors/datatables/js/jquery.dataTables.js">
		</script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/vendors/js/moment.js"></script>
		<script src="${pageContext.request.contextPath}/resources/js/validaRegistroDetalle.js"></script>
 	</body>

 </html>