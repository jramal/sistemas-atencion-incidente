package pe.upc.atencionincidente.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import pe.upc.atencionincidente.model.KbIncidente;
import pe.upc.atencionincidente.model.KbSolucion;
import pe.upc.atencionincidente.model.KbValidaRegistro;
import pe.upc.atencionincidente.service.SolicitudService;
import pe.upc.atencionincidente.service.ValidaRegistroService;

@Controller
@RequestMapping(value="/validaRegistro")
public class ValidaRegistroController {
	
	@Autowired
	ValidaRegistroService 	validaRegistroService;
	
	@Autowired
	SolicitudService solicitudService;
	

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView init() {
		ModelAndView mav = new ModelAndView("validaRegistro");
		mav.addObject("tiposSolicitud", this.getListTipoSolicitud());
		mav.addObject("sistemas", this.getListSistemas() );
		mav.addObject("validaRegistro", new KbValidaRegistro() );
		return mav;
	}
	
	@RequestMapping(value="buscar", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> buscarKbValidaRegistro(@ModelAttribute KbValidaRegistro form){
		System.out.println("buscarKbValidaRegistro");
		Map<String,Object> data = new HashMap<String,Object>();
	
		form.setTipoConsulta("1");
	
		List<KbValidaRegistro> validaRegistros = validaRegistroService.buscarKbValidaRegistro(form);
		
		data.put("data", validaRegistros);
		
//		if(form.getIdSubproceso() != null && !form.getIdSubproceso().equals("") && incidentes.size() > 0 ){
//			int secuencia = incidenteService.obtenerSecuencia();
//			data.put("secuencia", secuencia);
//		}
		return data;
	}
		
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> registrarKbValidaRegistro(@ModelAttribute KbValidaRegistro form){
		System.out.println("registrarKbValidaRegistro");
		Map<String,Object> data = new HashMap<String,Object>();
	
		form.setUsuarioAdicion("ADMIN");
		String idNumSecuencia = validaRegistroService.registrarKbValidaRegistro(form);

		data.put("idNumSecuencia", idNumSecuencia);

		return data;
	}
	
	@RequestMapping(value="/validaRegistro/cargar", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> cargarKbValidaRegistro(@ModelAttribute KbValidaRegistro form){
		System.out.println("cargarKbSolucion");
		
		form.setTipoConsulta("2");
		List<KbValidaRegistro> validaRegistros = validaRegistroService.buscarKbValidaRegistro(form);
		KbValidaRegistro KbValidaRegistro = validaRegistros.get(0);

		Map<String,Object> data = new HashMap<String,Object>();		
		data.put("validaRegistro",  KbValidaRegistro);

		return data;
	}
	
	@RequestMapping(value="/validaRegistro/eliminar", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> eliminarKbValidaRegistro(@ModelAttribute KbValidaRegistro form){
		System.out.println("eliminarKbValidaRegistro");
		
		validaRegistroService.eliminarKbValidaRegistro(form);

		Map<String,Object> data = new HashMap<String,Object>();		
		data.put("mensaje",  "ok");

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
	
	public Map<String, String> populateCombo(List<Map<String, Object>> rows) {
		Map<String, String> data = new LinkedHashMap<String, String>();
		for (Map<String, Object> row : rows) {
			data.put(row.get("id").toString(),row.get("description").toString());
		}
		return data;
	}
	/*
	private Map<String, String> getListKeyValues() {
		List<Map<String, Object>> lstSis = validaRegistroService.getListKeyValues();
		return populateCombo(lstSis);
	}
	
	private Map<String, String> getListKbValidaRegistro(String numSecuencia) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		KbValidaRegistro form = new KbValidaRegistro();
	    form.setNumSecuencia(numSecuencia);
		form.setTipoConsulta("3");
		List<KbValidaRegistro> validaRegistros = validaRegistroService.buscarKbValidaRegistro(form);
		
		validaRegistros.forEach(item->{
			Map<String,Object> row = new HashMap<String, Object>();
			row.put("id", item.getKeyValue());
			row.put("description", item.getKeyValue());
			list.add(row);
		});
		
		return populateCombo(list); 
	}
*/
	
}