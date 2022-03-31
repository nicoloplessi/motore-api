package com.motoreapi.demo.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.motoreapi.demo.Zonali.LegendaZonaliBean;
import com.motoreapi.demo.Zonali.MatchData;
import com.motoreapi.demo.Zonali.MatchData1;
import com.motoreapi.demo.Zonali.ZonaliBilanBean;
import com.motoreapi.demo.Zonali.ZonaliDataBean;
import com.motoreapi.demo.co2.Co2Bean;
import com.motoreapi.demo.fabbPicco.FabbPiccDayMeasure;
import com.motoreapi.demo.fabbPicco.FabbPiccMeasure;
import com.motoreapi.demo.fabbPicco.FabbPiccType;
import com.motoreapi.demo.fabbPicco.FabbisognoPicco;
import com.motoreapi.demo.fabbPicco.RawFabbPiccBean;
import com.motoreapi.demo.fabbisogno.DayMeasure;
import com.motoreapi.demo.fabbisogno.Fabbisogno;
import com.motoreapi.demo.fabbisogno.Measure;
import com.motoreapi.demo.fabbisogno.Type;
import com.motoreapi.demo.fer.FerBean;
import com.motoreapi.demo.fontiprimarie.DataFpRaw;
import com.motoreapi.demo.fontiprimarie.FontePrimaria;
import com.motoreapi.demo.fontiprimarie.FpRow;
import com.motoreapi.demo.fontiprimarie.ListFpRaw;
import com.motoreapi.demo.home.CurrentValue;
import com.motoreapi.demo.home.HomeBean;
import com.motoreapi.demo.home.HomeSection;
import com.motoreapi.demo.home.RawClass;
import com.motoreapi.demo.menu.CurrentValueMenu;
import com.motoreapi.demo.menu.Graph;
import com.motoreapi.demo.menu.MenuForCat;
import com.motoreapi.demo.menu.RawMenu;
import com.motoreapi.demo.model.APIEntity;
import com.motoreapi.demo.news.Article;
import com.motoreapi.demo.news.Images;
import com.motoreapi.demo.news.NewsBean;
import com.motoreapi.demo.news.NewsObj;

/*
 * Tutte le variabili che iniziano per "O" contengono i risultati delle query.
 * le variabili che iniziano per sql contengono le query, in particolare va letta solo la prima cifra dopo "sql",
 * ad esempio di "sql33" va letto "sql3" e significa che contiene la terza query del metodo.
 * 
 * Questo programma non utilizza Hibernate. le query vengono eseguite attraverso due oggetti jdbc che trovate istanziati all'inizio di questa classe.
 * per modificare i parametri di jdbc vanno modificati gli Application Context relativi,
 * che si devono trovare nel path indicato nell'istanza dell'oggetto ApplicationContext.
 * 
 * le varie api si trovano all'interno dello switch nel metodo getReultSet, i parametri relativi si trovano nella mappa parametri
 * e possono essere usati attraverso il metodo parametri.get("nomeParametro").
 * 
 * alcuni oggetti Json sono costruiti a mano attraverso le replace per esigenze di progetto, quindi sconsiglio di toccare le
 * replace se non strettamente necessario.
 */

@Service

public class APIService {

	ApplicationContext context = new FileSystemXmlApplicationContext("Context.xml");
	ApplicationContext contextBilan = new FileSystemXmlApplicationContext("bilanConfig.xml");
	JdbcTemplate jdbcTemplate = (JdbcTemplate) context.getBean("jdbcTemplate");

	JdbcTemplate jdbcTemplateBilan = (JdbcTemplate) contextBilan.getBean("jdbcTemplate");

	private static final Logger log = LoggerFactory.getLogger(APIService.class);
	DefaultPrettyPrinter p = new DefaultPrettyPrinter();
	DefaultPrettyPrinter.Indenter i = new DefaultIndenter("  ", " ");
	String fromDate;
	String toDate;
	ObjectMapper objectMapper = new ObjectMapper();

	public APIEntity getApiFromName(String name) {

		// APIEntity api = ApiRepo.findByApi(name);
		APIEntity api = new APIEntity();
		String sqlApi = "Select * from API where API='" + name + "'";
		List<Map<String, Object>> apis = jdbcTemplate.queryForList(sqlApi);
		log.info("sql: " + sqlApi + " executed, result: " + apis.toString());
		// api.setId(Long.parseLong((String)apis.get(0).get("ID")));
		api.setApi((String) apis.get(0).get("API"));
		api.setQuery((String) apis.get(0).get("QUERY"));
		api.setFilter((String) apis.get(0).get("FILTER"));
		api.setParameter((String) apis.get(0).get("PARAMETER"));
		api.setMock((String) apis.get(0).get("MOCK"));
		return api;
	}

	public APIEntity getApiFromNameAndParameter(String name, String param) {

		APIEntity api = new APIEntity();
		String sqlApi = "Select * from API where API='" + name + "' and PARAMETER ='" + param + "'";
		List<Map<String, Object>> apis = jdbcTemplate.queryForList(sqlApi);
		// api.setId(Long.parseLong((String)apis.get(0).get("ID")));
		log.info(apis.toString());
		api.setApi((String) apis.get(0).get("API"));

		api.setQuery((String) apis.get(0).get("QUERY"));
		api.setFilter((String) apis.get(0).get("FILTER"));
		api.setParameter((String) apis.get(0).get("PARAMETER"));
		api.setMock((String) apis.get(0).get("MOCK"));
		return api;
	}

	public String elaboraJson(String json, String key) throws JsonParseException, JsonMappingException, IOException {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDateTime now = LocalDateTime.now();

		JSONObject jo = new JSONObject(json);
		String query = (String) jo.get(key);
		query.replace("to_date(:fromDate", "to_date(" + dtf.format(now).toString());
		return query;
	}

	public String eliminaQuery(String json) {

		DocumentContext dc = JsonPath.parse(json).delete("$..QUERY");

		return dc.jsonString();
	}

	public String personalizzaMenu(String json) {

		DocumentContext dc = JsonPath.parse(json).delete("$..CATEGORY_TYPE_ID");

		return dc.jsonString();
	}

	// public String getMockfromName(String name) {
	// APIEntity apiMock = ApiRepo.findByApi(name);

	// return apiMock.getApi();
	// }

	public String post() {

		return null;
	}

	@SuppressWarnings("deprecation")
	public String dataConvert(String json) {
		JSONObject j = new JSONObject(json);
		Long data = (Long) j.get("DATA_QUARTO");
		Date date = new Date(data);
		j.remove("DATA_QUARTO");
		j.put("DATA_QUARTO", date.toGMTString());

		return j.toString();
	}

