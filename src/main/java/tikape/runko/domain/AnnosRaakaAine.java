/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;


public class AnnosRaakaAine {
    private Integer id;
    private RaakaAine raakaAine;
    private Annos annos;
    private Integer jarjestys;
    private String maara;
    private String ohje;

    public AnnosRaakaAine(Integer id, RaakaAine raakaAine, Annos annos, Integer jarjestys, String maara, String ohje) {
        this.id = id;
        this.raakaAine = raakaAine;
        this.annos = annos;
        this.jarjestys = jarjestys;
        this.maara = maara;
        this.ohje = ohje;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RaakaAine getRaakaAine() {
        return raakaAine;
    }

    public void setRaakaAine(RaakaAine raakaAine) {
        this.raakaAine = raakaAine;
    }

    public Annos getAnnos() {
        return annos;
    }

    public void setAnnos(Annos Annos) {
        this.annos = Annos;
    }

    public Integer getJarjestys() {
        return jarjestys;
    }

    public void setJarjestys(Integer jarjestys) {
        this.jarjestys = jarjestys;
    }

    public String getMaara() {
        return maara;
    }

    public void setMaara(String maara) {
        this.maara = maara;
    }

    public String getOhje() {
        return ohje;
    }

    public void setOhje(String ohje) {
        this.ohje = ohje;
    }

    
    


    
    
    
    
    
}
