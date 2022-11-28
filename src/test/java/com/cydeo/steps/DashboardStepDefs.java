package com.cydeo.steps;

import com.cydeo.pages.DashBoardPage;
import com.cydeo.pages.LoginPage;
import com.cydeo.utility.BrowserUtil;
import com.cydeo.utility.DB_Util;
import io.cucumber.java.en.*;
import org.junit.Assert;


public class DashboardStepDefs
{
    String actualUserNumbers;
    String actualBookNumbers;
    String actualBorrowedBookNumbers;
    LoginPage loginPage=new LoginPage();
    DashBoardPage dashBoardPage=new DashBoardPage();


    @Given("the user logged in as {string}")
    public void the_user_logged_in_as(String user) {
        loginPage.login(user);
         BrowserUtil.waitFor(4);
    }
    @When("user gets all information from modules")
    public void user_gets_all_information_from_modules() {

        actualUserNumbers = dashBoardPage.usersNumber.getText();
        System.out.println("actualUserNumbers = " + actualUserNumbers);
        actualBookNumbers = dashBoardPage.booksNumber.getText();
        System.out.println("actualBookNumbers = " + actualBookNumbers);
        actualBorrowedBookNumbers = dashBoardPage.borrowedBooksNumber.getText();
        System.out.println("actualBorrowedBookNumbers = " + actualBorrowedBookNumbers);
    }


        @Then("the informations should be same with database")
        public void the_informations_should_be_same_with_database() {

        /*
        which one is actual / expected

        Expected = database
        Actual = UI
         */


            //Step 1 - Make connection
//
//            DB_Util.createConnection();

            //USERS

            //run query
            DB_Util.runQuery("select count(*) from users");

            //store data
            String expectedUsers = DB_Util.getFirstRowFirstColumn();

            //compare
            Assert.assertEquals(expectedUsers,actualUserNumbers);



            //BOOKS

            //Run Qury
            DB_Util.runQuery("select  count(*) from books");
            //store data
            String expectedBooks = DB_Util.getFirstRowFirstColumn();
            //Compare
            Assert.assertEquals(expectedBooks,actualBookNumbers);


            //BORROWED BOOKS
            //Run Qury
            DB_Util.runQuery("select count(*) from book_borrow\n" +
                    "where is_returned=0");
            //store data
            String expectedBorrowedBooks = DB_Util.getFirstRowFirstColumn();
            //Compare
            Assert.assertEquals(expectedBorrowedBooks,actualBorrowedBookNumbers);



            //close connection
//            DB_Util.destroy();
    }
}
