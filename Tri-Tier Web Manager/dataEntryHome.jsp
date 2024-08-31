<!doctype html>
<%
    String message = (String) session.getAttribute("message");
    if (message == null) message = " ";
%>
<html>
<head>
    <title>Enterprise System - Data Entry System</title>
    <style>
        body {
            background: black;
            text-align: center;
            font-family: Arial;
        }

        div {
            margin-top: 2em;
        }

        fieldset {
            padding-top: 2em;
            padding-bottom: 0em;
            margin-bottom: 0em;
            margin-top: 1em;
            height: 11em;
        }

        h1 {
            color: red;
            font-size: 28pt;
        }

        h2 {
            color: cyan;
            font-size: 24pt;
        }

        input {
            color: yellow;
            background: #665D1E;
            font-weight: bold;
            font-size: 16pt;
            margin-right: auto;
            margin-left: auto;
        }

        input[type="text"] {
            width: 98%;
        }

        input[type="submit"] {
            color: lime;
            margin-top: 0.75em;
            margin-bottom: 3em;
        }

        input[type="reset"] {
            color: red;
            margin-top: 0.75em;
        }

        legend {
            color: white;
            text-align: left;
        }

        p {
            color: black;
            font-size: 13pt;
        }

        table {
            font-family: Verdana;
            border: 3px solid yellow;
            margin-left: auto;
            margin-right: auto;
            width: 75%;
        }

        th, td {
            padding: 5px;
            border: 1px solid yellow;
            color: lightblue;
            width: 20%;
        }

        textarea.cmd {
            width: 150px;
            height: 17pt;
            color: yellow;
            background: #665D1E;
            font-size: 14pt;
            font-family: Arial;
        }

        .highlight {
            color: red;
        }

        .main {
            color: white;
        }

        #bl {
            color: #708090;
        }

        #results {
            background-color: blue;
            color: white;
        }
    </style>
    <!-- Connecting to a CDN to access the latest JQuery library - as of 11/10/2023, this was 3.7.1 -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script type="text/javascript">
        function eraseText() {
            // Next line illustrates a straight JavaScript technique
            // document.getElementById("cmd").innerHTML = "";
            // Next line illustrates a JQuery technique
            $("#cmd").html("");
        }
    </script>
    <script type="text/javascript">
        function eraseData() {
            // Next line illustrates a straight JavaScript technique
            // document.getElementById("data").innerHTML = "";
            // Next line illustrates a JQuery technique
            $("#data").remove();
        }
    </script>
</head>
<body>
    <h1>Welcome to the Enterprise System</h1>
    <h2>Data Entry Application</h2>
    <p class="main">You are connected to the Enterprise System database as a <span class="highlight">data-entry-level</span> user.<br />
        Enter the data values in a form below to add a new record to the corresponding database table.<br />
        <hr>
    </p>
    <form action="SuppliersInsertServlet" method="post">
        <!-- onsubmit="myFunction()" to fire JS script to retain form data -->
        <!-- <fieldset> element provides the encompassing box around the form -->
        <!-- <legend> element provides the inset "title" CSS can be used to style both -->
        <fieldset>
            <legend>Data Entry for Suppliers</legend>
            <table>
                <tr>
                    <th>snum</th><th>sname</th><th>status</th><th>city</th>
                </tr>
                <tr id="cmd">
                    <td><textarea class="cmd" name="snum"></textarea></td>
                    <td><textarea class="cmd" name="sname"></textarea></td>
                    <td><textarea class="cmd" name="status"></textarea></td>
                    <td><textarea class="cmd" name="city"></textarea></td>
                </tr>
            </table>
            <input type="submit" value="Enter Supplier Record Into Database" name="suppliersform" />&nbsp; &nbsp; &nbsp;
            <input type="reset" value="Clear Data and Results" onclick="javascript: eraseData();" />&nbsp; &nbsp; &nbsp;
        </fieldset>
    </form>

    <form action="PartInsertServlet" method="post">
        <!-- onsubmit="myFunction()" to fire JS script to retain form data -->
        <!-- <fieldset> element provides the encompassing box around the form -->
        <!-- <legend> element provides the inset "title" CSS can be used to style both -->
        <fieldset>
            <legend>Data Entry for Parts</legend>
            <table>
                <tr>
                    <th>pnum</th><th>pname</th><th>color</th><th>weight</th><th>city</th>
                </tr>
                <tr id="cmd">
                    <td><textarea class="cmd" name="pnum"></textarea></td>
                    <td><textarea class="cmd" name="pname"></textarea></td>
                    <td><textarea class="cmd" name="color"></textarea></td>
                    <td><textarea class="cmd" name="weight"></textarea></td>
                    <td><textarea class="cmd" name="city"></textarea></td>
                </tr>
            </table>
            <input type="submit" value="Enter Part Record Into Database" name="partsform" />&nbsp; &nbsp; &nbsp;
            <input type="reset" value="Clear Data and Results" onclick="javascript: eraseData();" />&nbsp; &nbsp; &nbsp;
        </fieldset>
    </form>

    <form action="JobInsertServlet" method="post">
        <!-- onsubmit="myFunction()" to fire JS script to retain form data -->
        <!-- <fieldset> element provides the encompassing box around the form -->
        <!-- <legend> element provides the inset "title" CSS can be used to style both -->
        <fieldset>
            <legend>Data Entry for Jobs</legend>
            <table>
                <tr>
                    <th>jnum</th><th>jname</th><th>numworkers</th><th>city</th>
                </tr>
                <tr id="cmd">
                    <td><textarea class="cmd" name="jnum"></textarea></td>
                    <td><textarea class="cmd" name="jname"></textarea></td>
                    <td><textarea class="cmd" name="numworkers"></textarea></td>
                    <td><textarea class="cmd" name="city"></textarea></td>
                </tr>
            </table>
            <input type="submit" value="Enter Job Record Into Database" name="jobsform" />&nbsp; &nbsp; &nbsp;
            <input type="reset" value="Clear Data and Results" onclick="javascript: eraseData();" />&nbsp; &nbsp; &nbsp;
        </fieldset>
    </form>

    <form action="ShipmentInsertServlet" method="post">
        <!-- onsubmit="myFunction()" to fire JS script to retain form data -->
        <!-- <fieldset> element provides the encompassing box around the form -->
        <!-- <legend> element provides the inset "title" CSS can be used to style both -->
        <fieldset>
            <legend>Data Entry for Shipments</legend>
            <table>
                <tr>
                    <th>snum</th><th>pnum</th><th>jnum</th><th>quantity</th>
                </tr>
                <tr id="cmd">
                    <td><textarea class="cmd" name="snum"></textarea></td>
                    <td><textarea class="cmd" name="pnum"></textarea></td>
                    <td><textarea class="cmd" name="jnum"></textarea></td>
                    <td><textarea class="cmd" name="quantity"></textarea></td>
                </tr>
            </table>
            <input type="submit" value="Enter Shipment Record Into Database" name="shipmentsform" />&nbsp; &nbsp; &nbsp;
            <input type="reset" value="Clear Data and Results" onclick="javascript: eraseData();" />&nbsp; &nbsp; &nbsp;
        </fieldset>
    </form>

    <!-- One form for each of the four different tables in the project4 DB -->
    <!-- Remainder of the page same as for other front-end pages -->
    <center>
        <p>
            <b class="main">Execution Results:</b><br>
            <table id="data" id="results">
                <%-- JSP expression to access servlet variable: message --%>
                <%=message%>
            </table>
        </p>
    </center>
</body>
</html>