	public String getResultSet(String name, Map<String, String> parametri, String language)
			throws DataAccessException, IOException {

		String result;
		APIEntity api;

		if (parametri.containsKey("id")) {
			log.info("con parametri");
			api = getApiFromNameAndParameter(name, parametri.get("id"));
		} else {
			log.info("senza parametri");
			api = getApiFromName(name);
		}
		if (api == null) {
			log.error("API non trovata");
			return "Errore, api non trovata";
		}

		String output = null;
		ArrayList<HomeSection> homeSection = new ArrayList<>();

		String sql1 = api.getQuery();
		if (!language.equals("it") && !language.equals("en")) {
			language = "it";
		}
		sql1 = sql1.replace("lang_code = 'it'", "lang_code = \'" + language + "\'");
		p.indentArraysWith(i);
		p.indentObjectsWith(i);
		objectMapper.setDefaultPrettyPrinter(p);
		switch (name) {
		// --------------------------------------------------------------------------------------------------------------------------
		case "getHome":// conclusa con output identico alla precendente api
			for (int i = 0; i <= 3; i++) {
				log.info("execution" + i);
				String sql = sql1;
				String cat = "q.CATEGORY_TYPE_ID =" + i;
				sql = sql.replace("q.CATEGORY_TYPE_ID = 0", cat);
				// log.info(sql);
				List<Map<String, Object>> o = jdbcTemplate.queryForList(sql);

				result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
				// log.info(result);
				result = result.replace("[", "");
				String sql2 = elaboraJson(result, "QUERY");
				// log.info(sql2);
				List<Map<String, Object>> o1 = jdbcTemplateBilan.queryForList(sql2);
				o.addAll(o1);
				output = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
				output = eliminaQuery(output);
				output = output.replace("},{", ",").replace("[", "").replace("]", "");

				RawClass rawHome = objectMapper.readValue(output, RawClass.class);
				HomeSection home = new HomeSection();
				CurrentValue current = new CurrentValue();
				home.setTitleLabel(rawHome.getiNTEGRATION_ID());
				home.setcATEGORY_IMG(rawHome.getcATEGORY_IMG());
				home.setcATEGORY_TYPE_ID(rawHome.getcATEGORY_TYPE_ID());
				home.settITLE_LABEL_COLOR(rawHome.gettITLE_LABEL_COLOR());
				home.setsHOULD_SHOW_SECTION(rawHome.getsHOULD_SHOW_SECTION());

				if (rawHome.getpRODUZIONE() > rawHome.getSaldo()) {
					current.setpRODUZIONE(rawHome.getpRODUZIONE());
				} else {
					current.setpRODUZIONE(rawHome.getSaldo());
				}
				current.setC_V_MEASURE_DESCRIPTION(rawHome.getC_V_MEASURE_DESCRIPTION());
				current.setMeasureUnit(rawHome.getC_V_MEASURE_UNIT());
				current.setTextColor(rawHome.getC_V_TEXT_COLOR());
				home.setCurrentValue(current);
				homeSection.add(home);
			}
			HomeBean response = new HomeBean();
			LocalDate todaysDate = LocalDate.now();
			int time = LocalDateTime.now().getHour();
			String aggzer;
			if (time < 10) {
				aggzer = "0";
			} else {
				aggzer = "";
			}
			response.setLastUpdated(todaysDate + " " + aggzer + time + ":00:00");
			response.setHomeSections(homeSection);

			return objectMapper.writeValueAsString(response);
		// --------------------------------------------------------------------------------------------------------------------
		case "getFabbisognoPicco":

			// sql1.replace("***", par.getValue());
			List<Map<String, Object>> o1 = jdbcTemplate.queryForList(sql1);
			String date = parametri.get("fromDate");
			log.info("dimensione array:" + o1.size());

			// o1.get(0).put("fromDat", date);

			output = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o1);
			// log.info(output);
			output = output.substring(1, output.length() - 1);
			// long millis=System.currentTimeMillis();
			// String data="'"+ new Date(millis).toString()+"'";
			output = output.replace(":fromDate", "\'" + date + "\'");

			String sql2 = elaboraJson(output, "QUERY");

			// log.info("query:"+sql2);

			List<Map<String, Object>> o2 = jdbcTemplateBilan.queryForList(sql2);
			log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o2));
			String labelpicco = "";
			for (int y = 0; y < o2.size(); y++) {
				Calendar calendar = Calendar.getInstance();

				o2.get(y).replace("DATA_QUARTO", date + " " + o2.get(y).get("LBL_DATA_QUARTO") + ":00");
				labelpicco = date + " " + o2.get(y).get("LBL_DATA_QUARTO") + ":00";
				// o2.get(y).put("labelPiccoOra", date+"
				// "+o2.get(y).get("LBL_DATA_QUARTO")+":00");
				o2.get(y).remove("THERMOMIXED");
				o2.get(y).replace("LBL_DATA_QUARTO", o2.get(y).get("LBL_DATA_QUARTO") + ":00");

			}

			output = eliminaQuery(output);
			output = output + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o2);
			output = output.replace("}[   {", ",").replace("]", "");

			RawFabbPiccBean rawFPBean = objectMapper.readValue(output, RawFabbPiccBean.class);
			ArrayList<FabbPiccType> types = new ArrayList<FabbPiccType>();

			// creo i type
			FabbPiccType typeAutoProd = new FabbPiccType();
			typeAutoProd.setTypeID(rawFPBean.gettYPES_ID_AUTOPRODUZIONE());
			typeAutoProd.setHexColor(rawFPBean.gettYPES_HEXCOLOR_AUTOPRODUZIONE());
			typeAutoProd.setName(rawFPBean.gettYPES_NAME_AUTOPRODUZIONE());
			types.add(typeAutoProd);
			FabbPiccType typeEstero = new FabbPiccType();
			typeEstero.setName(rawFPBean.gettYPES_NAME_ESTERO());
			typeEstero.setTypeID(rawFPBean.gettYPES_ID_ESTERO());
			typeEstero.setHexColor(rawFPBean.gettYPES_HEXCOLOR_ESTERO());
			types.add(typeEstero);
			FabbPiccType typeGeo = new FabbPiccType();
			typeGeo.setName(rawFPBean.gettYPES_NAME_GEO());
			typeGeo.setTypeID(rawFPBean.gettYPES_ID_GEO());
			typeGeo.setHexColor(rawFPBean.gettYPES_HEXCOLOR_GEO());
			types.add(typeGeo);
			FabbPiccType typeHydro = new FabbPiccType();
			typeHydro.setName(rawFPBean.gettYPES_NAME_HYDRO());
			typeHydro.setTypeID(rawFPBean.gettYPES_ID_HYDRO());
			typeHydro.setHexColor(rawFPBean.gettYPES_HEXCOLOR_HYDRO());
			types.add(typeHydro);
			FabbPiccType typePMPG = new FabbPiccType();
			typePMPG.setName(rawFPBean.gettYPES_NAME_PMPG());
			typePMPG.setTypeID(rawFPBean.gettYPES_ID_PMPG());
			typePMPG.setHexColor(rawFPBean.gettYPES_HEXCOLOR_PMPG());
			types.add(typePMPG);
			FabbPiccType typeProdTerm = new FabbPiccType();
			typeProdTerm.setName(rawFPBean.gettYPES_NAME_PRODTERM());
			typeProdTerm.setTypeID(rawFPBean.gettYPES_ID_PRODTERM());
			typeProdTerm.setHexColor(rawFPBean.gettYPES_HEXCOLOR_PRODTERM());
			types.add(typeProdTerm);
			FabbPiccType typePv = new FabbPiccType();
			typePv.setName(rawFPBean.gettYPES_NAME_PV());
			typePv.setTypeID(rawFPBean.gettYPES_ID_PV());
			typePv.setHexColor(rawFPBean.gettYPES_HEXCOLOR_PV());
			types.add(typePv);
			FabbPiccType typeWind = new FabbPiccType();
			typeWind.setName(rawFPBean.gettYPES_NAME_WIND());
			typeWind.setTypeID(rawFPBean.gettYPES_ID_WIND());
			typeWind.setHexColor(rawFPBean.gettYPES_HEXCOLOR_WIND());
			types.add(typeWind);

			// creo i measure
			ArrayList<FabbPiccMeasure> measure = new ArrayList<FabbPiccMeasure>();
			FabbPiccMeasure fpmAutoProd = new FabbPiccMeasure();
			fpmAutoProd.setTypeID(typeAutoProd.getTypeID());
			fpmAutoProd.setValue(rawFPBean.getaUTOPRODUZIONE());
			measure.add(fpmAutoProd);
			FabbPiccMeasure fpmEstero = new FabbPiccMeasure();
			fpmEstero.setTypeID(typeEstero.getTypeID());
			fpmEstero.setValue(rawFPBean.geteSTERO());
			measure.add(fpmEstero);
			FabbPiccMeasure fpmGeo = new FabbPiccMeasure();
			fpmGeo.setTypeID(typeGeo.getTypeID());
			fpmGeo.setValue(rawFPBean.getgEO());
			measure.add(fpmGeo);
			FabbPiccMeasure fpmHydro = new FabbPiccMeasure();
			fpmHydro.setTypeID(typeHydro.getTypeID());
			fpmHydro.setValue(rawFPBean.gethYDRO());
			measure.add(fpmHydro);
			FabbPiccMeasure fpmPMPG = new FabbPiccMeasure();
			fpmPMPG.setTypeID(typePMPG.getTypeID());
			fpmPMPG.setValue(rawFPBean.getpMPG());
			measure.add(fpmPMPG);
			FabbPiccMeasure fpmProdTerm = new FabbPiccMeasure();
			fpmProdTerm.setTypeID(typeProdTerm.getTypeID());
			fpmProdTerm.setValue(rawFPBean.getpRODTERM());
			measure.add(fpmProdTerm);
			FabbPiccMeasure fpmPv = new FabbPiccMeasure();
			fpmPv.setTypeID(typePv.getTypeID());
			fpmPv.setValue(rawFPBean.getpV());
			measure.add(fpmPv);
			FabbPiccMeasure fpmWind = new FabbPiccMeasure();
			fpmWind.setTypeID(typeWind.getTypeID());
			fpmWind.setValue(rawFPBean.getwIND());
			measure.add(fpmWind);

			FabbPiccDayMeasure fpdm = new FabbPiccDayMeasure();
			fpdm.setMeasures(measure);
			fpdm.setLabelPiccoOra(rawFPBean.getdATA_QUARTO());
			fpdm.setReferenceDate(rawFPBean.getdATA_QUARTO());
			fpdm.setTimeLabel(rawFPBean.getlBL_DATA_QUARTO());

			ArrayList<FabbPiccDayMeasure> dayMeasures = new ArrayList<FabbPiccDayMeasure>();

			dayMeasures.add(fpdm);
			FabbisognoPicco fp = new FabbisognoPicco();
			if (fpdm.getLabelPiccoOra() == null && fpdm.getReferenceDate() == null) {
				log.info("entro nell'if -niente dati");
				fp.setDayMeasures(new ArrayList());
			} else {
				log.info("entro nell'else - ci sono i dati");
				fp.setDayMeasures(dayMeasures);

			}
			fp.setTypes(types);
			fp.setFromDate(date);
			fp.setMeasureDescription(rawFPBean.getmEASURE_DESCRIPTION());
			fp.setMeasureUnit(rawFPBean.getmEASURE_UNIT());
			fp.setName(rawFPBean.getnAME());

			String test = objectMapper.writeValueAsString(fp);

			log.info("getFabbisognoPicco executed");
			return test;

		// ------------------------------------------------------------------------------------------------------------------------------
		case "getMenuForCategory":
			String output1 = "";

			String[] campi = api.getFilter().split(",");
			String sqlx;
			ArrayList<Graph> listMenu = new ArrayList<Graph>();
			for (int j = 0; j < campi.length; j++) {
				sqlx = sql1;

				sqlx = sqlx.replace("***", campi[j]);
				// log.info(sqlx);
				List<Map<String, Object>> o3 = jdbcTemplate.queryForList(sqlx);

				result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o3);

				result = result.replace("[", "").replace("]", "");

				sql2 = elaboraJson(result, "QUERY");
				sql2 = sql2.replace(":fromDate", "\'" + LocalDate.now().toString() + "\'").replace(":toDate",
						"\'" + LocalDate.now().toString() + "\'");
				List<Map<String, Object>> o4 = jdbcTemplateBilan.queryForList(sql2);
				o3.addAll(o4);
				result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o3);
				result = eliminaQuery(result);
				result = personalizzaMenu(result);
				output1 = output1 + result;
				result = result.replace("[", "").replace("]", "").replace("PRODUZIONE", "SALDO")
						.replace("PERCENTUALE_FER", "SALDO").replace("TONNELLATE_CO2", "SALDO").replace("},{", ",")
						.replace("NULL", "SALDO").replace("MAX(PICCO)", "SALDO").replace("CONSUNTIVO", "SALDO")
						.replace("PICCO", "SALDO");
				log.info("+++ +++ +++ json da parsare  :" + result);
				RawMenu raw = objectMapper.readValue(result, RawMenu.class);
				Graph graph = new Graph();
				graph.settITLE_LABEL_COLOR(raw.gettITLE_LABEL_COLOR());
				graph.setdATA_TYPE_ID(raw.getdATA_TYPE_ID());
				graph.setgRAPH_ICON_IMAGE(raw.getgRAPH_ICON_IMAGE());
				graph.setgRAPH_TYPE_ID(raw.getgRAPH_TYPE_ID());
				graph.setsHOULD_SHOW_SECTION(raw.getsHOULD_SHOW_SECTION());
				graph.settITLE_LABEL(raw.gettITLE_LABEL());
				CurrentValueMenu value = new CurrentValueMenu();
				value.setC_V_MEASURE_DESCRIPTION(raw.getC_V_MEASURE_DESCRIPTION());
				value.setC_V_MEASURE_UNIT(raw.getC_V_MEASURE_UNIT());

				value.setsALDO(raw.getSALDO());

				value.setTextColor(raw.getC_V_TEXT_COLOR());
				graph.setCurrentValue(value);

				listMenu.add(graph);

				LocalDate.now().toString();

				log.info(sql2);

				System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA questo è o4:  "
						+ objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o4));
				output1 = output1.replace("} [ {", ",").replace("]", "").replace("}[ {", ",").replace("} {", "} ,{");
				// output1=eliminaQuery(output1);

			}

			LocalDate todaysDate1 = LocalDate.now();
			int time1 = LocalDateTime.now().getHour();

			if (time1 < 10) {
				aggzer = "0";
			} else {
				aggzer = "";
			}
			String param = parametri.get("id");
			MenuForCat mfc = new MenuForCat();
			mfc.setGraphs(listMenu);
			mfc.setCategoryTypeID(param);
			mfc.setLastUpdated(todaysDate1 + " " + aggzer + time1 + ":00:00");
			return objectMapper.writeValueAsString(mfc);

		// --------------------------------------------------------------------------------------------------------------------------
		case "getFontiPrimarie":
			sqlx = sql1;
			fromDate = parametri.get("fromDate");
			toDate = parametri.get("toDate");
			// sqlx=sqlx.replace("***",campi[j]);
			// log.info(sqlx);
			Map pm = new HashMap();
			pm.put("fromDate", fromDate);
			pm.put("toDate", toDate);
			List<Map<String, Object>> o5 = jdbcTemplate.queryForList(sqlx);
			result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o5);
			result = result.replace("[", "").replace("]", "");
			sql2 = elaboraJson(result, "QUERY");
			result = eliminaQuery(result);
			sql2 = sql2.replace(":fromDate", "\'" + fromDate + "\'").replace(":toDate", "\'" + toDate + "\'");
			List<Map<String, Object>> o6 = jdbcTemplateBilan.queryForList(sql2);
			for (int jj = 0; jj < o6.size(); jj++) {
				String oldlabel = (String) o6.get(jj).get("DATA_ORA");
				String labelSplit[] = oldlabel.split("\\\\T\\\\");
				String newlabel = labelSplit[1];
				o6.get(jj).replace("DATA_ORA", newlabel);
				o6.get(jj).remove("TYPES_ID_THERMOMIXED");
				o6.get(jj).replace("LBL_DATA_QUARTO", "");
				o6.get(jj).replace("LBL_DATA_ORA", "mxcx");

			}
			String var=result;
			result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(pm) + result
					+ objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o6);
			
			FpRow fpr= objectMapper.readValue(var, FpRow.class);
			
			String arrayMeasure=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o6).replace(": \".", ": \"0.").replace(": \"-.", ": \"-0.");
			
			ListFpRaw lfpr=objectMapper.readValue("{ \"lista\":"+arrayMeasure+"}", ListFpRaw.class);
			
			ArrayList<Type> typesFp=new ArrayList();
			
			Type typeAutoProd1 = new Type();
			typeAutoProd1.setTypeID(fpr.gettYPES_ID_AUTOPRODUZIONE());
			typeAutoProd1.setHexColor(fpr.gettYPES_HEXCOLOR_AUTOPRODUZIONE());
			typeAutoProd1.setName(fpr.gettYPES_NAME_AUTOPRODUZIONE());
			typesFp.add(typeAutoProd1);
			Type typeEstero1 = new Type();
			typeEstero1.setName(fpr.gettYPES_NAME_ESTERO());
			typeEstero1.setTypeID(fpr.gettYPES_ID_ESTERO());
			typeEstero1.setHexColor(fpr.gettYPES_HEXCOLOR_ESTERO());
			typesFp.add(typeEstero1);
			Type typeGeo1 = new Type();
			typeGeo1.setName(fpr.gettYPES_NAME_GEOTERMICO());
			typeGeo1.setTypeID(fpr.gettYPES_ID_GEOTERMICO());
			typeGeo1.setHexColor(fpr.gettYPES_HEXCOLOR_GEOTERMICO());
			typesFp.add(typeGeo1);
			Type typeHydro1 = new Type();
			typeHydro1.setName(fpr.gettYPES_NAME_IDRICO());
			typeHydro1.setTypeID(fpr.gettYPES_ID_IDRICO());
			typeHydro1.setHexColor(fpr.gettYPES_HEXCOLOR_IDRICO());
			typesFp.add(typeHydro1);
			Type typePMPG1 = new Type();
			typePMPG1.setName(fpr.gettYPES_NAME_POMPAGGIO());
			typePMPG1.setTypeID(fpr.gettYPES_ID_POMPAGGIO());
			typePMPG1.setHexColor(fpr.gettYPES_HEXCOLOR_POMPAGGIO());
			if(fpr.gettYPES_HEXCOLOR_POMPAGGIO()!=null) {
			typesFp.add(typePMPG1);
			}
			Type typePv1 = new Type();
			typePv1.setName(fpr.gettYPES_NAME_FOTOVOLTAICO());
			typePv1.setTypeID(fpr.gettYPES_ID_FOTOVOLTAICO());
			typePv1.setHexColor(fpr.gettYPES_HEXCOLOR_FOTOVOLTAICO());
			typesFp.add(typePv1);
			Type typeWind1 = new Type();
			typeWind1.setName(fpr.gettYPES_NAME_EOLICO());
			typeWind1.setTypeID(fpr.gettYPES_ID_EOLICO());
			typeWind1.setHexColor(fpr.gettYPES_HEXCOLOR_EOLICO());
			typesFp.add(typeWind1);
			Type typeThermomixed =new Type();
			typeThermomixed.setName(fpr.gettYPES_NAME_THERMOMIXED());
			typeThermomixed.setHexColor(fpr.gettYPES_HEXCOLOR_THERMOMIXED());
			typeThermomixed.setTypeID(fpr.gettYPES_ID_THERMOMIXED());
			typesFp.add(typeThermomixed);
			
			ArrayList<DayMeasure> DayMeasureFontiPrimarie= new ArrayList();
			
			for(DataFpRaw dfpr : lfpr.getLista())
			{
				ArrayList<Measure> measureFontiPrimarie=new ArrayList();
				Measure THERMOMIXED =new Measure();
				THERMOMIXED.setTypeID("THERMOMIXED");
				if(dfpr.gettHERMOMIXED()!=null) {
				THERMOMIXED.setValue(Double.parseDouble(dfpr.gettHERMOMIXED()));
				measureFontiPrimarie.add(THERMOMIXED);
				}
				
				Measure ESTERO=new Measure();
				ESTERO.setTypeID("ESTERO");
				if(dfpr.geteSTERO()!=null) {
				ESTERO.setValue(Double.parseDouble(dfpr.geteSTERO()));
				}else {
					ESTERO.setValue(null);
				}
				measureFontiPrimarie.add(ESTERO);
				Measure PV =new Measure();
				PV.setTypeID("PV");
				if(dfpr.getpV()!=null) {
				PV.setValue(Double.parseDouble(dfpr.getpV()));
				}else {
					PV.setValue(null);
				}
				measureFontiPrimarie.add(PV);
				Measure WIND =new Measure();
				WIND.setTypeID("WIND");
				if(dfpr.getwIND()!=null) {
				WIND.setValue(Double.parseDouble(dfpr.getwIND()));
				}else {
					WIND.setValue(null);
				}
				measureFontiPrimarie.add(WIND);
				Measure GEO=new Measure();
				GEO.setTypeID("GEO");
				if(dfpr.getgEO()!=null) {
				GEO.setValue(Double.parseDouble(dfpr.getgEO()));
				}else
				{
					GEO.setValue(null);
				}
				measureFontiPrimarie.add(GEO);
				Measure HYDRO= new Measure();
				HYDRO.setTypeID("HYDRO");
				if(dfpr.gethYDRO()!=null) {
				HYDRO.setValue(Double.parseDouble(dfpr.gethYDRO()));
				}else {
					HYDRO.setValue(null);
				}
				measureFontiPrimarie.add(HYDRO);
				Measure AUTOPRODUZIONE =new Measure();
				AUTOPRODUZIONE.setTypeID("AUTOPRODUZIONE");
				if(dfpr.getaUTOPRODUZIONE()!=null) {
				AUTOPRODUZIONE.setValue(Double.parseDouble(dfpr.getaUTOPRODUZIONE()));
				}else
				{
					AUTOPRODUZIONE.setValue(null);
				}
				measureFontiPrimarie.add(AUTOPRODUZIONE);
				
				Measure POMPAGGI =new Measure();
				POMPAGGI.setTypeID("POMPAGGI");
				if(dfpr.getpOMPAGGI()!=null) {
				POMPAGGI.setValue(Double.parseDouble(dfpr.getpOMPAGGI()));
				measureFontiPrimarie.add(POMPAGGI);
				}
				
				DayMeasure dmfp=new DayMeasure();
				dmfp.setMeasures(measureFontiPrimarie);
				dmfp.setTimeLabel(dfpr.getdATA_ORA());
				DayMeasureFontiPrimarie.add(dmfp);
				
				
				
			}
			
			FontePrimaria fontePrimaria=new FontePrimaria();
			fontePrimaria.setDayMeasures(DayMeasureFontiPrimarie);
			fontePrimaria.setTypes(typesFp);
			fontePrimaria.setFromDate(fromDate);
			fontePrimaria.setToDate(toDate);
			fontePrimaria.setMeasureDescription(fpr.getmEASURE_DESCRIPTION());
			fontePrimaria.setMeasureUnit(fpr.getmEASURE_UNIT());
			fontePrimaria.setName(fpr.getnAME());
			
			result=objectMapper.writeValueAsString(fontePrimaria);
			log.info("getFontiPrimarie executed");
			return result;
		// ---------------------------------------------------------------------------------------------------------------------------
		case "getFabbisogno":
			Fabbisogno fabbisogno = new Fabbisogno();

			sqlx = sql1;
			List<Map<String, Object>> o7 = jdbcTemplate.queryForList(sqlx);
			result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o7);
			result = result.replace("[", "").replace("]", "");
			sql2 = elaboraJson(result, "QUERY");
			result = eliminaQuery(result);
			String from = parametri.get("fromDate");
			String to = parametri.get("toDate");
			sql2 = sql2.replace(":fromDate", "\'" + from + "\'").replace(":toDate", "\'" + to + "\'");
			List<Map<String, Object>> o8 = jdbcTemplateBilan.queryForList(sql2);
			for (int h = 0; h < o8.size(); h++) {
				String separator = "\\";
				String timelabel = (String) o8.get(h).get("DATA_QUARTO");
				String[] labels = timelabel.split(Pattern.quote(separator));
				o8.get(h).replace("DATA_QUARTO", labels[2]);
				o8.get(h).remove("LBL_DATA_QUARTO");

			}
			if (parametri.get("id").equals("0")) {
				for (int ii = 0; ii < o8.size(); ii++) {
					o8.get(ii).remove("LBL_DATA");

				}
			}
			result = result + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o8);
			if (parametri.get("id").equals("0")) {
				result = result.replace("}[ {", "},{ \"dayMeasures\":[ {").replace("} ]", "}]}]}")
						.replace("\"NAM", "\"NAM").replace("\"TYPES_ID_CONSUNTIVO\"", "},\"types\":[{ \"typeID\"")
						.replace("TYPES_HEXCOLOR_CONSUNTIVO", "hexColor")
						.replace("\"TYPES_ID_PREVISIONE\"", "},{\"typeID\"").replace("TYPES_NAME_PREVISIONE", "name")
						.replace("TYPES_NAME_CONSUNTIVO", "name").replace("TYPES_HEXCOLOR_PREVISIONE", "hexColor")
						// .replace("\"LBL_DATA_QUARTO\"", "\"measures\":[{\"LBL_DATA_QUARTO\"")
						// .replace("\"LBL_DATA\"", "\"measures\":[{\"LBL_DATA\"")
						.replace(",\r\n  \"PREVENTIVO\"", "},{\"TypeId\"")
						.replace(",\r\n  \"CONSUNTIVO\"", "},{\"TypeId\"").replace("}, {\r\n  \"DATA", "}] }, {\"DATA")
						.replace("\"DATA_QUARTO", "\"timeLabel")
						.replace("\"MEASURE_UNIT",
								"\"fromDate\":\"" + from + "\",\"toDate\":\"" + to + "\",\"measureUnit")
						.replace(",},\"ty", ",\"ty")// .replace(", { \"tim", "]}, { \"tim")
						.replace("[   {     \"time", "],\"dayMeasures\":[   {     \"time")
						.replace(",},{\"typ", "},{\"typ")
						.replace(",     \"PREVENTIVO\" :", ",\"measures\":[{\"typeID\":\"PREVENTIVO\",\"value\" :")
						.replace(",     \"CONSUNTIVO\" :", "},{\"typeID\":\"CONSUNTIVO\",\"value\":")
						.replace("},   {     \"timeLabel\"", "}]},{\"timeLabel\"").replace("NAME", "name")
						.replace("MEASURE_DESCRIPTION", "measureDescription").replace(": .", ": 0.")
						.replace(": -.", ": -0.");

				if (!result.contains("[ ]")) {
					log.info("getFabbisogno json preCast: " + result);
					fabbisogno = objectMapper.readValue(result, Fabbisogno.class);
				} else {
					fabbisogno.setFromDate(from);
					fabbisogno.setToDate(to);
					fabbisogno.setMeasureUnit("GW");
					if (language.equals("it")) {
						fabbisogno.setName("Fabbisogno Italia");
						fabbisogno.setMeasureDescription("La richiesta di energia a livello nazionale.");
					} else {
						fabbisogno.setName("Total Load");
						fabbisogno.setMeasureDescription("Italian electricity system's total demand.");
					}
					fabbisogno.setDayMeasures(new ArrayList());
					fabbisogno.setTypes(new ArrayList());
				}
			} else {
				result = result.replace("}[ {", "},{ \"dayMeasures\":[ {").replace("} ]", "}]}]}")
						.replace("\"NAM", "\"NAM").replace("\"TYPES_ID_CONSUNTIVO\"", "},\"types\":[{ \"typeID\"")
						.replace("TYPES_HEXCOLOR_CONSUNTIVO", "hexColor")
						.replace("\"TYPES_ID_PREVISIONE\"", "},{\"typeID\"").replace("TYPES_NAME_PREVISIONE", "name")
						.replace("TYPES_NAME_CONSUNTIVO", "name").replace("TYPES_HEXCOLOR_PREVISIONE", "hexColor")
						// .replace(", \"PREVENTIVO\"", ",\"measures\":[{\"PREVENTIVO\"")
						// .replace("\"LBL_DATA\"", "\"measures\":[{\"LBL_DATA\"")
						.replace(",\r\n  \"PREVENTIVO\"", "},{\"typeID\":\"preventivo\",\"value\"")
						.replace(",\r\n  \"CONSUNTIVO\"", "},{\"typeID\":\"consuntivo\",\"value\"")
						.replace("}, {\r\n  \"DATA", "}] }, {\"DATA").replace("\"DATA_QUARTO", "\"timeLabel")
						.replace("\"MEASURE_UNIT",
								"\"fromDate\":\"" + from + "\",\"toDate\":\"" + to + "\",\"measureUnit")
						.replace(",},\"ty", ",\"ty").replace(",   {     \"tim", ",   {     \"tim")
						.replace("[   {     \"time", "],\"dayMeasures\":[   {     \"time")
						.replace(",},{\"typ", "},{\"typ").replace("},   {     \"timelabel", "},   {     \"timeLabel")
						.replace(",     \"PREVENTIVO\" :", ",\"measures\":[{\"typeID\":\"PREVENTIVO\",\"value\" :")
						.replace(",     \"CONSUNTIVO\" :", "},{\"typeID\":\"CONSUNTIVO\",\"value\":")
						.replace("},   {     \"timeLabel\"", "}]},{\"timeLabel\"").replace("NAME", "name")
						.replace("MEASURE_DESCRIPTION", "measureDescription").replace(": .", ": 0.")
						.replace(": -.", ": -0.");
				if (!result.contains("[ ]")) {
					fabbisogno = objectMapper.readValue(result, Fabbisogno.class);
				} else {
					fabbisogno.setFromDate(from);
					fabbisogno.setToDate(to);
					fabbisogno.setMeasureUnit("GW");
					if (language.equals("it")) {
						fabbisogno.setName("Fabbisogno Mercato");
						fabbisogno.setMeasureDescription("L'energia immessa in rete.");
					} else {
						fabbisogno.setName("Market Load");
						fabbisogno.setMeasureDescription("Energy input injected in the grid.");
					}
					fabbisogno.setDayMeasures(new ArrayList());
					fabbisogno.setTypes(new ArrayList());
				}
			}
			log.info("getFabbisogno executed");
			return objectMapper.writeValueAsString(fabbisogno);
		// -----------------------------------------------------------------------------------------------------------------------------
		case "getScambiZonali":
			ArrayList res = new ArrayList();
			
			float totalAmount = 0;
			fromDate = parametri.get("fromDate");
			toDate = parametri.get("toDate");
			Map ft = new HashMap();
			ft.put("fromDate", fromDate);
			ft.put("toDate", toDate);

			String tf = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ft);
			String query1 = api.getFilter();
			if (query1 != null) {
				query1 = query1.replace(":lang_code", "\'" + language + "\'");
			}
			String query2 = sql1;
			query2 = query2.replace(":lang_code", "\'" + language + "\'");
			List<Map<String, Object>> l2 = jdbcTemplate.queryForList(query2);
			l2.get(0).put("fromDate", fromDate);
			l2.get(0).put("toDate", toDate);
			if (parametri.get("id").equals("5") || parametri.get("id").equals("9")) {
				l2.get(0).put("totalAmount", "****");
			}

			List<Map<String, Object>> l1 = new ArrayList();
			String resultsql = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(l2);
			resultsql = resultsql.replace("[", "").replace("]", "");
			String query3 = elaboraJson(resultsql, "QUERY");
			resultsql = eliminaQuery(resultsql);
			String r1 = null;
			// result=resultsql;
			if (query1 != null) {
				l1 = jdbcTemplate.queryForList(query1);
			}

			// result=result+objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(l1);
			// result=result.replace("[", "").replace("]","");

			query3 = query3.replace(":fromDate", "\'" + fromDate + "\'").replace(":toDate", "\'" + toDate + "\'");
			ArrayList datiSomma = new ArrayList();
			ArrayList listm = new ArrayList();
			ArrayList legenda = new ArrayList();
			query3 = query3.replace(":lang_code", "\'" + language + "\'");
			List<Map<String, Object>> l3 = jdbcTemplateBilan.queryForList(query3);
			Map<String, Object> somma = new HashMap();

			// log.info(l1.toString());
			// log.info(l3.toString());

			List<ZonaliDataBean> zdBean = objectMapper.reader().forType(new TypeReference<List<ZonaliDataBean>>() {
			}).readValue(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(l1));

			List<ZonaliBilanBean> zbBean = objectMapper.reader().forType(new TypeReference<List<ZonaliBilanBean>>() {
			}).readValue(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(l3));

			log.info(zdBean.toString());
			log.info(zbBean.toString());
			ArrayList<MatchData> matchs = new ArrayList<MatchData>();
			ArrayList<MatchData1> matchs1 = new ArrayList<>();
			ArrayList<LegendaZonaliBean> legendaZonali = new ArrayList<LegendaZonaliBean>();
			legendaZonali.add(new LegendaZonaliBean("#FFFFF", "Italia", "0"));

			if (parametri.get("id").equals("4") || parametri.get("id").equals("10")) {
				for (int i = 0; i < zdBean.size(); i++) {
					for (int j = 0; j < zbBean.size(); j++) {
						if (zbBean.get(j).getZONA_DA().equals(zdBean.get(i).getFROMZONELABEL_CK())
								&& zbBean.get(j).getZONA_A().equals(zdBean.get(i).getTOZONELABEL_CK())) {
							MatchData1 match = new MatchData1(zbBean.get(j), zdBean.get(i));
							log.info("match :" + match.toString());
							matchs1.add(match);
							// totalAmount=totalAmount+Float.parseFloat(zbBean.get(j).getSCAMBIO());

						}
					}
				}

				res = divOra1(matchs1, "\\\\T\\\\");

				result = resultsql + objectMapper.writeValueAsString(res);
				result = result.replace("}[{\"DATA_ORA", "");
				result = result.replace("\"}[\"", "\",\"dayMeasures\":[ {  \"timeLabel\":\"")
						.replace("[{\"value", "\"measures\":[{\"value")
						.replace("\"}],\"", "\"     }]},{   \"timeLabel\":\"").replace("}]]", "}]}]}")
						.replace("DATA_TYPE_ID\":", "dataTypeId\":").replace("NAME\":\"", "name\":\"")
						.replace("MEASURE_UNIT\":\"", "measureUnit\":\"")
						.replace("MEASURE_DESCRIPTION\":\"", "measureDescription\":\"").replace("valuex", "value")
						.replace(": .", ": 0.").replace(": -.", ": -0.")
						.replace("}[]", ",\r\n" + "\"dayMeasures\":[]\r\n" + "}");
				return result;
			} else {

				if (parametri.get("id").equals("5") || parametri.get("id").equals("9")) {
					for (int s1 = 0; s1 < zdBean.size(); s1++) {
						if (zdBean.get(s1).getSCAMBIO().equals("negativo")) {
							legendaZonali.add(new LegendaZonaliBean(zdBean.get(s1)));
						}
						for (int s = 0; s < zbBean.size(); s++) {

							if (zbBean.get(s).getNAZIONE().equals(zdBean.get(s1).getFROMZONELABEL_CK())) {

								if (zdBean.get(s1).getSCAMBIO().equalsIgnoreCase("positivo")
										&& zbBean.get(s).getSALDO() > 0) {
									MatchData match = new MatchData(zbBean.get(s), zdBean.get(s1));
									matchs.add(match);
									if (match.getORA().equals("23")) {
										totalAmount = totalAmount + zbBean.get(s).getSALDO();

									}
									log.info("positivo creato");
								}
								if (zdBean.get(s1).getSCAMBIO().equals("negativo") && zbBean.get(s).getSALDO() < 0) {
									MatchData match = new MatchData(zbBean.get(s), zdBean.get(s1));
									matchs.add(match);
									if (match.getORA().equals("23")) {
										totalAmount = totalAmount + zbBean.get(s).getSALDO();

									}
									log.info("negativo creato");

								}

							}
						}
					}

					res = divOra(matchs, "\\\\T\\\\");

					result = resultsql + objectMapper.writeValueAsString(legendaZonali) + objectMapper.writeValueAsString(res);
					log.info("++++++++++++++++++++++++++++++++++++++++ log di debug:" + objectMapper.writeValueAsString(res));
					result = result.replace("}[{\"DATA_ORA", "");
					result = result.replace("}[{\"hex", ", \"types\": [ {\"hex");
					result = result.replace("\"}[\"", "\",\"dayMeasures\":[ {  \"timeLabel\":\"")
							// .replace("[{\"fromZone\":", "\"measures\":[{\"fromZone\":")
							.replace("[{\"value", "\"measures\":[{\"value")
							.replace("\"}],\"", "\"     }]},{   \"timeLabel\":\"").replace("}]]", "}]}]}")
							.replace("\"****\"", Float.toString(totalAmount))
							.replace("DATA_TYPE_ID\":", "dataTypeId\":").replace("NAME\":\"", "name\":\"")
							.replace("MEASURE_UNIT\":\"", "measureUnit\":\"")
							.replace("MEASURE_DESCRIPTION\":\"", "measureDescription\":\"").replace("valuex", "value")
							.replace("\"}][\"", "\"}],\"dayMeasures\":[{\"timeLabel\":\"").replace(": .", ": 0.")
							.replace(": -.", ": -0.").replace("][]", "],\r\n" + "\"dayMeasures\":[]\r\n" + "}")

					;

					return result;
				}

			}

		case "getNews":
			
			sqlx = sql1.replace("'it'", "'" + language + "'");
			List<Map<String, Object>> o01 = jdbcTemplate.queryForList(sqlx);
			result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o01);
			List<NewsBean> newsBean = objectMapper.reader().forType(new TypeReference<List<NewsBean>>() {
			}).readValue(result);
			String Queryimmagini = api.getFilter();

			for (int nw = 0; nw < newsBean.size(); nw++) {

				String queryimmagini = Queryimmagini.replace(":newsId", newsBean.get(nw).getNewsId());
				List<Map<String, Object>> listImmagini = jdbcTemplate.queryForList(queryimmagini);
				String jsonImmagini = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(listImmagini);
				List<Images> images = objectMapper.reader().forType(new TypeReference<List<Images>>() {
				}).readValue(jsonImmagini);
				newsBean.get(nw).setImages(images);
				String[] dataOraNews = newsBean.get(nw).getDatePublished().split("T");
				String DataCorretta = dataOraNews[0] + " " + dataOraNews[1];
				newsBean.get(nw).setDatePublished(DataCorretta);
			}

			result = objectMapper.writeValueAsString(newsBean);
			result = result.replace("[{\"date", "{\"articles\":[{\"date").replace("\"Abstract\":\"", "\"abstract\":\"")
					.replace(": .", ": 0.").replace(": -.", ": -0.");
			result = result + "}";
			NewsObj objResult = objectMapper.reader().forType(new TypeReference<NewsObj>() {
			}).readValue(result);
			log.info(objResult.toString());

			List<Article> artcleToPaging = objResult.getArticles();
			log.info("sto per stampare i parametri size e offset:");
			log.info(parametri.get("size"));
			log.info(parametri.get("offset"));
			objResult.setArticles(
					objResult.getPagination(artcleToPaging, parametri.get("size"), parametri.get("offset")));
			objResult.setOffSet(Integer.parseInt(parametri.get("offset")));
			objResult.setPageSize(Integer.parseInt(parametri.get("size")));
			return objectMapper.writeValueAsString(objResult);

		case "getCoperturaDaFer":
			

			String query111 = api.getFilter();
			String query222 = sql1;
			query222 = query222.replace(":lang_code", language);
			List<Map<String, Object>> l222 = jdbcTemplate.queryForList(query222);
			if (!l222.isEmpty()) {
				l222.get(0).put("fromDate", parametri.get("fromDate"));
				l222.get(0).put("toDate", parametri.get("toDate"));
			}
			String resultsql111 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(l222);
			resultsql111 = resultsql111.replace("[", "").replace("]", "");
			String query333 = elaboraJson(resultsql111, "QUERY");
			resultsql111 = eliminaQuery(resultsql111);
			// result=resultsql;
			query111 = query111.replace(":lang_code", "\'" + language + "\'");
			List<Map<String, Object>> l111 = jdbcTemplate.queryForList(query111);
			// result=result+objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(l1);
			// result=result.replace("[", "").replace("]","");

			query333 = query333.replace(":fromDate", "\'" + parametri.get("fromDate") + "\'").replace(":toDate",
					"\'" + parametri.get("toDate") + "\'");

			ArrayList<Map<String, Object>> listm11 = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> l333 = jdbcTemplateBilan.queryForList(query333);

			if (l111.isEmpty() || l333.isEmpty()) {
				String nodatainthisdate = eliminaQuery(
						objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(l222));
				nodatainthisdate = nodatainthisdate.replace("[", "").replace("]", "").replace("}", "");
				nodatainthisdate = nodatainthisdate + ",\"dayMeasures\":[]}";

				nodatainthisdate = nodatainthisdate.replace("\"}[\"", "\",\"dayMeasures\":[ {  \"timeLabel\":\"")
						.replace("[{\"va", "\"measures\":[{\"va")
						.replace("\"}],\"", "\"     }   ]},{   \"timeLabel\":\"").replace("}]]", "}]}]}")
						.replace("DATA_TYPE_ID", "dataTypeId").replace("\"LABEL\":\"", "\"name\":\"")
						.replace("MEASURE_UNIT", "measureUnit").replace("MEASURE_DESCRIPTION", "measureDescription")
						.replace(": .", ": 0.").replace(": -.", ": -0.");
				return nodatainthisdate;
			}

			for (int i = 0; i < l111.size(); i++) { // System.out.println("valore di i:"+i);
				for (Map.Entry<String, Object> entry : l111.get(i).entrySet()) {
					// descrizioni

					for (int j = 0; j < l333.size(); j++) {

						for (Map.Entry<String, Object> entrys : l333.get(j).entrySet()) {
							// dati

							if (l333.get(j).get("CODICE_ZONA").equals(l111.get(i).get("ZONA_CK"))) {
								Map<String, Object> m = new HashMap();
								// datiSomma11.add(l333.get(i).get("TONNELLATE_CO2"));

								m.put(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(l333.get(j)),
										objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(l111.get(i)));

								listm11.add(m);
								// l1.add(i, l3.get(j));
								break;
							}

						}
					}

				}
			}

			r1 = eliminaQuery(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(l222));
			for (int q = 0; q < listm11.size(); q++) {
				listm11.get(q).remove("CODICE_ZONA");
			}
			Set copy1 = new LinkedHashSet(listm11);
			listm11.clear();
			listm11.addAll(copy1);

			List<Map<String, Object>> listgroupby = new ArrayList<>();
			listgroupby.addAll(listm11);
			if (!listgroupby.isEmpty()) {
				// listgroupby.remove(0);
			}
			String r2 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(listm11);
			String arraytoorder = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(listgroupby);
			arraytoorder = arraytoorder.replace("\\", "").replace("}\" : \"{", "} , {").replace("}\"", "")
					.replace("\"{", "").replace("} , {   \"FROMZ", ",\"FROMZ").replace("ra.\"},{\"to", "ra.\",\"to")
					.replace("}   {        \"DA", ",\"dati\":[{\"DA").replace("} , {   \"ID", " ,    \"ID")
					.replace("},{\"fromDate", ",\"fromDate").replace("} , {   \"ZONA_CK\"", " ,    \"ZONA_CK\"");

			List<FerBean> ferBean = objectMapper.reader().forType(new TypeReference<List<FerBean>>() {
			}).readValue(arraytoorder);
			log.info("Ma sta andando?" + ferBean.toString());
			List<FerBean> ferBean1 = new ArrayList();
			List<FerBean> ferBean2 = new ArrayList();
			List<FerBean> ferBean3 = new ArrayList();
			List<FerBean> ferBean4 = new ArrayList();
			List<FerBean> ferBean5 = new ArrayList();
			List<FerBean> ferBean6 = new ArrayList();
			List<FerBean> ferBean7 = new ArrayList();
			List<FerBean> ferBean8 = new ArrayList();
			List<FerBean> ferBean9 = new ArrayList();
			List<FerBean> ferBean10 = new ArrayList();
			List<FerBean> ferBean11 = new ArrayList();
			List<FerBean> ferBean12 = new ArrayList();
			List<FerBean> ferBean13 = new ArrayList();
			List<FerBean> ferBean14 = new ArrayList();
			List<FerBean> ferBean15 = new ArrayList();
			List<FerBean> ferBean16 = new ArrayList();
			List<FerBean> ferBean17 = new ArrayList();
			List<FerBean> ferBean18 = new ArrayList();
			List<FerBean> ferBean19 = new ArrayList();
			List<FerBean> ferBean20 = new ArrayList();
			List<FerBean> ferBean21 = new ArrayList();
			List<FerBean> ferBean22 = new ArrayList();
			List<FerBean> ferBean23 = new ArrayList();
			List<FerBean> ferBean00 = new ArrayList();

			for (int cc = 0; cc < ferBean.size(); cc++) {
				if (ferBean.get(cc).getORA() != null) {
					if (ferBean.get(cc).getZone().equals("CNOR") && ferBean.get(cc).getORA().equals("00")) {
						log.info("ESISTE");
					}
					switch (ferBean.get(cc).getORA()) {

					case "01":
						ferBean1.add(ferBean.get(cc));
						log.info(ferBean.get(cc).toString());
						break;
					case "02":
						ferBean2.add(ferBean.get(cc));
						break;
					case "03":
						ferBean3.add(ferBean.get(cc));
						break;
					case "04":
						ferBean4.add(ferBean.get(cc));
						break;
					case "05":
						ferBean5.add(ferBean.get(cc));
						break;
					case "06":
						ferBean6.add(ferBean.get(cc));
						break;
					case "07":
						ferBean7.add(ferBean.get(cc));
						break;
					case "08":
						ferBean8.add(ferBean.get(cc));
						break;
					case "09":
						ferBean9.add(ferBean.get(cc));
						break;
					case "10":
						ferBean10.add(ferBean.get(cc));
						break;
					case "11":
						ferBean11.add(ferBean.get(cc));
						break;
					case "12":
						ferBean12.add(ferBean.get(cc));
						break;
					case "13":
						ferBean13.add(ferBean.get(cc));
						break;
					case "14":
						ferBean14.add(ferBean.get(cc));
						break;
					case "15":
						ferBean15.add(ferBean.get(cc));
						break;
					case "16":
						ferBean16.add(ferBean.get(cc));
						break;
					case "17":
						ferBean17.add(ferBean.get(cc));
						break;
					case "18":
						ferBean18.add(ferBean.get(cc));
						break;
					case "19":
						ferBean19.add(ferBean.get(cc));
						break;
					case "20":
						ferBean20.add(ferBean.get(cc));
						break;
					case "21":
						ferBean21.add(ferBean.get(cc));
						break;
					case "22":
						ferBean22.add(ferBean.get(cc));
						break;
					case "23":
						ferBean23.add(ferBean.get(cc));
						break;
					case "00":
						ferBean00.add(ferBean.get(cc));

						break;
					default:
						log.debug("error");
					}
				}
			}

			String pdata[];
			ArrayList ordinatedList = new ArrayList();
			// ordinatedList.add(listm11.get(0));
			if (!ferBean00.isEmpty()) {
				pdata = ferBean00.get(0).getDATA_ORA().split("T");
				ordinatedList.add(pdata[1]);
				ordinatedList.add(ferBean00);
			}
			if (!ferBean1.isEmpty()) {
				pdata = ferBean1.get(0).getDATA_ORA().split("T");
				ordinatedList.add(pdata[1]);
				ordinatedList.add(ferBean1);
			}
			if (!ferBean2.isEmpty()) {
				pdata = ferBean2.get(0).getDATA_ORA().split("T");
				ordinatedList.add(pdata[1]);
				ordinatedList.add(ferBean2);
			}
			if (!ferBean3.isEmpty()) {
				pdata = ferBean3.get(0).getDATA_ORA().split("T");
				ordinatedList.add(pdata[1]);
				ordinatedList.add(ferBean3);
			}
			if (!ferBean4.isEmpty()) {
				pdata = ferBean4.get(0).getDATA_ORA().split("T");
				ordinatedList.add(pdata[1]);
				ordinatedList.add(ferBean4);
			}
			if (!ferBean5.isEmpty()) {
				pdata = ferBean5.get(0).getDATA_ORA().split("T");
				ordinatedList.add(pdata[1]);
				ordinatedList.add(ferBean5);
			}
			if (!ferBean6.isEmpty()) {
				pdata = ferBean6.get(0).getDATA_ORA().split("T");
				ordinatedList.add(pdata[1]);
				ordinatedList.add(ferBean6);
			}
			if (!ferBean7.isEmpty()) {
				pdata = ferBean7.get(0).getDATA_ORA().split("T");
				ordinatedList.add(pdata[1]);
				ordinatedList.add(ferBean7);
			}
			if (!ferBean8.isEmpty()) {
				pdata = ferBean8.get(0).getDATA_ORA().split("T");
				ordinatedList.add(pdata[1]);
				ordinatedList.add(ferBean8);
			}
			if (!ferBean9.isEmpty()) {
				pdata = ferBean9.get(0).getDATA_ORA().split("T");
				ordinatedList.add(pdata[1]);
				ordinatedList.add(ferBean9);
			}
			if (!ferBean10.isEmpty()) {
				pdata = ferBean10.get(0).getDATA_ORA().split("T");
				ordinatedList.add(pdata[1]);
				ordinatedList.add(ferBean10);
			}
			if (!ferBean11.isEmpty()) {
				pdata = ferBean11.get(0).getDATA_ORA().split("T");
				ordinatedList.add(pdata[1]);
				ordinatedList.add(ferBean11);
			}
			if (!ferBean12.isEmpty()) {
				pdata = ferBean12.get(0).getDATA_ORA().split("T");
				ordinatedList.add(pdata[1]);
				ordinatedList.add(ferBean12);
			}
			if (!ferBean13.isEmpty()) {
				pdata = ferBean13.get(0).getDATA_ORA().split("T");
				ordinatedList.add(pdata[1]);
				ordinatedList.add(ferBean13);
			}
			if (!ferBean14.isEmpty()) {
				pdata = ferBean14.get(0).getDATA_ORA().split("T");
				ordinatedList.add(pdata[1]);
				ordinatedList.add(ferBean14);
			}
			if (!ferBean15.isEmpty()) {
				pdata = ferBean15.get(0).getDATA_ORA().split("T");
				ordinatedList.add(pdata[1]);
				ordinatedList.add(ferBean15);
			}
			if (!ferBean16.isEmpty()) {
				pdata = ferBean16.get(0).getDATA_ORA().split("T");
				ordinatedList.add(pdata[1]);
				ordinatedList.add(ferBean16);
			}
			if (!ferBean17.isEmpty()) {
				pdata = ferBean17.get(0).getDATA_ORA().split("T");
				ordinatedList.add(pdata[1]);
				ordinatedList.add(ferBean17);
			}
			if (!ferBean18.isEmpty()) {
				pdata = ferBean18.get(0).getDATA_ORA().split("T");
				ordinatedList.add(pdata[1]);
				ordinatedList.add(ferBean18);
			}
			if (!ferBean19.isEmpty()) {
				pdata = ferBean19.get(0).getDATA_ORA().split("T");
				ordinatedList.add(pdata[1]);
				ordinatedList.add(ferBean19);
			}
			if (!ferBean20.isEmpty()) {
				pdata = ferBean20.get(0).getDATA_ORA().split("T");
				ordinatedList.add(pdata[1]);
				ordinatedList.add(ferBean20);
			}
			if (!ferBean21.isEmpty()) {
				pdata = ferBean21.get(0).getDATA_ORA().split("T");
				ordinatedList.add(pdata[1]);
				ordinatedList.add(ferBean21);
			}
			if (!ferBean22.isEmpty()) {
				pdata = ferBean22.get(0).getDATA_ORA().split("T");
				ordinatedList.add(pdata[1]);
				ordinatedList.add(ferBean22);
			}
			if (!ferBean23.isEmpty()) {
				pdata = ferBean23.get(0).getDATA_ORA().split("T");
				ordinatedList.add(pdata[1]);
				ordinatedList.add(ferBean23);
			}
			result = resultsql111 + objectMapper.writeValueAsString(ordinatedList);

			result = result.replace("\"}[\"", "\",\"dayMeasures\":[ {  \"timeLabel\":\"")
					.replace("[{\"va", "\"measures\":[{\"va").replace("\"}],\"", "\"     }   ]},{   \"timeLabel\":\"")
					.replace("}]]", "}]}]}").replace("DATA_TYPE_ID", "dataTypeId")
					.replace("\"LABEL\":\"", "\"name\":\"").replace("MEASURE_UNIT", "measureUnit")
					.replace("MEASURE_DESCRIPTION", "measureDescription").replace(": .", ": 0.")
					.replace(": -.", ": -0.");

			return result;

		case "getRisparmioCO2":
			String query11 = api.getFilter();
			query11 = query11.replace(":lang_code", "\'" + language + "\'");
			String query22 = sql1;
			query22 = query22.replace(":lang_code", "\'" + language + "\'");
			List<Map<String, Object>> l22 = jdbcTemplate.queryForList(query22);

			l22.get(0).put("fromDate", parametri.get("fromDate"));
			l22.get(0).put("toDate", parametri.get("fromDate"));
			String resultsql1 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(l22);
			resultsql1 = resultsql1.replace("[", "").replace("]", "");
			String query33 = elaboraJson(resultsql1, "QUERY");
			resultsql1 = eliminaQuery(resultsql1);
			// result=resultsql;
			List<Map<String, Object>> l11 = jdbcTemplate.queryForList(query11);

			query33 = query33.replace(":fromDate", "\'" + parametri.get("fromDate") + "\'")
					.replace(":toDate", "\'" + parametri.get("toDate") + "\'")
					.replace(":lang_code", "\'" + language + "\'");
			;
			ArrayList datiSomma1 = new ArrayList();
			ArrayList listm1 = new ArrayList();

			List<Map<String, Object>> l33 = jdbcTemplateBilan.queryForList(query33);

			if (l11.isEmpty() || l33.isEmpty()) {
				String nodatainthisdate = eliminaQuery(
						objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(l22));
				nodatainthisdate = nodatainthisdate.replace("[", "").replace("]", "").replace("}", "");
				nodatainthisdate = nodatainthisdate + ",\"dayMeasures\":[]}";
				nodatainthisdate = nodatainthisdate.replace("\"}[\"", "\",\"dayMeasures\":[ {  \"timeLabel\":\"")
						.replace("[{\"value", "\"measures\":[{\"value")
						.replace("\"}],\"", "\"     }   ]},{   \"timeLabel\":\"").replace("}]]", "}]}]}")
						.replace("\",\"fromDate", "\",\"totalAmount\":0" + ",\"fromDate")
						.replace("DATA_TYPE_ID", "dataTypeId").replace("\"LABEL\":\"", "\"name\":\"")
						.replace("MEASURE_UNIT", "measureUnit").replace("MEASURE_DESCRIPTION", "measureDescription")
						.replace(": .", ": 0.").replace(": -.", ": -0.");
				return nodatainthisdate;
			}
			Map<String, Object> somma1 = new HashMap();
			for (int i = 0; i < l11.size(); i++) { // System.out.println("valore di i:"+i);
				for (Map.Entry<String, Object> entry : l11.get(i).entrySet()) {
					// descrizioni

					for (int j = 0; j < l33.size(); j++) {
						// System.out.println("valore di j:"+j);
						for (Map.Entry<String, Object> entrys : l33.get(j).entrySet()) {
							// dati

							if (l33.get(j).get("CODICE_ZONA").equals(l11.get(i).get("ZONA_CK"))) {

								Map<String, Object> m = new HashMap();
								datiSomma1.add(l33.get(i).get("TONNELLATE_CO2"));

								m.put(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(l33.get(j)),
										objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(l11.get(i)));

								listm1.add(m);
								// l1.add(i, l3.get(j));
								break;
							}

						}
					}

				}
			}
			
			String jm1 = objectMapper.writeValueAsString(listm1);
			System.out.println("jjjjjjjjjjson:" + jm1);
			// result=result+objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(l3);
			// result=result.replace("[", "").replace("]","");
			BigDecimal sum1 = new BigDecimal("0");
			for (int z = 0; z < datiSomma1.size(); z++) {
				sum1 = sum1.add((BigDecimal) datiSomma1.get(z));
			}
			l22.get(0).put("totalAmount", sum1);

			Set copy = new LinkedHashSet(listm1);
			listm1.clear();
			listm1.addAll(copy);
			String r11 = eliminaQuery(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(l22));
			result = r11 + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(listm1);

			result = result.replace("[", "").replace("]", "");

			result = result.replace("\\", "").replace("}\" : \"{", "} , {").replace("}\"", "").replace("\"{", "")
					.replace("} , {   \"ZONA_", ",\"ZONA_").replace("ra.\"},{\"to", "ra.\",\"to")
					.replace("}   {        \"DA", ",\"dati\":[{\"DA").replace("}{\"DATA_TYPE", ",\"DATA_TYPE");
			result = result + "]}";

			// listm1.remove(0);

			String parsable = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(listm1);
			parsable = parsable.replace("\\", "").replace("}\" : \"{", "} , {").replace("}\"", "").replace("\"{", "")
					.replace("} , {   \"FROMZ", ",\"FROMZ").replace("ra.\"},{\"to", "ra.\",\"to")
					.replace("}   {        \"DA", ",\"dati\":[{\"DA").replace("}{\"DATA_TYPE", ",\"DATA_TYPE")
					.replace("} , {   \"ZONA_CK", " ,    \"ZONA_CK");

			List<Co2Bean> co2Bean = objectMapper.reader().forType(new TypeReference<List<Co2Bean>>() {
			}).readValue(parsable);

			List<Co2Bean> co2Bean1 = new ArrayList();
			List<Co2Bean> co2Bean2 = new ArrayList();
			List<Co2Bean> co2Bean3 = new ArrayList();
			List<Co2Bean> co2Bean4 = new ArrayList();
			List<Co2Bean> co2Bean5 = new ArrayList();
			List<Co2Bean> co2Bean6 = new ArrayList();
			List<Co2Bean> co2Bean7 = new ArrayList();
			List<Co2Bean> co2Bean8 = new ArrayList();
			List<Co2Bean> co2Bean9 = new ArrayList();
			List<Co2Bean> co2Bean10 = new ArrayList();
			List<Co2Bean> co2Bean11 = new ArrayList();
			List<Co2Bean> co2Bean12 = new ArrayList();
			List<Co2Bean> co2Bean13 = new ArrayList();
			List<Co2Bean> co2Bean14 = new ArrayList();
			List<Co2Bean> co2Bean15 = new ArrayList();
			List<Co2Bean> co2Bean16 = new ArrayList();
			List<Co2Bean> co2Bean17 = new ArrayList();
			List<Co2Bean> co2Bean18 = new ArrayList();
			List<Co2Bean> co2Bean19 = new ArrayList();
			List<Co2Bean> co2Bean20 = new ArrayList();
			List<Co2Bean> co2Bean21 = new ArrayList();
			List<Co2Bean> co2Bean22 = new ArrayList();
			List<Co2Bean> co2Bean23 = new ArrayList();
			List<Co2Bean> co2Bean00 = new ArrayList();

			for (int cc = 0; cc < co2Bean.size(); cc++) {
				if (co2Bean.get(cc).getORA() != null) {
					switch (co2Bean.get(cc).getORA()) {

					case "01":
						co2Bean1.add(co2Bean.get(cc));
						log.info(co2Bean.get(cc).toString());
						break;
					case "02":
						co2Bean2.add(co2Bean.get(cc));
						break;
					case "03":
						co2Bean3.add(co2Bean.get(cc));
						break;
					case "04":
						co2Bean4.add(co2Bean.get(cc));
						break;
					case "05":
						co2Bean5.add(co2Bean.get(cc));
						break;
					case "06":
						co2Bean6.add(co2Bean.get(cc));
						break;
					case "07":
						co2Bean7.add(co2Bean.get(cc));
						break;
					case "08":
						co2Bean8.add(co2Bean.get(cc));
						break;
					case "09":
						co2Bean9.add(co2Bean.get(cc));
						break;
					case "10":
						co2Bean10.add(co2Bean.get(cc));
						break;
					case "11":
						co2Bean11.add(co2Bean.get(cc));
						break;
					case "12":
						co2Bean12.add(co2Bean.get(cc));
						break;
					case "13":
						co2Bean13.add(co2Bean.get(cc));
						break;
					case "14":
						co2Bean14.add(co2Bean.get(cc));
						break;
					case "15":
						co2Bean15.add(co2Bean.get(cc));
						break;
					case "16":
						co2Bean16.add(co2Bean.get(cc));
						break;
					case "17":
						co2Bean17.add(co2Bean.get(cc));
						break;
					case "18":
						co2Bean18.add(co2Bean.get(cc));
						break;
					case "19":
						co2Bean19.add(co2Bean.get(cc));
						break;
					case "20":
						co2Bean20.add(co2Bean.get(cc));
						break;
					case "21":
						co2Bean21.add(co2Bean.get(cc));
						break;
					case "22":
						co2Bean22.add(co2Bean.get(cc));
						break;
					case "23":
						co2Bean23.add(co2Bean.get(cc));
						break;
					case "00":
						co2Bean00.add(co2Bean.get(cc));
						break;
					}
				}
			}

			String pdata1[];
			ArrayList ordinatedList1 = new ArrayList();
			// ordinatedList1.add(listm11.get(0));
			if (!co2Bean00.isEmpty()) {
				pdata1 = co2Bean00.get(0).getDATA_ORA().split("T");
				ordinatedList1.add(pdata1[1]);
				ordinatedList1.add(co2Bean00);
			}
			if (!co2Bean1.isEmpty()) {
				pdata1 = co2Bean1.get(0).getDATA_ORA().split("T");
				ordinatedList1.add(pdata1[1]);
				ordinatedList1.add(co2Bean1);
			}
			if (!co2Bean2.isEmpty()) {
				pdata1 = co2Bean2.get(0).getDATA_ORA().split("T");
				ordinatedList1.add(pdata1[1]);
				ordinatedList1.add(co2Bean2);
			}
			if (!co2Bean3.isEmpty()) {
				pdata1 = co2Bean3.get(0).getDATA_ORA().split("T");
				ordinatedList1.add(pdata1[1]);
				ordinatedList1.add(co2Bean3);
			}
			if (!co2Bean4.isEmpty()) {
				pdata1 = co2Bean4.get(0).getDATA_ORA().split("T");
				ordinatedList1.add(pdata1[1]);
				ordinatedList1.add(co2Bean4);
			}
			if (!co2Bean5.isEmpty()) {
				pdata1 = co2Bean5.get(0).getDATA_ORA().split("T");
				ordinatedList1.add(pdata1[1]);
				ordinatedList1.add(co2Bean5);
			}
			if (!co2Bean6.isEmpty()) {
				pdata1 = co2Bean6.get(0).getDATA_ORA().split("T");
				ordinatedList1.add(pdata1[1]);
				ordinatedList1.add(co2Bean6);
			}
			if (!co2Bean7.isEmpty()) {
				pdata1 = co2Bean7.get(0).getDATA_ORA().split("T");
				ordinatedList1.add(pdata1[1]);
				ordinatedList1.add(co2Bean7);
			}
			if (!co2Bean8.isEmpty()) {
				pdata1 = co2Bean8.get(0).getDATA_ORA().split("T");
				ordinatedList1.add(pdata1[1]);
				ordinatedList1.add(co2Bean8);
			}
			if (!co2Bean9.isEmpty()) {
				pdata1 = co2Bean9.get(0).getDATA_ORA().split("T");
				ordinatedList1.add(pdata1[1]);
				ordinatedList1.add(co2Bean9);
			}
			if (!co2Bean10.isEmpty()) {
				pdata1 = co2Bean10.get(0).getDATA_ORA().split("T");
				ordinatedList1.add(pdata1[1]);
				ordinatedList1.add(co2Bean10);
			}
			if (!co2Bean11.isEmpty()) {
				pdata1 = co2Bean11.get(0).getDATA_ORA().split("T");
				ordinatedList1.add(pdata1[1]);
				ordinatedList1.add(co2Bean11);
			}
			if (!co2Bean12.isEmpty()) {
				pdata1 = co2Bean12.get(0).getDATA_ORA().split("T");
				ordinatedList1.add(pdata1[1]);
				ordinatedList1.add(co2Bean12);
			}
			if (!co2Bean13.isEmpty()) {
				pdata1 = co2Bean13.get(0).getDATA_ORA().split("T");
				ordinatedList1.add(pdata1[1]);
				ordinatedList1.add(co2Bean13);
			}
			if (!co2Bean14.isEmpty()) {
				pdata1 = co2Bean14.get(0).getDATA_ORA().split("T");
				ordinatedList1.add(pdata1[1]);
				ordinatedList1.add(co2Bean14);
			}
			if (!co2Bean15.isEmpty()) {
				pdata1 = co2Bean15.get(0).getDATA_ORA().split("T");
				ordinatedList1.add(pdata1[1]);
				ordinatedList1.add(co2Bean15);
			}
			if (!co2Bean16.isEmpty()) {
				pdata1 = co2Bean16.get(0).getDATA_ORA().split("T");
				ordinatedList1.add(pdata1[1]);
				ordinatedList1.add(co2Bean16);
			}
			if (!co2Bean17.isEmpty()) {
				pdata1 = co2Bean17.get(0).getDATA_ORA().split("T");
				ordinatedList1.add(pdata1[1]);
				ordinatedList1.add(co2Bean17);
			}
			if (!co2Bean18.isEmpty()) {
				pdata1 = co2Bean18.get(0).getDATA_ORA().split("T");
				ordinatedList1.add(pdata1[1]);
				ordinatedList1.add(co2Bean18);
			}
			if (!co2Bean19.isEmpty()) {
				pdata1 = co2Bean19.get(0).getDATA_ORA().split("T");
				ordinatedList1.add(pdata1[1]);
				ordinatedList1.add(co2Bean19);
			}
			if (!co2Bean20.isEmpty()) {
				pdata1 = co2Bean20.get(0).getDATA_ORA().split("T");
				ordinatedList1.add(pdata1[1]);
				ordinatedList1.add(co2Bean20);
			}
			if (!co2Bean21.isEmpty()) {
				pdata1 = co2Bean21.get(0).getDATA_ORA().split("T");
				ordinatedList1.add(pdata1[1]);
				ordinatedList1.add(co2Bean21);
			}
			if (!co2Bean22.isEmpty()) {
				pdata1 = co2Bean22.get(0).getDATA_ORA().split("T");
				ordinatedList1.add(pdata1[1]);
				ordinatedList1.add(co2Bean22);
			}
			if (!co2Bean23.isEmpty()) {
				pdata1 = co2Bean23.get(0).getDATA_ORA().split("T");
				ordinatedList1.add(pdata1[1]);
				ordinatedList1.add(co2Bean23);
			}

			result = resultsql1 + objectMapper.writeValueAsString(ordinatedList1);
			result = result.replace("\"}[\"", "\",\"dayMeasures\":[ {  \"timeLabel\":\"")
					.replace("[{\"value", "\"measures\":[{\"value")
					.replace("\"}],\"", "\"     }   ]},{   \"timeLabel\":\"").replace("}]]", "}]}]}")
					.replace("\",\"fromDate", "\",\"totalAmount\":" + sum1.floatValue() + ",\"fromDate")
					.replace("DATA_TYPE_ID", "dataTypeId").replace("\"LABEL\":\"", "\"name\":\"")
					.replace("MEASURE_UNIT", "measureUnit").replace("MEASURE_DESCRIPTION", "measureDescription")
					.replace(": .", ": 0.").replace(": -.", ": -0.");

			return result;

		case "getFontiRinnovabili":
			sqlx = sql1;
			fromDate = parametri.get("fromDate");
			toDate = parametri.get("toDate");

			List<Map<String, Object>> o45 = jdbcTemplate.queryForList(sqlx);

			Map pm1 = new HashMap();
			pm1.put("fromDate", fromDate);
			pm1.put("toDate", toDate);

			result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o45);
			result = result.replace("[", "").replace("]", "");

			sql2 = elaboraJson(result, "QUERY");
			result = eliminaQuery(result);
			sql2 = sql2.replace(":fromDate", "\'" + fromDate + "\'").replace(":toDate", "\'" + toDate + "\'");
			List<Map<String, Object>> o46 = jdbcTemplateBilan.queryForList(sql2);
			List<Map<String, Object>> perData = new ArrayList();
			ArrayList misure = new ArrayList();

			for (int y = 0; y < o46.size(); y++) {
				for (int t = 0; t < o46.size(); t++) {
					if (o46.get(y).get("DATA_ORA").equals(o46.get(t).get("DATA_ORA"))) {
						Map orario = new HashMap();
						Map data = new HashMap();

						if (y > 0) {
							if (!o46.get(y - 1).get("DATA_ORA").equals(o46.get(y).get("DATA_ORA"))) {
								if (o46.get(t).get("SOTTOTIPO").equals("EOLICO")) {
									String orari = (String) o46.get(t).get("DATA_ORA");

									orario.putIfAbsent("data_ora", orari);

								}
								data.put("typeId", o46.get(t).get("SOTTOTIPO"));
								data.put("produzione" + o46.get(t).get("SOTTOTIPO"), o46.get(t).get("PRODUZIONE_GWH"));
							}
						} else {
							if (o46.get(t).get("SOTTOTIPO").equals("EOLICO")) {
								orario.putIfAbsent("data_ora", o46.get(t).get("DATA_ORA"));
							}
							data.put("typeId", o46.get(t).get("SOTTOTIPO"));
							data.put("produzione" + o46.get(t).get("SOTTOTIPO"), o46.get(t).get("PRODUZIONE_GWH"));
						}

						perData.add(orario);

						perData.add(data);

						// perData.add(o46.get(t));
						// System.out.println(o46.get(t).toString());

					}
				}
			}
			for (int or = 0; or < perData.size(); or++) {
				String orari = (String) perData.get(or).get("data_ora");
				if (orari != null) {
					orari = orari.substring(13, 21);
					perData.get(or).replace("data_ora", orari);
				}
			}
			System.out.println("perdata:" + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(perData));
			result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(pm1) + result
					+ objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(perData);

			result = result.replace("}{\"DATA", ",\"DATA").replace("{ },", "")
					.replace("},   {     \"produzioneEOLICO", ",\"measures\":[{\"produzioneEOLICO")
					.replace("}[   {     \"data", "}],\"dayMesures\":[   {     \"data")
					.replace(
							"},                                                                                                                           {     \"data_ora",
							"}]},                                                                                                                           {     \"data_ora")
					.replace(
							",                                                                                                                        { }",
							"")
					.replace("produzioneEOLICO", "produzione").replace("produzioneGEOTERMICO", "produzione")
					.replace("produzioneIDRICO", "produzione").replace("produzioneSOLARE", "produzione")
					.replace("produzioneTERMICO RINNOVABILE", "produzione")
					.replace("\",\"TYPES_ID_EOLICO", "\",\"types\":[{\"typeId")
					.replace(",\"TYPES_ID_GEOTERMICO", "},{\"typeId").replace(",\"TYPES_ID_IDRICO", "},{\"typeId")
					.replace(",\"TYPES_ID_SOLARE", "},{\"typeId").replace(",\"TYPES_ID_TERMICO_RINN", "},{\"typeId")
					.replace("TYPES_NAME_EOLICO", "typeName").replace("TYPES_NAME_GEOTERMICO", "typeName")
					.replace("TYPES_NAME_IDRICO", "typeName").replace("TYPES_NAME_SOLARE", "typeName")
					.replace("TYPES_NAME_TERMICO_RINN", "typeName").replace("TYPES_HEXCOLOR_EOLICO", "typeHexcolor")
					.replace("TYPES_HEXCOLOR_GEOTERMICO", "typeHexcolor")
					.replace("TYPES_HEXCOLOR_IDRICO", "typeHexcolor").replace("TYPES_HEXCOLOR_SOLARE", "typeHexcolor")
					.replace("TYPES_HEXCOLOR_TERMICO_RINN", "typeHexcolor").replace("\\\\T\\\\", " ")
					.replace("DATA_TYPE_ID", "dataTypeId").replace("NAME\":", "name\":")
					.replace("MEASURE_UNIT\":", "measureUnit\":")
					.replace("MEASURE_DESCRIPTION\":", "measureDescription\":").replace("typeName", "name")
					.replace("typeHexcolor", "hexColor").replace("produzione\"", "value\"")
					.replace("data_ora\"", "timeLabel\"").replace(": .", ": 0.").replace(": -.", ": -0.")

			;
			result = result + "}]}";
			result = result.replace("}[ ]}]}", "}\r\n" + "],\"dayMeasures\":[]\r\n" + "}");

			return result;
		default:

			return null;
		}

	}

	public String treQuery(APIEntity api, String sql1) throws IOException {

		String result = new String();
		String query1 = api.getFilter();
		String query2 = sql1;
		List<Map<String, Object>> l2 = jdbcTemplate.queryForList(query2);
		String resultsql = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(l2);
		resultsql = resultsql.replace("[", "").replace("]", "");
		String query3 = elaboraJson(resultsql, "QUERY");
		resultsql = eliminaQuery(resultsql);
		result = resultsql;
		List<Map<String, Object>> l1 = jdbcTemplate.queryForList(query1);
		result = result + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(l1);
		result = result.replace("[", "").replace("]", "");

		query3 = query3.replace(":fromDate", "\'2021-02-09\'").replace(":toDate", "\'2021-02-10\'");

		List<Map<String, Object>> l3 = jdbcTemplate.queryForList(query3);
		result = result + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(l3);
		result = result.replace("[", "").replace("]", "");
		return result;
	}

	public String ElaboraParm(Map<String, String> param) {

		return "Key: " + param.keySet() + "Value: " + param.values();
	}

	public ArrayList divOra(ArrayList<MatchData> matchs, String spl) {
		List<MatchData> matchData1 = new ArrayList();
		List<MatchData> matchData2 = new ArrayList();
		List<MatchData> matchData3 = new ArrayList();
		List<MatchData> matchData4 = new ArrayList();
		List<MatchData> matchData5 = new ArrayList();
		List<MatchData> matchData6 = new ArrayList();
		List<MatchData> matchData7 = new ArrayList();
		List<MatchData> matchData8 = new ArrayList();
		List<MatchData> matchData9 = new ArrayList();
		List<MatchData> matchData10 = new ArrayList();
		List<MatchData> matchData11 = new ArrayList();
		List<MatchData> matchData12 = new ArrayList();
		List<MatchData> matchData13 = new ArrayList();
		List<MatchData> matchData14 = new ArrayList();
		List<MatchData> matchData15 = new ArrayList();
		List<MatchData> matchData16 = new ArrayList();
		List<MatchData> matchData17 = new ArrayList();
		List<MatchData> matchData18 = new ArrayList();
		List<MatchData> matchData19 = new ArrayList();
		List<MatchData> matchData20 = new ArrayList();
		List<MatchData> matchData21 = new ArrayList();
		List<MatchData> matchData22 = new ArrayList();
		List<MatchData> matchData23 = new ArrayList();
		List<MatchData> matchData00 = new ArrayList();

		for (int cc = 0; cc < matchs.size(); cc++) {
			if (matchs.get(cc).getORA() != null) {

				switch (matchs.get(cc).getORA()) {

				case "01":
					matchData1.add(matchs.get(cc));
					log.info(matchs.get(cc).toString());
					break;
				case "1":
					matchData1.add(matchs.get(cc));
					log.info(matchs.get(cc).toString());
					break;
				case "02":
					matchData2.add(matchs.get(cc));
					break;
				case "2":
					matchData2.add(matchs.get(cc));
					break;
				case "3":
					matchData3.add(matchs.get(cc));
					break;
				case "03":
					matchData3.add(matchs.get(cc));
					break;
				case "4":
					matchData4.add(matchs.get(cc));
					break;
				case "04":
					matchData4.add(matchs.get(cc));
					break;
				case "5":
					matchData5.add(matchs.get(cc));
					break;
				case "05":
					matchData5.add(matchs.get(cc));
					break;
				case "6":
					matchData6.add(matchs.get(cc));
					break;
				case "06":
					matchData6.add(matchs.get(cc));
					break;
				case "7":
					matchData7.add(matchs.get(cc));
					break;
				case "07":
					matchData7.add(matchs.get(cc));
					break;
				case "8":
					matchData8.add(matchs.get(cc));
					break;
				case "08":
					matchData8.add(matchs.get(cc));
					break;
				case "9":
					matchData9.add(matchs.get(cc));
					break;
				case "09":
					matchData9.add(matchs.get(cc));
					break;
				case "10":
					matchData10.add(matchs.get(cc));
					break;
				case "11":
					matchData11.add(matchs.get(cc));
					break;
				case "12":
					matchData12.add(matchs.get(cc));
					break;
				case "13":
					matchData13.add(matchs.get(cc));
					break;
				case "14":
					matchData14.add(matchs.get(cc));
					break;
				case "15":
					matchData15.add(matchs.get(cc));
					break;
				case "16":
					matchData16.add(matchs.get(cc));
					break;
				case "17":
					matchData17.add(matchs.get(cc));
					break;
				case "18":
					matchData18.add(matchs.get(cc));
					break;
				case "19":
					matchData19.add(matchs.get(cc));
					break;
				case "20":
					matchData20.add(matchs.get(cc));
					break;
				case "21":
					matchData21.add(matchs.get(cc));
					break;
				case "22":
					matchData22.add(matchs.get(cc));
					break;
				case "23":
					matchData23.add(matchs.get(cc));
					break;
				case "00":
					matchData00.add(matchs.get(cc));

					break;
				case "0":
					matchData00.add(matchs.get(cc));

					break;
				default:
					log.debug("error");
				}
			}
		}

		String pdata3[];
		ArrayList ordinatedList3 = new ArrayList();
		// ordinatedList3.add(listm11.get(0));
		if (!matchData00.isEmpty()) {
			pdata3 = matchData00.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			ordinatedList3.add(matchData00);
		}
		if (!matchData1.isEmpty()) {
			pdata3 = matchData1.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			ordinatedList3.add(matchData1);
		}
		if (!matchData2.isEmpty()) {
			pdata3 = matchData2.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			ordinatedList3.add(matchData2);
		}
		if (!matchData3.isEmpty()) {
			pdata3 = matchData3.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			ordinatedList3.add(matchData3);
		}
		if (!matchData4.isEmpty()) {
			pdata3 = matchData4.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData4.get(0).getORA());
			ordinatedList3.add(matchData4);
		}
		if (!matchData5.isEmpty()) {
			pdata3 = matchData5.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData5.get(0).getORA());
			ordinatedList3.add(matchData5);
		}
		if (!matchData6.isEmpty()) {
			pdata3 = matchData6.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData6.get(0).getORA());
			ordinatedList3.add(matchData6);
		}
		if (!matchData7.isEmpty()) {
			pdata3 = matchData7.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData7.get(0).getORA());
			ordinatedList3.add(matchData7);
		}
		if (!matchData8.isEmpty()) {
			pdata3 = matchData8.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData8.get(0).getORA());
			ordinatedList3.add(matchData8);
		}
		if (!matchData9.isEmpty()) {
			pdata3 = matchData9.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData9.get(0).getORA());
			ordinatedList3.add(matchData9);
		}
		if (!matchData10.isEmpty()) {
			pdata3 = matchData10.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData10.get(0).getORA());
			ordinatedList3.add(matchData10);
		}
		if (!matchData11.isEmpty()) {
			pdata3 = matchData11.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData11.get(0).getORA());
			ordinatedList3.add(matchData11);
		}
		if (!matchData12.isEmpty()) {
			pdata3 = matchData12.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData12.get(0).getORA());
			ordinatedList3.add(matchData12);
		}
		if (!matchData13.isEmpty()) {
			pdata3 = matchData13.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData13.get(0).getORA());
			ordinatedList3.add(matchData13);
		}
		if (!matchData14.isEmpty()) {
			pdata3 = matchData14.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData14.get(0).getORA());
			ordinatedList3.add(matchData14);
		}
		if (!matchData15.isEmpty()) {
			pdata3 = matchData15.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData15.get(0).getORA());
			ordinatedList3.add(matchData15);
		}
		if (!matchData16.isEmpty()) {
			pdata3 = matchData16.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData16.get(0).getORA());
			ordinatedList3.add(matchData16);
		}
		if (!matchData17.isEmpty()) {
			pdata3 = matchData17.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData17.get(0).getORA());
			ordinatedList3.add(matchData17);
		}
		if (!matchData18.isEmpty()) {
			pdata3 = matchData18.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData18.get(0).getORA());
			ordinatedList3.add(matchData18);
		}
		if (!matchData19.isEmpty()) {
			pdata3 = matchData19.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData19.get(0).getORA());
			ordinatedList3.add(matchData19);
		}
		if (!matchData20.isEmpty()) {
			pdata3 = matchData20.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData20.get(0).getORA());
			ordinatedList3.add(matchData20);
		}
		if (!matchData21.isEmpty()) {
			pdata3 = matchData21.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData21.get(0).getORA());
			ordinatedList3.add(matchData21);
		}
		if (!matchData22.isEmpty()) {
			pdata3 = matchData22.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData22.get(0).getORA());
			ordinatedList3.add(matchData22);
		}
		if (!matchData23.isEmpty()) {
			pdata3 = matchData23.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData23.get(0).getORA());
			ordinatedList3.add(matchData23);
		}
		return ordinatedList3;
	}

	public ArrayList divOra1(ArrayList<MatchData1> matchs, String spl) {
		List<MatchData1> matchData1 = new ArrayList();
		List<MatchData1> matchData2 = new ArrayList();
		List<MatchData1> matchData3 = new ArrayList();
		List<MatchData1> matchData4 = new ArrayList();
		List<MatchData1> matchData5 = new ArrayList();
		List<MatchData1> matchData6 = new ArrayList();
		List<MatchData1> matchData7 = new ArrayList();
		List<MatchData1> matchData8 = new ArrayList();
		List<MatchData1> matchData9 = new ArrayList();
		List<MatchData1> matchData10 = new ArrayList();
		List<MatchData1> matchData11 = new ArrayList();
		List<MatchData1> matchData12 = new ArrayList();
		List<MatchData1> matchData13 = new ArrayList();
		List<MatchData1> matchData14 = new ArrayList();
		List<MatchData1> matchData15 = new ArrayList();
		List<MatchData1> matchData16 = new ArrayList();
		List<MatchData1> matchData17 = new ArrayList();
		List<MatchData1> matchData18 = new ArrayList();
		List<MatchData1> matchData19 = new ArrayList();
		List<MatchData1> matchData20 = new ArrayList();
		List<MatchData1> matchData21 = new ArrayList();
		List<MatchData1> matchData22 = new ArrayList();
		List<MatchData1> matchData23 = new ArrayList();
		List<MatchData1> matchData00 = new ArrayList();

		for (int cc = 0; cc < matchs.size(); cc++) {
			if (matchs.get(cc).getORA() != null) {

				switch (matchs.get(cc).getORA()) {

				case "01":
					matchData1.add(matchs.get(cc));
					log.info(matchs.get(cc).toString());
					break;
				case "1":
					matchData1.add(matchs.get(cc));
					log.info(matchs.get(cc).toString());
					break;
				case "02":
					matchData2.add(matchs.get(cc));
					break;
				case "2":
					matchData2.add(matchs.get(cc));
					break;
				case "3":
					matchData3.add(matchs.get(cc));
					break;
				case "03":
					matchData3.add(matchs.get(cc));
					break;
				case "4":
					matchData4.add(matchs.get(cc));
					break;
				case "04":
					matchData4.add(matchs.get(cc));
					break;
				case "5":
					matchData5.add(matchs.get(cc));
					break;
				case "05":
					matchData5.add(matchs.get(cc));
					break;
				case "6":
					matchData6.add(matchs.get(cc));
					break;
				case "06":
					matchData6.add(matchs.get(cc));
					break;
				case "7":
					matchData7.add(matchs.get(cc));
					break;
				case "07":
					matchData7.add(matchs.get(cc));
					break;
				case "8":
					matchData8.add(matchs.get(cc));
					break;
				case "08":
					matchData8.add(matchs.get(cc));
					break;
				case "9":
					matchData9.add(matchs.get(cc));
					break;
				case "09":
					matchData9.add(matchs.get(cc));
					break;
				case "10":
					matchData10.add(matchs.get(cc));
					break;
				case "11":
					matchData11.add(matchs.get(cc));
					break;
				case "12":
					matchData12.add(matchs.get(cc));
					break;
				case "13":
					matchData13.add(matchs.get(cc));
					break;
				case "14":
					matchData14.add(matchs.get(cc));
					break;
				case "15":
					matchData15.add(matchs.get(cc));
					break;
				case "16":
					matchData16.add(matchs.get(cc));
					break;
				case "17":
					matchData17.add(matchs.get(cc));
					break;
				case "18":
					matchData18.add(matchs.get(cc));
					break;
				case "19":
					matchData19.add(matchs.get(cc));
					break;
				case "20":
					matchData20.add(matchs.get(cc));
					break;
				case "21":
					matchData21.add(matchs.get(cc));
					break;
				case "22":
					matchData22.add(matchs.get(cc));
					break;
				case "23":
					matchData23.add(matchs.get(cc));
					break;
				case "00":
					matchData00.add(matchs.get(cc));

					break;
				case "0":
					matchData00.add(matchs.get(cc));

					break;
				default:
					log.debug("error");
				}
			}
		}

		String pdata3[];
		ArrayList ordinatedList3 = new ArrayList();
		// ordinatedList3.add(listm11.get(0));
		if (!matchData00.isEmpty()) {
			pdata3 = matchData00.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			ordinatedList3.add(matchData00);

		}
		if (!matchData1.isEmpty()) {
			pdata3 = matchData1.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			ordinatedList3.add(matchData1);

		}
		if (!matchData2.isEmpty()) {
			pdata3 = matchData2.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			ordinatedList3.add(matchData2);

		}
		if (!matchData3.isEmpty()) {
			pdata3 = matchData3.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			ordinatedList3.add(matchData3);

		}
		if (!matchData4.isEmpty()) {
			pdata3 = matchData4.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData4.get(0).getORA());
			ordinatedList3.add(matchData4);

		}
		if (!matchData5.isEmpty()) {
			pdata3 = matchData5.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData5.get(0).getORA());
			ordinatedList3.add(matchData5);

		}
		if (!matchData6.isEmpty()) {
			pdata3 = matchData6.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData6.get(0).getORA());
			ordinatedList3.add(matchData6);

		}
		if (!matchData7.isEmpty()) {
			pdata3 = matchData7.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData7.get(0).getORA());
			ordinatedList3.add(matchData7);

		}
		if (!matchData8.isEmpty()) {
			pdata3 = matchData8.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData8.get(0).getORA());
			ordinatedList3.add(matchData8);

		}
		if (!matchData9.isEmpty()) {
			pdata3 = matchData9.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData9.get(0).getORA());
			ordinatedList3.add(matchData9);

		}
		if (!matchData10.isEmpty()) {
			pdata3 = matchData10.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData10.get(0).getORA());
			ordinatedList3.add(matchData10);

		}
		if (!matchData11.isEmpty()) {
			pdata3 = matchData11.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData11.get(0).getORA());
			ordinatedList3.add(matchData11);

		}
		if (!matchData12.isEmpty()) {
			pdata3 = matchData12.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData12.get(0).getORA());
			ordinatedList3.add(matchData12);

		}
		if (!matchData13.isEmpty()) {
			pdata3 = matchData13.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData13.get(0).getORA());
			ordinatedList3.add(matchData13);

		}
		if (!matchData14.isEmpty()) {
			pdata3 = matchData14.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData14.get(0).getORA());
			ordinatedList3.add(matchData14);

		}
		if (!matchData15.isEmpty()) {
			pdata3 = matchData15.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData15.get(0).getORA());
			ordinatedList3.add(matchData15);

		}
		if (!matchData16.isEmpty()) {
			pdata3 = matchData16.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData16.get(0).getORA());
			ordinatedList3.add(matchData16);

		}
		if (!matchData17.isEmpty()) {
			pdata3 = matchData17.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData17.get(0).getORA());
			ordinatedList3.add(matchData17);

		}
		if (!matchData18.isEmpty()) {
			pdata3 = matchData18.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData18.get(0).getORA());
			ordinatedList3.add(matchData18);

		}
		if (!matchData19.isEmpty()) {
			pdata3 = matchData19.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData19.get(0).getORA());
			ordinatedList3.add(matchData19);

		}
		if (!matchData20.isEmpty()) {
			pdata3 = matchData20.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData20.get(0).getORA());
			ordinatedList3.add(matchData20);

		}
		if (!matchData21.isEmpty()) {
			pdata3 = matchData21.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData21.get(0).getORA());
			ordinatedList3.add(matchData21);

		}
		if (!matchData22.isEmpty()) {
			pdata3 = matchData22.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData22.get(0).getORA());
			ordinatedList3.add(matchData22);

		}
		if (!matchData23.isEmpty()) {
			pdata3 = matchData23.get(0).getDATA_ORA().split(spl);
			ordinatedList3.add(pdata3[1]);
			// ordinatedList3.add(matchData23.get(0).getORA());
			ordinatedList3.add(matchData23);
		}
		return ordinatedList3;
	}

}
