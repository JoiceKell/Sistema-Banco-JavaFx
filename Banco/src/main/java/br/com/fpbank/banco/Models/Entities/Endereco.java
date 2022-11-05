package br.com.fpbank.banco.Models.Entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Endereco {

    // Atributos
    private final IntegerProperty cep;
    private final IntegerProperty num;
    private final StringProperty rua;
    private final StringProperty bairro;
    private final StringProperty cidade;
    private final StringProperty estado;
    private final StringProperty complemento;

    public Endereco(int cep, int num, String rua, String bairro, String cidade, String estado, String complemento) {
        this.cep = new SimpleIntegerProperty(this, "CEP", cep);
        this.num = new SimpleIntegerProperty(this, "Numero", num);
        this.rua = new SimpleStringProperty(this, "Rua", rua);
        this.bairro = new SimpleStringProperty(this, "Bairro", bairro);
        this.cidade = new SimpleStringProperty(this, "Cidade", cidade);
        this.estado = new SimpleStringProperty(this, "Estado", estado);
        this.complemento = new SimpleStringProperty(this, "Complemento", complemento);
    }

    public int getCep() {
        return cep.get();
    }

    public IntegerProperty cepProperty() {
        return cep;
    }

    public void setCep(int cep) {
        this.cep.set(cep);
    }

    public int getNum() {
        return num.get();
    }

    public IntegerProperty numProperty() {
        return num;
    }

    public void setNum(int num) {
        this.num.set(num);
    }

    public String getRua() {
        return rua.get();
    }

    public StringProperty ruaProperty() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua.set(rua);
    }

    public String getBairro() {
        return bairro.get();
    }

    public StringProperty bairroProperty() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro.set(bairro);
    }

    public String getCidade() {
        return cidade.get();
    }

    public StringProperty cidadeProperty() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade.set(cidade);
    }

    public String getEstado() {
        return estado.get();
    }

    public StringProperty estadoProperty() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado.set(estado);
    }

    public String getComplemento() {
        return complemento.get();
    }

    public StringProperty complementoProperty() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento.set(complemento);
    }

}
