package com.example.Worlds.Controllers;
import com.example.Worlds.Service.Model;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import org.tensorflow.SavedModelBundle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class MainController {
    Model model1 = new Model();
    SavedModelBundle model = SavedModelBundle.load("C:\\Users\\Данила\\Downloads\\Проба\\Стрым\\Стрым\\Wonders\\src\\main\\resources\\my_model", "serve");
    String path = "C:\\Photo\\load.jpg";

    @GetMapping("/")
    public String MainPage() {
        return "index.html";
    }

    @GetMapping(value = "/error", produces = MediaType.TEXT_HTML_VALUE)
    public String sendErrorPage() {
        System.out.println("error");
        return "index.html";
    }


    @GetMapping(value = "/Burj_khalifa", produces = MediaType.TEXT_HTML_VALUE)
    public String sendBurj_khalifaPage() {
        return "static/Burj_khalifa.html";
    }


    @GetMapping(value = "/Chichen_itza", produces = MediaType.TEXT_HTML_VALUE)
    public String sendChichen_itzaPage() {
        return "Chichen_itza.html";
    }


    @GetMapping(value = "/Christ_the_reedemer", produces = MediaType.TEXT_HTML_VALUE)
    public String sendChrist_the_reedemerPage() {
        return "Christ_the_remember.html";
    }


    @GetMapping(value = "/Eiffel_tower", produces = MediaType.TEXT_HTML_VALUE)
    public String sendEiffel_towerPage() {
        return "Eiffel_tower.html";
    }


    @GetMapping(value = "/Great_wall_of_china", produces = MediaType.TEXT_HTML_VALUE)
    public String sendGreat_wall_of_chinaPage() {
        return "Great_wall_of_china.html";
    }


    @GetMapping(value = "/Machu_pichu", produces = MediaType.TEXT_HTML_VALUE)
    public String sendMachu_pichuPage() {
        return "Machu_pichu.html";
    }


    @GetMapping(value = "/Pyramids_of_giza", produces = MediaType.TEXT_HTML_VALUE)
    public String sendPyramids_of_gizaPage() {
        return "Pyramids_of_giza.html";
    }


    @GetMapping(value = "/Roman_colosseum", produces = MediaType.TEXT_HTML_VALUE)
    public String sendRoman_colosseumPage() {
        return "roman_colosseum.html";
    }


    @GetMapping(value = "/Statue_of_liberty", produces = MediaType.TEXT_HTML_VALUE)
    public String sendStatue_of_libertyPage() {
        return "Statue_of_liberty.html";
    }


    @GetMapping(value = "/Stonehenge", produces = MediaType.TEXT_HTML_VALUE)
    public String sendStonehengePage() {
        return "stonehenge.html";
    }


    @GetMapping(value = "/Taj_mahal", produces = MediaType.TEXT_HTML_VALUE)
    public String sendTaj_mahalPage() {
        return "taj_mahal.html";
    }

    @GetMapping(value = "/Venezuela_angel_falls", produces = MediaType.TEXT_HTML_VALUE)
    public String sendVenezuela_angel_fallsPage() {
        return "venezuela_angel_falls.html";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public RedirectView Processing(@RequestParam("file") MultipartFile file) throws FileNotFoundException{
        if(!file.isEmpty()){
            try{
                file.transferTo(new File(path));
                String str = model1.getAnswer(path,model);
                System.out.println(str);
                switch (str) {
                    case "Burj_khalifa":
                        return new RedirectView("/Burj_khalifa");
                    case "Chichen_itza":
                        return new RedirectView("/Chichen_itza");
                    case "Christ_the_reedemer":
                        return new RedirectView("/Christ_the_reedemer");
                    case "Eiffel_tower":
                        return new RedirectView("/Eiffel_tower");
                    case "Great_wall_of_china":
                        return new RedirectView("/Great_wall_of_china");
                    case "Machu_pichu":
                        return new RedirectView("/Machu_pichu");
                    case "Pyramids_of_giza":
                        return new RedirectView("/Pyramids_of_giza");
                    case "Roman_colosseum":
                        return new RedirectView("/Roman_colosseum");
                    case "Statue_of_liberty":
                        return new RedirectView("/Statue_of_liberty");
                    case "Stonehenge":
                        return new RedirectView("/Stonehenge");
                    case "Taj_mahal":
                        return new RedirectView("/Taj_mahal");
                    case "Venezuela_angel_falls":
                        return new RedirectView("/Venezuela_angel_falls");
                    default:
                        return new RedirectView("/error");
                }
            }
            catch (Exception e){
                System.out.println(e);
                return new RedirectView("Eiffel_tower.html");
            }
        }
        else {
            System.out.println("Файл не найден");
            return new RedirectView("/errorNoFile");
        }
    }

}