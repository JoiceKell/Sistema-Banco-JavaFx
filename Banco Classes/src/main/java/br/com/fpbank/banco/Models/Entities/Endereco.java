package br.com.fpbank.banco.Models.Entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Endereco {

    // Atributos
    private final StringProperty cep;
    private final IntegerProperty num;
    private final StringProperty rua;
    private final StringProperty bairro;
    private final StringProperty cidade;
    private final StringProperty estado;
    private final StringProperty complemento;

    public Endereco(String rua, int num, String cep, String complemento, String cidade, String estado, String bairro) {
        this.cep = new SimpleStringProperty(this, "CEP", cep);
        this.num = new SimpleIntegerProperty(this, "Numero", num);
        this.rua = new SimpleStringProperty(this, "Rua", rua);
        this.bairro = new SimpleStringProperty(this, "Bairro", bairro);
        this.cidade = new SimpleStringProperty(this, "Cidade", cidade);
        this.estado = new SimpleStringProperty(this, "Estado", estado);
        this.complemento = new SimpleStringProperty(this, "Complemento", complemento);
    }

    public StringProperty cepProperty() {
        return cep;
    }

    public IntegerProperty numProperty() {
        return num;
    }

    public StringProperty ruaProperty() {
        return rua;
    }

    public StringProperty bairroProperty() {
        return bairro;
    }

    public StringProperty cidadeProperty() {
        return cidade;
    }

    public StringProperty estadoProperty() {
        return estado;
    }

    public StringProperty complementoProperty() {
        return complemento;
    }
}
