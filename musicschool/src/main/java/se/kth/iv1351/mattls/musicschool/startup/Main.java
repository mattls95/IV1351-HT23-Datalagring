package se.kth.iv1351.mattls.musicschool.startup;

import se.kth.iv1351.mattls.musicschool.controller.ListController;
import se.kth.iv1351.mattls.musicschool.controller.RentController;
import se.kth.iv1351.mattls.musicschool.controller.TerminateController;
import se.kth.iv1351.mattls.musicschool.integration.MusicSchoolDBException;
import se.kth.iv1351.mattls.musicschool.view.MainMenuView;

public class Main {
    public static void main(String[] args) {
        try {
            MainMenuView mainMenu = new MainMenuView(new ListController(),new RentController(), new TerminateController());
        } catch (MusicSchoolDBException bdbe) {
            System.out.println("Could not connect to Bank db.");
            bdbe.printStackTrace();
        }
    }
}