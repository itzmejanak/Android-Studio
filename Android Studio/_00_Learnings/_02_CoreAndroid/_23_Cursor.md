Cursor
Cursor is the Interface which represents a 2 dimensional table of any database. When you try to retrieve some data using SELECT statement, then the database will first create a CURSOR object and return its reference to you.

In simple words, Cursor is a Interface whice returns collection of your query data. moveToFirst() is used to point the cursor position from where you want to get data from your cursor. There are methods moveToLast(), moveToNext(), moveToPrevious(), moveToPosition(position) by which you can iterate through your cursor by desired way.

For example, you have data in your Cursor

`
Lalit
Rithesh
Paresh
Chandra
`

moveToFirst() - If you use cursor.moveToFirst() then in this case it will point Lalit, as it is the first data in your cursor. To get the next data from cursor you can use moveToNext().

moveToLast() - This will point Chandra as the current data in your cursor. To get the previous data from cursor you can use moveToPrevious()

