package soa.web;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;


@Controller
public class SearchController {

	@Autowired
	  private ProducerTemplate producerTemplate;

	@RequestMapping("/")
    public String index() {
        return "index";
    }


    @RequestMapping(value="/search")
    @ResponseBody
    public Object search(@RequestParam("q") String q) {
        HashMap<String, Object> header = new HashMap<>();
        int index=q.indexOf("max:");
        if(index!=-1){
            int numIndex = index + 4;
            String num = q.substring(numIndex);
            int number = Integer.parseInt(num);
            q = q.substring(0, index-1);
            header.put("CamelTwitterCount", number);
        }
        header.put("CamelTwitterKeywords", q);
        return producerTemplate.requestBodyAndHeaders("direct:search", "", header);
    }
}