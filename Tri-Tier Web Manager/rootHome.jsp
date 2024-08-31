<!doctype html>
<%
    // start scriptlet
    String sqlStatement = (String) session.getAttribute("sqlStatement");
    if (sqlStatement == null) sqlStatement = "select * from suppliers";
    String message = (String) session.getAttribute("message");
    if (message == null) message = " ";
    // end scriptlet
%>
<html>
<head>
    <title>Enterprise System - Client</title>
    <style>
      body {
        background: black;
        text-align: center;
        font-family: Arial;
        color: white; /* Set default text color to white */
      }
      h1 {
        color: yellow;
        font-size: 28pt;
      }
      h2 {
        color: lawngreen;
        font-size: 24pt;
      }
      input {
        color: yellow;
        background: #665D1E;
        font-weight: bold;
        font-size: 16pt;
      }
      input[type="submit"] {
        color: lime;
      }
      input[type="reset"] {
        color: red;
      }
      p {
        color: white; /* Set text color to white */
        font-size: 13pt;
      }
      .main {
        color: white; /* Set text color to white */
      }
      .highlight {
        color: red; /* Set text color to red */
      }
      .resultTable {
        color: white;
      }
      .resultTable table {
        border-collapse: collapse;
        width: auto; /* Automatically fit column width */
      }
      .resultTable th, .resultTable td {
        border: 1px solid white;
        padding: 8px;
        font-size: 12px; /* Smaller text */
        width: auto; /* Automatically fit column width */
        height: auto; /* Automatically fit column height */
        white-space: nowrap; /* Prevent text from wrapping */
      }
      .resultTable th {
        background-color: red;
      }
      /* New style for error message */
      .error {
        color: red;
        font-weight: bold;
      }
    </style>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script type="text/javascript">
        function eraseText() {
            $("#cmd").html("");
        }

        function eraseData() {
            $(".resultTable table").html(""); // Clear only the result table content
        }
    </script>
</head>
<body>
    <h1>Welcome to the Enterprise System</h1>
    <h2>A Servlet/JSP-based Multi-tiered Enterprise Application Using A Tomcat Container</h2>
    <hr>
    <p class="main">You are connected to the Project 4 Enterprise System database as a <span class="highlight">root-level</span> user. <br />
        Please enter any SQL query or update command in the box below. <br /><br />
    </p>
    <form action="RootUserServlet" method="post">
        <textarea id="cmd" name="sqlStatement" cols=60 rows=8><%=sqlStatement%></textarea><br> <br>
        <input type="submit" value="Execute Command" /> &nbsp; &nbsp; &nbsp;
        <input type="reset" value="Reset Form" onclick="javascript:eraseText();" /> &nbsp; &nbsp; &nbsp;
        <input type="button" value="Clear Results" onclick="javascript:eraseData();" />
    </form>

    <p>All execution results will appear below this line.</p>

    <!-- Adjusted block to display execution results -->
    <div class="resultTable">
        <p class="resultText">
            <% if (message != null) { %>
                <!-- Display execution results -->
                <hr>
                <center>
                    <b class="main">Execution Results:</b><br>
                    <%= message %>
                </center>
            <% } %>
        </p>
    </div>
</body>
</html>