package uniandes.dpoo.proyecto1.modelo.Nota;

public class NotaNum implements Nota {
    private float nota;

    public NotaNum(float nota){
        this.nota = nota;
    }

    @Override
    public boolean isNumeric() {
        return true;
    }

    @Override
    public float notaNum() {
        return nota;
    }

    @Override
    public calCual notaCual() {
        return null;
    }

    @Override
    public boolean aprobo() {
        return nota>=3;
    }
}
