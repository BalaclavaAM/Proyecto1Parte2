package uniandes.dpoo.proyecto1.modelo.Nota;

public interface  Nota {

    public boolean isNumeric ();
    public float notaNum();
    public calCual notaCual();
    public boolean aprobo();

    public class NotaCual implements Nota{
        private calCual nota;

        public NotaCual(calCual nota){
            this.nota = nota;
        }
        @Override
        public boolean isNumeric() {
            return false;
        }

        @Override
        public float notaNum() {
            return 0;
        }

        @Override
        public calCual notaCual() {
            return nota;
        }

        @Override
        public boolean aprobo() {
            return nota.equals(calCual.aprovado);
        }
    }


}
