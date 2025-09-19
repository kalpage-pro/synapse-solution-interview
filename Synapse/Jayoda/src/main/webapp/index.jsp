<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Lottery Results</title>
</head>
<body>
<h2>Check Lottery Results</h2>
<form action="LotteryServlet" method="GET">
    <label for="lottery">Select Lottery:</label>
    <select name="lottery" id="lottery">
        <option value="jayoda">Jayoda</option>
        <option value="jovisetha">Jovisetha</option>
    </select>
    <br><br>
    <button type="submit">Get Results</button>
</form>
</body>
</html>
