package pe.upc.atencionincidente.web;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import pe.upc.atencionincidente.model.KbIncidente;
import pe.upc.atencionincidente.model.KbIncidenteKeyValues;
import pe.upc.atencionincidente.model.KbSolucion;
import pe.upc.atencionincidente.model.KbSolucionCheck;
import pe.upc.atencionincidente.model.KbSolucionSetup;
import pe.upc.atencionincidente.model.KbSolucionSetupCheck;
import pe.upc.atencionincidente.service.IncidenteService;
import pe.upc.atencionincidente.service.SolicitudService;

@Controller
@RequestMapping(value="/incidente")
public class IncidenteController {
	
	@Autowired
	SolicitudService solicitudService;
	
	@Autowired
	IncidenteService incidenteService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView init() {
		ModelAndView mav = new ModelAndView("incidente");
		mav.addObject("tiposSolicitud", this.getListTipoSolicitud() );
		mav.addObject("sistemas", this.getListSistemas() );
		mav.addObject("incidente", new KbIncidente() );
		return mav;
	}
	
	@RequestMapping(value="buscar", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> buscarKbIncidente(@ModelAttribute KbIncidente form){
		System.out.println("buscarKbIncidente");
		Map<String,Object> data = new HashMap<String,Object>();
	
		form.setTipoConsulta("1");
		List<KbIncidente> incidentes = incidenteService.buscarKbIncidente(form);
		
		data.put("data", incidentes);
		
//		if(form.getIdSubproceso() != null && !form.getIdSubproceso().equals("") && incidentes.size() > 0 ){
//			int secuencia = incidenteService.obtenerSecuencia();
//			data.put("secuencia", secuencia);
//		}
		return data;
	}
		
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> registrarKbIncidente(@ModelAttribute KbIncidente form){
		System.out.println("registrarKbIncidente");
		Map<String,Object> data = new HashMap<String,Object>();
	
		form.setUsuarioAdicion("ADMIN");
		String idIncidenteBase = incidenteService.registrarKbIncidente(form);

		data.put("idIncidenteBase", idIncidenteBase);

		return data;
	}
	
	@RequestMapping(value="/cargar", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> cargarKbIncidente(@ModelAttribute KbIncidente form){
		System.out.println("cargarKbIncidente");
		
		form.setTipoConsulta("7");
		List<KbIncidente> incidentes = incidenteService.buscarKbIncidente(form);
		KbIncidente kbIncidente = incidentes.get(0);

		Map<String,Object> data = new HashMap<String,Object>();		
		data.put("incidente",  kbIncidente);

		return data;
	}
	
	@RequestMapping(value="/{idIncidenteBase}/agregarValorClave", method=RequestMethod.GET)
	public ModelAndView cargarValorClave(@PathVariable(value="idIncidenteBase") String idIncidenteBase){
		System.out.println("cargarValorClave");
		ModelAndView mav = new ModelAndView("incidente_valor_clave");
	
		KbIncidente form = new KbIncidente();
		form.setIdIncidenteBase(idIncidenteBase);
		form.setTipoConsulta("7");
		List<KbIncidente> incidentes = incidenteService.buscarKbIncidente(form);
		
		if(incidentes.size() > 0){
			Map<String, String> listKeyValues = this.getListKeyValues();
			Map<String, String> listKbIncidenteValorClave = this.getListKbIncidenteValorClave(idIncidenteBase);
			for (Map.Entry<String, String> entry : listKbIncidenteValorClave.entrySet()){
				listKeyValues.remove(entry.getKey());
			}
			
			KbIncidente kbIncidente = incidentes.get(0);
			mav.addObject("tiposSolicitud", this.getListTipoSolicitud() );
			mav.addObject("sistemas", this.getListSistemas() );
			mav.addObject("procesos", this.listProcesos(kbIncidente.getIdSistema()) );
			mav.addObject("subProcesos", this.listSubProcesos(kbIncidente.getIdSistema(), kbIncidente.getIdProceso()) );
			mav.addObject("incidente",  kbIncidente);
			mav.addObject("valoresClave",  listKeyValues);
			mav.addObject("valoresClaveIncidente",  listKbIncidenteValorClave);
		}

		return mav;
	}
	
	@RequestMapping(value="/agregarValorClave", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> registrarKbIncidenteKeyValues(@ModelAttribute KbIncidenteKeyValues form){
		System.out.println("registrarKbIncidenteKeyValues");
		Map<String,Object> data = new HashMap<String,Object>();
	
		incidenteService.eliminarKbIncidenteKeyValues(form);
		
		form.setUsuarioAdicion("ADMIN");
		if(form.getValoresClaveIncidente() != null && !form.getValoresClaveIncidente().equals(""))
			for (String valor : form.getValoresClaveIncidente().split(",")) {
				form.setIdKeyValue(valor);
				String idIncidenteBase = incidenteService.registrarKbIncidenteKeyValues(form);
				System.out.println(idIncidenteBase);
			}

		//data.put("idIncidenteBase", idIncidenteBase);

		return data;
	}
	
	@RequestMapping(value="/solucion/buscar", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> buscarKbSolucion(@ModelAttribute KbSolucion form){
		System.out.println("buscarKbSolucion");
		
		form.setTipoConsulta("2");
		List<KbSolucion> soluciones = incidenteService.buscarKbSolucion(form);

		Map<String,Object> data = new HashMap<String,Object>();
		data.put("soluciones",  soluciones);

		return data;
	}
	
	@RequestMapping(value="/solucion", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> registrarKbSolucion(@ModelAttribute KbSolucion form){
		System.out.println("registrarKbSolucion");
		Map<String,Object> data = new HashMap<String,Object>();
	
		form.setUsuarioAdicion("ADMIN");
		String idSolucion = incidenteService.registrarKbSolucion(form);

		data.put("idSolucion", idSolucion);

		return data;
	}
	
	@RequestMapping(value="/solucion/cargar", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> cargarKbSolucion(@ModelAttribute KbSolucion form){
		System.out.println("cargarKbSolucion");
		
		form.setTipoConsulta("9");
		List<KbSolucion> soluciones = incidenteService.buscarKbSolucion(form);
		KbSolucion kbSolucion = soluciones.get(0);

		Map<String,Object> data = new HashMap<String,Object>();		
		data.put("solucion",  kbSolucion);

		return data;
	}
	
	@RequestMapping(value="/solucion/eliminar", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> eliminarKbSolucion(@ModelAttribute KbSolucion form){
		System.out.println("eliminarKbSolucion");
		
		incidenteService.eliminarKbSolucion(form);

		Map<String,Object> data = new HashMap<String,Object>();		
		data.put("mensaje",  "ok");

		return data;
	}
	
	@RequestMapping(value="/solucionSetup/buscar", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> buscarKbSolucionSeup(@ModelAttribute KbSolucionSetup form){
		System.out.println("buscarKbSolucionSeup");
		
		form.setTipoConsulta("3");
		List<KbSolucionSetup> solucionesSetup = incidenteService.buscarKbSolucionSetup(form);

		Map<String,Object> data = new HashMap<String,Object>();
		data.put("solucionesSetup",  solucionesSetup);

		return data;
	}
	
	@RequestMapping(value="/solucionSetup", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> registrarKbSolucionSetup(@ModelAttribute KbSolucionSetup form){
		System.out.println("registrarKbSolucionSetup");
		Map<String,Object> data = new HashMap<String,Object>();
	
		form.setUsuarioAdicion("ADMIN");
		if(form.getTxtRuta() != null && !form.getTxtRuta().equals("")){
			Path p = Paths.get(form.getTxtRuta());
			String file = p.getFileName().toString();
			form.setTxtAnexo(file);
		}
		if(form.getTxtAnexo() == null){
			form.setTxtAnexo("");
		}
		String nunSecuencia = incidenteService.registrarKbSolucionSetup(form);

		data.put("numSecuencia", nunSecuencia);

		return data;
	}
	
	@RequestMapping(value="/solucionSetup/cargar", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> cargarKbSolucionSetup(@ModelAttribute KbSolucionSetup form){
		System.out.println("cargarKbSolucionSetup");
		
		form.setTipoConsulta("10");
		List<KbSolucionSetup> soluciones = incidenteService.buscarKbSolucionSetup(form);
		KbSolucionSetup kbSolucionSetup = soluciones.get(0);

		Map<String,Object> data = new HashMap<String,Object>();		
		data.put("solucionSetup",  kbSolucionSetup);

		return data;
	}
	
	@RequestMapping(value="/solucionSetup/eliminar", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> eliminarKbSolucionSetup(@ModelAttribute KbSolucionSetup form){
		System.out.println("eliminarKbSolucionSetup");
		
		incidenteService.eliminarKbSolucionSetup(form);

		Map<String,Object> data = new HashMap<String,Object>();		
		data.put("mensaje",  "ok");

		return data;
	}
	
	@RequestMapping(value="/solucionCheck/buscar", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> buscarKbSolucionCheck(@ModelAttribute KbSolucionCheck form){
		System.out.println("buscarKbSolucionCheck");
		
		form.setTipoConsulta("4");
		List<KbSolucionCheck> solucionesCheck = incidenteService.buscarKbSolucionCheck(form);
		
		form.setTipoConsulta("5");
		List<KbSolucionCheck> solucionesSetupCheck = incidenteService.buscarKbSolucionCheck(form);
		
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("solucionesCheck",  solucionesCheck);
		data.put("solucionesSetupCheck",  solucionesSetupCheck);
		return data;
	}
	
	@RequestMapping(value="/solucionSetupCheck", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> registrarKbSolucionSetupCheck(@ModelAttribute KbSolucionSetupCheck form){
		System.out.println("registrarKbSolucionSetupCheck");
		Map<String,Object> data = new HashMap<String,Object>();
		
		incidenteService.eliminarKbSolucionSetupCheck(form);
		
		form.setUsuarioAdicion("ADMIN");
		if(form.getSolucionesSetupCheck() != null && !form.getSolucionesSetupCheck().equals(""))
			for (String valor : form.getSolucionesSetupCheck().split(",")) {
				form.setIdSolucionCh(valor);
				String idSolucionCh = incidenteService.registrarKbSolucionSetupCheck(form);
				System.out.println(idSolucionCh);
			}

		//data.put("idIncidenteBase", idIncidenteBase);

		return data;
	}
	
	@RequestMapping(value="/getListProcesos", method=RequestMethod.GET )
    public @ResponseBody Map<String,String> listProcesos(@RequestParam("idSistema") String idSistema){
		List<Map<String, Object>> lstPro = solicitudService.getListProcesos(idSistema);
		return populateCombo(lstPro);
	}
	
	@RequestMapping(value="/getListSubProcesos", method=RequestMethod.GET )
    public @ResponseBody Map<String,String> listSubProcesos(@RequestParam("idSistema") String idSistema,@RequestParam("idProceso") String idProceso){
		List<Map<String, Object>> lstSubPro = solicitudService.getListSubProcesos(idSistema, idProceso);
		return populateCombo(lstSubPro);
	}
	
	private Map<String, String> getListTipoSolicitud() {
		List<Map<String, Object>> lstSol = solicitudService.getListTipoSolicitud();
		return populateCombo(lstSol);
	}
	
	private Map<String, String> getListSistemas() {
		List<Map<String, Object>> lstSis = solicitudService.getSistemas();
		return populateCombo(lstSis);
	}
	
	private Map<String, String> getListKeyValues() {
		List<Map<String, Object>> lstSis = incidenteService.getListKeyValues();
		return populateCombo(lstSis);
	}
	
	private Map<String, String> getListKbIncidenteValorClave(String idIncidenteBase) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		KbIncidente form = new KbIncidente();
		form.setIdIncidenteBase(idIncidenteBase);
		form.setTipoConsulta("8");
		List<KbIncidente> incidentes = incidenteService.buscarKbIncidenteValorClave(form);
		
		incidentes.forEach(item->{
			Map<String,Object> row = new HashMap<String, Object>();
			row.put("id", item.getKeyValue());
			row.put("description", item.getKeyValue());
			list.add(row);
		});
		
		return populateCombo(list);
	}
	
	public Map<String, String> populateCombo(List<Map<String, Object>> rows) {
		Map<String, String> data = new LinkedHashMap<String, String>();
		for (Map<String, Object> row : rows) {
			data.put(row.get("id").toString(),row.get("description").toString());
		}
		return data;
	}
	
}
