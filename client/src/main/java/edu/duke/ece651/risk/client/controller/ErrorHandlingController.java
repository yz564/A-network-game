package edu.duke.ece651.risk.client.controller;

/**
 * Takes care of displaying an error message to the user interacting with the GUI.
 */
public interface ErrorHandlingController {
    /**
     * Set error message for the user of the GUI to see.
     *
     * @param error is the error that you want the user to see on the screen.
     */
    void setErrorMessage(String error);

    /**
     * Set error message to an empty string.
     */
    void clearErrorMessage();
}
