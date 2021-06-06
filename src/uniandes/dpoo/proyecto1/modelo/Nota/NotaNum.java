package uniandes.dpoo.proyecto1.modelo.Nota;

public class NotaNum implements Nota {
    private final float nota;

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

    @Override
    public String toString() {
        return String.valueOf(notaNum());
    }
}
