package modelo;

public class PersonaM {

    public int getCodper() {
        return codper;
    }

    public void setCodper(int codper) {
        this.codper = codper;
    }

    public String getNomper() {
        return nomper;
    }

    public void setNomper(String nomper) {
        this.nomper = nomper;
    }

    public String getApeper() {
        return apeper;
    }

    public void setApeper(String apeper) {
        this.apeper = apeper;
    }

    public String getDniper() {
        return dniper;
    }

    public void setDniper(String dniper) {
        this.dniper = dniper;
    }

    public String getTelfper() {
        return telfper;
    }

    public void setTelfper(String telfper) {
        this.telfper = telfper;
    }

    public String getTipper() {
        return tipper;
    }

    public void setTipper(String tipper) {
        this.tipper = tipper;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }
    public String getUsrper() {
        return usrper;
    }

    public void setUsrper(String usrper) {
        this.usrper = usrper;
    }

    public String getPswper() {
        return pswper;
    }

    public void setPswper(String pswper) {
        this.pswper = pswper;
    }
    

    public int getCoddis() {
        return coddis;
    }

    public void setCoddis(int coddis) {
        this.coddis = coddis;
    }    

    public String getEstlog() {
        return estlog;
    }

    public void setEstlog(String estlog) {
        this.estlog = estlog;
    }    

    public String getNomdis() {
        return nomdis;
    }

    public void setNomdis(String nomdis) {
        this.nomdis = nomdis;
    }
    
    
    private int codper, coddis;
    private String nomper, apeper, dniper, telfper, tipper;
    private String nomdis, dir;
    private String usrper, pswper, estlog;

    @Override
    public String toString() {
        return "[" + this.codper + "," + this.nomper + "," + this.apeper + "," + this.dniper + "," + this.telfper + "," + this.tipper + ","+this.nomdis+","+this.dir+","+this.usrper+","+this.pswper+"]";
    }
}
