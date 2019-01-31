package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.PersonaM;
import servicios.ConsultaGob;

public class PersonaD extends Conexion {

    public boolean autenticar(PersonaM persona) {
        boolean autentica = false;
        try {
            String sql = "SELECT AUTENTICAR('" + persona.getUsrper() + "' ,'" + persona.getPswper() + "')";
            Statement s = this.conectar().prepareStatement(sql);
            ResultSet rs = s.executeQuery(sql);
            String rspta = null;
            while (rs.next()) {
                rspta = String.valueOf(rs.getObject(1));
            }
            s.close();
            rs.close();
            this.desconectar();
            if (!"0".equals(rspta) && (!rspta.isEmpty() || rspta != null)) {
                persona.setTipper(rspta);
                autentica = true;
            }
        } catch (Exception e) {
            System.out.println("error autenticacion()" + e.getMessage());
        }
        return autentica;
    }

    public void accionPersona(PersonaM persona, char tipo) throws Exception {
        try {
            String sql = null;
            persona.setCoddis(devolverCodigoDistrito(persona.getNomdis()));
            switch (tipo) {
                case '1':
                    if (ConsultaGob.existeDocumento(persona.getDocper())) {
                        JOptionPane.showMessageDialog(null, "El documento ingresado ya existe.");
                        return;
                    }
                    sql = "INSERT INTO PERSONA (NOMPER, APEPER, DOCPER, TLFPER, TIPPER, DISTRITO_CODDIS_DISPER, DIRPER, USRLOGPER, PSWLOGPER, ESTLOGPER) VALUES (?,?,?,?,?,?,?,?,?,?)";
                    if (!"C".equals(persona.getTipper())&&!"E".equals(persona.getTipper())) {
                        persona.setUsrper(persona.getDocper());
                        persona.setPswper("@" + persona.getDocper());
                        persona.setEstlog("A");
                    }
                    break;
                case '2':
                    sql = "UPDATE PERSONA SET NOMPER=?, APEPER=?, DOCPER=?, TLFPER=?, TIPPER=?, DISTRITO_CODDIS_DISPER=?, DIRPER=?, USRLOGPER=?, PSWLOGPER=?, ESTLOGPER=? WHERE DNIPER='" + persona.getDocper()+ "'";
                    if ("C".equals(persona.getTipper())||!"E".equals(persona.getTipper())) {
                        persona.setEstlog("I");
                    } else {
                        persona.setEstlog("A");
                    }
                    break;
                case '3':
                    sql = "DELETE FROM PERSONA WHERE DOCPER= ? ";
                default:
                    break;
            }
            System.out.println(persona.toString());
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            if (tipo!='3') {
                ps.setString(1, persona.getNomper());
                ps.setString(2, persona.getApeper());
                ps.setString(3, persona.getDocper());
                ps.setString(4, persona.getTelfper());
                ps.setString(5, persona.getTipper());
                ps.setInt(6, persona.getCoddis());
                ps.setString(7, persona.getDir());
                ps.setString(8, persona.getUsrper());
                ps.setString(9, persona.getPswper());
                ps.setString(10, persona.getEstlog());
            } else {
                ps.setString(1, persona.getDocper());
            }

            ps.executeUpdate();
            ps.close();
            this.desconectar();

        } catch (Exception e) {
            System.out.println("error AccionPer()");
            System.out.println(e.getMessage());
        }
    }

    public int devolverCodigoDistrito(String nombreDelDistrito) {
        int cod = 0;
        try {            
            String sql = "SELECT CODDIS FROM DISTRITO WHERE NOMDIS='" + nombreDelDistrito + "'";
            Statement s = this.conectar().prepareStatement(sql);
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                cod = Integer.valueOf(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println("error devolver codigos distriros PErsonaD"+e.getMessage());
        }
        return cod;
    }

    public DefaultTableModel listarPersonas(char tipper, boolean estado) throws Exception {

        DefaultTableModel tblTemp = null;
        try {
            String clmsTemp = "CÓDIGO,NOMBRES,APELLIDOS,DOCUMENTO,TELÉFONO,DEPARTAMENTO,PROVINCIA,DISTRITO,DIRECCION,TIPO";
            String sql;
            String inner = "INNER JOIN DISTRITO ON PERSONA.DISTRITO_CODDIS_DISPER=DISTRITO.CODDIS INNER JOIN PROVINCIA ON DISTRITO.PROVINCIA_CODPROV = PROVINCIA.CODPROV INNER JOIN DEPARTAMENTO ON PROVINCIA.DEPARTAMENTO_CODDEP = DEPARTAMENTO.CODDEP";

            sql = "SELECT PERSONA.CODPER, PERSONA.NOMPER, PERSONA.APEPER, PERSONA.DOCPER, PERSONA.TLFPER, DEPARTAMENTO.NOMDEP,PROVINCIA.NOMPROV,DISTRITO.NOMDIS,PERSONA.DIRPER,PERSONA.TIPPER";
            if (estado) {
                if (tipper == 'V' || tipper == 'I') {
                    clmsTemp += ",USUARIO,CONTRA";
                    sql += ", PERSONA.USRLOGPER, PERSONA.PSWLOGPER";
                }
                sql += " FROM PERSONA " + inner + " WHERE PERSONA.TIPPER = '" + tipper + "'";
            } else {
                sql += " FROM PERSONA " + inner;
            }
            String clms[] = clmsTemp.split(",");
            tblTemp = new DefaultTableModel(null, clms);
            Statement s = this.conectar().prepareStatement(sql);
            ResultSet rs = s.executeQuery(sql);
            String dts[] = new String[clms.length];

            while (rs.next()) {
                for (int i = 0; i < dts.length; i++) {
                    dts[i] = rs.getString(i + 1);
                }
                tblTemp.addRow(dts);
            }
            s.close();
            rs.close();
            this.desconectar();

        } catch (Exception e) {
            System.out.println(e.getMessage() + e.getMessage());
            System.out.println("Error ListarPer()");
        }
        return tblTemp;
    }

}
