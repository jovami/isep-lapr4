@echo off

set IP=%1
set PORT=%2


java -jar base.app.board.client\target\base.app.board.client-1.4.0-SNAPSHOT.jar %IP% 9000

