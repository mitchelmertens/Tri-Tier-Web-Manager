<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String message = (String) session.getAttribute("message");
    if (message == null) message = " ";
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Fall 2023 Project Enterprise System</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            background-color: black;
            color: white;
            font-size: 130%;
            /* Increase font size by 30% */
        }

        .yellow-text {
            color: yellow;
            text-align: center;
            margin-top: 1%;
        }

        .green-text {
            color: green;
            text-align: center;
        }

        .grey-line {
            border-bottom: 1px solid grey;
            margin-top: 1%;
        }

        .grey-line2 {
            border-bottom: 1px solid grey;
        }

        .grey-line-text {
            text-align: center;
            margin: 1% 10%;
            font-size: 80%;
        }

        .yellow-text-entry {
            color: yellow;
        }

        .red-text-entry {
            color: red;
        }

        .no-border {
            border: none;
        }

        .grey-rectangle {
            background-color: grey;
            padding: 20px;
            /* Added padding for better spacing */
            margin: 1% 1% 0 1%;
            /* Top, right, bottom, left */
        }

        .option-text {
            color: blue;
            font-size: 120%;
            /* Increase font size for the five selections */
            margin-bottom: 10px;
            /* Adjusted spacing between selections */
            display: inline-block;
            /* Display each option inline */
        }

        .black-text {
            color: black;
        }

        .button-container {
            text-align: center;
            margin-top: 1%;
        }

        .execute-button {
            background-color: darkslategrey;
            color: green;
            /* Green text color */
            padding: 10px 20px;
            margin-right: 10px;
            border: none;
            cursor: pointer;
            transition: background-color 0.3s, box-shadow 0.3s, border-radius 0.3s;
            /* Added transition for rounded edges */
            border-radius: 8px;
            /* Rounded edges */
        }

        .execute-button:active {
            background-color: darkgrey;
            box-shadow: 0 2px 0 rgba(0, 0, 0, 0.2);
        }

        .clear-button {
            background-color: darkslategrey;
            color: red;
            /* Red text color */
            padding: 10px 20px;
            border: none;
            cursor: pointer;
            transition: background-color 0.3s, box-shadow 0.3s, border-radius 0.3s;
            /* Added transition for rounded edges */
            border-radius: 8px;
            /* Rounded edges */
        }

        .clear-button:active {
            background-color: darkgrey;
            box-shadow: 0 2px 0 rgba(0, 0, 0, 0.2);
        }

        .execution-results-text {
            text-align: center;
            margin: 1% 10%;
            font-size: 80%;
        }

        .grey-line3 {
            border-bottom: 1px solid grey;
            margin-top: 1%;
        }

        .execution-results-header {
            text-align: center;
            margin: 1% 10%;
        }
    </style>
</head>
<body>
    <div class="yellow-text">
        <h1>Welcome to the Project Enterprise System</h1>
    </div>

    <div class="green-text">
        <h2>A Servlet/JSP-based Multi-tiered Enterprise Application Using A Tomcat Container</h2>
    </div>

    <div class="grey-line"></div>

    <div class="grey-line-text no-border">
        <p>You are connected to the Project Enterprise System database as an <span class="red-text-entry">accountant-level</span> user. Please select the operation you would like to perform from the list below.</p>
    </div>

    <div class="grey-rectangle">
        <form action="AccountantUserServlet" method="post">
            <input type="radio" name="cmd" id="1" value="1">
            <label for="option1" class="option-text">Get The Maximum Value Of All Suppliers <span class="black-text">(Returns a maximum value)</span></label><br>

            <input type="radio" name="cmd" id="2" value="2">
            <label for="option2" class="option-text">Get The Total Weight Of All Parts <span class="black-text">(Returns a sum)</span></label><br>

            <input type="radio" name="cmd" id="3" value="3">
            <label for="option3" class="option-text">Get The Total Number of Shipments <span class="black-text">(Returns the current number of shipments in total)</span></label><br>

            <input type="radio" name="cmd" id="4" value="4">
            <label for="option4" class="option-text">Get The Name And Number Of Workers Of The Job With The Most Workers <span class="black-text">(Returns two values)</span></label><br>

            <input type="radio" name="cmd" id="5" value="5">
            <label for="option5" class="option-text">List The Name And Status Of Every Supplier <span class="black-text">(Returns a list of supplier names with status)</span></label><br>

            <!-- Buttons for Execute Command and Clear Results -->
            <div class="button-container">
                <button class="execute-button" type="submit">Execute Command</button>
                <button class="clear-button" type="button">Clear Results</button>
            </div>
        </form>
    </div>

    <!-- New elements below the grey rectangle -->
    <div class="execution-results-text no-border">
        <p>All execution results will appear below this line.</p>
    </div>

    <div class="grey-line3"></div>

    <div class="execution-results-header">
        <center>
            <p>
                <b class="main">Execution Results:</b><br>
                <table id="data" id="results">
                    <tbody style="color: black;">
                   <%=session.getAttribute("htmlRows")%>
                </table>
            </p>
        </center>
    </div>

</body>
</html>
