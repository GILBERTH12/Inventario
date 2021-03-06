/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import forms.bean.BeanCaracteristicaPlantilla;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import util.ConeccionMySql;

/**
 *
 * @author Mario
 */
public class GestionCaracteristicaPlantilla extends ConeccionMySql {

    Connection cn = null;
    Statement st = null;
    private ArrayList<Object> GR_CARACTERISTICAPLANTILLA;

    public ArrayList<Object> IngresaCaracteristicaPlantilla(int idPlantillaDispositivo, String nombre, Boolean obligatorio, Boolean habilitar, Boolean transac, Connection tCn) {

        int mod = -99;
        ArrayList<Object> resultado = new ArrayList<Object>();
        PreparedStatement psInsertar = null;

        try {

            if (transac == false) { //si no es una transaccion busca una nueva conexion

                ArrayList<Object> resultad = new ArrayList<Object>();
                resultad = (ArrayList) getConection();

                if ((Boolean) resultad.get(0) == false) { // si no hubo error al obtener la conexion

                    cn = (Connection) resultad.get(1);

                } else { //si hubo error al obtener la conexion retorna el error para visualizar

                    resultado.add(true);
                    resultado.add(resultad.get(1));
                    return resultado;

                }

            } else { //si es una transaccion asigna la conexion utilizada

                cn = tCn;

            }

            psInsertar = cn.prepareStatement("insert into caracteristicaPlantilla (idCaracteristicaPlantilla, idPlantillaDispositivo, nombre, obligatorio, habilitar) values (null,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            psInsertar.setInt(1, idPlantillaDispositivo);
            psInsertar.setString(2, nombre);
            psInsertar.setBoolean(3, obligatorio);
            psInsertar.setBoolean(4, habilitar);
            psInsertar.executeUpdate(); // Se ejecuta la inserción.

            mod = psInsertar.getUpdateCount();

            if (transac == false) { // si no es una transaccion cierra la conexion

                cn.close();

            }

            resultado.add(false); //si no hubo un error asigna false
            resultado.add(mod); // y el numero de registros consultados

        } catch (Exception e) {

            resultado.add(true); //si hubo error asigna true
            resultado.add(e); //y asigna el error para retornar y visualizar

            if (cn != null){
                cn.rollback();
                cn.close();
            }
            
        } finally {

            return resultado;

        }

    }

    public ArrayList<Object> MostrarCaracteristicaPlantilla(Boolean transac, Connection tCn) {

        ArrayList<Object> resultado = new ArrayList<Object>();
        PreparedStatement psSelectConClave = null;

        try {

            GR_CARACTERISTICAPLANTILLA = new ArrayList<Object>();

            if (transac == false) { //si no es una transaccion busca una nueva conexion

                ArrayList<Object> resultad = new ArrayList<Object>();
                resultad = (ArrayList) getConection();

                if ((Boolean) resultad.get(0) == false) { // si no hubo error al obtener la conexion

                    cn = (Connection) resultad.get(1);

                } else { //si hubo error al obtener la conexion retorna el error para visualizar

                    resultado.add(true);
                    resultado.add(resultad.get(1));
                    return resultado;

                }

            } else { //si es una transaccion asigna la conexion utilizada

                cn = tCn;

            }

            psSelectConClave = cn.prepareStatement("SELECT  p.idCaracteristicaPlantilla, p.idPlantillaDispositivo, p.nombre, p.obligatorio, p.habilitar FROM caracteristicaPlantilla p");
            ResultSet rs = psSelectConClave.executeQuery();

            BeanCaracteristicaPlantilla bu;
            while (rs.next()) {
                bu = new BeanCaracteristicaPlantilla();

                bu.setIdCaracteristicaPlantilla(rs.getObject("p.idCaracteristicaPlantilla"));
                bu.setIdPlantillaDispositivo(rs.getObject("p.idPlantillaDispositivo"));
                bu.setNombre(rs.getObject("p.nombre"));
                bu.setObligatorio(rs.getObject("p.obligatorio"));
                bu.setHabilitar(rs.getObject("p.habilitar"));

                GR_CARACTERISTICAPLANTILLA.add(bu);

            }

            if (transac == false) { // si no es una transaccion cierra la conexion
                cn.close();
            }

            resultado.add(false); //si no hubo un error asigna false
            resultado.add(GR_CARACTERISTICAPLANTILLA); // y registros consultados

        } catch (Exception e) {

            resultado.add(true); //si hubo error asigna true
            resultado.add(e); //y asigna el error para retornar y visualizar

            if (cn != null){
                cn.rollback();
                cn.close();
            }
            
        } finally {

            return resultado;

        }

    }

    public ArrayList<Object> MostrarCaracteristicaPlantilla(int idCaracteristicaPlantilla, Boolean transac, Connection tCn) {

        ArrayList<Object> resultado = new ArrayList<Object>();
        PreparedStatement psSelectConClave = null;

        try {

            GR_CARACTERISTICAPLANTILLA = new ArrayList<Object>();

            if (transac == false) { //si no es una transaccion busca una nueva conexion

                ArrayList<Object> resultad = new ArrayList<Object>();
                resultad = (ArrayList) getConection();

                if ((Boolean) resultad.get(0) == false) { // si no hubo error al obtener la conexion

                    cn = (Connection) resultad.get(1);

                } else { //si hubo error al obtener la conexion retorna el error para visualizar

                    resultado.add(true);
                    resultado.add(resultad.get(1));
                    return resultado;

                }

            } else { //si es una transaccion asigna la conexion utilizada

                cn = tCn;

            }

            psSelectConClave = cn.prepareStatement("SELECT p.idCaracteristicaPlantilla, p.idPlantillaDispositivo, p.nombre, p.obligatorio, p.habilitar FROM caracteristicaPlantilla p WHERE p.idPlantillaDispositivo = ?");
            psSelectConClave.setInt(1, idCaracteristicaPlantilla);
            ResultSet rs = psSelectConClave.executeQuery();

            BeanCaracteristicaPlantilla bu;
            while (rs.next()) {
                bu = new BeanCaracteristicaPlantilla();

                bu.setIdCaracteristicaPlantilla(rs.getObject("p.idCaracteristicaPlantilla"));
                bu.setIdPlantillaDispositivo(rs.getObject("p.idPlantillaDispositivo"));
                bu.setNombre(rs.getObject("p.nombre"));
                bu.setObligatorio(rs.getObject("p.obligatorio"));
                bu.setHabilitar(rs.getObject("p.habilitar"));

                GR_CARACTERISTICAPLANTILLA.add(bu);

            }

            if (transac == false) { // si no es una transaccion cierra la conexion
                cn.close();
            }

            resultado.add(false); //si no hubo un error asigna false
            resultado.add(GR_CARACTERISTICAPLANTILLA); // y registros consultados

        } catch (Exception e) {

            resultado.add(true); //si hubo error asigna true
            resultado.add(e); //y asigna el error para retornar y visualizar

            if (cn != null){
                cn.rollback();
                cn.close();
            }
            
        } finally {

            return resultado;

        }

    }

    public ArrayList<Object> MostrarCaracteristicaPlantilla(int idCaracteristicaPlantilla, int idPlantillaDispositivo, Boolean transac, Connection tCn) {

        ArrayList<Object> resultado = new ArrayList<Object>();
        BeanCaracteristicaPlantilla bu = new BeanCaracteristicaPlantilla();
        PreparedStatement psSelectConClave = null;

        try {

            if (transac == false) { //si no es una transaccion busca una nueva conexion

                ArrayList<Object> resultad = new ArrayList<Object>();
                resultad = (ArrayList) getConection();

                if ((Boolean) resultad.get(0) == false) { // si no hubo error al obtener la conexion

                    cn = (Connection) resultad.get(1);

                } else { //si hubo error al obtener la conexion retorna el error para visualizar

                    resultado.add(true);
                    resultado.add(resultad.get(1));
                    return resultado;

                }

            } else { //si es una transaccion asigna la conexion utilizada

                cn = tCn;

            }

            psSelectConClave = cn.prepareStatement("SELECT p.idCaracteristicaPlantilla, p.idPlantillaDispositivo, p.nombre, p.obligatorio, p.habilitar FROM caracteristicaPlantilla p WHERE p.idCaracteristicaPlantilla = ? AND p.idPlantillaDispositivo = ?");
            psSelectConClave.setInt(1, idCaracteristicaPlantilla);
            psSelectConClave.setInt(2, idPlantillaDispositivo);
            ResultSet rs = psSelectConClave.executeQuery();

            while (rs.next()) {
                bu = new BeanCaracteristicaPlantilla();

                bu.setIdCaracteristicaPlantilla(rs.getObject("p.idCaracteristicaPlantilla"));
                bu.setIdPlantillaDispositivo(rs.getObject("p.idPlantillaDispositivo"));
                bu.setNombre(rs.getObject("p.nombre"));
                bu.setObligatorio(rs.getObject("p.obligatorio"));
                bu.setHabilitar(rs.getObject("p.habilitar"));

            }

            if (transac == false) { // si no es una transaccion cierra la conexion
                cn.close();
            }

            resultado.add(false); //si no hubo un error asigna false
            resultado.add(bu); // y registro consultado

        } catch (Exception e) {

            resultado.add(true); //si hubo error asigna true
            resultado.add(e); //y asigna el error para retornar y visualizar

            if (cn != null){
                cn.rollback();
                cn.close();
            }
            
        } finally {

            return resultado;

        }

    }

    public ArrayList<Object> ModificaCaracteristicaPlantilla(int idCaracteristicaPlantilla, int idPlantillaDispositivo, String nombre, Boolean obligatorio, Boolean habilitar, Boolean transac, Connection tCn) {

        int mod = -99;
        ArrayList<Object> resultado = new ArrayList<Object>();
        PreparedStatement psUpdate = null;

        try {

            if (transac == false) { //si no es una transaccion busca una nueva conexion

                ArrayList<Object> resultad = new ArrayList<Object>();
                resultad = (ArrayList) getConection();

                if ((Boolean) resultad.get(0) == false) { // si no hubo error al obtener la conexion

                    cn = (Connection) resultad.get(1);

                } else { //si hubo error al obtener la conexion retorna el error para visualizar

                    resultado.add(true);
                    resultado.add(resultad.get(1));
                    return resultado;

                }

            } else { //si es una transaccion asigna la conexion utilizada

                cn = tCn;

            }

            String query = "UPDATE caracteristicaPlantilla SET obligatorio = ?";
            query += ", habilitar = ?";
            query += " WHERE idCaracteristicaPlantilla = ? AND idPlantillaDispositivo = ? AND nombre = ?";
            psUpdate = cn.prepareStatement(query);
            psUpdate.setBoolean(1, obligatorio);
            psUpdate.setBoolean(2, habilitar);
            psUpdate.setInt(3, idCaracteristicaPlantilla);
            psUpdate.setInt(4, idPlantillaDispositivo);
            psUpdate.setString(5, nombre);
            psUpdate.executeUpdate();

            mod = psUpdate.getUpdateCount();

            if (transac == false) { // si no es una transaccion cierra la conexion
                cn.close();
            }

            resultado.add(false); //si no hubo un error asigna false
            resultado.add(mod); //  y el numero de registros consultados

        } catch (Exception e) {

            resultado.add(true); //si hubo error asigna true
            resultado.add(e); //y asigna el error para retornar y visualizar

            if (cn != null){
                cn.rollback();
                cn.close();
            }
            
        } finally {

            return resultado;

        }

    }

    public ArrayList<Object> EliminaCaracteristicaPlantilla(int idCaracteristicaPlantilla, int idPlantillaDispositivo, Boolean transac, Connection tCn) {

        int mod = -99;
        ArrayList<Object> resultado = new ArrayList<Object>();
        PreparedStatement psDelete = null;

        try {

            if (transac == false) { //si no es una transaccion busca una nueva conexion

                ArrayList<Object> resultad = new ArrayList<Object>();
                resultad = (ArrayList) getConection();

                if ((Boolean) resultad.get(0) == false) { // si no hubo error al obtener la conexion

                    cn = (Connection) resultad.get(1);

                } else { //si hubo error al obtener la conexion retorna el error para visualizar

                    resultado.add(true);
                    resultado.add(resultad.get(1));
                    return resultado;

                }

            } else { //si es una transaccion asigna la conexion utilizada

                cn = tCn;

            }

            psDelete = cn.prepareStatement("DELETE FROM caracteristicaPlantilla WHERE  idCaracteristicaPlantilla = ? AND idPlantillaDispositivo = ?");
            psDelete.setInt(1, idCaracteristicaPlantilla);
            psDelete.setInt(2, idPlantillaDispositivo);
            psDelete.executeUpdate();

            mod = psDelete.getUpdateCount();

            if (transac == false) { // si no es una transaccion cierra la conexion
                cn.close();
            }

            resultado.add(false); //si no hubo un error asigna false
            resultado.add(mod); //  y el numero de registros consultados

        } catch (Exception e) {

            resultado.add(true); //si hubo error asigna true
            resultado.add(e); //y asigna el error para retornar y visualizar

            if (cn != null){
                cn.rollback();
                cn.close();
            }
            
        } finally {

            return resultado;

        }

    }

    public ArrayList<Object> EliminaCaracteristicaPlantilla(int idPlantillaDispositivo, Boolean transac, Connection tCn) {

        int mod = -99;
        ArrayList<Object> resultado = new ArrayList<Object>();
        PreparedStatement psDelete = null;

        try {

            if (transac == false) { //si no es una transaccion busca una nueva conexion

                ArrayList<Object> resultad = new ArrayList<Object>();
                resultad = (ArrayList) getConection();

                if ((Boolean) resultad.get(0) == false) { // si no hubo error al obtener la conexion

                    cn = (Connection) resultad.get(1);

                } else { //si hubo error al obtener la conexion retorna el error para visualizar

                    resultado.add(true);
                    resultado.add(resultad.get(1));
                    return resultado;

                }

            } else { //si es una transaccion asigna la conexion utilizada

                cn = tCn;

            }

            psDelete = cn.prepareStatement("DELETE FROM caracteristicaPlantilla WHERE  idPlantillaDispositivo = ?");
            psDelete.setInt(1, idPlantillaDispositivo);
            psDelete.executeUpdate();

            mod = psDelete.getUpdateCount();

            if (transac == false) { // si no es una transaccion cierra la conexion
                cn.close();
            }

            resultado.add(false); //si no hubo un error asigna false
            resultado.add(mod); //  y el numero de registros consultados

        } catch (Exception e) {

            resultado.add(true); //si hubo error asigna true
            resultado.add(e); //y asigna el error para retornar y visualizar

            if (cn != null){
                cn.rollback();
                cn.close();
            }
            
        } finally {

            return resultado;

        }

    }

    public ArrayList<Object> commint(Connection tCn) {

        ArrayList<Object> resultado = new ArrayList<Object>();

        try {

            tCn.commit();
            resultado.add(false); //si no hubo un error asigna false

        } catch (Exception e) {

            resultado.add(true); //si hubo error asigna true
            resultado.add(e); //y asigna el error para retornar y visualizar

            if (cn != null){
                cn.rollback();
                cn.close();
            }
            
        } finally {

            return resultado;

        }

    }

    public ArrayList<Object> autoCommint(boolean valor, Connection tCn) {

        ArrayList<Object> resultado = new ArrayList<Object>();

        try {

            tCn.setAutoCommit(valor);
            resultado.add(false); //si no hubo un error asigna false

        } catch (Exception e) {

            resultado.add(true); //si hubo error asigna true
            resultado.add(e); //y asigna el error para retornar y visualizar

        } finally {

            return resultado;

        }

    }

    public ArrayList<Object> rollback(Connection tCn) {

        ArrayList<Object> resultado = new ArrayList<Object>();

        try {

            tCn.rollback();
            resultado.add(false); //si no hubo un error asigna false

        } catch (Exception e) {

            resultado.add(true); //si hubo error asigna true
            resultado.add(e); //y asigna el error para retornar y visualizar

        } finally {

            return resultado;

        }

    }

    public ArrayList<Object> ObtenerConexion() {

        ArrayList<Object> resultado = new ArrayList<Object>();

        try {

            ArrayList<Object> resultad = new ArrayList<Object>();
            resultad = (ArrayList) getConection();

            if ((Boolean) resultad.get(0) == false) { // si no hubo error al obtener la conexion

                cn = (Connection) resultad.get(1);
                resultado.add(false); //si no hubo un error asigna false
                resultado.add(cn); // y se envia la nueva conexion

            } else { //si hubo error al obtener la conexion retorna el error para visualizar

                resultado.add(true);
                resultado.add(resultad.get(1));
                return resultado;

            }

        } catch (Exception e) {

            resultado.add(true); //si hubo error asigna true
            resultado.add(e); //y asigna el error para retornar y visualizar

        } finally {

            return resultado;

        }

    }
//    public void MostrarTipoDocumentoFormulario(String IdTipoDocumento) {
//        try {
//            cn = getConection();
//
//            String query = "SELECT p.idTipoDocumento, p.nombre ";
//            query += "FROM tipoDocumento p ";
//            query += "WHERE  p.idTipoDocumento = " + IdTipoDocumento;
//
//
//            System.out.println("***********************************************");
//            System.out.println("*****       MostrarTipoDocumentoFormulario     *****");
//            System.out.println("***********************************************");
//
//            System.out.println(query);
//            st = cn.createStatement();
//            ResultSet rs = st.executeQuery(query);
//
//            BeanTipoDocumentoAnt bu;
//            while (rs.next()) {
//                bu = new BeanTipoDocumentoAnt();
//
//                setIdTipoDocumento(rs.getObject("p.idTipoDocumento"));
//                setNombre(rs.getObject("p.nombre"));
//
//            }
//
//            st.close();
//            cn.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//        }
//
//    }
//    private ArrayList<Object> GR_USUARIOS2;
//
//    public ArrayList<Object> MostrarUsuarios2(String aux, String aux2) {
//        try {
//            GR_USUARIOS2 = new ArrayList<Object>();
//            cn = getConection();
//
//            String query = "SELECT p.idUsuarios, p.tipoDocumentacion, p.documento, p.nombre1, p.nombre2, p.apellido1, ";
//            query += "p.apellido2, p.foto ";
//            query += "FROM usuarios p, cargo c ";
//            query += "WHERE p.id_cargo = c.id_cargo ";
//            if (aux.equals("nombre")) {
//                query += " AND p.nombres = '" + aux2 + "'";
//            } else if (aux.equals("cargo")) {
//                query += " AND p.id_cargo='" + aux2 + "'";
//            }
//
//            System.out.println("***********************************************");
//            System.out.println("*****       Cargando grilla  GR_USUARIOS  *****");
//            System.out.println("***********************************************");
//
//            System.out.println(query);
//            st = cn.createStatement();
//            ResultSet rs = st.executeQuery(query);
//
//            BeanUsuarios bu;
//            while (rs.next()) {
//                bu = new BeanUsuarios();
//
//                bu.setIdUsuarios(rs.getObject("p.idUsuarios"));
//                bu.setTipoDocumentacion(rs.getObject("p.tipoDocumentacion"));
//                bu.setDocumento(rs.getObject("p.documento"));
//                bu.setNombre1(rs.getObject("p.nombre1"));
//                bu.setNombre2(rs.getObject("p.nombre2"));
//                bu.setApellido1(rs.getObject("p.apellido1"));
//                bu.setApellido2(rs.getObject("p.apellido2"));
//                bu.setFoto(rs.getObject("p.foto"));
//
//                GR_USUARIOS2.add(bu);
//
//            }
//
//            st.close();
//            cn.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//        }
//        return GR_USUARIOS2;
//    }
//}
    private Object idCaracteristicaPlantilla;
    private Object idPlantillaDispositivo;
    private Object nombre;
    private Object habilitar;
    private Object obligatorio;

    public Object getIdCaracteristicaPlantilla() {
        return idCaracteristicaPlantilla;
    }

    public void setIdCaracteristicaPlantilla(Object idCaracteristicaPlantilla) {
        this.idCaracteristicaPlantilla = idCaracteristicaPlantilla;
    }

    public Object getIdPlantillaDispositivo() {
        return idPlantillaDispositivo;
    }

    public void setIdPlantillaDispositivo(Object idPlantillaDispositivo) {
        this.idPlantillaDispositivo = idPlantillaDispositivo;
    }

    public Object getNombre() {
        return nombre;
    }

    public void setNombre(Object nombre) {
        this.nombre = nombre;
    }

    public Object getHabilitar() {
        return habilitar;
    }

    public void setHabilitar(Object habilitar) {
        this.habilitar = habilitar;
    }

    public Object getObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(Object obligatorio) {
        this.obligatorio = obligatorio;
    }
}
