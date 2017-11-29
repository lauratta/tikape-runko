package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.OpiskelijaDao;
import tikape.runko.database.AnnosDao;
import tikape.runko.domain.*;
import tikape.runko.database.AnnosRaakaAineDao;
import tikape.runko.database.RaakaAineDao;

public class Main {

    public static void main(String[] args) throws Exception {
        //Database database = new Database("jdbc:sqlite:opiskelijat.db");
        Database database = new Database("jdbc:sqlite:tietokanta.db");
        
        //database.init();
        
        // hello!
        
        AnnosDao annosDao = new AnnosDao(database);
        AnnosRaakaAineDao annosRaakaAineDao = new AnnosRaakaAineDao(database);
        RaakaAineDao raakaAineDao = new RaakaAineDao(database);
        
        OpiskelijaDao opiskelijaDao = new OpiskelijaDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti", "tervehdys");

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        get("/smoothiet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("smoothiet", annosDao.findAll());

            return new ModelAndView(map, "smoothiet");
        }, new ThymeleafTemplateEngine());

        get("/smoothiet/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("smoothie", annosDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("smoothieIngredients", annosRaakaAineDao.findAllForAnnosKey(Integer.parseInt(req.params("id"))));
  

            return new ModelAndView(map, "smoothie");
        }, new ThymeleafTemplateEngine());
        
        get("/smoothiet_new", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("smoothiet", annosDao.findAll());
            map.put("ingredients", raakaAineDao.findAll());

            return new ModelAndView(map, "smoothiet_new");
        }, new ThymeleafTemplateEngine());
        
        post("/smoothiet_new", (req, res) -> {
            Annos annos = new Annos(-1, req.queryParams("name"));
            annosDao.saveAnnos(annos);
            res.redirect("/smoothiet_new");
            return "";
        });
        
        get("/raaka_aineet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("ingredients", raakaAineDao.findAll());

            return new ModelAndView(map, "raaka_aineet");
        }, new ThymeleafTemplateEngine());
        
        post("/raaka_aineet", (req, res) -> {
            RaakaAine ra = new RaakaAine(-1, req.queryParams("name"));
            raakaAineDao.saveRaakaAine(ra);
            res.redirect("/raaka_aineet");
            return "";
        });
        
        post("/raaka_aineet/:id", (req, res) -> {
            Integer ingredientId = Integer.parseInt(req.params(":id"));
            raakaAineDao.delete(ingredientId);
            res.redirect("/raaka_aineet");
            return "";
        });
        
        post("/smoothiet_new/:id", (req, res) -> {
            Integer annosId = Integer.parseInt(req.params(":id"));
            annosDao.delete(annosId);
            res.redirect("/smoothiet_new");
            return "";
        });
        
        post("/smoothiet_new/2", (req, res) -> {
            int haettavaAnnos = Integer.parseInt(req.queryParams("smoothieId"));
            Annos a = annosDao.findOne(haettavaAnnos);
            System.out.println("Annos_id: " + haettavaAnnos);
            
            int haettavaRaakaAine = Integer.parseInt(req.queryParams("ingredientId"));
            RaakaAine ra = raakaAineDao.findOne(haettavaRaakaAine);
            System.out.println("Ra_id: " + haettavaRaakaAine);
            
            int jarjestys = Integer.parseInt(req.queryParams("order"));
            
            AnnosRaakaAine ara = new AnnosRaakaAine(-1, ra, a, jarjestys, req.queryParams("quantity"), req.queryParams("instructions"));
            
            annosRaakaAineDao.saveAnnosRaakaAine(ara);
            res.redirect("/smoothiet_new");
            return "";
        });
        
        
        get("/opiskelijat", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("opiskelijat", opiskelijaDao.findAll());

            return new ModelAndView(map, "opiskelijat");
        }, new ThymeleafTemplateEngine());

        get("/opiskelijat/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("opiskelija", opiskelijaDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "opiskelija");
        }, new ThymeleafTemplateEngine());
    }
}
