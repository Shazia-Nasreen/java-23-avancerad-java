public class SuperHuman extends Human implements SmartI,SnyggI{
  int iQ = DEFAULT_IQ;

    @Override
    public void getSmart() {
        iQ += 10;
        System.out.println(iQ);
    }
}
