public interface A_InputSystem {

    // return User Input Object
    public A_UserInput getUserInput();
    // interpret UserInput and tell the User Object what to do
    public void command(A_GameObject userObject, A_UserInput userInput);

}
