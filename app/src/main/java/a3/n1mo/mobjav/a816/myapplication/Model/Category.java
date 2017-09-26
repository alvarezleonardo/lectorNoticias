package a3.n1mo.mobjav.a816.myapplication.Model;

import java.util.List;

/**
 * Created by leona on 24/10/2016.
 */

public class Category {
    private String name;
    private Integer codigo;

    public Category(String name, Integer codigo) {
        this.name = name;
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
